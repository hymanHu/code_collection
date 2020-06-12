package com.thornBird.personalArchives.modules.account.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thornBird.personalArchives.modules.account.entity.Account;

@Controller
public class AccountController {
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(ModelMap modelMap) {
		modelMap.addAttribute("account", new Account());
		modelMap.addAttribute("template", "/login/index");
		return "index";
	}
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String main(ModelMap modelMap) {
		modelMap.addAttribute("template", "/main/index");
		return "index";
	}

	@RequestMapping(value="/doLogin", method=RequestMethod.POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String doLogin(@ModelAttribute("account")  Account account, RedirectAttributes attributes) {
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(new UsernamePasswordToken(account.getAccountName(), account.getPassword()));
		} catch (AuthenticationException e) {
			attributes.addFlashAttribute("errorMessage", "Account name or password error.");
			return "redirect:/login";
		}
		return "redirect:/index";
	}
}
