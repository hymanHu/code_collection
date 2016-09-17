package com.thornBird.think.server.daoServer;

import java.util.Date;
import java.util.List;

import com.thornBird.think.model.PollerType;
import com.thornBird.think.model.mysqlModel.PollerReport;

/**
 * 对应数据库表report的到server操作
 * @author hyman
 *
 */
public interface IPollerReportServer {
	
	int deleteByDateAndPollerType(Date dropDate, PollerType pollerType);
	
	List<PollerReport> loadReport(int itemId, PollerType pollerType, String startTime, String endTime);
}
