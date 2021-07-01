package com.TripToEasy.model.parsingModel.flight.multiCity;

import java.util.List;

/**
 * Created by ptblr-1195 on 5/3/18.
 */

public class MultiCityParsing {
    String priceValue;
    String flightIsRefundable;
    String flightCode;
    String provabAuthKey;
    String bookingSource;
    String token;
    List<FlightDetailList> flightDetailLists;

    public MultiCityParsing(String priceValue, String flightIsRefundable,
                            String flightCod, List<FlightDetailList> flightDetailLists) {
        this.priceValue = priceValue;
        this.flightIsRefundable = flightIsRefundable;
        this.flightCode = flightCod;
        this.flightDetailLists = flightDetailLists;
    }

    public MultiCityParsing(String priceValue, String flightIsRefundable,
                            String flightCode, String provabAuthKey,
                            String bookingSource, String token,
                            List<FlightDetailList> flightDetailLists) {
        this.priceValue = priceValue;
        this.flightIsRefundable = flightIsRefundable;
        this.flightCode = flightCode;
        this.provabAuthKey = provabAuthKey;
        this.bookingSource = bookingSource;
        this.token = token;
        this.flightDetailLists = flightDetailLists;
    }

    public String getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(String priceValue) {
        this.priceValue = priceValue;
    }

    public String getFlightIsRefundable() {
        return flightIsRefundable;
    }

    public void setFlightIsRefundable(String flightIsRefundable) {
        this.flightIsRefundable = flightIsRefundable;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getProvabAuthKey() {
        return provabAuthKey;
    }

    public void setProvabAuthKey(String provabAuthKey) {
        this.provabAuthKey = provabAuthKey;
    }

    public String getBookingSource() {
        return bookingSource;
    }

    public void setBookingSource(String bookingSource) {
        this.bookingSource = bookingSource;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<FlightDetailList> getFlightDetailLists() {
        return flightDetailLists;
    }

    public void setFlightDetailLists(List<FlightDetailList> flightDetailLists) {
        this.flightDetailLists = flightDetailLists;
    }
}