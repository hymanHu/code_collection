package com.thornBird.think.server.daoServer.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.thornBird.think.dao.IPollerOutputKeyValueDao;
import com.thornBird.think.model.mysqlModel.PollerOutputKeyValue;
import com.thornBird.think.server.daoServer.IPollerOutputKeyValueServer;

@Service("pollerOutputKeyValueServer")
public class PollerOutputKeyValueServerImpl implements IPollerOutputKeyValueServer {
	
	@Resource(name="pollerOutputKeyValueDao")
	private IPollerOutputKeyValueDao pollerOutputKeyValueDao;

	public int deleteByDate(Date dropDate) {
		return pollerOutputKeyValueDao.deleteByDate(dropDate);
	}

	public int insert(PollerOutputKeyValue pollerOutputKeyValue) {
		return pollerOutputKeyValueDao.insert(pollerOutputKeyValue);
	}

}
