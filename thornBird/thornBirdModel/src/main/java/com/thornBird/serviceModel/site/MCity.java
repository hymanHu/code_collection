package com.thornBird.serviceModel.site;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Description: city
 * @author: HymanHu
 * @date: 2019-08-28 14:39:20
 */
@Entity
@Table(name = "m_city")
public class MCity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="city_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cityId;
	@Column(name="city_name", length = 45)
	private String cityName;
	@Column(name="local_city_name", length = 45)
	private String localCityName;
	@Column(name="country_id")
	private int countryId;
	@Column(name="district", length = 45)
	private String district;
	@Column(name="population")
	private int population;
	@Column(name="date_modified")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date dateModified;
	@Column(name="date_created")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date dateCreated;
	
	/*
	 * country_id ---- 两个属性同时映射了该字段，添加insertable、updatable为false，标识该属性不参与创建更新操作
	 * @JsonIgnore ---- 不序列化该字段，避免无限递归问题
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id", insertable = false, updatable = false)
	@JsonIgnore
	private MCountry mCountry;

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

	public MCountry getmCountry() {
		return mCountry;
	}

	public void setmCountry(MCountry mCountry) {
		this.mCountry = mCountry;
	}
}
