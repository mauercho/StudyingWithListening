
-- 기본 테이블 생성
CREATE TABLE `auth` (
    `auth_id` bigint(20) NOT NULL COMMENT 'auto increment',
    `auth_name` varchar(10) NULL DEFAULT "user" COMMENT 'user, admin, teacher',
    PRIMARY KEY (`auth_id`)
);

-- users 테이블 생성
CREATE TABLE `users` (
    `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'auto increment',
    `login_id` varchar(20) NOT NULL,
    `password` varchar(100) NOT NULL,
    `auth_id` bigint(20) NOT NULL,
    `name` varchar(100) NOT NULL,
    `nickname` varchar(100) NOT NULL,
    `leaves` int(11) DEFAULT 0,
    `user_exp` int(11) DEFAULT 0,
    `user_level` int(11) DEFAULT 0 COMMENT '경험치에 따라 오름',
    `user_created_at` datetime DEFAULT now(),
    PRIMARY KEY (`user_id`),
    CONSTRAINT `FK_auth_TO_users_1` FOREIGN KEY (`auth_id`) REFERENCES `auth` (`auth_id`)
);

CREATE TABLE `koala` (
    `koala_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'auto increment',
    `user_id` bigint(20) NOT NULL,
    `koala_name` varchar(20) DEFAULT "코알라" COMMENT '이름 수정 가능',
    `koala_level` int(11) DEFAULT 1 COMMENT '레벨 1부터 시작',
    `koala_exp` int(11) DEFAULT 0,
    `koala_type` int(11) DEFAULT 1 COMMENT '1: grey 2: pink 3: orange',
    `koala_created_at` datetime DEFAULT now() COMMENT '코알라 생성 시간',
    PRIMARY KEY (`koala_id`),
    CONSTRAINT `FK_users_TO_koala_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

-- 기타 테이블 생성
CREATE TABLE `lectures` (
    `lecture_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'auto increment',
    `teacher_id` bigint(20) NOT NULL,
    `lecture_title` varchar(100) NOT NULL,
    `lecture_detail` varchar(100) NULL,
    `lecture_url` text NOT NULL,
    `is_open` tinyint(1) DEFAULT 1 COMMENT '0: 끝남, 1: 진행 중',
    PRIMARY KEY (`lecture_id`),
    CONSTRAINT `FK_users_TO_lectures_1` FOREIGN KEY (`teacher_id`) REFERENCES `users` (`user_id`)
);

