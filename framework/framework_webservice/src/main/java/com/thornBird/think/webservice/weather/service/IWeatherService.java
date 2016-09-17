package com.thornBird.think.webservice.weather.service;

import java.util.List;

public interface IWeatherService {
	
	/**
	 * @return 一个一维字符串数组 String()，结构为：洲或国内省份的名称
	 */
	public List<String> getSupportProvince();
	
	/**
	 * @param byProvinceName: 指定的洲或国内的省份，若为ALL或空则表示返回全部城市
	 * @return 一个一维字符串数组 String()，结构为：城市名称(城市代码)
	 */
	public List<String> getSupportCity(String byProvinceName);
	
	/**
	 * @param cityName: 城市中文名称(国外城市可用英文)或城市代码(不输入默认为上海市)
	 * @return 一个一维数组 String(22)，共有23个元素
	 * String(0) 到 String(4)：省份，城市，城市代码，城市图片名称，最后更新时间
	 * String(5) 到 String(11)：当天的 气温，概况，风向和风力，天气趋势开始图片名称(以下称：图标一)，
	 * 天气趋势结束图片名称(以下称：图标二)，现在的天气实况，天气和生活指数
	 * String(12) 到 String(16)：第二天的 气温，概况，风向和风力，图标一，图标二
	 * String(17) 到 String(21)：第三天的 气温，概况，风向和风力，图标一，图标二
	 * String(22) 被查询的城市或地区的介绍
	 */
	public List<String> getWeatherbyCityName(String cityName);
	
}
