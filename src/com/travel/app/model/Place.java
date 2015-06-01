package com.travel.app.model;

public class Place {
	
	private String cityName;
	
	private String placeName;
	
	private String description;

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	@Override
	public String toString() {
		String place = "City : "+cityName+", Place : "+placeName+", Description : "+description;
		return place;
	}
	

}
