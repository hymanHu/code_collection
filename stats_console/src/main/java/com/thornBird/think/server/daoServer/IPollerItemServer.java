package com.thornBird.think.server.daoServer;

import java.util.List;

import com.thornBird.think.model.mysqlModel.PollerItem;

/**
 * 对应数据库表poller_item的server操作
 * @author hyman
 *
 */
public interface IPollerItemServer {
	
	int deleteAll();

	int deleteById(int id);

	int deleteByGroupId(int groupId);

	int insertItem(PollerItem pollerItem);

	PollerItem loadById(int id);

	List<PollerItem> loadByGroupId(int groupId);

	List<PollerItem> loadAll();
	
	int updateItem(PollerItem pollerItem, int pollerItemId);
}
