package com.thornBird.modules.site.services;

import java.util.List;

import com.thornBird.serviceModel.site.MCity;
import com.thornBird.serviceModel.site.MCountry;

/**
 * @Description: Site Service
 * @author: HymanHu
 * @date: 2019-08-30 16:52:13
 */
public interface SiteService {
	
	List<MCity> getCitiesByCountryId(int countryId);
	
	MCountry getCountryByCountryId(int countryId);
}
