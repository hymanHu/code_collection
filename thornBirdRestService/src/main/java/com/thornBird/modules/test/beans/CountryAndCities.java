package com.thornBird.modules.test.beans;

import java.io.Serializable;
import java.util.List;

import com.thornBird.serviceModel.site.MCity;
import com.thornBird.serviceModel.site.MCountry;
import com.thornBird.serviceModel.test.City;
import com.thornBird.serviceModel.test.Country;

/**
 * @Description: Country And Cities
 * @author: HymanHu
 * @date: 2019-08-10 13:17:03
 */
public class CountryAndCities implements Serializable {

	private static final long serialVersionUID = 1L;

	private Country country;
	private List<City> cities;
	
	private MCountry mCountry;
	private List<MCity> mCities;

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

	public MCountry getmCountry() {
		return mCountry;
	}

	public void setmCountry(MCountry mCountry) {
		this.mCountry = mCountry;
	}

	public List<MCity> getmCities() {
		return mCities;
	}

	public void setmCities(List<MCity> mCities) {
		this.mCities = mCities;
	}

}
