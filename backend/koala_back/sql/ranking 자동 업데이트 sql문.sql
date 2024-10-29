DROP PROCEDURE IF EXISTS UpdateUserRanking;
DROP TRIGGER IF EXISTS UpdateRankingAfterUserExpChange;
DROP EVENT IF EXISTS UpdateUserRankingEvent;

-- 1. 저장 프로시저

DELIMITER $$

CREATE PROCEDURE UpdateUserRanking()
BEGIN
    DECLARE prev_exp INT DEFAULT -1;
    DECLARE user_rank INT DEFAULT 0;
    DECLARE current_dense_rank INT DEFAULT 0;

    -- 순위 테이블 초기화
TRUNCATE TABLE ranking;

-- 사용자 경험치를 기준으로 순위를 매기기
SET @current_dense_rank = 0;
    SET @user_rank = 0;
    SET @prev_exp = -1;

INSERT INTO ranking (user_id, ranking)
SELECT user_id,
       CASE
           WHEN @prev_exp = user_exp THEN @user_rank
           ELSE @current_dense_rank := @current_dense_rank + 1
           END AS ranking,
       @prev_exp := user_exp
FROM users
ORDER BY user_exp DESC;
END $$

DELIMITER ;


-- 2. 이벤트 스케줄러

CREATE EVENT UpdateUserRankingEvent
ON SCHEDULE EVERY 1 DAY
STARTS (TIMESTAMP(CURRENT_DATE) + INTERVAL 1 DAY)
DO
    CALL UpdateUserRanking();

-- 3. 트리거

DELIMITER $$

CREATE TRIGGER UpdateRankingAfterUserExpChange
    AFTER UPDATE ON users
    FOR EACH ROW
BEGIN
    IF OLD.user_exp != NEW.user_exp THEN
        CALL UpdateUserRanking();
END IF;
END $$

DELIMITER ;



-- 절취선

START TRANSACTION;

-- 순위 계산 및 업데이트
-- 1. 순위 테이블 초기화
TRUNCATE TABLE ranking;

-- 2. 순위 계산 및 업데이트
SET @dense_rank := 0;
SET @prev_exp := NULL;

INSERT INTO ranking (user_id, ranking)
SELECT user_id, 
       ranking
FROM (
    SELECT user_id, 
           user_exp,
           @dense_rank := IF(@prev_exp = user_exp, @dense_rank, @dense_rank + 1) AS ranking,
           @prev_exp := user_exp
    FROM users
    ORDER BY user_exp DESC
) AS subquery;



commit;

-- 절취선

DROP TRIGGER IF EXISTS UpdateRankingAfterUserExpChange;