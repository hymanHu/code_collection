package com.thornBird.serviceModel.test;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description: Country
 * @author: HymanHu
 * @date: 2019-06-08 17:24:24
 */
@Entity
@Table(name = "country")
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int countryId;
	@Column(length = 50)
	private String countryName;
	@Column(length = 45)
	private String localCountryName;
	@Column(length = 20)
	private String countryCode;
	@Column(length = 20)
	private String countryCode2;
	@Column
	private String continent;
	@Column
	private int capital;
	@Column(length = 50)
	private String timeZone;
	@Column
	private int languageId;
	@Column
	private int currencyId;
	@Column
	private Date dateModified;
	@Column
	private Date dateCreated;

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

}
