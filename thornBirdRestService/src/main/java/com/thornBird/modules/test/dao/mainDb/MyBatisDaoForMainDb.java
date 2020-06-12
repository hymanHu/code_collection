package com.thornBird.modules.test.dao.mainDb;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;
import com.thornBird.modules.test.beans.CountryAndCities;
import com.thornBird.serviceModel.site.MCity;
import com.thornBird.serviceModel.site.MCountry;
import com.thornBird.serviceModel.test.City;
import com.thornBird.serviceModel.test.Country;

/**
 * @Description: MyBatis Dao For MainDb
 * @author: HymanHu
 * @date: 2019-08-15 21:25:24
 */
@Repository
@Mapper
public interface MyBatisDaoForMainDb {

	/**
	 * #{countryId} ---- prepared statement
	 * '${countryId}' ---- statement
	 */
	@Select("select * from city where countryId = #{countryId}")
	public List<City> getCitiesByCountryId(int countryId);
	
	@Select("select * from city order by cityName")
	public Page<City> getCitiesByPage(int pageNumber, int pageSize);
	
	@Select("select * from country where countryId = #{countryId}")
	public Country getCountryByCountryId(int countryId);
	
	@Select("select countryId from country where countryId = #{countryId}")
	@Results({
		@Result(
			property="country", 
			column="countryId",
			javaType=Country.class, 
			one=@One(select="com.thornBird.modules.test.dao.mainDb.MyBatisDaoForMainDb.getCountryByCountryId")),
		@Result(
			property="cities",
			column="countryId",
			javaType=List.class,
			many=@Many(select="com.thornBird.modules.test.dao.mainDb.MyBatisDaoForMainDb.getCitiesByCountryId"))
	})
	public CountryAndCities getCitiesAndCountries(int countryId);
	
	@Select("select * from m_country where country_id = #{countryId}")
	public MCountry getCountryByCountryId2(int countryId);
	
	@Select("select * from m_city where country_id = #{countryId}")
	public List<MCity> getCitiesByCountryId2(@Param("countryId") int countryId);
	
	@Select("select country_id from m_country where country_id = #{countryId}")
	@Results({
		@Result(
			property="mCountry", 
			column="country_id",
			javaType=MCountry.class, 
			one=@One(select="com.thornBird.modules.test.dao.mainDb.MyBatisDaoForMainDb.getCountryByCountryId2")),
		@Result(
			property="mCities",
			column="country_id",
			javaType=List.class,
			many=@Many(select="com.thornBird.modules.test.dao.mainDb.MyBatisDaoForMainDb.getCitiesByCountryId2"))
	})
	public CountryAndCities getCountryAndCities2(int countryId);
	
}
