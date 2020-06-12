package com.thornBird.modules.test.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.thornBird.config.dataSource.aopPart.DataBaseAnnotation;
import com.thornBird.modules.test.beans.CountryAndCities;
import com.thornBird.modules.test.beans.WorldCity;
import com.thornBird.modules.test.dao.mainDb.MyBatisDaoForMainDb;
import com.thornBird.modules.test.dao.worldDb.MyBatisDaoForWorldDb;
import com.thornBird.modules.test.services.MyBatisService;
import com.thornBird.serviceModel.site.MCity;
import com.thornBird.serviceModel.site.MCountry;
import com.thornBird.serviceModel.test.City;
import com.thornBird.serviceModel.test.Country;

/**
 * @Description: MyBatis Service Impl
 * @author: HymanHu
 * @date: 2019-08-15 21:25:09
 */
@Service
public class MyBatisServiceImpl implements MyBatisService {
	
	@Autowired
	private MyBatisDaoForMainDb myBatisDao;
	@Autowired
	private MyBatisDaoForWorldDb myBatisDaoForWorldDb;

	@Override
	@DataBaseAnnotation("maindb")
	@Transactional(value="mybatisTransactionManager")
	public List<City> getCitiesByCountryId(int countryId) {
		return Optional.ofNullable(myBatisDao.getCitiesByCountryId(countryId)).orElse(Collections.emptyList());
	}

	@Override
	public Page<City> getCitiesBypage(int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		return myBatisDao.getCitiesByPage(pageNumber, pageSize);
	}

	@Override
	public Country getCountryByCountryId(int countryId) {
		return myBatisDao.getCountryByCountryId(countryId);
	}

	@Override
	public CountryAndCities getCitiesAndCountries(int countryId) {
		return myBatisDao.getCitiesAndCountries(countryId);
	}

	@Override
	@DataBaseAnnotation("world")
	public Page<WorldCity> getCitiesInWorldDb(int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		return myBatisDaoForWorldDb.getCitiesInWorldDb(pageNumber, pageSize);
	}

	@Override
	public MCountry getCountryByCountryId2(int countryId) {
		return myBatisDao.getCountryByCountryId2(countryId);
	}

	@Override
	public List<MCity> getCitiesByCountryId2(int countryId) {
		return Optional.ofNullable(myBatisDao.getCitiesByCountryId2(countryId)).orElse(Collections.emptyList());
	}

	@Override
	public CountryAndCities getCitiesAndCountries2(int countryId) {
		return myBatisDao.getCountryAndCities2(countryId);
	}
}
