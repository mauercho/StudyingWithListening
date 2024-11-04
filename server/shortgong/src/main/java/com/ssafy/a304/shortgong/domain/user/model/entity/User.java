package com.ssafy.a304.shortgong.domain.user.model.entity;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;
import com.ssafy.a304.shortgong.domain.uploadContent.model.entity.UploadContent;
import com.ssafy.a304.shortgong.global.model.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "user")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id", columnDefinition = "BIGINT(20)")
	private Long id;

	@Column(unique = true, columnDefinition = "VARCHAR(64)")
	private String nickname;

	@Column(unique = true, columnDefinition = "VARCHAR(64)")
	private String username;

	@Column(name = "password_hash", columnDefinition = "VARCHAR(64)", nullable = false)
	private String passwordHash;

	@Column(unique = true, columnDefinition = "VARCHAR(64)")
	private String profileFileName;

	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true, fetch = LAZY)
	private List<UploadContent> uploadContents = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "writer", cascade = ALL, orphanRemoval = true, fetch = LAZY)
	private List<Summary> summaries = new ArrayList<>();

}
