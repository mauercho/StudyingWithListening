package com.ssafy.domain.sentence.model.entity;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import com.ssafy.domain.lecture.model.entity.Lecture;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "lecture_sentences")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class LectureSentence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lecture_sentence_id")
	private Long lectureSentenceId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "lecture_id", nullable = false)
	private Lecture lecture;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "sentence_id", nullable = false)
	private Sentence sentence;

}
