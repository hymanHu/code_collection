package com.thornBird.modules.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.thornBird.modules.test.beans.CountryAndCities;
import com.thornBird.modules.test.services.JpaService;
import com.thornBird.serviceModel.test.City;
import com.thornBird.serviceModel.test.WCity;
import com.thornBird.utils.ConstantPool;

/**
 * @Description: Jpa Controller
 * @author: HymanHu
 * @date: 2019-08-08 22:00:01
 */
@RestController
@RequestMapping("/jpa")
public class JpaController {
	
	@Autowired
	private JpaService jpaService;
	
	/**
	 * http://dev-services.thornBird.com/jpa/cities
	 * https://dev-services.thornBird.com:8085/jpa/cities
	 */
	@RequestMapping(value="/cities", method=RequestMethod.GET)
	public List<City> getCitiesByPage() {
		return jpaService.getCitiesByPage(ConstantPool.DEFAULT_PAGE_NUMBER, ConstantPool.DEFAULT_PAGE_SIZE);
	}
	
	/**
	 * http://dev-services.thornBird.com/jpa/cities/42
	 * https://dev-services.thornBird.com:8085/jpa/cities/42
	 */
	@RequestMapping(value="/cities/{countryId}", method=RequestMethod.GET)
	public List<City> getCitiesByCountryId(@PathVariable("countryId") int countryId) {
		return jpaService.getCitiesByCountryId(countryId);
	}
	
	/**
	 * http://dev-services.thornBird.com/jpa/cities/sql/42
	 * https://dev-services.thornBird.com:8085/jpa/cities/sql/42
	 */
	@RequestMapping(value="/cities/sql/{countryId}", method=RequestMethod.GET)
	public List<City> getCitiesBySql(@PathVariable("countryId") int countryId) {
		return jpaService.getCitiesBySql(countryId, 
				(ConstantPool.DEFAULT_PAGE_NUMBER - 1) * ConstantPool.DEFAULT_PAGE_SIZE, 
				ConstantPool.DEFAULT_PAGE_NUMBER * ConstantPool.DEFAULT_PAGE_SIZE);
	}
	
	/**
	 * 连表查询接口，未调试通过
	 * http://dev-services.thornBird.com/jpa/citiesAndCounties/sql/42
	 * https://dev-services.thornBird.com:8085/jpa/citiesAndCounties/sql/42
	 */
	@RequestMapping(value="/citiesAndCounties/sql/{countryId}", method=RequestMethod.GET)
	public List<CountryAndCities> getCitiesAndCountiesBySql(@PathVariable("countryId") int countryId) {
		return jpaService.getCitiesAndCountiesBySql(countryId, 
				(ConstantPool.DEFAULT_PAGE_NUMBER - 1) * ConstantPool.DEFAULT_PAGE_SIZE, 
				ConstantPool.DEFAULT_PAGE_NUMBER * ConstantPool.DEFAULT_PAGE_SIZE);
	}
	
	/**
	 * http://dev-services.thornBird.com/jpa/city/1891
	 * https://dev-services.thornBird.com:8085/jpa/city/1891
	 */
	@RequestMapping(value="/city/{cityId}", method=RequestMethod.GET)
	public City getCityById(@PathVariable("cityId") int cityId) {
		return jpaService.getCityById(cityId);
	}
	
	/**
	 * http://dev-services.thornBird.com/jpa/city/save
	 * https://dev-services.thornBird.com:8085/jpa/city/save
	 */
	@RequestMapping(value="/city/save", method=RequestMethod.POST, produces="application/json")
	public City updateCity(@RequestBody City city) {
		return jpaService.updateCity(city);
	}
	
	/**
	 * http://dev-services.thornBird.com/jpa/wCites/chn
	 * https://dev-services.thornBird.com:8085/jpa/wCites/chn
	 */
	@RequestMapping(value="/wCites/{countryCode}", method=RequestMethod.GET)
	public List<WCity> getCitiesByCountryCode(@PathVariable("countryCode") String countryCode) {
		return jpaService.getCitiesByCountryCode(countryCode);
	}
}
