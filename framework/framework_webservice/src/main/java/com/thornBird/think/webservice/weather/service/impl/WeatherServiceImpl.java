package com.thornBird.think.webservice.weather.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import cn.com.webxml.weatherWebservice.ArrayOfString;
import cn.com.webxml.weatherWebservice.WeatherWebService;
import cn.com.webxml.weatherWebservice.WeatherWebServiceSoap;

import com.thornBird.think.webservice.weather.service.IWeatherService;

public class WeatherServiceImpl implements IWeatherService {

	public final static String WEATHER_WSDL_URL = "http://webservice.webxml.com.cn/WebServices/WeatherWebService.asmx?wsdl";
	public final static URL URL;
	static {
		URL URL_TEMP = null;
		try {
			URL_TEMP = new URL(WEATHER_WSDL_URL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		URL = URL_TEMP;
	}
	public final static WeatherWebServiceSoap weatherWebServiceSoap = (new WeatherWebService(URL)).getWeatherWebServiceSoap();
	
	@Override
	public List<String> getSupportProvince() {
		ArrayOfString arrayOfString = weatherWebServiceSoap.getSupportProvince();
		return arrayOfString.getString();
	}

	@Override
	public List<String> getSupportCity(String byProvinceName) {
		ArrayOfString arrayOfString = weatherWebServiceSoap.getSupportCity(byProvinceName);
		return arrayOfString.getString();
	}
	
	@Override
	public List<String> getWeatherbyCityName(String cityName) {
		ArrayOfString arrayOfString = weatherWebServiceSoap.getWeatherbyCityName(cityName);
		return arrayOfString.getString();
	}

	public static void main(String[] args) {
		IWeatherService weatherService = new WeatherServiceImpl();
		
		List<String> provinces = weatherService.getSupportProvince();
		System.out.println(provinces.toString());
		
		List<String> citys = weatherService.getSupportCity("all");
		System.out.println(citys.toString());
		
		List<String> weather = weatherService.getWeatherbyCityName("成都");
		System.out.println(weather.toString());
		
	}

}
