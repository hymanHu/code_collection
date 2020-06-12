package com.thornBird.modules.test.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thornBird.modules.test.services.MyBatisService;
import com.thornBird.serviceModel.test.City;
import com.thornBird.serviceModel.test.Country;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {
	
	@Autowired
	private MyBatisService myBatisService;
	
	/**
	 * http://dev-services.thornBird.com/thymeleaf/test
	 * http://dev.thornBird.com/thymeleaf/test
	 */
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public String thymeleafTestPage(ModelMap modelMap) {
		Country country = myBatisService.getCountryByCountryId(42);
		List<City> cities = myBatisService.getCitiesByCountryId(country.getCountryId());
		
		modelMap.addAttribute("thymeleafTitle", "thymeleafTestPage");
		modelMap.addAttribute("checked", true);
		modelMap.addAttribute("changeType", "checkbox");
		modelMap.addAttribute("currentNumber", 100);
		modelMap.addAttribute("baiduUrl", "test");
		modelMap.addAttribute("shopLogo", "https://gss0.bdstatic.com/5bVWsj_p_tVS5dKfpU_Y_D3/res/r/image/2017-09-26/352f1d243122cf52462a2e6cdcb5ed6d.png");
		modelMap.addAttribute("today", new Date());
		
		modelMap.addAttribute("country", country);
		modelMap.addAttribute("cities", cities.stream().limit(5).collect(Collectors.toList()));
		modelMap.addAttribute("template", "test/thymeleafTestPage");
		
		return "index";
	}
}
