package com.thornBird.think.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.thornBird.think.dao.IPollerOutputJSONDao;
import com.thornBird.think.model.mysqlModel.PollerOutputJSON;

@Repository("pollerOutputJSONDao")
public class PollerOutputJSONDaoImpl implements IPollerOutputJSONDao {
	
	@Resource(name="namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private RowMapper<PollerOutputJSON> rowMapper = new RowMapper<PollerOutputJSON>() {

		public PollerOutputJSON mapRow(ResultSet rs, int rowNum) throws SQLException {
			PollerOutputJSON pollerOutputJSON = new PollerOutputJSON();
			pollerOutputJSON.setId(rs.getInt("id"));
			pollerOutputJSON.setItemId(rs.getInt("poller_item_id"));
			pollerOutputJSON.setCreateTime(rs.getTimestamp("created_time"));
			pollerOutputJSON.setOutput(rs.getString("output"));
			return pollerOutputJSON;
		}
	};

	public int deleteByDate(Date dropDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(dropDate);
		
		String sql = "delete from poller_output where created_time<DATE_FORMAT(:date,'%Y-%m-%d %H:%i:%S')";
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("date", date);
		
		return namedParameterJdbcTemplate.update(sql, parameter);
	}

	public int insert(PollerOutputJSON pollerOutputJSON) {
		String sql = "insert into poller_output(poller_item_id,created_time,output)" +
				" values(:poller_item_id, :created_time, :output)";
		
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("poller_item_id", pollerOutputJSON.getItemId());
		parameter.addValue("created_time", pollerOutputJSON.getCreateTime());
		parameter.addValue("output", pollerOutputJSON.getOutput());
		
		return namedParameterJdbcTemplate.update(sql, parameter);
	}

	public List<PollerOutputJSON> load(int itemId, String startTime, String endTime) {
		String sql = "select id,poller_item_id,created_time,output from poller_output " +
				"where poller_item_id=:poller_item_id and created_time between :start and :end " +
				"order by created_time asc";
		
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("poller_item_id", itemId);
		parameter.addValue("start", startTime);
		parameter.addValue("end", endTime);
		
		return namedParameterJdbcTemplate.query(sql, parameter, rowMapper);
	}

}
