package com.thornBird.serviceModel.test;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description: City
 * @author: HymanHu
 * @date: 2019-06-08 17:17:52
 */
@Entity
@Table(name = "city")
public class City implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cityId", unique = true, nullable = false)
	private int cityId;
	@Column(name = "cityName", length = 45)
	private String cityName;
	@Column(name = "localCityName", length = 45)
	private String localCityName;
	@Column(name = "countryId")
	private int countryId;
	@Column(name = "population")
	private long population;
	@Column(name = "dateModified")
	private Date dateModified;
	@Column(name = "dateCreated")
	private Date dateCreated;

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getLocalCityName() {
		return localCityName;
	}

	public void setLocalCityName(String localCityName) {
		this.localCityName = localCityName;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

}
