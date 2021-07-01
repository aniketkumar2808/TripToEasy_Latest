package com.TripToEasy.model.parsingModel.holiday;

/**
 * Created by ptblr-1195 on 10/4/18.
 */

public class HolidayCity {
    String cityName;
    String cityId;

    public HolidayCity(){

    }

    public HolidayCity(String cityName){
        this.cityName = cityName;
    }

    public HolidayCity(String cityName, String cityId) {
        this.cityName = cityName;
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}