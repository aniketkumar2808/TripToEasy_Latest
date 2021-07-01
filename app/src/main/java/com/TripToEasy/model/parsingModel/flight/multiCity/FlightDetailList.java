
package com.TripToEasy.model.parsingModel.flight.multiCity;

/**
 * Created by ptblr-1195 on 5/3/18.
 */

public class FlightDetailList {
    String flightName;
    String flightImage;
    String flightCode;
    String flightDepartureTime;
    String flightDeparturePlace;
    String flightArrivalTime;
    String flightArrivalPlace;
    String flightTravelTime;
    String flightStopCount;
    String token;
    String flightDepartureDate;
    String flightArrivalDate;


    public FlightDetailList(String flightName, String flightImage, String flightCode,
                            String flightDepartureTime,String flightDepartureDate, String flightDeparturePlace,
                            String flightArrivalTime,String flightArrivalDate, String flightArrivalPlace,
                            String flightTravelTime, String token, String flightStopCount) {
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
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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