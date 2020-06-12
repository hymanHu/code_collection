package com.thornBird.modules.site.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thornBird.modules.site.services.SiteService;
import com.thornBird.serviceModel.site.MCity;
import com.thornBird.serviceModel.site.MCountry;

/**
 * @Description: Site Controller
 * @author: HymanHu
 * @date: 2019-08-30 16:56:26
 */
@RestController
@RequestMapping("/site")
public class SiteController {

	@Autowired
	private SiteService siteService;
	
	/**
	 * get Cities By Country Id
	 * http://dev-services.thornBird.com/site/cities/522
	 * https://dev-services.thornBird.com:8085/site/cities/522
	 */
	@RequestMapping(value="/cities/{countryId}", method=RequestMethod.GET)
	public List<MCity> getCitiesByCountryId(@PathVariable("countryId") int countryId) {
		List<MCity> cities = siteService.getCitiesByCountryId(countryId);
		return cities;
	}
	
	/**
	 * get Country By Country Id
	 * http://dev-services.thornBird.com/site/country/522
	 * https://dev-services.thornBird.com:8085/site/country/522
	 */
	@RequestMapping(value="/country/{countryId}", method=RequestMethod.GET)
	public MCountry getCountryByCountryId(@PathVariable("countryId") int countryId) {
		return siteService.getCountryByCountryId(countryId);
	}
}
