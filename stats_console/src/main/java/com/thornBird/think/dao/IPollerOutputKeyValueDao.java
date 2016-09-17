package com.thornBird.think.dao;

import java.util.Date;

import com.thornBird.think.model.mysqlModel.PollerOutputKeyValue;

/**
 * 对应数据库表poller_output_detail的dao操作
 * @author hyman
 *
 */
public interface IPollerOutputKeyValueDao {
	
	// 根据时间删除数据
	int deleteByDate(Date dropDate);

	int insert(PollerOutputKeyValue pollerOutputKeyValue);
}
