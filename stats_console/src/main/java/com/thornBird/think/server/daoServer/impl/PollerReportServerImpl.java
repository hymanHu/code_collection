package com.thornBird.think.server.daoServer.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.thornBird.think.dao.IPollerReportDao;
import com.thornBird.think.model.PollerType;
import com.thornBird.think.model.mysqlModel.PollerReport;
import com.thornBird.think.server.daoServer.IPollerReportServer;

@Service("pollerReportServer")
public class PollerReportServerImpl implements IPollerReportServer {
	
	@Resource(name="pollerReportDao")
	private IPollerReportDao pollerReportDao;

	public List<PollerReport> loadReport(int itemId, PollerType pollerType, String startTime, String endTime) {
		return pollerReportDao.loadReport(itemId, pollerType, startTime, endTime);
	}

	public int deleteByDateAndPollerType(Date dropDate, PollerType pollerType) {
		return pollerReportDao.deleteByDateAndPollerType(dropDate, pollerType);
	}

}
