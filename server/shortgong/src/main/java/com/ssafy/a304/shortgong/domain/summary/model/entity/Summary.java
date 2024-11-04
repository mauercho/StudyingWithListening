package com.ssafy.a304.shortgong.domain.summary.model.entity;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.a304.shortgong.domain.index.model.entity.Index;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.uploadContent.model.entity.UploadContent;
import com.ssafy.a304.shortgong.domain.user.model.entity.User;
import com.ssafy.a304.shortgong.global.model.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "summary")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class Summary extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "summary_id", columnDefinition = "BIGINT(20)")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "writer_id", referencedColumnName = "user_id", nullable = false)
	private User writer;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "upload_content_id", nullable = false)
	private UploadContent uploadContent;

	@Column(name = "summary_title", columnDefinition = "VARCHAR(64)")
	private String title;

	@Column(name = "folder_name", columnDefinition = "VARCHAR(64)")
	private String folderName;

	@Builder.Default
	@OneToMany(mappedBy = "summary", cascade = ALL, orphanRemoval = true, fetch = LAZY)
	private List<Sentence> sentences = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "summary", cascade = ALL, orphanRemoval = true, fetch = LAZY)
	private List<Index> indexes = new ArrayList<>();
}
