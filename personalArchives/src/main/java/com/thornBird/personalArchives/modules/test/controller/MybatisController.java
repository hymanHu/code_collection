package com.thornBird.personalArchives.modules.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thornBird.personalArchives.modules.account.entity.Account;
import com.thornBird.personalArchives.modules.test.entity.City;
import com.thornBird.personalArchives.modules.test.entity.Country;
import com.thornBird.personalArchives.modules.test.service.MybatisService;
import com.thornBird.personalArchives.modules.test.vo.CountryAndCities;

@Controller
@RequestMapping("/mybatis")
public class MybatisController {
	
	@Autowired
	private MybatisService mybatisService;

	@RequestMapping("/cities/{countryId}")
	@ResponseBody
	public List<City> getCitiesByCountryId(@PathVariable("countryId") int countryId) {
		return mybatisService.getCitiesByCountryId(countryId);
	}
	
	@RequestMapping("/country/{countryId}")
	@ResponseBody
	public Country getCountryById(@PathVariable("countryId") int countryId) {
		return mybatisService.getCountryById(countryId);
	}
	
	@RequestMapping("/countryAndCities/{countryId}")
	@ResponseBody
	public CountryAndCities getCountryAndCities(@PathVariable("countryId") int countryId) {
		return mybatisService.getCountryAndCities(countryId);
	}
	
	@RequestMapping(value="/account", method=RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public void addAccount(@RequestBody Account account) {
		mybatisService.addAccount(account);
	}
	
	@RequestMapping(value="/account", method=RequestMethod.PUT, consumes="application/json")
	@ResponseBody
	public void updateAccount(@RequestBody Account account) {
		mybatisService.updateAccount(account);
	}
	
	@RequestMapping(value="/account/{accountId}", method=RequestMethod.DELETE)
	@ResponseBody
	public void deleteAccount(@PathVariable("accountId") int accountId) {
		mybatisService.deleteAccount(accountId);
	}
}
