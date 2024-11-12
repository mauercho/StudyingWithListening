package com.ssafy.a304.shortgong.domain.sentence.model.entity;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.a304.shortgong.domain.index.model.entity.Index;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@Table(name = "sentence")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class Sentence extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "sentence_id", columnDefinition = "BIGINT(20)")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "summary_id", nullable = false)
	private Summary summary;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "sentence_title_id", nullable = false)
	private SentenceTitle sentenceTitle;

	@Column(name = "sentence_point", nullable = false, columnDefinition = "TEXT")
	private String sentencePoint;

	@Setter
	@Column(name = "sentence_content_normal", nullable = false, columnDefinition = "TEXT")
	private String sentenceContentNormal;

	@Setter
	@Column(name = "sentence_content_simple", nullable = false, columnDefinition = "TEXT")
	private String sentenceContentSimple;

	@Setter
	@Column(name = "sentence_content_detail", nullable = false, columnDefinition = "TEXT")
	private String sentenceContentDetail;

	@Column(name = "normal_voice_file_name", columnDefinition = "VARCHAR(256)")
	private String normalVoiceFileName;

	@Column(name = "detail_voice_file_name", columnDefinition = "VARCHAR(256)")
	private String detailVoiceFileName;

	@Column(name = "simple_voice_file_name", columnDefinition = "VARCHAR(256)")
	private String simpleVoiceFileName;

	@Column(name = "sentence_order", nullable = false)
	private Integer order;

	@Builder.Default
	@Column(name = "open_status", nullable = false)
	private Boolean openStatus = true;

	@Builder.Default
	@OneToMany(mappedBy = "sentence", cascade = ALL, orphanRemoval = true, fetch = LAZY)
	private List<Index> indexes = new ArrayList<>();

	public void updateVoiceFileName(String voiceFileName) {

		this.normalVoiceFileName = voiceFileName;
	}

	public void updateOpenStatus(Boolean openStatus) {

		this.openStatus = openStatus;
	}

}
