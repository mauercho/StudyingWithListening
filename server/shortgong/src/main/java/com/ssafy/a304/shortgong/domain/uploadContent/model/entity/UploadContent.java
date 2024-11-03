package com.ssafy.a304.shortgong.domain.uploadContent.model.entity;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;
import com.ssafy.a304.shortgong.domain.user.model.entity.User;
import com.ssafy.a304.shortgong.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "upload_content")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class UploadContent extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "upload_content_id", columnDefinition = "BIGINT(20)")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "file_name", columnDefinition = "VARCHAR(64)")
	private String fileName;

	@Column(name = "content", columnDefinition = "TEXT", nullable = false)
	private String content;

	@OneToOne(mappedBy = "uploadContent", cascade = ALL, fetch = LAZY, orphanRemoval = true)
	private Summary summary;
}
