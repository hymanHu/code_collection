#! /bin/bash

conf_dir=/opt/etc/mysql/
MYSQL_HOME=/opt/local/mysql/
MYSQL_CLIENT=$MYSQL_HOME/bin/mysql

if [ ! -x $MYSQL_CLIENT ]
then
	echo "please install mysql first"
fi

for f in $(ls $conf_dir/*.cnf);
do


$MYSQL_CLIENT --defaults-file=$f --user=root << END
	
	use mysql;
	delete from user where User='';
	GRANT ALL PRIVILEGES ON *.* TO 'lj'@'%' IDENTIFIED BY 'livejournal' WITH GRANT OPTION;
	GRANT ALL PRIVILEGES ON *.* TO 'lj'@'localhost' IDENTIFIED BY 'livejournal' WITH GRANT OPTION;
	flush privileges;

	create database  IF NOT EXISTS stats;
use stats;

CREATE TABLE poller_group (
  id int(11) NOT NULL AUTO_INCREMENT,
  node_key varchar(100) NOT NULL,
  node_value varchar(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_GRP (node_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE poller_item (
  id int(11) NOT NULL AUTO_INCREMENT,
  poller_group_id int(11) NOT NULL,
  poller_name varchar(100) NOT NULL,
  poller_type varchar(20) NOT NULL,
  poller_cmd varchar(200) NOT NULL,
  poller_interval int(11) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE poller_output (
  id int(11) NOT NULL AUTO_INCREMENT,
  poller_item_id int(11) NOT NULL,
  created_time datetime NOT NULL,
  output text,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE poller_output_detail (
  id int(11) NOT NULL AUTO_INCREMENT,
  poller_item_id int(11) NOT NULL,
  stats_key varchar(50) NOT NULL,
  stats_value varchar(50) NOT NULL,
  created_time datetime NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE report_half_hour (
  id int(11) NOT NULL AUTO_INCREMENT,
  poller_item_id int(11) NOT NULL,
  stats_key varchar(50) NOT NULL,
  stats_value varchar(50) NOT NULL,
  start_time datetime NOT NULL,
  end_time datetime NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE report_hour (
  id int(11) NOT NULL AUTO_INCREMENT,
  poller_item_id int(11) NOT NULL,
  stats_key varchar(50) NOT NULL,
  stats_value varchar(50) NOT NULL,
  start_time datetime NOT NULL,
  end_time datetime NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE report_day (
  id int(11) NOT NULL AUTO_INCREMENT,
  poller_item_id int(11) NOT NULL,
  stats_key varchar(50) NOT NULL,
  stats_value varchar(50) NOT NULL,
  start_time datetime NOT NULL,
  end_time datetime NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE report_month (
  id int(11) NOT NULL AUTO_INCREMENT,
  poller_item_id int(11) NOT NULL,
  stats_key varchar(50) NOT NULL,
  stats_value varchar(50) NOT NULL,
  start_time datetime NOT NULL,
  end_time datetime NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE report_year (
  id int(11) NOT NULL AUTO_INCREMENT,
  poller_item_id int(11) NOT NULL,
  stats_key varchar(50) NOT NULL,
  stats_value varchar(50) NOT NULL,
  start_time datetime NOT NULL,
  end_time datetime NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DELIMITER $$

DROP PROCEDURE IF EXISTS proc_half_hour$$
CREATE PROCEDURE  proc_half_hour(paramPollerItemId INT, paramStatsKey VARCHAR(50), paramStatsValue VARCHAR(50), paramCreatedTime DATETIME)
BEGIN
	DECLARE createdMinute INT;
	DECLARE createdStartTime DATETIME;
	DECLARE tid INT DEFAULT 0;
	DECLARE pollerItemId INT;
	DECLARE statsKey VARCHAR(50);
	DECLARE statsValue DECIMAL(20,3);
	DECLARE startTime DATETIME;
  DECLARE endTime DATETIME;
        
	
	SELECT MINUTE(paramCreatedTime)  INTO createdMinute;
	/* select createdMinute; */
	
	IF createdMinute<30 THEN
		SELECT DATE_FORMAT(paramCreatedTime, '%Y-%m-%d %k:00:00') INTO createdStartTime;
	ELSE
		SELECT DATE_FORMAT(paramCreatedTime, '%Y-%m-%d %k:30:00') INTO createdStartTime;
	END IF;
	
	/*SELECT createdStartTime;*/
	
	SELECT id, poller_item_id, stats_key, stats_value, start_time, end_time
        INTO tid, pollerItemId, statsKey, statsValue, startTime, endTime
        FROM report_half_hour
        WHERE poller_item_id = paramPollerItemId AND stats_key = paramStatsKey AND start_time = createdStartTime;
     
    /* select tid;*/
    IF tid=0 THEN
        SET endTime = DATE_ADD(createdStartTime, INTERVAL 30 MINUTE);
        
        INSERT INTO report_half_hour(poller_item_id, stats_key, stats_value, start_time, end_time)
            VALUES(paramPollerItemId, paramStatsKey, paramStatsValue, createdStartTime, endTime);
    ELSE
        UPDATE report_half_hour SET stats_value = paramStatsValue + statsValue
            WHERE poller_item_id = paramPollerItemId AND stats_key = paramStatsKey AND start_time = createdStartTime;
    END IF;
	
END $$

DELIMITER ;

DELIMITER $$

DROP PROCEDURE IF EXISTS proc_hour$$

CREATE PROCEDURE proc_hour(paramPollerItemId INT, paramStatsKey VARCHAR(50), paramStatsValue VARCHAR(50), paramCreatedTime DATETIME)
BEGIN
	DECLARE createdStartTime DATETIME;
	DECLARE tid INT DEFAULT 0;
	DECLARE pollerItemId INT;
	DECLARE statsKey VARCHAR(50);
	DECLARE statsValue DECIMAL(20,3);
	DECLARE startTime DATETIME;
    DECLARE endTime DATETIME;

    SELECT DATE_FORMAT(paramCreatedTime, '%Y-%m-%d %k:00:00') INTO createdStartTime;

	SELECT id, poller_item_id, stats_key, stats_value, start_time, end_time
        INTO tid, pollerItemId, statsKey, statsValue, startTime, endTime
        FROM report_hour
        WHERE poller_item_id = paramPollerItemId AND stats_key = paramStatsKey AND start_time = createdStartTime;
     
    
    IF tid=0 THEN
        SET endTime = DATE_ADD(createdStartTime, INTERVAL 1 HOUR);
        
        INSERT INTO report_hour(poller_item_id, stats_key, stats_value, start_time, end_time)
            VALUES(paramPollerItemId, paramStatsKey, paramStatsValue, createdStartTime, endTime);
    ELSE
        UPDATE report_hour SET stats_value = paramStatsValue + statsValue
            WHERE poller_item_id = paramPollerItemId AND stats_key = paramStatsKey AND start_time = createdStartTime;
    END IF;
	
END$$

DELIMITER ;


DELIMITER $$

DROP PROCEDURE IF EXISTS proc_day$$

CREATE PROCEDURE proc_day(paramPollerItemId INT, paramStatsKey VARCHAR(50), paramStatsValue VARCHAR(50), paramCreatedTime DATETIME)
BEGIN
	DECLARE createdStartTime DATETIME;
	DECLARE tid INT DEFAULT 0;
	DECLARE pollerItemId INT;
	DECLARE statsKey VARCHAR(50);
	DECLARE statsValue DECIMAL(20,3);
	DECLARE startTime DATETIME;
    DECLARE endTime DATETIME;

    SELECT DATE_FORMAT(paramCreatedTime, '%Y-%m-%d 00:00:00') INTO createdStartTime;

	SELECT id, poller_item_id, stats_key, stats_value, start_time, end_time
        INTO tid, pollerItemId, statsKey, statsValue, startTime, endTime
        FROM report_day
        WHERE poller_item_id = paramPollerItemId AND stats_key = paramStatsKey AND start_time = createdStartTime;
     
    
    IF tid=0 THEN
        SET endTime = DATE_ADD(createdStartTime, INTERVAL 1 DAY);
        
        INSERT INTO report_day(poller_item_id, stats_key, stats_value, start_time, end_time)
            VALUES(paramPollerItemId, paramStatsKey, paramStatsValue, createdStartTime, endTime);
    ELSE
        UPDATE report_day SET stats_value = paramStatsValue + statsValue
            WHERE poller_item_id = paramPollerItemId AND stats_key = paramStatsKey AND start_time = createdStartTime;
    END IF;
	
END$$

DELIMITER ;


DELIMITER $$

DROP PROCEDURE IF EXISTS proc_month$$

CREATE PROCEDURE proc_month(paramPollerItemId INT, paramStatsKey VARCHAR(50), paramStatsValue VARCHAR(50), paramCreatedTime DATETIME)
BEGIN
	DECLARE createdStartTime DATETIME;
	DECLARE tid INT DEFAULT 0;
	DECLARE pollerItemId INT;
	DECLARE statsKey VARCHAR(50);
	DECLARE statsValue DECIMAL(20,3);
	DECLARE startTime DATETIME;
    DECLARE endTime DATETIME;

    SELECT DATE_FORMAT(paramCreatedTime, '%Y-%m-01 00:00:00') INTO createdStartTime;

	SELECT id, poller_item_id, stats_key, stats_value, start_time, end_time
        INTO tid, pollerItemId, statsKey, statsValue, startTime, endTime
        FROM report_month
        WHERE poller_item_id = paramPollerItemId AND stats_key = paramStatsKey AND start_time = createdStartTime;
     
    
    IF tid=0 THEN
        SET endTime = DATE_ADD(createdStartTime, INTERVAL 1 MONTH);
        
        INSERT INTO report_month(poller_item_id, stats_key, stats_value, start_time, end_time)
            VALUES(paramPollerItemId, paramStatsKey, paramStatsValue, createdStartTime, endTime);
    ELSE
        UPDATE report_month SET stats_value = paramStatsValue + statsValue
            WHERE poller_item_id = paramPollerItemId AND stats_key = paramStatsKey AND start_time = createdStartTime;
    END IF;
	
END$$

DELIMITER ;

DELIMITER $$

DROP PROCEDURE IF EXISTS proc_year$$

CREATE PROCEDURE proc_year(paramPollerItemId INT, paramStatsKey VARCHAR(50), paramStatsValue VARCHAR(50), paramCreatedTime DATETIME)
BEGIN
	DECLARE createdStartTime DATETIME;
	DECLARE tid INT DEFAULT 0;
	DECLARE pollerItemId INT;
	DECLARE statsKey VARCHAR(50);
	DECLARE statsValue DECIMAL(20,3);
	DECLARE startTime DATETIME;
    DECLARE endTime DATETIME;

    SELECT DATE_FORMAT(paramCreatedTime, '%Y-01-01 00:00:00') INTO createdStartTime;

	SELECT id, poller_item_id, stats_key, stats_value, start_time, end_time
        INTO tid, pollerItemId, statsKey, statsValue, startTime, endTime
        FROM report_year
        WHERE poller_item_id = paramPollerItemId AND stats_key = paramStatsKey AND start_time = createdStartTime;
     
    
    IF tid=0 THEN
        SET endTime = DATE_ADD(createdStartTime, INTERVAL 1 YEAR);
        
        INSERT INTO report_year(poller_item_id, stats_key, stats_value, start_time, end_time)
            VALUES(paramPollerItemId, paramStatsKey, paramStatsValue, createdStartTime, endTime);
    ELSE
        UPDATE report_year SET stats_value = paramStatsValue + statsValue
            WHERE poller_item_id = paramPollerItemId AND stats_key = paramStatsKey AND start_time = createdStartTime;
    END IF;
	
END$$

DELIMITER ;


DELIMITER $$

DROP TRIGGER IF EXISTS trg_report$$

CREATE
    TRIGGER trg_report AFTER INSERT ON poller_output_detail 
    FOR EACH ROW BEGIN
	CALL proc_half_hour(new.poller_item_id, new.stats_key, new.stats_value,new.created_time);
	CALL proc_hour(new.poller_item_id, new.stats_key, new.stats_value,new.created_time);
	CALL proc_day(new.poller_item_id, new.stats_key, new.stats_value,new.created_time);
	CALL proc_month(new.poller_item_id, new.stats_key, new.stats_value,new.created_time);
	CALL proc_year(new.poller_item_id, new.stats_key, new.stats_value,new.created_time);
END;
$$

DELIMITER ;


END

done

echo "initial finish!"

