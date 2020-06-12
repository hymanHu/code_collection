package com.thornBird.modules.test.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thornBird.config.dataSource.aopPart.DataBaseAnnotation;
import com.thornBird.modules.test.beans.CountryAndCities;
import com.thornBird.modules.test.dao.mainDb.JpaCityDao;
import com.thornBird.modules.test.dao.worldDb.JpaWCityDao;
import com.thornBird.modules.test.services.JpaService;
import com.thornBird.serviceModel.test.City;
import com.thornBird.serviceModel.test.WCity;

/**
 * @Description: Jpa Service Impl
 * @author: HymanHu
 * @date: 2019-08-14 10:01:15
 */
@Service
public class JpaServiceImpl implements JpaService {
	
	@Autowired
	private JpaCityDao jpaDao;
	@Autowired
	private JpaWCityDao jpaWCityDao;

	@Override
	@DataBaseAnnotation("maindb")
	public List<City> getCitiesByPage(int page, int pageSize) {
		City city = new City();
		city.setCountryId(42);
//		city.setLocalCityName("beijing");
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("localCityName", ExampleMatcher.GenericPropertyMatchers.startsWith())
				.withIgnorePaths("population", "cityId"); // 这两个字段不参与匹配
		Example<City> example = Example.of(city, matcher);
		Sort sort = new Sort(Sort.Direction.ASC, "cityName");
		PageRequest pageRequest = PageRequest.of(page, pageSize, sort);
		return Optional.ofNullable(jpaDao.findAll(example, pageRequest).getContent()).orElse(Collections.emptyList());
	}
	
	@Override
	@DataBaseAnnotation("world")
	public List<WCity> getCitiesByCountryCode(String countryCode) {
		return Optional.ofNullable(jpaWCityDao.getCitiesByCountryCode(countryCode)).orElse(Collections.emptyList());
	}
	
	@Override
	public List<City> getCitiesByCountryId(int countryId) {
		return Optional.ofNullable(jpaDao.getCitiesByCountryId(countryId)).orElse(Collections.emptyList());
	}

	@Override
	public List<CountryAndCities> getCitiesAndCountiesBySql(int countryId, int start, int end) {
		return Optional.ofNullable(jpaDao.getCitiesAndCountiesBySql(countryId, start, end))
				.orElse(Collections.emptyList());
	}

	@Override
	public List<City> getCitiesBySql(int countryId, int start, int end) {
		return Optional.ofNullable(jpaDao.getCitiesBySql(countryId, start, end)).orElse(Collections.emptyList());
	}

	@Override
	public City getCityById(int cityId) {
		return jpaDao.getOne(cityId);
	}

	@Override
	@Transactional
	public City updateCity(City city) {
		city = jpaDao.saveAndFlush(city);
		System.out.println(Collections.emptyList().get(2));
		return city;
//		return cityDaoApi.saveAndFlush(city);
	}

}
