package com.thornBird.serviceModel.site;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Description: Country
 * @author: HymanHu
 * @date: 2019-08-28 14:22:48
 */
@Entity
@Table(name = "m_country")
public class MCountry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="country_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int countryId;
	@Column(name="country_name", length = 50)
	private String countryName;
	@Column(name="local_country_name", length = 45)
	private String localCountryName;
	@Column(name="country_code", length = 20)
	private String countryCode;
	@Column(name="country_code2", length = 20)
	private String countryCode2;
	@Column(name="continent", length = 20)
	private String continent;
	@Column(name="region", length = 45)
	private String region;
	@Column(name="surface_area")
	private float surfaceArea;
	@Column(name="indep_year")
	private int indepYear;
	@Column(name="population")
	private int population;
	@Column(name="life_expectancy")
	private float lifeExpectancy; // 预期寿命
	@Column(name="gnp")
	private float gnp;
	@Column(name="government_form", length = 45)
	private String governmentForm;
	@Column(name="head_of_state", length = 60)
	private String headOfState;
	@Column(name="capital")
	private int capital;
	@Column(name="time_zone", length = 50)
	private String timeZone;
	@Column(name="language_id")
	private int languageId;
	@Column(name="currency_id")
	private int currencyId;
	@Column(name="date_modified")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date dateModified;
	@Column(name="date_created")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date dateCreated;
	
	/*
	 * mappedBy 对应 @ManyToOne 的属性名称
	 * cascade ---- 练级操作
	 */
	@OneToMany(mappedBy = "mCountry", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private Set<MCity> mCities;

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getLocalCountryName() {
		return localCountryName;
	}

	public void setLocalCountryName(String localCountryName) {
		this.localCountryName = localCountryName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryCode2() {
		return countryCode2;
	}

	public void setCountryCode2(String countryCode2) {
		this.countryCode2 = countryCode2;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public float getSurfaceArea() {
		return surfaceArea;
	}

	public void setSurfaceArea(float surfaceArea) {
		this.surfaceArea = surfaceArea;
	}

	public int getIndepYear() {
		return indepYear;
	}

	public void setIndepYear(int indepYear) {
		this.indepYear = indepYear;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public float getLifeExpectancy() {
		return lifeExpectancy;
	}

	public void setLifeExpectancy(float lifeExpectancy) {
		this.lifeExpectancy = lifeExpectancy;
	}

	public float getGnp() {
		return gnp;
	}

	public void setGnp(float gnp) {
		this.gnp = gnp;
	}

	public String getGovernmentForm() {
		return governmentForm;
	}

	public void setGovernmentForm(String governmentForm) {
		this.governmentForm = governmentForm;
	}

	public String getHeadOfState() {
		return headOfState;
	}

	public void setHeadOfState(String headOfState) {
		this.headOfState = headOfState;
	}

	public int getCapital() {
		return capital;
	}

	public void setCapital(int capital) {
		this.capital = capital;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public int getLanguageId() {
		return languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public int getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
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

	public Set<MCity> getmCities() {
		return mCities;
	}

	public void setmCities(Set<MCity> mCities) {
		this.mCities = mCities;
	}

}
