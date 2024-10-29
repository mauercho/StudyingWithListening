package com.ssafy.domain.user.model.entity;

import static lombok.AccessLevel.*;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Embeddable
public class StudyTimeId implements Serializable {

	private int timeCalType;
	private Long userId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		StudyTimeId that = (StudyTimeId)o;
		return timeCalType == that.timeCalType && Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(timeCalType, userId);
	}
}
