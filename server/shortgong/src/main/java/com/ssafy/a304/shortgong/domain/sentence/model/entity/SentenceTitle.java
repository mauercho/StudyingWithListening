package com.ssafy.a304.shortgong.domain.sentence.model.entity;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "sentence_title", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)

public class SentenceTitle {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "sentence_title_id", columnDefinition = "BIGINT(20)")
	private Long id;

	@Column(name = "sentence_title_name", columnDefinition = "VARCHAR(128)", unique = true)
	private String name;
}
