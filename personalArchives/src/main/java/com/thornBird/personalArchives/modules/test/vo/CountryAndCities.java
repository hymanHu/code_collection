package com.thornBird.personalArchives.modules.test.vo;

import java.io.Serializable;
import java.util.List;

import com.thornBird.personalArchives.modules.test.entity.City;
import com.thornBird.personalArchives.modules.test.entity.Country;

/**
 * @Description: 
 * @author: HymanHu
 * @date: 2019-09-19 13:07:48
 */
public class CountryAndCities implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Country country;
	private List<City> cities;

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}
}
