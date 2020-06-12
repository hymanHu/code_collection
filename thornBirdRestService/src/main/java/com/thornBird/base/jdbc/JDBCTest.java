package com.thornBird.base.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thornBird.serviceModel.test.City;

/**
 * @Description: JDBC Test
 * @author: HymanHu
 * @date: 2019-08-07 14:49:49
 */
public class JDBCTest {
	private String url = "jdbc:mysql://localhost:3306/maindb?characterEncoding=utf-8";
	private String user = "hymanHu";
	private String psd = "Hj@549698";
	
	public static void main(String[] args) {
		JDBCTest jdbcTest = new JDBCTest();
		jdbcTest.updateTest();
		jdbcTest.queryTest();
	}
	
	public void updateTest() {
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, psd);
			connection.setAutoCommit(false);
			String sql = "update city set localCityName = '北京' where cityId = ?;";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, 1891);
			ps.execute();
			System.out.println("Update Count: " + ps.getUpdateCount());
			connection.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void queryTest() {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, psd);
			String sql = "select * from city where countryId = 42 limit 1, 10;";
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			List<City> cities = new ArrayList<City>();
			while(rs.next()) {
				City city = new City();
				city.setCityId(rs.getInt("cityId"));
				city.setCityName(rs.getString("cityName"));
				city.setCountryId(rs.getInt("countryId"));
				city.setDateCreated(rs.getDate("dateCreated"));
				city.setDateModified(rs.getDate("dateModified"));
				city.setLocalCityName(rs.getString("localCityName"));
				city.setPopulation(rs.getLong("population"));
				cities.add(city);
			}
			ObjectMapper objectMapper = new ObjectMapper();
			System.out.println(objectMapper.writeValueAsString(cities));
		} catch (ClassNotFoundException | SQLException | JsonProcessingException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
