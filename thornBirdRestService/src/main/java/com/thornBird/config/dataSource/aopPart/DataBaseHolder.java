package com.thornBird.config.dataSource.aopPart;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Description: Data Base Holder
 * @author: HymanHu
 * @date: 2019-08-16 16:24:56
 */
public class DataBaseHolder {
	private final static Logger LOGGER = LoggerFactory.getLogger(DataBaseHolder.class);
	// 使用ThreadLocal实现线程安全，创建副本各不干扰
	private static final ThreadLocal<String> DATA_BASE_HOLDER = new ThreadLocal<String>();

	// 配置不同数据库名称
	public enum DataBase {
		maindb, world
	}
	
	public static void setDataBase(String dataBase) {
		LOGGER.debug("设置数据源：  " + dataBase);
		DATA_BASE_HOLDER.set(dataBase);
	}
	
	public static String getDataBase() {
		String dataBase = StringUtils.isBlank(DATA_BASE_HOLDER.get()) ? 
				DataBase.maindb.toString() : DATA_BASE_HOLDER.get();
		return dataBase;
	}
	
	public static void clearDataBase( ) {
		DATA_BASE_HOLDER.remove();
	}
}
