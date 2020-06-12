package com.thornBird.modules.test.dao.worldDb;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;
import com.thornBird.modules.test.beans.WorldCity;

/**
 * @Description: MyBatis Dao For WorldDb
 * @author: HymanHu
 * @date: 2019-08-17 19:53:58
 */
@Repository
@Mapper
public interface MyBatisDaoForWorldDb {

	@Select("select * from city order by name")
	public Page<WorldCity> getCitiesInWorldDb(int pageNumber, int pageSize);
}
