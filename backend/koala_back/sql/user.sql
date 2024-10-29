use koala;
-- 권한 설정
insert into auth(auth_id, auth_name)
    value('0', 'admin'),
('1', 'user'),
('2', 'teacher');

-- 유저 설정
INSERT INTO users (user_id, login_id, password, auth_id, name, nickname) VALUES
 ('1292', 'uuas5866', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '0', '고동연', '관리자란희'),
 ('1293', 'ssyoung102', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '0', '윤서영', '관리자서영'),
 ('1294', 'wewqwew153', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '0', '대주형', '관리자주형'),
 ('1295', 'tjdgus2308', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '0', '김성현', '관리자성현x3'),
 ('1296', 'one_pst', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '0', '박수진', '관리자박박'),
 ('1297', 'kyg0954', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '0', '김유갱', '관리자유갱'),
 ('1298', 'user1', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', 'John Doe', '존도'),
 ('1299', 'user2', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', 'Jane Smith', '열정소녀'),
 ('1300', 'user3', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', 'Alice Johnson', '커피중독자'),
 ('1301', 'user4', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', 'Bob Brown', '브라운곰'),
 ('1302', 'user5', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', 'Charlie Davis', '햄버거광'),
 ('1303', 'user6', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', 'Diana Evans', '고양이집사'),
 ('1304', 'user7', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', 'Frank Green', '프랭크왕자'),
 ('1305', 'user8', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', 'Grace Hill', '반짝반짝'),
 ('1306', 'user9', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', 'Henry Scott', '헨리호'),
 ('1307', 'user10', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', 'Ivy Taylor', '하이브이비'),
 ('1308', 'user11', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', '김철수', '대한민국철수'),
 ('1309', 'user12', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', '이영희', '영희는귀여워'),
 ('1310', 'user13', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', '박민수', '민수짱'),
 ('1311', 'user14', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', '최수정', '수정크리스탈'),
 ('1312', 'user15', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', '정호석', '호석파워'),
 ('1313', 'user16', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', '한예은', '예쁜예은'),
 ('1314', 'user17', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', '신동엽', '동엽이형'),
 ('1315', 'user18', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', '고혜민', '미녀혜민'),
 ('1316', 'user19', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', '문지호', '지호의모험'),
 ('1317', 'user20', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', '오서준', '서준이도전'),
 ('1318', 'user21', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', '에밀리 해일럿', '에밀리햇'),
 ('1319', 'user22', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', '제이콥 리차드슨', '제이콥'),
 ('1320', 'user23', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', '올리비아 윌슨', '올리비아'),
 ('1321', 'user24', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', '루카스 브라운', '루카스'),
 ('1322', 'user25', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', '이사벨라 테일러', '벨라'),
 ('1323', 'user26', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', '헨리 존슨', '헨리'),
 ('1324', 'user27', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', '샬롯 데이비스', '샬롯'),
 ('1325', 'user28', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', '메이슨 가르시아', '메이슨'),
 ('1326', 'user29', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', '소피아 로드리게즈', '소피'),
 ('1327', 'user30', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '1', '제임스 마르티네즈', '제임스');


-- 선생님
INSERT INTO users (user_id, login_id, password, auth_id, name, nickname) VALUES
 ('1328', 'teacher1', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '2', '김지희', '김지희선생님'),
 ('1329', 'teacher2', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '2', '최미주', '최미주선생님'),
 ('1330', 'teacher3', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '2', '조수빈', '조수빈선생님'),
 ('1331', 'teacher4', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '2', '남지현', '남지현선생님'),
 ('1332', 'teacher5', '{bcrypt}$2a$10$ohb1.RATzM9wf0uY.7c9tOf.CHanCeIz/32IzCug9CroOd/b1pWdS', '2', '정지훈', '정지훈선생님');


-- 유저 공부 시간
INSERT INTO study_time (time_cal_type, user_id) VALUES
(0, '1292'), (1, '1292'), (2, '1292'), (3, '1292'), (4, '1292'), (5, '1292'), (6, '1292'), (7, '1292'), (8, '1292'), (9, '1292'), (10, '1292'), (11, '1292'), (12, '1292'), (13, '1292'), (14, '1292'),
(0, '1293'), (1, '1293'), (2, '1293'), (3, '1293'), (4, '1293'), (5, '1293'), (6, '1293'), (7, '1293'), (8, '1293'), (9, '1293'), (10, '1293'), (11, '1293'), (12, '1293'), (13, '1293'), (14, '1293'),
(0, '1294'), (1, '1294'), (2, '1294'), (3, '1294'), (4, '1294'), (5, '1294'), (6, '1294'), (7, '1294'), (8, '1294'), (9, '1294'), (10, '1294'), (11, '1294'), (12, '1294'), (13, '1294'), (14, '1294'),
(0, '1295'), (1, '1295'), (2, '1295'), (3, '1295'), (4, '1295'), (5, '1295'), (6, '1295'), (7, '1295'), (8, '1295'), (9, '1295'), (10, '1295'), (11, '1295'), (12, '1295'), (13, '1295'), (14, '1295'),
(0, '1296'), (1, '1296'), (2, '1296'), (3, '1296'), (4, '1296'), (5, '1296'), (6, '1296'), (7, '1296'), (8, '1296'), (9, '1296'), (10, '1296'), (11, '1296'), (12, '1296'), (13, '1296'), (14, '1296'),
(0, '1297'), (1, '1297'), (2, '1297'), (3, '1297'), (4, '1297'), (5, '1297'), (6, '1297'), (7, '1297'), (8, '1297'), (9, '1297'), (10, '1297'), (11, '1297'), (12, '1297'), (13, '1297'), (14, '1297'),
(0, '1298'), (1, '1298'), (2, '1298'), (3, '1298'), (4, '1298'), (5, '1298'), (6, '1298'), (7, '1298'), (8, '1298'), (9, '1298'), (10, '1298'), (11, '1298'), (12, '1298'), (13, '1298'), (14, '1298'),
(0, '1299'), (1, '1299'), (2, '1299'), (3, '1299'), (4, '1299'), (5, '1299'), (6, '1299'), (7, '1299'), (8, '1299'), (9, '1299'), (10, '1299'), (11, '1299'), (12, '1299'), (13, '1299'), (14, '1299'),
(0, '1300'), (1, '1300'), (2, '1300'), (3, '1300'), (4, '1300'), (5, '1300'), (6, '1300'), (7, '1300'), (8, '1300'), (9, '1300'), (10, '1300'), (11, '1300'), (12, '1300'), (13, '1300'), (14, '1300'),
(0, '1301'), (1, '1301'), (2, '1301'), (3, '1301'), (4, '1301'), (5, '1301'), (6, '1301'), (7, '1301'), (8, '1301'), (9, '1301'), (10, '1301'), (11, '1301'), (12, '1301'), (13, '1301'), (14, '1301'),
(0, '1302'), (1, '1302'), (2, '1302'), (3, '1302'), (4, '1302'), (5, '1302'), (6, '1302'), (7, '1302'), (8, '1302'), (9, '1302'), (10, '1302'), (11, '1302'), (12, '1302'), (13, '1302'), (14, '1302'),
(0, '1303'), (1, '1303'), (2, '1303'), (3, '1303'), (4, '1303'), (5, '1303'), (6, '1303'), (7, '1303'), (8, '1303'), (9, '1303'), (10, '1303'), (11, '1303'), (12, '1303'), (13, '1303'), (14, '1303'),
(0, '1304'), (1, '1304'), (2, '1304'), (3, '1304'), (4, '1304'), (5, '1304'), (6, '1304'), (7, '1304'), (8, '1304'), (9, '1304'), (10, '1304'), (11, '1304'), (12, '1304'), (13, '1304'), (14, '1304'),
(0, '1305'), (1, '1305'), (2, '1305'), (3, '1305'), (4, '1305'), (5, '1305'), (6, '1305'), (7, '1305'), (8, '1305'), (9, '1305'), (10, '1305'), (11, '1305'), (12, '1305'), (13, '1305'), (14, '1305'),
(0, '1306'), (1, '1306'), (2, '1306'), (3, '1306'), (4, '1306'), (5, '1306'), (6, '1306'), (7, '1306'), (8, '1306'), (9, '1306'), (10, '1306'), (11, '1306'), (12, '1306'), (13, '1306'), (14, '1306'),
(0, '1307'), (1, '1307'), (2, '1307'), (3, '1307'), (4, '1307'), (5, '1307'), (6, '1307'), (7, '1307'), (8, '1307'), (9, '1307'), (10, '1307'), (11, '1307'), (12, '1307'), (13, '1307'), (14, '1307'),
(0, '1308'), (1, '1308'), (2, '1308'), (3, '1308'), (4, '1308'), (5, '1308'), (6, '1308'), (7, '1308'), (8, '1308'), (9, '1308'), (10, '1308'), (11, '1308'), (12, '1308'), (13, '1308'), (14, '1308'),
(0, '1309'), (1, '1309'), (2, '1309'), (3, '1309'), (4, '1309'), (5, '1309'), (6, '1309'), (7, '1309'), (8, '1309'), (9, '1309'), (10, '1309'), (11, '1309'), (12, '1309'), (13, '1309'), (14, '1309'),
(0, '1310'), (1, '1310'), (2, '1310'), (3, '1310'), (4, '1310'), (5, '1310'), (6, '1310'), (7, '1310'), (8, '1310'), (9, '1310'), (10, '1310'), (11, '1310'), (12, '1310'), (13, '1310'), (14, '1310'),
(0, '1311'), (1, '1311'), (2, '1311'), (3, '1311'), (4, '1311'), (5, '1311'), (6, '1311'), (7, '1311'), (8, '1311'), (9, '1311'), (10, '1311'), (11, '1311'), (12, '1311'), (13, '1311'), (14, '1311'),
(0, '1312'), (1, '1312'), (2, '1312'), (3, '1312'), (4, '1312'), (5, '1312'), (6, '1312'), (7, '1312'), (8, '1312'), (9, '1312'), (10, '1312'), (11, '1312'), (12, '1312'), (13, '1312'), (14, '1312'),
(0, '1313'), (1, '1313'), (2, '1313'), (3, '1313'), (4, '1313'), (5, '1313'), (6, '1313'), (7, '1313'), (8, '1313'), (9, '1313'), (10, '1313'), (11, '1313'), (12, '1313'), (13, '1313'), (14, '1313'),
(0, '1314'), (1, '1314'), (2, '1314'), (3, '1314'), (4, '1314'), (5, '1314'), (6, '1314'), (7, '1314'), (8, '1314'), (9, '1314'), (10, '1314'), (11, '1314'), (12, '1314'), (13, '1314'), (14, '1314'),
(0, '1315'), (1, '1315'), (2, '1315'), (3, '1315'), (4, '1315'), (5, '1315'), (6, '1315'), (7, '1315'), (8, '1315'), (9, '1315'), (10, '1315'), (11, '1315'), (12, '1315'), (13, '1315'), (14, '1315'),
(0, '1316'), (1, '1316'), (2, '1316'), (3, '1316'), (4, '1316'), (5, '1316'), (6, '1316'), (7, '1316'), (8, '1316'), (9, '1316'), (10, '1316'), (11, '1316'), (12, '1316'), (13, '1316'), (14, '1316'),
(0, '1317'), (1, '1317'), (2, '1317'), (3, '1317'), (4, '1317'), (5, '1317'), (6, '1317'), (7, '1317'), (8, '1317'), (9, '1317'), (10, '1317'), (11, '1317'), (12, '1317'), (13, '1317'), (14, '1317'),
(0, '1318'), (1, '1318'), (2, '1318'), (3, '1318'), (4, '1318'), (5, '1318'), (6, '1318'), (7, '1318'), (8, '1318'), (9, '1318'), (10, '1318'), (11, '1318'), (12, '1318'), (13, '1318'), (14, '1318'),
(0, '1319'), (1, '1319'), (2, '1319'), (3, '1319'), (4, '1319'), (5, '1319'), (6, '1319'), (7, '1319'), (8, '1319'), (9, '1319'), (10, '1319'), (11, '1319'), (12, '1319'), (13, '1319'), (14, '1319'),
(0, '1320'), (1, '1320'), (2, '1320'), (3, '1320'), (4, '1320'), (5, '1320'), (6, '1320'), (7, '1320'), (8, '1320'), (9, '1320'), (10, '1320'), (11, '1320'), (12, '1320'), (13, '1320'), (14, '1320'),
(0, '1321'), (1, '1321'), (2, '1321'), (3, '1321'), (4, '1321'), (5, '1321'), (6, '1321'), (7, '1321'), (8, '1321'), (9, '1321'), (10, '1321'), (11, '1321'), (12, '1321'), (13, '1321'), (14, '1321'),
(0, '1322'), (1, '1322'), (2, '1322'), (3, '1322'), (4, '1322'), (5, '1322'), (6, '1322'), (7, '1322'), (8, '1322'), (9, '1322'), (10, '1322'), (11, '1322'), (12, '1322'), (13, '1322'), (14, '1322'),
(0, '1323'), (1, '1323'), (2, '1323'), (3, '1323'), (4, '1323'), (5, '1323'), (6, '1323'), (7, '1323'), (8, '1323'), (9, '1323'), (10, '1323'), (11, '1323'), (12, '1323'), (13, '1323'), (14, '1323'),
(0, '1324'), (1, '1324'), (2, '1324'), (3, '1324'), (4, '1324'), (5, '1324'), (6, '1324'), (7, '1324'), (8, '1324'), (9, '1324'), (10, '1324'), (11, '1324'), (12, '1324'), (13, '1324'), (14, '1324'),
(0, '1325'), (1, '1325'), (2, '1325'), (3, '1325'), (4, '1325'), (5, '1325'), (6, '1325'), (7, '1325'), (8, '1325'), (9, '1325'), (10, '1325'), (11, '1325'), (12, '1325'), (13, '1325'), (14, '1325'),
(0, '1326'), (1, '1326'), (2, '1326'), (3, '1326'), (4, '1326'), (5, '1326'), (6, '1326'), (7, '1326'), (8, '1326'), (9, '1326'), (10, '1326'), (11, '1326'), (12, '1326'), (13, '1326'), (14, '1326'),
(0, '1327'), (1, '1327'), (2, '1327'), (3, '1327'), (4, '1327'), (5, '1327'), (6, '1327'), (7, '1327'), (8, '1327'), (9, '1327'), (10, '1327'), (11, '1327'), (12, '1327'), (13, '1327'), (14, '1327'),
(0, '1328'), (1, '1328'), (2, '1328'), (3, '1328'), (4, '1328'), (5, '1328'), (6, '1328'), (7, '1328'), (8, '1328'), (9, '1328'), (10, '1328'), (11, '1328'), (12, '1328'), (13, '1328'), (14, '1328'),
(0, '1329'), (1, '1329'), (2, '1329'), (3, '1329'), (4, '1329'), (5, '1329'), (6, '1329'), (7, '1329'), (8, '1329'), (9, '1329'), (10, '1329'), (11, '1329'), (12, '1329'), (13, '1329'), (14, '1329'),
(0, '1330'), (1, '1330'), (2, '1330'), (3, '1330'), (4, '1330'), (5, '1330'), (6, '1330'), (7, '1330'), (8, '1330'), (9, '1330'), (10, '1330'), (11, '1330'), (12, '1330'), (13, '1330'), (14, '1330'),
(0, '1331'), (1, '1331'), (2, '1331'), (3, '1331'), (4, '1331'), (5, '1331'), (6, '1331'), (7, '1331'), (8, '1331'), (9, '1331'), (10, '1331'), (11, '1331'), (12, '1331'), (13, '1331'), (14, '1331'),
(0, '1332'), (1, '1332'), (2, '1332'), (3, '1332'), (4, '1332'), (5, '1332'), (6, '1332'), (7, '1332'), (8, '1332'), (9, '1332'), (10, '1332'), (11, '1332'), (12, '1332'), (13, '1332'), (14, '1332');


-- 코알라 만들기
INSERT INTO koala (user_id) VALUES
('1292'),
('1293'),
('1294'),
('1295'),
('1296'),
('1297'),
('1298'),
('1299'),
('1300'),
('1301'),
('1302'),
('1303'),
('1304'),
('1305'),
('1306'),
('1307'),
('1308'),
('1309'),
('1310'),
('1311'),
('1312'),
('1313'),
('1314'),
('1315'),
('1316'),
('1317'),
('1318'),
('1319'),
('1320'),
('1321'),
('1322'),
('1323'),
('1324'),
('1325'),
('1326'),
('1327'),
('1328'),
('1329'),
('1330'),
('1331'),
('1332');

-- 지금까지 넣은 사람들로 rank 테이블 구성
SET SQL_SAFE_UPDATES = 0;
DELETE from ranking;
INSERT INTO ranking (user_id, ranking)
SELECT user_id,
       RANK() OVER (ORDER BY user_level DESC, user_exp DESC) AS ranking
FROM users;
SET SQL_SAFE_UPDATES = 1;

