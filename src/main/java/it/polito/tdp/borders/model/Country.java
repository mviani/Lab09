package it.polito.tdp.borders.model;

import java.util.Objects;

public class Country {
    private int key;
    private String code;
    private String countryName;
    private Integer confinanti;
	public Integer getConfinanti() {
		return confinanti;
	}
	public void setConfinanti(Integer confinanti) {
		this.confinanti = confinanti;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public Country(int key, String code, String countryName) {
		super();
		this.key = key;
		this.code = code;
		this.countryName = countryName;
	}
	@Override
	public int hashCode() {
		return Objects.hash(key);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		return key == other.key;
	}
	@Override
	public String toString() {
		return  countryName ;
	}
    
    
}
