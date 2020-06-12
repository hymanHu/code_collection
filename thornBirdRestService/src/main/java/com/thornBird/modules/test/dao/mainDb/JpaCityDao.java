package com.thornBird.modules.test.dao.mainDb;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thornBird.modules.test.beans.CountryAndCities;
import com.thornBird.serviceModel.test.City;


/**
 * @Description: Jpa City Dao
 * @author: HymanHu
 * @date: 2019-08-05 20:10:00
 */
@Repository
public interface JpaCityDao extends JpaRepository<City, Integer> {
	
	List<City> getCitiesByCountryId(int countryId);
	
//	@Query("select * from city where countryId = :countryId limit :start, :end;")
	@Query(nativeQuery = true, value = "select * from city where countryId = :countryId limit :start, :end")
	List<City> getCitiesBySql(@Param("countryId") int countryId, @Param("start") int start, @Param("end") int end);
	
	@Query(nativeQuery = true, value = "select "
			+ "city.cityId, city.cityName, city.localCityName, city.population, "
			+ "country.countryId, country.countryName, country.countryCode, country.countryCode2 " 
			+ "from city left join country on city.countryId = country.countryId "
			+ "where city.countryId = :countryId limit :start, :end")
	List<CountryAndCities> getCitiesAndCountiesBySql(@Param("countryId") int countryId, 
			@Param("start") int start, @Param("end") int end);
	
}
