package com.cpkf.dao.mongo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

/**
 * MongoDB安装<br>
 * 根据操作系统下载不同版本的MongoDB，解压<br>
 * 创建文件夹 mkdir /data/db<br>
 * 配置环境变量 bin目录<br>
 * mongo服务端(指定data目录，默认目录/data/db)：mongod -dbpath "D:\mongodb-win32-x86_64-2.2.3\data"<br>
 * mongo客户端：mongo<br>
 * 
 * MongoDB规则<br>
 * 数据库(db)--数据集(collections)--数据(JSON)<br>
 * 
 * MongoDB常用命令<br>
 * db.addUser('name','pwd') 添加用户<br>
 * db.system.users.find() 查看所有用户<br>
 * db.auth('name','pwd') 用户验证<br>
 * db.removeUser('name') 移除用户<br>
 * show users 查看所有用户<br>
 * show dbs 查看所有数据库<br>
 * show collections 查看所有数据集<br>
 * db.printCollectionStats() 查看数据集状态<br>
 * db.foo.find() 查询所有<br>
 * 
 * @author hyman
 * 
 */
public class MongoDBTest {
	
	private final static Logger logger = LoggerFactory.getLogger(MongoDBTest.class);
	
	private Mongo mongo;
	private DB db;
	
	public MongoDBTest() {
		initMongoDB();
	}
	
	public void initMongoDB(){
		StringBuffer sb = new StringBuffer();
		InputStream is = null;
		BufferedReader br = null;
		try {
			// 建立数据库连接
			mongo = new Mongo("localhost", 27017);
			db = mongo.getDB("test");
			
			// 添加用户并验证用户权限
			char[] pwd = {'r', 'o', 'o', 't'};
			db.addUser("root", pwd);
			boolean flag = db.authenticate("root", pwd);
			
			// 初始化当前数据库下数据集
//			is = MongoDBTest.class.getClassLoader().getResourceAsStream("testFile/mongoTest.sql");
//			br = new BufferedReader(new InputStreamReader(is, "utf-8"));
//			String line = null;
//			while((line = br.readLine()) != null) {
//				sb.append(line);
//			}
//			logger.debug(sb.toString());
//			db.eval(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void queryTest() {
		logger.debug(String.format("%s%s%s", "=====", "查询数据部列表", "====="));
		for (String dbName : mongo.getDatabaseNames()) {
			logger.debug(dbName);
		}
		
		logger.debug(String.format("%s%s%s", "=====", "查询当前数据库下数据集列表", "====="));
		Set<String> collections = db.getCollectionNames();
		for (String collection : collections) {
			logger.debug(collection);
		}
		
		logger.debug(String.format("%s%s%s", "=====", "得到一个数据集的索引列表", "====="));
		DBCollection menuCollection = db.getCollection("menu");
		List<DBObject> menuIndexs = menuCollection.getIndexInfo();
		for (DBObject dbObject : menuIndexs) {
			logger.debug("" + dbObject);
		}
		
		logger.debug(String.format("%s%s%s", "=====", "查询数据集第一条数据", "====="));
		DBObject dbObject = menuCollection.findOne();
		logger.debug(dbObject.toString());
		
		logger.debug(String.format("%s%s%s", "=====", "查询数据集所有数据", "====="));
		List<DBObject> dbObjects = menuCollection.find().toArray();
		for (DBObject dbObject2 : dbObjects) {
			logger.debug(dbObject2.toString());
		}
		
		logger.debug(String.format("%s%s%s", "=====", "查询数据集所有数据(游标的方式)", "====="));
		DBCursor dbCursor = menuCollection.find();
		while (dbCursor.hasNext()) {
			logger.debug(dbCursor.next().toString());
		}
		
		logger.debug(String.format("%s%s%s", "=====", "查询数据量", "====="));
		logger.debug("总数据量: " + menuCollection.find().count());
		logger.debug("name=Roles数据量: " + menuCollection.find(new BasicDBObject("name", "Roles")).count());
		
		logger.debug(String.format("%s%s%s", "=====", "带条件查询", "====="));
		BasicDBObject bo = new BasicDBObject();
		bo.put("age", "12");
		dbCursor = menuCollection.find(bo);
		while (dbCursor.hasNext()) {
			logger.debug(dbCursor.next().toString());
		}
		
		logger.debug(String.format("%s%s%s", "=====", "分页查询-skip-跳过几条数据-limit-限制几条数据", "====="));
		dbCursor = menuCollection.find().skip(2).limit(2);
		while (dbCursor.hasNext()) {
			logger.debug(dbCursor.next().toString());
		}
		
		logger.debug(String.format("%s%s%s", "=====", "比较查询$gt大于, $gte大于等于, $lt小于, $lte小于等于, $in包含 (支持数字比较，不支持字符串比较) ", "====="));
		BasicDBObject bo2 = new BasicDBObject();
		bo2.put("age", new BasicDBObject("$gt", "20").append("$lt", "30"));
		dbCursor = menuCollection.find(bo2);
		while (dbCursor.hasNext()) {
			logger.debug(dbCursor.next().toString());
		}
		
		logger.debug(String.format("%s%s%s", "=====", "正则表达式查询", "====="));
		Pattern pattern = Pattern.compile("us", Pattern.CASE_INSENSITIVE);
		BasicDBObject bo3 = new BasicDBObject("name", pattern);
		dbCursor = menuCollection.find(bo3);
		while (dbCursor.hasNext()) {
			logger.debug(dbCursor.next().toString());
		}
		
	}
	
	public void updateDBTest() {
		// 创建数据库，数据集，并向数据集中插入数据
		List<DBObject> dbObjects = new ArrayList<DBObject>();
		for (int i = 0; i < 5; i++) {
			BasicDBObject dbs = new BasicDBObject();  
            dbs.put("name", "hj" + i);  
            dbs.put("age", i);  
            BasicDBObject info = new BasicDBObject();  
            info.put("x", i);  
            info.put("y", i + 1);  
            dbs.put("info", info);
            dbObjects.add(dbs);
		}
		db = mongo.getDB("aaa");
		DBCollection dbCollection = db.getCollection("user");
		dbCollection.insert(dbObjects);
		
		// 删除数据库
		logger.debug(String.format("%s%s%s", "=====", "删除前数据库列表", "====="));
		for (String dbName : mongo.getDatabaseNames()) {
			logger.debug(dbName);
		}
		mongo.dropDatabase("aaa");
		logger.debug(String.format("%s%s%s", "=====", "删除后数据库列表", "====="));
		for (String dbName : mongo.getDatabaseNames()) {
			logger.debug(dbName);
		}
	}
	
	public static void main(String[] args) {
		MongoDBTest mongoDBTest = new MongoDBTest();
		mongoDBTest.queryTest();
//		mongoDBTest.updateDBTest();
	}

}
