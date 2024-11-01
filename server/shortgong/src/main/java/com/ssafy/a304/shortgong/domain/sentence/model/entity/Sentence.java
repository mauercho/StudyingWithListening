package com.ssafy.a304.shortgong.domain.sentence.model.entity;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.a304.shortgong.domain.index.model.entity.Index;
import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;
import com.ssafy.a304.shortgong.global.entity.BaseEntity;

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

	@Column(name = "sentence_content", nullable = false)
	private String sentenceContent;

	@Column(name = "voice_file_name", columnDefinition = "VARCHAR(64)")
	private String voiceFileName;

	@Column(name = "order", nullable = false)
	private Integer order;

	@Builder.Default
	@Column(name = "open_status", nullable = false)
	private Boolean openStatus = true;

	@Builder.Default
	@OneToMany(mappedBy = "sentence", cascade = ALL, orphanRemoval = true, fetch = LAZY)
	private List<Index> indexes = new ArrayList<>();

}
