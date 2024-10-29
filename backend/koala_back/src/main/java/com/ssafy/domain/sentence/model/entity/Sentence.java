package com.ssafy.domain.sentence.model.entity;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.domain.review.model.entity.ReviewSentence;
import com.ssafy.domain.user.model.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "sentences")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class Sentence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sentence_id")
	private Long sentenceId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "sentence_text")
	private String sentenceText;

	@Column(name = "topic_category")
	String topicCategory;

	@Column(name = "sentence_length")
	private Integer sentenceLength;

	@Builder.Default
	@Column(name = "sentence_created_at")
	private LocalDateTime sentenceCreatedAt = LocalDateTime.now();

	@OneToMany(mappedBy = "sentence", cascade = ALL, orphanRemoval = true, fetch = LAZY)
	private List<LectureSentence> lectureSentences = new ArrayList<>();

	@OneToMany(mappedBy = "sentence", cascade = ALL, orphanRemoval = true, fetch = LAZY)
	private List<ReviewSentence> reviewSentences = new ArrayList<>();
}
