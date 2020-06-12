package com.thornBird.modules.site.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thornBird.serviceModel.site.MCity;

/**
 * @Description: MCity Dao
 * @author: HymanHu
 * @date: 2019-08-30 16:46:57
 */
@Repository
public interface MCityDao extends JpaRepository<MCity, Integer> {

	List<MCity> getCitiesByCountryId(int countryId);
}
