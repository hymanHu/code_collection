package com.thornBird.modules.commons.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description: Common Controller
 * welcome controller
 * login controller
 * @author: HymanHu
 * @date: 2019-09-01 20:42:50
 */
@Controller
public class CommonController {

	/**
	 * http://dev-services.thornBird.com/welcome
	 * https://dev-services.thornBird.com:8085/welcome
	 */
	@RequestMapping(value="/welcome", method=RequestMethod.GET)
	public String welcome(ModelMap modelMap) {
		return "index";
	}
	
	/**
	 * http://dev-services.thornBird.com/login
	 * https://dev-services.thornBird.com:8085/login
	 */
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(ModelMap modelMap) {
		return "index";
	}
}
