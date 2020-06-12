package com.thornBird.modules.test.dao.worldDb;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thornBird.serviceModel.test.WCity;

/**
 * @Description: Jpa WCity Dao
 * @author: HymanHu
 * @date: 2019-08-30 14:13:26
 */
@Repository
public interface JpaWCityDao extends JpaRepository<WCity, Integer> {

	List<WCity> getCitiesByCountryCode(String countryCode);
}
