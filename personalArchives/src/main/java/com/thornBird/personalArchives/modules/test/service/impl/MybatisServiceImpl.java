package com.thornBird.personalArchives.modules.test.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thornBird.personalArchives.modules.account.entity.Account;
import com.thornBird.personalArchives.modules.test.dao.MybatisDao;
import com.thornBird.personalArchives.modules.test.entity.City;
import com.thornBird.personalArchives.modules.test.entity.Country;
import com.thornBird.personalArchives.modules.test.service.MybatisService;
import com.thornBird.personalArchives.modules.test.vo.CountryAndCities;

@Service
public class MybatisServiceImpl implements MybatisService {

	@Autowired
	private MybatisDao mybatisDao;
	
	@Override
	public List<City> getCitiesByCountryId(int countryId) {
		return Optional.ofNullable(mybatisDao.getCitiesByCountryId(countryId)).orElse(Collections.emptyList());
	}

	@Override
	public Country getCountryById(int countryId) {
//		return mybatisDao.getCountryById(countryId);
		return mybatisDao.getCountryByCountryId2(countryId);
	}

	@Override
	public CountryAndCities getCountryAndCities(int countryId) {
		return mybatisDao.getCountryAndCities(countryId);
	}

	@Override
	public void addAccount(Account account) {
		mybatisDao.addAccount(account);
	}

	@Override
	public void updateAccount(Account account) {
		mybatisDao.updateAccount(account);
	}

	@Override
	public void deleteAccount(int accountId) {
		mybatisDao.deleteAccount(accountId);
	}
}
