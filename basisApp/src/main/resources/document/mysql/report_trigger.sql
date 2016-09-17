/*分隔符，告诉msql解释器，这段代码结束，可以运行了，默认delimiter是';'，但是在某些语句中包含';'，所以自定义分隔符*/
DELIMITER $$

DROP TRIGGER IF EXISTS report_trigger;$$

/*new. & old. 相当于某列创建了新旧两个临时变量，insert时，new合法，delete时，old合法，update时，new & old可同时使用。*/
CREATE TRIGGER report_trigger AFTER INSERT ON `poller_output_detail` FOR EACH ROW 
BEGIN
	CALL report_proc(new.poller_item_id, 'YEAR', new.stats_key, new.stats_value, new.created_time);
	CALL report_proc(new.poller_item_id, 'MONTH', new.stats_key, new.stats_value, new.created_time);
	CALL report_proc(new.poller_item_id, 'DAY', new.stats_key, new.stats_value, new.created_time);
	CALL report_proc(new.poller_item_id, 'HOUR', new.stats_key, new.stats_value, new.created_time);
	CALL report_proc(new.poller_item_id, 'HALF_HOUR', new.stats_key, new.stats_value, new.created_time);
END;$$

DELIMITER ;