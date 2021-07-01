package com.TripToEasy.model.parsingModel.flight;

/**
 * Created by ptblr-1195 on 27/2/18.
 */

public class OneWayList {
    String flightName;
    String flightImage;
    String flightCode;
    String flightDepartureTime;
    String flightDeparturePlace;
    String flightArrivalTime;
    String flightArrivalPlace;
    String flightTravelTime;
    String flightStopCount;
    String flightPrice;
    String flightIsRefundable;
    String provabAuthKey;
    String bookingSource;
    String token;
    String tokenKey;
    String cabinClass;
    Boolean isSelected;
    String flightDepartureDate;
    String flightArrivalDate;
    public OneWayList(String flightName, String flightImage, String flightCode,
                      String flightDepartureTime, String flightDepartureDate, String flightDeparturePlace,
                      String flightArrivalTime,String flightArrivalDate, String flightArrivalPlace,
                      String flightTravelTime, String flightStopCount,
                      String flightPrice, String flightIsRefundable,
                      String provabAuthKey, String bookingSource, String token,
                      String tokenKey, String cabinClass) {
        this.flightName = flightName;
        this.flightImage = flightImage;
        this.flightCode = flightCode;
        this.flightDepartureTime = flightDepartureTime;
        this.flightDepartureDate = flightDepartureDate;
        this.flightDeparturePlace = flightDeparturePlace;
        this.flightArrivalTime = flightArrivalTime;
        this.flightArrivalDate = flightArrivalDate;
        this.flightArrivalPlace = flightArrivalPlace;
        this.flightTravelTime = flightTravelTime;
        this.flightStopCount = flightStopCount;
        this.flightPrice = flightPrice;
        this.flightIsRefundable = flightIsRefundable;
        this.provabAuthKey = provabAuthKey;
        this.bookingSource = bookingSource;
        this.token = token;
        this.tokenKey = tokenKey;
        this.cabinClass = cabinClass;
    }

    public OneWayList(String flightName, String flightImage, String flightCode,
                      String flightDepartureTime, String flightDeparturePlace,
                      String flightArrivalTime, String flightArrivalPlace,
                      String flightTravelTime, String flightStopCount,
                      String flightPrice, String flightIsRefundable,
                      String provabAuthKey, String bookingSource, String token) {
        this.flightName = flightName;
        this.flightImage = flightImage;
        this.flightCode = flightCode;
        this.flightDepartureTime = flightDepartureTime;
        this.flightDeparturePlace = flightDeparturePlace;
        this.flightArrivalTime = flightArrivalTime;
        this.flightArrivalPlace = flightArrivalPlace;
        this.flightTravelTime = flightTravelTime;
        this.flightStopCount = flightStopCount;
        this.flightPrice = flightPrice;
        this.flightIsRefundable = flightIsRefundable;
        this.provabAuthKey = provabAuthKey;
        this.bookingSource = bookingSource;
        this.token = token;
    }

    public OneWayList(String flightName, String flightImage, String flightCode,
                      String flightDepartureTime,String flightDepartureDate, String flightDeparturePlace,
                      String flightArrivalTime, String flightArrivalDate, String flightArrivalPlace,
                      String flightTravelTime, String flightStopCount,
                      String flightPrice, String flightIsRefundable,
                      String provabAuthKey, String bookingSource,
                      String token, String tokenKey,Boolean isSelected) {
        this.flightName = flightName;
        this.flightImage = flightImage;
        this.flightCode = flightCode;
        this.flightDepartureTime = flightDepartureTime;
        this.flightDepartureDate = flightDepartureDate;
        this.flightDeparturePlace = flightDeparturePlace;
        this.flightArrivalTime = flightArrivalTime;
        this.flightArrivalDate = flightArrivalDate;
        this.flightArrivalPlace = flightArrivalPlace;
        this.flightTravelTime = flightTravelTime;
        this.flightStopCount = flightStopCount;
        this.flightPrice = flightPrice;
        this.flightIsRefundable = flightIsRefundable;
        this.provabAuthKey = provabAuthKey;
        this.bookingSource = bookingSource;
        this.token = token;
        this.tokenKey=tokenKey;
        this.isSelected = isSelected;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getFlightImage() {
        return flightImage;
    }

    public void setFlightImage(String flightImage) {
        this.flightImage = flightImage;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getFlightDepartureTime() {
        return flightDepartureTime;
    }

    public void setFlightDepartureTime(String flightDepartureTime) {
        this.flightDepartureTime = flightDepartureTime;
    }

    public String getFlightDeparturePlace() {
        return flightDeparturePlace;
    }

    public void setFlightDeparturePlace(String flightDeparturePlace) {
        this.flightDeparturePlace = flightDeparturePlace;
    }

    public String getFlightArrivalTime() {
        return flightArrivalTime;
    }

    public void setFlightArrivalTime(String flightArrivalTime) {
        this.flightArrivalTime = flightArrivalTime;
    }

    public String getFlightArrivalPlace() {
        return flightArrivalPlace;
    }

    public void setFlightArrivalPlace(String flightArrivalPlace) {
        this.flightArrivalPlace = flightArrivalPlace;
    }

    public String getFlightTravelTime() {
        return flightTravelTime;
    }

    public void setFlightTravelTime(String flightTravelTime) {
        this.flightTravelTime = flightTravelTime;
    }

    public String getFlightStopCount() {
        return flightStopCount;
    }

    public void setFlightStopCount(String flightStopCount) {
        this.flightStopCount = flightStopCount;
    }

    public String getFlightPrice() {
        return flightPrice;
    }

    public void setFlightPrice(String flightPrice) {
        this.flightPrice = flightPrice;
    }

    public String getFlightIsRefundable() {
        return flightIsRefundable;
    }

    public void setFlightIsRefundable(String flightIsRefundable) {
        this.flightIsRefundable = flightIsRefundable;
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

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public String getCabinClass() {
        return cabinClass;
    }

    public void setCabinClass(String cabinClass) {
        this.cabinClass = cabinClass;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getFlightDepartureDate() {
        return flightDepartureDate;
    }

    public void setFlightDepartureDate(String flightDepartureDate) {
        this.flightDepartureDate = flightDepartureDate;
    }

    public String getFlightArrivalDate() {
        return flightArrivalDate;
    }

    public void setFlightArrivalDate(String flightArrivalDate) {
        this.flightArrivalDate = flightArrivalDate;
    }
}