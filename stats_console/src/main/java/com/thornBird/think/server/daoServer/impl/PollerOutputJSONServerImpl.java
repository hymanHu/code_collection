package com.thornBird.think.server.daoServer.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.thornBird.think.dao.IPollerOutputJSONDao;
import com.thornBird.think.model.mysqlModel.PollerOutputJSON;
import com.thornBird.think.server.daoServer.IPollerOutputJSONServer;

@Service("pollerOutputJSONServer")
public class PollerOutputJSONServerImpl implements IPollerOutputJSONServer {
	
	@Resource(name="pollerOutputJSONDao")
	private IPollerOutputJSONDao pollerOutputJSONDao;

	public int deleteByDate(Date dropDate) {
		return pollerOutputJSONDao.deleteByDate(dropDate);
	}

	public int insert(PollerOutputJSON pollerOutputJSON) {
		return pollerOutputJSONDao.insert(pollerOutputJSON);
	}

	public List<PollerOutputJSON> load(int itemId, String startTime, String endTime) {
		return pollerOutputJSONDao.load(itemId, startTime, endTime);
	}

}
