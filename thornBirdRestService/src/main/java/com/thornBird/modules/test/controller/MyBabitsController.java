package com.thornBird.modules.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.thornBird.modules.test.beans.CountryAndCities;
import com.thornBird.modules.test.beans.WorldCity;
import com.thornBird.modules.test.services.MyBatisService;
import com.thornBird.serviceModel.site.MCity;
import com.thornBird.serviceModel.site.MCountry;
import com.thornBird.serviceModel.test.City;
import com.thornBird.serviceModel.test.Country;
import com.thornBird.utils.ConstantPool;

@RestController
@RequestMapping("/myBatis")
public class MyBabitsController {
	
	@Autowired
	private MyBatisService myBatisService;
	
	/**
	 * http://dev-services.thornBird.com/myBatis/cities/42
	 * https://dev-services.thornBird.com:8085/myBatis/cities/42
	 * @return
	 */
	@RequestMapping(value="/cities/{countryId}", method=RequestMethod.GET)
	public List<City> getCitiesByCountryId(@PathVariable("countryId") int countryId) {
		return myBatisService.getCitiesByCountryId(countryId);
	}
	
	/**
	 * http://dev-services.thornBird.com/myBatis/citiesPage?pageNumber=1&pageSize=5
	 * https://dev-services.thornBird.com:8085/myBatis/citiesPage?pageNumber=1&pageSize=5
	 */
	@RequestMapping(value="/citiesPage", method=RequestMethod.GET)
	public Page<City> getCitiesBypage(
			@RequestParam(value="pageNumber", required=false, 
				defaultValue="" + ConstantPool.DEFAULT_PAGE_NUMBER) int pageNumber, 
			@RequestParam(value="pageSize", required=false, 
				defaultValue="" + ConstantPool.DEFAULT_PAGE_SIZE) int pageSize) {
		return myBatisService.getCitiesBypage(pageNumber, pageSize);
	}
	
	/**
	 * http://dev-services.thornBird.com/myBatis/country/42
	 * https://dev-services.thornBird.com:8085/myBatis/country/42
	 */
	@RequestMapping(value="/country/{countryId}", method=RequestMethod.GET)
	public Country getCountryByCountryId(@PathVariable("countryId") int countryId) {
		return myBatisService.getCountryByCountryId(countryId);
	}
	
	/**
	 * http://dev-services.thornBird.com/myBatis/countryAndCities/42
	 * https://dev-services.thornBird.com:8085/myBatis/countryAndCities/42
	 */
	@RequestMapping(value="/countryAndCities/{countryId}", method=RequestMethod.GET)
	public CountryAndCities getCitiesAndCountries(@PathVariable("countryId") int countryId) {
		return myBatisService.getCitiesAndCountries(countryId);
	}
	
	/**
	 * http://dev-services.thornBird.com/myBatis/worldCitiesPage?pageNumber=1&pageSize=5
	 * https://dev-services.thornBird.com:8085/myBatis/worldCitiesPage?pageNumber=1&pageSize=5
	 */
	@RequestMapping(value="/worldCitiesPage", method=RequestMethod.GET)
	public Page<WorldCity> getCitiesInWorldDb(
			@RequestParam(value="pageNumber", required=false, 
				defaultValue="" + ConstantPool.DEFAULT_PAGE_NUMBER) int pageNumber, 
			@RequestParam(value="pageSize", required=false, 
				defaultValue="" + ConstantPool.DEFAULT_PAGE_SIZE) int pageSize) {
		return myBatisService.getCitiesInWorldDb(pageNumber, pageSize);
	}
	
	/**
	 * http://dev-services.thornBird.com/myBatis/mCountry/522
	 * https://dev-services.thornBird.com:8085/myBatis/mCountry/522
	 */
	@RequestMapping(value="/mCountry/{countryId}", method=RequestMethod.GET)
	public MCountry getCountryByCountryId2(@PathVariable(value="countryId") int countryId) {
		return myBatisService.getCountryByCountryId2(countryId);
	}
	
	/**
	 * http://dev-services.thornBird.com/myBatis/mCities/522
	 * https://dev-services.thornBird.com:8085/myBatis/mCities/522
	 */
	@RequestMapping(value="/mCities/{countryId}", method=RequestMethod.GET)
	public List<MCity> getCitiesByCountryId2(@PathVariable(value="countryId") int cityId) {
		return myBatisService.getCitiesByCountryId2(cityId);
	}
	
	/**
	 * http://dev-services.thornBird.com/myBatis/countryAndCities2/522
	 * https://dev-services.thornBird.com:8085/myBatis/countryAndCities2/522
	 */
	@RequestMapping(value="/countryAndCities2/{countryId}", method=RequestMethod.GET)
	public CountryAndCities getCitiesAndCountries2(@PathVariable("countryId") int countryId) {
		return myBatisService.getCitiesAndCountries2(countryId);
	}

}
