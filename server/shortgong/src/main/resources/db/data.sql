INSERT INTO user (user_id, nickname, password_hash, profile_file_name, username, created_at, modified_at)
VALUES (1, '춘배', '비밀번호', 'chunbae.png', 'chunbae', '2024-11-04 11:11:11', '2024-11-04 11:11:11');

-- INSERT INTO upload_content(upload_content_id, content, file_name, user_id, created_at, modified_at)
-- VALUES (1, 'TEST 내용 입니다', 'test_upload_content_file', 1, '2024-11-04 11:11:11', '2024-11-04 11:11:11');
--
-- INSERT INTO summary(summary_id, folder_name, summary_title, upload_content_id, writer_id, created_at, modified_at)
-- VALUES (1, 'test_summary_folder', '요약본 제목', 1, 1, '2024-11-04 11:11:11', '2024-11-04 11:11:11');
--
-- INSERT INTO sentence(sentence_id, sentence_content, summary_id, sentence_order, voice_file_name, open_status,
--                      created_at,
--                      modified_at)
-- VALUES (1, 'TEST SENTENCE', 1, 1, 'test_voice_1.mp3', 1, '2024-11-04 11:11:11', '2024-11-04 11:11:11');
--
-- INSERT INTO summary_index(index_id, index_title, title_level, sentence_id, summary_id, created_at, modified_at)
-- VALUES (1, '대제목', 0, 1, 1, '2024-11-04 11:11:11', '2024-11-04 11:11:11');