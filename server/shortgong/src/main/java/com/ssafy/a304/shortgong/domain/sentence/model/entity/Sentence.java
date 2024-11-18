package com.ssafy.a304.shortgong.domain.sentence.model.entity;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.a304.shortgong.domain.index.model.entity.Index;
import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.TwoAnswerResponse;
import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;
import com.ssafy.a304.shortgong.global.model.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Table(
	name = "sentence",
	uniqueConstraints = {
		@UniqueConstraint(name = "UniqueSummaryAndQuestion", columnNames = {"summary_id", "sentence_question"}),
		@UniqueConstraint(name = "UniqueSummaryAndPoint", columnNames = {"summary_id", "sentence_point"})})
public class Sentence extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "sentence_id", columnDefinition = "BIGINT(20)")
	private Long id;

	@ManyToOne(fetch = EAGER) // TODO : Lazy로 변경해야 함?
	@JoinColumn(name = "summary_id", nullable = false)
	private Summary summary;

	@ManyToOne(fetch = EAGER) // TODO : Lazy로 변경해야 함?
	@JoinColumn(name = "sentence_title_id", nullable = false)
	private SentenceTitle sentenceTitle;

	@Column(name = "sentence_point", nullable = false, columnDefinition = "VARCHAR(256)")
	private String point;

	@Column(name = "sentence_question", nullable = false, columnDefinition = "VARCHAR(256)")
	private String question;

	@Column(name = "sentence_content_normal", columnDefinition = "TEXT")
	private String sentenceContentNormal;

	@Column(name = "sentence_content_simple", columnDefinition = "TEXT")
	private String sentenceContentSimple;

	@Column(name = "question_file_name", columnDefinition = "TEXT")
	private String questionFileName;

	@Column(name = "normal_voice_file_name", columnDefinition = "VARCHAR(256)")
	private String normalVoiceFileName;

	@Column(name = "simple_voice_file_name", columnDefinition = "VARCHAR(256)")
	private String simpleVoiceFileName;

	@Column(name = "sentence_order", nullable = false)
	private Integer order;

	@Column(name = "open_status", nullable = false)
	private Boolean openStatus = true;

	@OneToMany(mappedBy = "sentence", cascade = ALL, orphanRemoval = true, fetch = LAZY)
	private List<Index> indexes = new ArrayList<>();

	@Builder
	public Sentence(Summary summary, SentenceTitle sentenceTitle, String point, String question, int order) {

		this.summary = summary;
		this.sentenceTitle = sentenceTitle;
		this.point = point;
		this.question = question;
		this.order = order;
	}

	public void updateThreeAnswerResponse(TwoAnswerResponse twoAnswerResponse) {

		if (twoAnswerResponse.getSimpleAnswer() != null) {
			this.sentenceContentSimple = twoAnswerResponse.getSimpleAnswer();
		}
		if (twoAnswerResponse.getNormalAnswer() != null) {
			this.sentenceContentNormal = twoAnswerResponse.getNormalAnswer();
		}
	}

	public void updateQuestionVoiceFileName(String questionFileName) {

		this.questionFileName = questionFileName;
	}

	public void updateNormalVoiceFileName(String normalVoiceFileName) {

		this.normalVoiceFileName = normalVoiceFileName;
	}

	public void updateSimpleVoiceFileName(String simpleVoiceFileName) {

		this.simpleVoiceFileName = simpleVoiceFileName;
	}

}

