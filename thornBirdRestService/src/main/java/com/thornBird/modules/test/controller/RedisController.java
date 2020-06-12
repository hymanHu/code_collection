package com.thornBird.modules.test.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thornBird.serviceModel.test.City;
import com.thornBird.utils.RedisUtils;

@RestController
@RequestMapping("/redis")
public class RedisController {
	
	@Autowired
	private RedisUtils redisUtils;
	// 如果不用utils，直接饮用RedisTemplate
//	@Autowired
//	private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * http://dev-services.thornBird.com/redis/cities
	 * https://dev-services.thornBird.com:8085/redis/cities
	 */
	@RequestMapping("/cities")
	public List<Object> getCities(){
		redisUtils.delete("cities");
		List<Object> cities = new ArrayList<Object>();
		IntStream.range(1, 11).forEach(item -> {
			City city = new City();
			city.setCityId(item);
			city.setCityName("cityName" + item);
			city.setCountryId(item + 100);
			city.setDateCreated(new Date());
			city.setLocalCityName("localCityName" + item);
			city.setPopulation(10000 + item);
			cities.add(city);
		});
		redisUtils.setList("cities", cities);
		System.out.println("------------------------------" + redisUtils.getListSize("cities"));
		return redisUtils.getListRang("cities", 0, cities.size() - 1);
	}
}
