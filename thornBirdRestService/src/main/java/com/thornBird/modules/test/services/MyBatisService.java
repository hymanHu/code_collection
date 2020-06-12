package com.thornBird.modules.test.services;

import java.util.List;

import com.github.pagehelper.Page;
import com.thornBird.modules.test.beans.CountryAndCities;
import com.thornBird.modules.test.beans.WorldCity;
import com.thornBird.serviceModel.site.MCity;
import com.thornBird.serviceModel.site.MCountry;
import com.thornBird.serviceModel.test.City;
import com.thornBird.serviceModel.test.Country;

/**
 * @Description: MyBatis Service
 * @author: HymanHu
 * @date: 2019-08-15 21:24:57
 */
public interface MyBatisService {
	
	public List<City> getCitiesByCountryId(int countryId);
	
	Page<City> getCitiesBypage(int pageNumber, int pageSize);
	
	public Country getCountryByCountryId(int countryId);
	
	public CountryAndCities getCitiesAndCountries(int countryId);
	
	Page<WorldCity> getCitiesInWorldDb(int pageNumber, int pageSize);
	
	MCountry getCountryByCountryId2(int countryId);
	
	List<MCity> getCitiesByCountryId2(int countryId);
	
	public CountryAndCities getCitiesAndCountries2(int countryId);
}
