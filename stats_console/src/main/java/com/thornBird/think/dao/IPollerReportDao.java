package com.thornBird.think.dao;

import java.util.Date;
import java.util.List;

import com.thornBird.think.model.PollerType;
import com.thornBird.think.model.mysqlModel.PollerReport;

/**
 * 对应数据库表report的到dao操作
 * @author hyman
 *
 */
public interface IPollerReportDao {
	
	int deleteByDateAndPollerType(Date dropDate, PollerType pollerType);
	
	List<PollerReport> loadReport(int itemId, PollerType pollerType, String startTime, String endTime);
}
