DELIMITER $$

DROP PROCEDURE IF EXISTS report_proc;$$

CREATE PROCEDURE report_proc(paramPollerItemId INT, paramPollerType VARCHAR(20), paramStatsKey VARCHAR(50), paramStatsValue VARCHAR(50), paramCreateTime DATETIME)
BEGIN
	/*定义report表字段*/
	DECLARE tId INT DEFAULT 0;
	DECLARE pollerItemId INT;
	DECLARE pollType VARCHAR(20);
	DECLARE statsKey VARCHAR(50);
	/*DECIMAL(20,3) 十进制，数字最长20位，3位小数*/
	DECLARE statsValue DECIMAL(20,3);
	DECLARE startTime DATETIME;
	DECLARE endTime DATETIME;
	DECLARE createdStartTime DATETIME;
	DECLARE createdMinute INT;

	/* 根据poller_type(YEAR, MONTH, DAY, HOUR, HALF_HOUR)设置createdStartTime & endTime */
	IF (paramPollerType = 'YEAR') THEN 
		SELECT DATE_FORMAT(paramCreateTime, '%Y-01-01 00:00:00') INTO createdStartTime;
		SET endTime = DATE_ADD(createdStartTime, INTERVAL 1 YEAR); 
	END IF; 
	IF (paramPollerType = 'MONTH') THEN 
		SELECT DATE_FORMAT(paramCreateTime, '%Y-%m-01 00:00:00') INTO createdStartTime;
		SET endTime = DATE_ADD(createdStartTime, INTERVAL 1 MONTH);
	END IF;
	IF (paramPollerType = 'DAY') THEN
		SELECT DATE_FORMAT(paramCreateTime, '%Y-%m-%d 00:00:00') INTO createdStartTime;
		SET endTime = DATE_ADD(createdStartTime, INTERVAL 1 DAY);
	END IF;
	IF (paramPollerType = 'HOUR') THEN
		SELECT DATE_FORMAT(paramCreateTime, '%Y-%m-%d %k:00:00') INTO createdStartTime;
		SET endTime = DATE_ADD(createdStartTime, INTERVAL 1 HOUR);
	END IF;
	IF (paramPollerType = 'HALF_HOUR') THEN
		SELECT MINUTE(paramCreateTime)  INTO createdMinute;
		IF (createdMinute < 30) THEN
			SELECT DATE_FORMAT(paramCreateTime, '%Y-%m-%d %k:00:00') INTO createdStartTime;
		ELSE
			SELECT DATE_FORMAT(paramCreateTime, '%Y-%m-%d %k:30:00') INTO createdStartTime;
		END IF;
		SET endTime = DATE_ADD(createdStartTime, INTERVAL 30 MINUTE);
	END IF;

	/*首先根据参数在report表中查询是否已经存在记录，如果存在则赋值给自定义变量*/
	SELECT id, poller_item_id, poller_type, stats_key, stats_value, start_time, end_time 
		INTO tId, pollerItemId, pollType, statsKey, statsValue, startTime, endTime 
		FROM `report` 
		WHERE poller_item_id = paramPollerItemId AND poller_type = paramPollerType AND stats_key = paramStatsKey AND start_time = createdStartTime;
    
	/*如果没有查询到结果，则插入记录，否则变更记录value。*/
	IF (tId = 0) THEN 
		INSERT INTO `report`(poller_item_id, poller_type, stats_key, stats_value, start_time, end_time) 
			VALUES(paramPollerItemId, paramPollerType, paramStatsKey, paramStatsValue, createdStartTime, endTime);
	ELSE 
		UPDATE `report` SET stats_value = paramStatsValue + statsValue 
			WHERE poller_item_id = paramPollerItemId AND poller_type = paramPollerType AND stats_key = paramStatsKey AND start_time = createdStartTime;
	END IF;
END;$$

DELIMITER ;