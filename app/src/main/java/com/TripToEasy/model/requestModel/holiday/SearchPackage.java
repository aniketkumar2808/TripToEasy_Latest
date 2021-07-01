package com.TripToEasy.model.requestModel.holiday;

/**
 * Created by ptblr-1195 on 11/4/18.
 */

public class SearchPackage {
    private String city;
    private String duration;
    private String expirydate;

    public SearchPackage(String city, String duration,
                         String expirydate) {
        this.city = city;
        this.duration = duration;
        this.expirydate = expirydate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }
}