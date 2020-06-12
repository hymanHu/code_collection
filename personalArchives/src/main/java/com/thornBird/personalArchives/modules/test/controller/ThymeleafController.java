package com.thornBird.personalArchives.modules.test.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thornBird.personalArchives.modules.test.entity.City;
import com.thornBird.personalArchives.modules.test.entity.Country;
import com.thornBird.personalArchives.modules.test.service.MybatisService;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {
	private final static Logger LOGGER = LoggerFactory.getLogger(ThymeleafController.class);
	
	@Autowired
	private MybatisService mybatisService;

	@RequestMapping(value="/testPage/{countryId}", method=RequestMethod.GET)
	public String testPage(@PathVariable("countryId") int countryId, ModelMap modelMap) {
		
		List<City> cities = mybatisService.getCitiesByCountryId(countryId);
		Country country = mybatisService.getCountryById(countryId);
		
		modelMap.addAttribute("thymeleafTitle", "thymeleaf title");
		modelMap.addAttribute("checked", true);
		modelMap.addAttribute("currentNumber", 22);
		modelMap.addAttribute("changeType", "checkbox");
		modelMap.addAttribute("baiduUrl", "http://www.baidu.com");
		modelMap.addAttribute("shopLogo", "https://gss1.bdstatic.com/5bVXsj_p_tVS5dKfpU_Y_D3/qiusuo_icon/2a04dce430443d6593f2158e5ea83479.png");
		modelMap.addAttribute("country", country);
		modelMap.addAttribute("cities", cities.stream().limit(10).collect(Collectors.toList()));
		modelMap.addAttribute("today", new Date());
		modelMap.addAttribute("template", "test/index");
		
		return "index";
	}
	
	@RequestMapping("/download/{fileName}")
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName) {
		Path path = Paths.get("d:/upload/" + fileName);
		try {
			Resource resource = new UrlResource(path.toUri());
			if (resource.exists() || resource.isReadable()) {
				return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@PostMapping("/upload")
	public String uploadFile(HttpServletRequest request, 
			@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "file can not be null");
			return "redirect:/thymeleaf/testPage/522";
		}
		
		try {
			String fileName = System.currentTimeMillis() + file.getOriginalFilename();
			String destFileName = "d:/upload" + File.separator + fileName;
			
			File destFile = new File(destFileName);
			file.transferTo(destFile);
			
//			byte[] bytes = file.getBytes();
//			Path path = Paths.get(destFileName);
//			Files.write(path, bytes);
			
			redirectAttributes.addFlashAttribute("message", "success");
		} catch (IOException e) {
			LOGGER.debug(e.getMessage());
		}
		
		return "redirect:/thymeleaf/testPage/522";
	}
	
	@PostMapping("/bacthFileUpload")
	public String bacthFileUpload(HttpServletRequest request, 
			@RequestParam("file") MultipartFile[] file, RedirectAttributes redirectAttributes) {
		
		try {
			for (MultipartFile multipartFile : file) {
				String fileName = System.currentTimeMillis() + multipartFile.getOriginalFilename();
				String destFileName = "d:/upload" + File.separator + fileName;
				
				File destFile = new File(destFileName);
				multipartFile.transferTo(destFile);
				
				redirectAttributes.addFlashAttribute("message", "success");
			}
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
		}
		
		return "redirect:/thymeleaf/testPage/522";
	}
}
