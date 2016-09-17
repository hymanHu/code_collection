package com.thornBird.think.server.datasourceServer;

/**
 * 数据源接口，将mysql、redis、http等各种统计数据转换为字符串
 * @author human
 */
public interface IDataSource {
	String toJson();
}
