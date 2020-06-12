package com.thornBird.modules.test.dao.mainDb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thornBird.serviceModel.test.Country;

/**
 * @Description: Jpa Country Dao
 * @author: HymanHu
 * @date: 2019-08-15 13:25:48
 */
@Repository
public interface JpaCountryDao extends JpaRepository<Country, Integer> {

}
