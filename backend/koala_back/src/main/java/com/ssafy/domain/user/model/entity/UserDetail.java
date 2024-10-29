package com.ssafy.domain.user.model.entity;

import static lombok.AccessLevel.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "user_details")
@NoArgsConstructor(access = PROTECTED)
public class UserDetail {

	@Id
	@Column(name = "user_id", nullable = false)
	private Long userId;

	@OneToOne
	@MapsId
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "user_introduce")
	private String userIntroduce;

	@Column(name = "org_name")
	private String orgName;

}
