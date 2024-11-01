package com.ssafy.a304.shortgong.domain.index.model.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;
import com.ssafy.a304.shortgong.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "index")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class Index extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "index_id", columnDefinition = "BIGINT(20)")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "summary_id", nullable = false)
	private Summary summary;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "sentence_id", nullable = false)
	private Sentence sentence;

	@Column(name = "title_level", nullable = false)
	private Boolean titleLevel;

	@Column(name = "index_title", columnDefinition = "VARCHAR(64)", nullable = false)
	private String indexTitle;

}
