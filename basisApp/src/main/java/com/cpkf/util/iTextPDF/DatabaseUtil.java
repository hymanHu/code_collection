package com.cpkf.util.iTextPDF;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseUtil {

	private Connection connection;
	private Statement st;
	private PreparedStatement ps;

	public DatabaseUtil(String driverString, String urlString, String userName, String password)
			throws SQLException {
		try {
			Class.forName(driverString);
			connection = DriverManager.getConnection(urlString, userName, password);
		} catch (Exception e) {
			throw new SQLException("Database driver not found.");
		}
	}

	public DatabaseUtil(String driverString, String urlString) throws SQLException {
		try {
			Class.forName(driverString);
			connection = DriverManager.getConnection(urlString);
		} catch (Exception e) {
			throw new SQLException("Database driver not found.");
		}

	}

	public void close() throws SQLException {
		if (st != null) {
			st.close();
		}

		if (ps != null) {
			ps.close();
		}

		if (connection != null) {
			connection.close();
		}
	}

	public ResultSet query(String sql) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(sql);
		return ps.executeQuery();
	}

	public void update(String expression) throws SQLException {
		st = connection.createStatement();
		int i = st.executeUpdate(expression);
		close();
		if (i == -1) {
			throw new SQLException("db error : " + expression);
		}
	}
	
	public List<Map<String, Object>> convertToList(ResultSet rs) throws SQLException {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> recordMap = null;
		
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		
		while (rs.next()) {
			recordMap = new TreeMap<String, Object>();
			for (int i = 1; i <= columnCount; i ++) {
				recordMap.put(rsmd.getColumnName(i), rs.getObject(i));
			}
			result.add(recordMap);
		}
		
		return result;
	}
	
	public JSONArray convertToJson(ResultSet rs) throws SQLException, JSONException {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		
		while(rs.next()) {
			jsonObject = new JSONObject();
			for (int i = 1; i <= columnCount; i++) {
				jsonObject.put(rsmd.getColumnName(i), rs.getObject(i));
			}
			jsonArray.put(jsonObject);
		}
		
		return jsonArray;
	}

	public static void main(String[] args) {
		try {
			DatabaseUtil dc = new DatabaseUtil("com.mysql.jdbc.Driver",
					"jdbc:mysql://localhost:3306/notepad?characterEncoding=utf-8", "root", "");
			ResultSet rs = dc.query("SELECT * FROM account WHERE available = TRUE;");
			System.out.println(dc.convertToList(rs));
			rs = dc.query("SELECT * FROM account WHERE available = TRUE;");
			JSONArray jSONArray = dc.convertToJson(rs);
			System.out.println(jSONArray);
			dc.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
