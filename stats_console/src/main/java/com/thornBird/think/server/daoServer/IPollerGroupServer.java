package com.thornBird.think.server.daoServer;

import java.util.List;

import com.thornBird.think.model.mysqlModel.PollerGroup;

/**
 * 对应数据库表poller_group的server操作
 * @author hyman
 *
 */
public interface IPollerGroupServer {
	
	int deleteAll();

	int deleteById(int id);

	int insertGroup(String groupName, String groupValue);

	int updateGroup(int id, String groupName, String groupValue);

	List<PollerGroup> loadAll();
}
