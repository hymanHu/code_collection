package com.thornBird.modules.site.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thornBird.serviceModel.site.MCountry;

/**
 * @Description: M Country Dao
 * @author: HymanHu
 * @date: 2019-08-30 17:02:13
 */
@Repository
public interface MCountryDao extends JpaRepository<MCountry, Integer> {
	
	public MCountry getCountryByCountryId(int CountryId); 
}
