package com.thornBird.think.dao;

import java.util.Date;
import java.util.List;

import com.thornBird.think.model.mysqlModel.PollerOutputJSON;

/**
 * 对应数据库表poller_output的dao操作
 * @author hyman
 *
 */
public interface IPollerOutputJSONDao {
	
	// 删除某时间之后的数据
	int deleteByDate(Date dropDate);

	int insert(PollerOutputJSON pollerOutputJSON);

	// 根据itemId和时间段查询stats输出集合
	List<PollerOutputJSON> load(int itemId, String startTime, String endTime);
}
