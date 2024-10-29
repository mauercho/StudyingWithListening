package com.ssafy.domain.lecture.model.entity;

import static jakarta.persistence.FetchType.*;

import java.io.Serializable;

import com.ssafy.domain.user.model.entity.User;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "registered_lecture")
public class RegisteredLecture implements Serializable {

	@EmbeddedId
	private RegisteredLectureId id;

	@ManyToOne(fetch = LAZY)
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = LAZY)
	@MapsId("lectureId")
	@JoinColumn(name = "lecture_id")
	private Lecture lecture;

}
