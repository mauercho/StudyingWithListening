package com.ssafy.domain.lecture.repository;

import com.ssafy.domain.lecture.model.entity.Lecture;
import com.ssafy.domain.lecture.model.entity.LectureNote;
import com.ssafy.domain.user.model.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LectureNoteRepository extends JpaRepository<LectureNote, Long> {

    @Query("SELECT l FROM LectureNote l WHERE l.user = :user AND l.lecture = :lecture")
    List<LectureNote> findByLectureId(@Param("user") User user, @Param("lecture") Lecture lecture);

    @Query("SELECT COUNT(l) FROM LectureNote l WHERE l.user = :user AND l.lecture = :lecture")
    Long countByUserIdAndLectureId(@Param("user") User user, @Param("lecture") Lecture lecture);

}
