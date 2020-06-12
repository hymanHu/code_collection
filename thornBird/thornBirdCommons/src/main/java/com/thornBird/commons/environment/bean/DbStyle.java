package com.thornBird.commons.environment.bean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description: Db Style
 * @author: HymanHu
 * @date: 2019-01-15 10:05:48  
 */
public enum DbStyle {
	mainDb("mainDb"), abyssDb("abyssDb");

	private String dbName;

	private DbStyle(String dbName) {
		this.dbName = dbName;
	}
	
	private static Map<String, DbStyle> dbStyleMap = new HashMap<>();
	static {
		for(DbStyle dbStyle : DbStyle.values()) {
			dbStyleMap.put(dbStyle.getDbName().toUpperCase(), dbStyle);
		}
	}
	
	public static DbStyle getDbStyle(String dbName) {
		return StringUtils.isNotBlank(dbName) && dbStyleMap.get(dbName.toUpperCase()) != null ?  
				dbStyleMap.get(dbName.toUpperCase()) : dbStyleMap.get("mainDb".toUpperCase());
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public static void main(String[] args) {
		System.out.println(DbStyle.values());
		System.out.println(DbStyle.getDbStyle("maindb"));
		
	}
	
}
