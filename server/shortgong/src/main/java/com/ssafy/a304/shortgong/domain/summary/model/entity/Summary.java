package com.ssafy.a304.shortgong.domain.summary.model.entity;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.a304.shortgong.domain.index.model.entity.Index;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.uploadContent.model.entity.UploadContent;
import com.ssafy.a304.shortgong.domain.user.model.entity.User;

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
public class Summary {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "summary_id")
	private Long summaryId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "writer_id", referencedColumnName = "user_id", nullable = false)
	private User writer;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "upload_content_id", nullable = false)
	private UploadContent uploadContent;

	@Column(name = "summary_title")
	private String summaryTitle;

	@Column(name = "folder_name")
	private String folderName;

	@Builder.Default
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@Builder.Default
	@Column(name = "modified_at", nullable = false)
	private LocalDateTime modifiedAt = LocalDateTime.now();

	@Builder.Default
	@OneToMany(mappedBy = "summary", cascade = ALL, orphanRemoval = true, fetch = LAZY)
	private List<Sentence> sentences = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "summary", cascade = ALL, orphanRemoval = true, fetch = LAZY)
	private List<Index> indexes = new ArrayList<>();
}
