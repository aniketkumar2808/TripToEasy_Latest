package com.TripToEasy.model.requestModel;

/**
 * Created by ptblr-1195 on 14/5/18.
 */

public class FairRule {
    String data_access_key;
    String booking_source;
    String search_access_key;

    public FairRule(String data_access_key, String booking_source, String search_access_key) {
        this.data_access_key = data_access_key;
        this.booking_source = booking_source;
        this.search_access_key = search_access_key;
    }
}