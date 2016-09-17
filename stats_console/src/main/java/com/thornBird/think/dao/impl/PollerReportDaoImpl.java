package com.thornBird.think.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.thornBird.think.dao.IPollerReportDao;
import com.thornBird.think.model.PollerType;
import com.thornBird.think.model.mysqlModel.PollerReport;

@Repository("pollerReportDao")
public class PollerReportDaoImpl implements IPollerReportDao {
	
	@Resource(name="namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private RowMapper<PollerReport> rowMapper = new RowMapper<PollerReport>() {

		public PollerReport mapRow(ResultSet rs, int rowNumber) throws SQLException {
			PollerReport pollerReport = new PollerReport();
			pollerReport.setId(rs.getInt("id"));
			pollerReport.setItemId(rs.getInt("poller_item_id"));
			pollerReport.setPollertype(PollerType.valueOf(rs.getString("poller_type")));
			pollerReport.setStatsKey(rs.getString("stats_key"));
			pollerReport.setStatsValue(rs.getString("stats_value"));
			pollerReport.setStartTime(rs.getTimestamp("start_time"));
			pollerReport.setEndTime(rs.getTimestamp("end_time"));

			return pollerReport;
		}
	};

	public List<PollerReport> loadReport(int itemId, PollerType pollerType, String startTime, String endTime) {
		String sql = "select id, poller_item_id, poller_type, stats_key, stats_value, start_time, end_time from report " +
				"WHERE poller_item_id=:poller_item_id and poller_type=:poller_type " +
				"and start_time between :start and :end";
		
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("poller_item_id", itemId);
		parameter.addValue("poller_type", pollerType.toString());
		parameter.addValue("start", startTime);
		parameter.addValue("end", endTime);
		
		return namedParameterJdbcTemplate.query(sql, parameter, rowMapper);
	}

	public int deleteByDateAndPollerType(Date dropDate, PollerType pollerType) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(dropDate);
		
		String sql = "delete from report where poller_type=:poller_type and end_time<DATE_FORMAT(:date,'%Y-%m-%d %H:%i:%S')";
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("date", date);
		parameter.addValue("poller_type", pollerType.toString());
		
		return namedParameterJdbcTemplate.update(sql, parameter);
	}

	public static void main(String[] args) {
		FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext(
				new String[] { "/src/main/webapp/WEB-INF/conf/spring/applicationContext.xml" });
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = ctx.getBean("namedParameterJdbcTemplate", NamedParameterJdbcTemplate.class);
		
		RowMapper<PollerReport> rowMapper = new RowMapper<PollerReport>() {

			public PollerReport mapRow(ResultSet rs, int rowNumber) throws SQLException {
				PollerReport pollerReport = new PollerReport();
				pollerReport.setId(rs.getInt("id"));
				pollerReport.setItemId(rs.getInt("poller_item_id"));
				pollerReport.setPollertype(PollerType.valueOf(rs.getString("poller_type")));
				pollerReport.setStatsKey(rs.getString("stats_key"));
				pollerReport.setStatsValue(rs.getString("stats_value"));
				pollerReport.setStartTime(rs.getTimestamp("start_time"));
				pollerReport.setEndTime(rs.getTimestamp("end_time"));

				return pollerReport;
			}
		};
		
		String sql = "select id, poller_item_id, poller_type, stats_key, stats_value, start_time, end_time from report " +
				"WHERE poller_item_id=:poller_item_id and poller_type=:poller_type " +
				"and start_time between :start and :end";
		
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("poller_item_id", 38);
		parameter.addValue("poller_type", "DAY");
		parameter.addValue("start", "2013-02-18 00:00:00");
		parameter.addValue("end", "2013-02-28 23:59:59");
		
		List<PollerReport> pollerReports = namedParameterJdbcTemplate.query(sql, parameter, rowMapper);
		System.out.println(pollerReports.toString());
	}
	
}
