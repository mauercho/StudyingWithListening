package com.ssafy.domain.lecture.model.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredLectureId implements Serializable {

	private Long userId;
	private Long lectureId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof RegisteredLectureId))
			return false;
		RegisteredLectureId that = (RegisteredLectureId)o;
		return Objects.equals(userId, that.userId) && Objects.equals(lectureId, that.lectureId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, lectureId);
	}

}
