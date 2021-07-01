package com.TripToEasy.model;

/**
 * Created by ptblr-1174 on 24/4/18.
 */

public class CountryInfo {
    private String id, name, phonecode, country_code, iso;

    public CountryInfo(){

    }
    public CountryInfo(String id, String name, String phonecode, String country_code, String iso){
        this.id=id;
        this.name=name;
        this.phonecode=phonecode;
        this.country_code=country_code;
        this.iso=iso;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(String phonecode) {
        this.phonecode = phonecode;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }
}
