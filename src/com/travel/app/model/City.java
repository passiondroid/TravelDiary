package com.travel.app.model;

public class City {
	
	private String cityName;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	@Override
	public boolean equals(Object o) {
		City city = (City)o;
		if(city.getCityName().equals(this.cityName))
			return true;
		else
			return false;
	}
	

}
