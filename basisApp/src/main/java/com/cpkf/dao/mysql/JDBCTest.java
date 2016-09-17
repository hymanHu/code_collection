package com.cpkf.dao.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**  
 * Filename:    JDBCTest.java
 * Description: 测试mysql、oracle非批量插入数据、批量插入数据性能比较
 * Company:     
 * @author:     Jiang.hu
 * @version:    1.0
 * Create at:   2011-4-2 下午01:59:45
 * modified:    
 */
public class JDBCTest {
	public static void main(String[] args) {
//		JDBCTest.test_mysql();
		JDBCTest.test_mysql_batch();
	}
	public static void test_mysql(){
		/*
		 * "jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=UTF-8"
		 * 在xml文件中有以下几类字符要进行转义替换
		 * &lt; <
		 * &gt; >
		 * &amp; &
		 * &apos; '
		 * &quot; "
		 */
		String url = "jdbc:mysql://localhost:3306/test?characterEncoding=utf-8";
		String user = "root";
		String password = "root";
		Connection co = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			co = DriverManager.getConnection(url, user, password);
			co.setAutoCommit(false);
			String sql = "insert into user(userID,name) values(?,?)";
			ps = co.prepareStatement(sql);
			long a = System.currentTimeMillis();
			for(int i = 0;i < 10000;i ++){
				ps.setInt(1, i);
				ps.setString(2, "张三");
				ps.execute();
			}
			co.commit();
			long b = System.currentTimeMillis();
			System.out.println("mysql非批量插入10000条数据用时：" + (b - a) + "ms");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null){
					ps.close();
				}
				if(co != null){
					co.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void test_mysql_batch(){
		String url = "jdbc:mysql://localhost:3306/test?characterEncoding=utf-8";
		String userName = "root";
		String pwd = "root";
		Connection co = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			co = DriverManager.getConnection(url,userName,pwd);
			co.setAutoCommit(false);
			String sql = "insert into user(userID,name) values(?,?)";
			ps = co.prepareStatement(sql);
			long a = System.currentTimeMillis();
			for(int i = 0;i < 10000;i ++){
				ps.setInt(1, i);
				ps.setString(2, "李四");
				ps.addBatch();
			}
			ps.executeBatch();
			co.commit();
			long b = System.currentTimeMillis();
			System.out.println("mysql批量插入数据用时：" + (b -a) + "ms");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null){
					ps.close();
				}
				if(co != null){
					co.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
