package com.ssafy.a304.shortgong.domain.summary.model.entity;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.a304.shortgong.domain.index.model.entity.Index;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.uploadContent.model.entity.UploadContent;
import com.ssafy.a304.shortgong.domain.user.model.entity.User;
import com.ssafy.a304.shortgong.global.model.constant.ClovaVoice;
import com.ssafy.a304.shortgong.global.model.entity.BaseEntity;
import com.ssafy.a304.shortgong.global.util.RandomUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "summary")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class Summary extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "summary_id", columnDefinition = "BIGINT(20)")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "writer_id", referencedColumnName = "user_id", nullable = false)
	private User writer;

	@OneToOne(fetch = EAGER)
	@JoinColumn(name = "upload_content_id", nullable = false)
	private UploadContent uploadContent;

	@Column(name = "summary_title", columnDefinition = "VARCHAR(64)")
	private String title;

	@Column(name = "folder_name", columnDefinition = "VARCHAR(64)")
	private String folderName;

	@Column(name = "clova_voice", columnDefinition = "VARCHAR(64)")
	@Enumerated(EnumType.STRING)
	private ClovaVoice clovaVoice;

	@Builder.Default
	@OneToMany(mappedBy = "summary", cascade = ALL, orphanRemoval = true, fetch = LAZY)
	private List<Sentence> sentences = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "summary", cascade = ALL, orphanRemoval = true, fetch = LAZY)
	private List<Index> indexes = new ArrayList<>();

	// TODO : Class 단위의 Builder 지우기 고려
	@Builder
	public Summary(User writer, UploadContent uploadContent) {

		this.writer = writer;
		this.uploadContent = uploadContent;
	}

	public void updateTitle(String title) {

		this.title = title;
	}

	@PrePersist
	private void prePersist() {

		if (this.folderName == null || this.folderName.isEmpty()) {
			this.folderName = RandomUtil.generateUUID(); // 기본 폴더 이름을 설정하거나, 원하는 로직으로 변경 가능
		}
	}
}
