package com.thornBird.config.dataSource.aopPart;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Description: DataSource Routing
 * @author: HymanHu
 * @date: 2019-08-16 17:12:00
 */
@SuppressWarnings("all")
public class DataSourceRouting extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataBaseHolder.getDataBase();
	}
}
