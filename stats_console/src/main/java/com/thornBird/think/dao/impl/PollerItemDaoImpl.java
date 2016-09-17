package com.thornBird.think.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.thornBird.think.dao.IPollerItemDao;
import com.thornBird.think.model.StatsType;
import com.thornBird.think.model.mysqlModel.PollerItem;

@Repository("pollerItemDao")
public class PollerItemDaoImpl implements IPollerItemDao {
	
	@Resource(name="namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private RowMapper<PollerItem> rowMapper = new RowMapper<PollerItem>() {

		public PollerItem mapRow(ResultSet rs, int rowNumber)
				throws SQLException {
			PollerItem pollerItem = new PollerItem();
			pollerItem.setId(rs.getInt(1));
			pollerItem.setGroupId(rs.getInt(2));
			pollerItem.setPollerName(rs.getString(3));
			pollerItem.setStatsType(StatsType.valueOf(rs.getString(4)));
			pollerItem.setPollerCommand(rs.getString(5));
			pollerItem.setPollerInterval(rs.getInt(6));
			return pollerItem;
		}
	};

	public int deleteAll() {
		String sql = "delete from poller_item";
		return namedParameterJdbcTemplate.getJdbcOperations().update(sql);
	}

	public int deleteById(int id) {
		String sql = "delete from poller_item where id=:id";
		
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("id", id);
		
		return namedParameterJdbcTemplate.update(sql, parameter);
	}

	public int deleteByGroupId(int groupId) {
		String sql = "delete from poller_item where poller_group_id=:poller_group_id";
		
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("poller_group_id", groupId);
		
		return namedParameterJdbcTemplate.update(sql, parameter);
	}

	public int insertItem(PollerItem pollerItem) {
		String sql = "insert into poller_item(poller_group_id,poller_name,poller_type,poller_cmd,poller_interval)" +
				" values(:poller_group_id,:poller_name,:poller_type,:poller_cmd,:poller_interval)";
		
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("poller_group_id", pollerItem.getGroupId());
		parameter.addValue("poller_name", pollerItem.getPollerName());
		parameter.addValue("poller_type", pollerItem.getStatsType().toString());
		parameter.addValue("poller_cmd", pollerItem.getPollerCommand());
		parameter.addValue("poller_interval", pollerItem.getPollerInterval());
		
		return namedParameterJdbcTemplate.update(sql, parameter);
	}

	public PollerItem loadById(int id) {
		String sql = "select id,poller_group_id,poller_name,poller_type,poller_cmd,poller_interval" +
				" from poller_item where id=:id";
		
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("id", id);
		
		List<PollerItem> pollerItems = namedParameterJdbcTemplate.query(sql, parameter, rowMapper);
		return pollerItems == null || pollerItems.isEmpty() ? null : pollerItems.get(0);
	}

	public List<PollerItem> loadByGroupId(int groupId) {
		String sql = "select id,poller_group_id,poller_name,poller_type,poller_cmd,poller_interval" +
				" from poller_item where poller_group_id=:poller_group_id";
		
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("poller_group_id", groupId);
		
		return namedParameterJdbcTemplate.query(sql, parameter, rowMapper);
	}

	public List<PollerItem> loadAll() {
		String sql = "select id,poller_group_id,poller_name,poller_type,poller_cmd,poller_interval from poller_item";
		
		return namedParameterJdbcTemplate.getJdbcOperations().query(sql, rowMapper);
	}

	public int updateItem(PollerItem pollerItem, int pollerItemId) {
		String sql = "update poller_item set poller_group_id=:poller_group_id, poller_name=:poller_name, " +
				"poller_type=:poller_type, poller_cmd=:poller_cmd, poller_interval=:poller_interval " +
				"where id=:id";
		
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("poller_group_id", pollerItem.getGroupId());
		parameter.addValue("poller_name", pollerItem.getPollerName());
		parameter.addValue("poller_type", pollerItem.getStatsType().toString());
		parameter.addValue("poller_cmd", pollerItem.getPollerCommand());
		parameter.addValue("poller_interval", pollerItem.getPollerInterval());
		parameter.addValue("id", pollerItemId);
		
		return namedParameterJdbcTemplate.update(sql, parameter);
	}


}
