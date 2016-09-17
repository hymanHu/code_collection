package com.thornBird.think.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.thornBird.think.dao.IPollerOutputKeyValueDao;
import com.thornBird.think.model.mysqlModel.PollerOutputKeyValue;

@Repository("pollerOutputKeyValueDao")
public class PollerOutputKeyValueDaoImpl implements IPollerOutputKeyValueDao {
	
	@Resource(name="namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public int deleteByDate(Date dropDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(dropDate);
		
		String sql = "delete from poller_output_detail where created_time<DATE_FORMAT(:date,'%Y-%m-%d %H:%i:%S')";
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("date", date);
		
		return namedParameterJdbcTemplate.update(sql, parameter);
	}

	public int insert(PollerOutputKeyValue pollerOutputKeyValue) {
		String sql = "insert into poller_output_detail(poller_item_id, stats_key, stats_value, created_time)" +
				" values(:poller_item_id, :stats_key, :stats_value, :created_time)";
		
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("poller_item_id", pollerOutputKeyValue.getItemId());
		parameter.addValue("stats_key", pollerOutputKeyValue.getStatsKey());
		parameter.addValue("stats_value", pollerOutputKeyValue.getStatsValue());
		parameter.addValue("created_time", pollerOutputKeyValue.getCreateTime());
		
		return namedParameterJdbcTemplate.update(sql, parameter);
	}

}
