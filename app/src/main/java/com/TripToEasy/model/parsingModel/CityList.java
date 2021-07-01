package com.TripToEasy.model.parsingModel;

/**
 * Created by ptblr-1195 on 21/3/18.
 */

public class CityList {
    String searchCityName;
    String cityId;
    Integer city_pref;

    public CityList(String searchCityName, String cityId) {
        this.searchCityName = searchCityName;
        this.cityId = cityId;
    }

    public CityList(String searchCityName, String cityId, Integer city_pref) {
        this.searchCityName = searchCityName;
        this.cityId = cityId;
        this.city_pref = city_pref;
    }

    public CityList() {
        super();
    }

    public String getSearchCityName() {
        return searchCityName;
    }

    public void setSearchCityName(String searchCityName) {
        this.searchCityName = searchCityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Integer getCity_pref() {
        return city_pref;
    }

    public void setCity_pref(Integer city_pref) {
        this.city_pref = city_pref;
    }
}
