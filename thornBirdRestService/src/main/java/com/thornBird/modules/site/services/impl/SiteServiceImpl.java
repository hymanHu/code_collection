package com.thornBird.modules.site.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thornBird.modules.site.dao.MCityDao;
import com.thornBird.modules.site.dao.MCountryDao;
import com.thornBird.modules.site.services.SiteService;
import com.thornBird.serviceModel.site.MCity;
import com.thornBird.serviceModel.site.MCountry;

@Service
public class SiteServiceImpl implements SiteService {
	
	@Autowired
	private MCityDao mCityDao;
	@Autowired
	private MCountryDao mCountryDao;

	@Override
	public List<MCity> getCitiesByCountryId(int countryId) {
		return Optional.ofNullable(mCityDao.getCitiesByCountryId(countryId)).orElse(Collections.emptyList());
	}

	@Override
	public MCountry getCountryByCountryId(int countryId) {
		return mCountryDao.getCountryByCountryId(countryId);
	}
	
}
