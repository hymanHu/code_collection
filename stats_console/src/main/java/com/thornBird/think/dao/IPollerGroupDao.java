package com.thornBird.think.dao;

import java.util.List;

import com.thornBird.think.model.mysqlModel.PollerGroup;

/**
 * 对应数据库表poller_group的dao操作
 * @author hyman
 */
public interface IPollerGroupDao {
	
	int deleteAll();

	int deleteById(int id);

	int insertGroup(String groupName, String groupValue);

	int updateGroup(int id, String groupName, String groupValue);

	List<PollerGroup> loadAll();
}
