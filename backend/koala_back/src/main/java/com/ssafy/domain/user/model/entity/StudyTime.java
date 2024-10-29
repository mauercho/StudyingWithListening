package com.ssafy.domain.user.model.entity;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@Table(name = "study_time")
@IdClass(StudyTimeId.class)
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class StudyTime implements Serializable {

	@Id
	@Column(name = "time_cal_type")
	private Integer timeCalType;

	@Id
	@Column(name = "user_id")
	private Long userId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	@Setter
	@Builder.Default
	@Column(name = "talk_time")
	private Integer talkTime = 0;

	@Setter
	@Builder.Default
	@Column(name = "sentence_num")
	private Integer sentenceNum = 0;

	@Setter
	@Builder.Default
	@Column(name = "lecture_num")
	private Integer lectureNum = 0;

	public void increaseTalkTime(Integer talkTime) {
		this.talkTime += talkTime;
	}

	public void increaseSentenceNum(Integer sentenceNum) {
		this.sentenceNum += sentenceNum;
	}

	public void increaseLectureNum() {
		this.lectureNum += 1;
	}
}
