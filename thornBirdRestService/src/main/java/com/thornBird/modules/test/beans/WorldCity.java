package com.thornBird.modules.test.beans;

import java.io.Serializable;


/**
 * @Description: City in World Db
 * @author: HymanHu
 * @date: 2019-08-17 19:59:56
 */
public class WorldCity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String countryCode;
	private String district;
	private long population;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}
}
