package com.thornBird.personalArchives.modules.test.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.thornBird.personalArchives.modules.account.entity.Account;
import com.thornBird.personalArchives.modules.test.entity.City;
import com.thornBird.personalArchives.modules.test.entity.Country;
import com.thornBird.personalArchives.modules.test.vo.CountryAndCities;

@Repository
@Mapper
public interface MybatisDao {

	/**
	 * #{countryId} ---- prepared statement, select * from m_city where country_id = ?
	 * '${countryId}' ---- statement, select * from m_city where country_id = 'some id'
	 */
	@Select("select * from m_city where country_id = #{countryId}")
	List<City> getCitiesByCountryId(int countryId);
	
	@Select("select * from m_country where country_id = #{countryId}")
	Country getCountryById(int countryId);
	
	Country getCountryByCountryId2(int countryId);
	
	@Select("select country_id from m_country where country_id=#{countryId}")
	@Results({
		@Result(property="country", 
				column="country_id",
				javaType=Country.class, 
				one=@One(select="com.thornBird.personalArchives.modules.test.dao.MybatisDao.getCountryById")),
		@Result(property="cities",
				column="country_id",
				javaType=List.class,
				many=@Many(select="com.thornBird.personalArchives.modules.test.dao.MybatisDao.getCitiesByCountryId"))})
	CountryAndCities getCountryAndCities(int countryId);
	
	@Insert("insert into m_account(account_name, date_created) "
			+ "values (#{accountName, jdbcType=VARCHAR}, #{dateCreated, jdbcType=DATE})")
	void addAccount(Account account);
	
	@Update("update m_account set account_name = #{accountName} where account_id=#{accountId}")
	void updateAccount(Account account);
	
	@Delete("delete from m_account where account_id=#{accountId}")
	void deleteAccount(int accountId);
}
