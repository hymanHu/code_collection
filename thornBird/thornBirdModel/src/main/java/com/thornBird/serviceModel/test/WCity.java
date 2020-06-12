package com.thornBird.serviceModel.test;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description: world city
 * @author: HymanHu
 * @date: 2019-08-29 19:56:01
 */
@Entity
@Table(name = "city")
public class WCity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;
	@Column(name = "Name", length = 45)
	private String name;
	@Column(name = "CountryCode", length = 20)
	private String countryCode;
	@Column(name = "District", length = 45)
	private String district;
	@Column(name = "Population")
	private int population;

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

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}
}
