package com.thornBird.think.server.daoServer.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.thornBird.think.dao.IPollerGroupDao;
import com.thornBird.think.model.mysqlModel.PollerGroup;
import com.thornBird.think.server.daoServer.IPollerGroupServer;

@Service("pollerGroupServer")
public class PollerGroupServerImpl implements IPollerGroupServer {
	
	@Resource(name="pollerGroupDao")
	private IPollerGroupDao pollerGroupDao;

	public int deleteAll() {
		return pollerGroupDao.deleteAll();
	}

	public int deleteById(int id) {
		return pollerGroupDao.deleteById(id);
	}

	public int insertGroup(String groupName, String groupValue) {
		return pollerGroupDao.insertGroup(groupName, groupValue);
	}

	public int updateGroup(int id, String groupName, String groupValue) {
		return pollerGroupDao.updateGroup(id, groupName, groupValue);
	}

	public List<PollerGroup> loadAll() {
		return pollerGroupDao.loadAll();
	}

}