CREATE TABLE `sentences` (
    `sentence_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'auto increment',
    `user_id` bigint(20) NOT NULL COMMENT '회원 pk',
    `sentence_text` varchar(20) NULL,
    `topic_category` varchar(5) NULL COMMENT '일상, 교육, 행정, 사용자',
    `sentence_length` int NULL,
    `sentence_created_at` datetime DEFAULT now(),
    PRIMARY KEY (`sentence_id`),
    CONSTRAINT `FK_users_TO_sentences_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

CREATE TABLE `user_details` (
    `user_id` bigint(20) NOT NULL COMMENT '회원ID 참조',
    `user_introduce` varchar(100) NULL,
    `org_name` varchar(30) NOT NULL,
    PRIMARY KEY (`user_id`),
    CONSTRAINT `FK_users_TO_user_details_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

CREATE TABLE `lecture_notes` (
    `note_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'auto increment',
    `user_id` bigint(20) NOT NULL,
    `lecture_id` bigint(20) NOT NULL,
    `note_title` varchar(100) NOT NULL,
    `note_content` varchar(255) NOT NULL,
    `note_created_at` datetime DEFAULT now(),
    PRIMARY KEY (`note_id`),
    CONSTRAINT `FK_users_TO_lecture_notes_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `FK_lectures_TO_lecture_notes_1` FOREIGN KEY (`lecture_id`) REFERENCES `lectures` (`lecture_id`)
);

CREATE TABLE `review_sentences` (
    `review_sentence_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'auto increment',
    `user_id` bigint(20) NOT NULL,
    `sentence_id` bigint(20) NOT NULL,
    `review_sentence_created_at` datetime DEFAULT now(),
    PRIMARY KEY (`review_sentence_id`),
    CONSTRAINT `FK_users_TO_review_sentences_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `FK_sentences_TO_review_sentences_1` FOREIGN KEY (`sentence_id`) REFERENCES `sentences` (`sentence_id`)
);

CREATE TABLE `lecuture_schedule` (
    `lecture_schedule_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'auto increment',
    `lecture_id` bigint(20) NOT NULL,
    `day` int NOT NULL COMMENT '1~7 월~일',
    `start_time` time NOT NULL,
    `end_time` time NOT NULL,
    PRIMARY KEY (`lecture_schedule_id`),
    CONSTRAINT `FK_lectures_TO_lecuture_schedule_1` FOREIGN KEY (`lecture_id`) REFERENCES `lectures` (`lecture_id`)
);

CREATE TABLE `study_time` (
    `time_cal_type` int NOT NULL COMMENT '지난주: 0, 이번 주:1, 총 시간: 2',
    `user_id` bigint(20) NOT NULL,
    `talk_time` int DEFAULT 0 COMMENT 'AI 회화 서비스 이용 시간',
    `sentence_num` int DEFAULT 0 COMMENT '받아쓰기 문작 작성 횟수',
    `lecture_num` int DEFAULT 0 COMMENT '참여한 화상 강의 수',
    PRIMARY KEY (`time_cal_type`, `user_id`),
    CONSTRAINT `FK_users_TO_study_time_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

CREATE TABLE `board` (
    `board_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'auto increment',
    `user_id` bigint(20) NOT NULL,
    `board_title` varchar(50) NOT NULL,
    `board_content` text NOT NULL,
    `board_created_at` datetime DEFAULT now(),
    `board_modified_at` datetime NULL,
    `hit` int(11) DEFAULT 0 COMMENT '조회수',
    `comment_num` int(11) DEFAULT 0,
    PRIMARY KEY (`board_id`),
    CONSTRAINT `FK_users_TO_board_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

CREATE TABLE `board_comment` (
    `comment_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'auto increment',
    `board_id` bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    `comment_content` text NOT NULL,
    `comment_created_at` datetime DEFAULT now(),
    `comment_modified_at` datetime NULL,
    PRIMARY KEY (`comment_id`),
    CONSTRAINT `FK_board_TO_board_comment_1` FOREIGN KEY (`board_id`) REFERENCES `board` (`board_id`),
    CONSTRAINT `FK_users_TO_board_comment_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

CREATE TABLE `board_images` (
    `board_img_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'auto increment',
    `board_id` bigint(20) NOT NULL,
    `board_img_url` text NOT NULL,
    PRIMARY KEY (`board_img_id`),
    CONSTRAINT `FK_board_TO_board_images_1` FOREIGN KEY (`board_id`) REFERENCES `board` (`board_id`)
);

CREATE TABLE `lecture_sentences` (
    `lecture_sentence_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'auto increment',
    `lecture_id` bigint(20) NOT NULL,
    `sentence_id` bigint(20) NOT NULL,
    PRIMARY KEY (`lecture_sentence_id`),
    CONSTRAINT `FK_lectures_TO_lecture_sentences_1` FOREIGN KEY (`lecture_id`) REFERENCES `lectures` (`lecture_id`),
    CONSTRAINT `FK_sentences_TO_lecture_sentences_1` FOREIGN KEY (`sentence_id`) REFERENCES `sentences` (`sentence_id`)
);

CREATE TABLE `ranking` (
    `user_id` bigint(20) NOT NULL,
    `ranking` int(11) DEFAULT 0,
    PRIMARY KEY (`user_id`),
    CONSTRAINT `FK_users_TO_ranking_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

CREATE TABLE `registered_lecture` (
    `user_id` bigint(20) NOT NULL,
    `lecture_id` bigint(20) NOT NULL,
    PRIMARY KEY (`user_id`, `lecture_id`),
    CONSTRAINT `FK_users_TO_registered_lecture_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `FK_lectures_TO_registered_lecture_1` FOREIGN KEY (`lecture_id`) REFERENCES `lectures` (`lecture_id`)
);
