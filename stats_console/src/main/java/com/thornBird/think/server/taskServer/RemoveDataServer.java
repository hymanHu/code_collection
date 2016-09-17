package com.thornBird.think.server.taskServer;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.thornBird.think.model.PollerType;
import com.thornBird.think.server.daoServer.IPollerOutputJSONServer;
import com.thornBird.think.server.daoServer.IPollerOutputKeyValueServer;
import com.thornBird.think.server.daoServer.IPollerReportServer;

@Service("removeDataServer")
public class RemoveDataServer {

	private static final Logger logger = LoggerFactory.getLogger(RemoveDataServer.class);

	@Resource(name = "pollerOutputJSONServer")
	private IPollerOutputJSONServer pollerOutputJSONServer;
	@Resource(name = "pollerOutputKeyValueServer")
	private IPollerOutputKeyValueServer pollerOutputKeyValueServer;
	@Resource(name = "pollerReportServer")
	private IPollerReportServer pollerReportServer;

	/**
	 * cron表达式，至少6个，可能7个 
	 * *通配符，表示每、?不想设置该字段，用于日期、/重复次数(10/2:10秒执行2次)
	 * 秒、分、小时、月份日期、月份、星期日期、年份 下面的设置表示每天3点执行
	 */
	@Scheduled(cron = "0 0 3 * * *")
	public void removeTask() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -10);
		Date date = calendar.getTime();

		int removedNumber1 = pollerOutputJSONServer.deleteByDate(date);
		logger.info("Delete poller_output count:{}", removedNumber1);
		int removedNumber2 = pollerOutputKeyValueServer.deleteByDate(date);
		logger.info("Delete poller_output_detail count:{}", removedNumber2);

		calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -12);
		date = calendar.getTime();
		int removedNumber3 = pollerReportServer.deleteByDateAndPollerType(date, PollerType.YEAR);
		logger.info("Delete year report count:{}", removedNumber3);

		calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -12);
		date = calendar.getTime();
		int removedNumber4 = pollerReportServer.deleteByDateAndPollerType(date, PollerType.MONTH);
		logger.info("Delete month report count:{}", removedNumber4);

		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -30);
		date = calendar.getTime();
		int removedNumber5 = pollerReportServer.deleteByDateAndPollerType(date, PollerType.DAY);
		logger.info("Delete day report count:{}", removedNumber5);

		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -12);
		date = calendar.getTime();
		int removedNumber6 = pollerReportServer.deleteByDateAndPollerType(date, PollerType.HOUR);
		logger.info("Delete hour report count:{}", removedNumber6);

		calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -6);
		date = calendar.getTime();
		int removedNumber7 = pollerReportServer.deleteByDateAndPollerType(date, PollerType.HALF_HOUR);
		logger.info("Delete half hour report count:{}", removedNumber7);

	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext(
				new String[] { "/src/main/webapp/WEB-INF/conf/spring/applicationContext.xml" });
	}
}
