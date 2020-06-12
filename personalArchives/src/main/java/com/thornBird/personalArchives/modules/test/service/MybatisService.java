package com.thornBird.personalArchives.modules.test.service;

import java.util.List;

import com.thornBird.personalArchives.modules.account.entity.Account;
import com.thornBird.personalArchives.modules.test.entity.City;
import com.thornBird.personalArchives.modules.test.entity.Country;
import com.thornBird.personalArchives.modules.test.vo.CountryAndCities;

/**
 * @Description: Mybatis Service
 * @author: HymanHu
 * @date: 2019-09-18 16:07:34
 */
public interface MybatisService {
	
	List<City> getCitiesByCountryId(int countryId);
	
	Country getCountryById(int countryId);
	
	CountryAndCities getCountryAndCities(int countryId);
	
	void addAccount(Account account);
	
	void updateAccount(Account account);
	
	void deleteAccount(int accountId);
}
