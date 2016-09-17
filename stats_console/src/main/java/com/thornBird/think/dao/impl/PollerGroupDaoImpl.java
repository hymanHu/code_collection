package com.thornBird.think.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.thornBird.think.dao.IPollerGroupDao;
import com.thornBird.think.model.mysqlModel.PollerGroup;

@Repository("pollerGroupDao")
public class PollerGroupDaoImpl implements IPollerGroupDao {
	
	@Resource(name="namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private RowMapper<PollerGroup> rowMapper = new RowMapper<PollerGroup>() {
		
		public PollerGroup mapRow(ResultSet rs, int rowNumber) throws SQLException {
			PollerGroup pollerGroup = new PollerGroup();
			pollerGroup.setId(rs.getInt(1));
			pollerGroup.setGroupName(rs.getString(2));
			pollerGroup.setGroupValue(rs.getString(3));
			return pollerGroup;
		}
	};
	
	public int deleteAll() {
		String sql = "delete from poller_group";
		
		return namedParameterJdbcTemplate.getJdbcOperations().update(sql);
	}

	public int deleteById(int id) {
		String sql = "delete from poller_group where id=:id";
		
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("id", id);
		
		return namedParameterJdbcTemplate.update(sql, parameter);
	}

	public int insertGroup(String groupName, String groupValue) {
		String sql = "insert into poller_group(group_name, group_value) values(:group_name, :group_value)";
		
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("group_name", groupName);
		parameter.addValue("group_value", groupValue);
		
		return namedParameterJdbcTemplate.update(sql, parameter);
	}

	public int updateGroup(int id, String groupName, String groupValue) {
		String sql = "update poller_group set group_name=:group_name, group_value=:group_value where id=:id";
		
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("id", id);
		parameter.addValue("group_name", groupName);
		parameter.addValue("group_value", groupValue);
		
		return namedParameterJdbcTemplate.update(sql, parameter);
	}

	public List<PollerGroup> loadAll() {
		String sql = "select id, group_name, group_value from poller_group";
		
		return namedParameterJdbcTemplate.getJdbcOperations().query(sql, rowMapper);
	}

	public static void main(String[] args) {
		FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext(
				new String[] { "/src/main/webapp/WEB-INF/conf/spring/applicationContext.xml" });
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = ctx.getBean("namedParameterJdbcTemplate", NamedParameterJdbcTemplate.class);
		
		String sql = "insert into poller_group(group_name, group_value) values(:group_name, :group_value)";
		
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("group_name", "hj");
		parameter.addValue("group_value", "hyman");
		
		namedParameterJdbcTemplate.update(sql, parameter);
	}
}
