package com.ssafy.domain.user.model.entity;

import static com.ssafy.domain.user.model.validation.UserValidation.*;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ssafy.domain.board.model.entity.Board;
import com.ssafy.domain.board.model.entity.BoardComment;
import com.ssafy.domain.koala.model.entity.Koala;
import com.ssafy.domain.lecture.model.entity.Lecture;
import com.ssafy.domain.lecture.model.entity.LectureNote;
import com.ssafy.domain.lecture.model.entity.RegisteredLecture;
import com.ssafy.domain.review.model.entity.ReviewSentence;
import com.ssafy.domain.sentence.model.entity.Sentence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@Table(name = "users")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class User implements UserDetails {
	// Spring Security는 인증 및 권한 부여 과정에서 UserDetails 객체를 사용
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "login_id", nullable = false)
	private String loginId;

	@Column(name = "password", nullable = false)
	private String password;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "auth_id", nullable = false)
	private Auth auth;

	@Column(name = "name", nullable = false)
	private String name;

	@Setter
	@Column(name = "nickname", nullable = false)
	private String nickname;

	@Builder.Default
	@Column(name = "leaves")
	private Integer leaves = 0;

	@Setter
	@Builder.Default
	@Column(name = "user_exp")
	private Long userExp = 0L;

	@Builder.Default
	@Column(name = "user_level")
	private Integer userLevel = 1;

	@Builder.Default
	@Column(name = "user_created_at")
	private LocalDateTime userCreatedAt = LocalDateTime.now();

	@Setter
	@Column(name = "refresh_token")
	private String refreshToken;

	@Builder.Default
	@OneToMany(mappedBy = "teacher", cascade = ALL, fetch = LAZY, orphanRemoval = true)
	private List<Lecture> lectures = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = ALL, fetch = LAZY, orphanRemoval = true)
	private List<Sentence> sentences = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = ALL, fetch = LAZY, orphanRemoval = true)
	private List<ReviewSentence> reviewSentences = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = ALL, fetch = LAZY, orphanRemoval = true)
	private List<StudyTime> studyTimes = new ArrayList<>();

	@OneToOne(mappedBy = "user", cascade = ALL, fetch = LAZY, orphanRemoval = true)
	private UserDetail userDetail;

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = ALL, fetch = LAZY, orphanRemoval = true)
	private List<RegisteredLecture> registeredLectures = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = ALL, fetch = LAZY, orphanRemoval = true)
	private List<LectureNote> lectureNotes = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = ALL, fetch = LAZY, orphanRemoval = true)
	private List<Board> boards = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = ALL, fetch = LAZY, orphanRemoval = true)
	private List<BoardComment> boardComments = new ArrayList<>();

	@OneToOne(mappedBy = "user", cascade = ALL, fetch = LAZY, orphanRemoval = true)
	private Ranking ranking;

	@OneToMany(mappedBy = "user", cascade = ALL, fetch = LAZY, orphanRemoval = true)
	private List<Koala> koalas;

	@OneToOne(mappedBy = "user", cascade = ALL, fetch = LAZY, orphanRemoval = true)
	private AiTalkLog aiTalkLog;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 알아보기 쉽게 일반적으로 이름 반환한다고 함
		return List.of(new SimpleGrantedAuthority(auth.getAuthName()));
	}

	@Override
	public String getUsername() {
		// 유일한 값
		return this.loginId;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void increaseUserLevel() {
		this.userLevel++;
	}

	public void increaseUserExp() {
		this.userExp++;
		checkUserExp();
	}

	public void increaseUserExp(Integer value) {
		this.userExp += value;
		checkUserExp();
	}

	public void checkUserExp() {
		if (this.userExp >= 100) {
			increaseUserLevel();
			this.userExp -= 100L;
		}
	}

	public void increaseUserLeaves(Integer value) {
		this.leaves += value;
	}

	public void decreaseLeaves() {
		validateDecreaseLeaves(this.leaves);
		this.leaves--;
	}

}
