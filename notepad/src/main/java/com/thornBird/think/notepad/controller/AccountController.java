package com.thornBird.think.notepad.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccountController {

	private Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@RequestMapping(value = "toSignUp", method = {RequestMethod.GET, RequestMethod.POST})
	public String toSignUp(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("go to the sign up page.");
		return "signUp";
	}
}
