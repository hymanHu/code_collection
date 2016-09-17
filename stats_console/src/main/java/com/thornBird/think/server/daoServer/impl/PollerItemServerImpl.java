package com.thornBird.think.server.daoServer.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.thornBird.think.dao.IPollerItemDao;
import com.thornBird.think.model.mysqlModel.PollerItem;
import com.thornBird.think.server.daoServer.IPollerItemServer;

@Service("pollerItemServer")
public class PollerItemServerImpl implements IPollerItemServer {

	@Resource(name="pollerItemDao")
	private IPollerItemDao pollerItemDao;
	
	public int deleteAll() {
		return pollerItemDao.deleteAll();
	}

	public int deleteById(int id) {
		return pollerItemDao.deleteById(id);
	}

	public int deleteByGroupId(int groupId) {
		return pollerItemDao.deleteByGroupId(groupId);
	}

	public int insertItem(PollerItem pollerItem) {
		return pollerItemDao.insertItem(pollerItem);
	}

	public PollerItem loadById(int id) {
		return pollerItemDao.loadById(id);
	}

	public List<PollerItem> loadByGroupId(int groupId) {
		return pollerItemDao.loadByGroupId(groupId);
	}

	public List<PollerItem> loadAll() {
		return pollerItemDao.loadAll();
	}

	public int updateItem(PollerItem pollerItem, int pollerItemId) {
		return pollerItemDao.updateItem(pollerItem, pollerItemId);
	}

}
