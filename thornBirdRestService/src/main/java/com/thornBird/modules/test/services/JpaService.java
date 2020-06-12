package com.thornBird.modules.test.services;

import java.util.List;

import com.thornBird.modules.test.beans.CountryAndCities;
import com.thornBird.serviceModel.test.City;
import com.thornBird.serviceModel.test.WCity;

/**
 * @Description: Jpa Service
 * @author: HymanHu
 * @date: 2019-08-05 20:13:18
 */
public interface JpaService {

	public List<City> getCitiesByPage(int page, int pageSize);
	
	public List<City> getCitiesByCountryId(int countryId);
	
	public List<City> getCitiesBySql(int countryId, int start, int end);
	
	public List<CountryAndCities> getCitiesAndCountiesBySql(int countryId, int start, int end);

	public City getCityById(int cityId);
	
	public City updateCity(City city);
	
	public List<WCity> getCitiesByCountryCode(String countryCode);

}
