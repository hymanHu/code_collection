package com.thornBird.modules.test.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thornBird.modules.test.beans.MongoDbBean;

/**
 * @Description: MongoDb Controller
 * @author: HymanHu
 * @date: 2019-08-19 14:19:44
 */
@RestController
@RequestMapping("/mongodb")
public class MongoDbController {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * http://dev-services.thornBird.com/mongodb/mongoDbBean
	 * https://dev-services.thornBird.com:8085/mongodb/mongoDbBean
	 */
	@RequestMapping(value="/mongoDbBean",method=RequestMethod.GET)
	public List<MongoDbBean> getCitiesFromMongo() {
		MongoDbBean bean = new MongoDbBean();
		bean.setUserId(111);
		bean.setUserName("HymanHu");
		bean.setCreateDate(new Date());
		List<String> list = Stream.of("aaaa", "sssss", "ddddd").collect(Collectors.toList());
		bean.setMyList(list);
		Map<String, String> map = new HashMap<String, String>();
		map.put("testkey", "testvalue");
		bean.setMyMap(map);
		
		// save
		mongoTemplate.save(bean);
		
		Query query=new Query(Criteria.where("user_id").is(bean.getUserId()));
		Update update= new Update().
				set("user_name", bean.getUserName() + "111")
				.set("create_date", new Date());
		mongoTemplate.updateFirst(query, update, MongoDbBean.class);
		
//		mongoTemplate.remove(query, MongoDbBean.class);
		mongoTemplate.findOne(query , MongoDbBean.class);
		
		return mongoTemplate.find(query , MongoDbBean.class);
	}
	
}
