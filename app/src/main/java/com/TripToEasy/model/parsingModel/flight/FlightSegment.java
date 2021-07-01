package com.TripToEasy.model.parsingModel.flight;

/**
 * Created by ptblr-1195 on 15/3/18.
 */

public class FlightSegment {
    String airlineCode;
    String airlineName;
    String departureAirportCode;
    String departureAirportName;
    String arrivalAirportCode;
    String arrivalAirportName;
    String departureTime;
    String arrivalTime;
    String travelTime;
    String originDateTime;
    String destinationDateTime;
    String airlineId;

    public FlightSegment(String airlineCode, String airlineName,
                         String departureAirportCode, String departureAirportName,
                         String arrivalAirportCode, String arrivalAirportName,
                         String departureTime, String arrivalTime, String travelTime,
                         String originDateTime, String destinationDateTime,
                         String airlineId) {
        this.airlineCode = airlineCode;
        this.airlineName = airlineName;
        this.departureAirportCode = departureAirportCode;
        this.departureAirportName = departureAirportName;
        this.arrivalAirportCode = arrivalAirportCode;
        this.arrivalAirportName = arrivalAirportName;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.travelTime = travelTime;
        this.originDateTime = originDateTime;
        this.destinationDateTime = destinationDateTime;
        this.airlineId = airlineId;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    public String getDepartureAirportName() {
        return departureAirportName;
    }

    public void setDepartureAirportName(String departureAirportName) {
        this.departureAirportName = departureAirportName;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public String getArrivalAirportName() {
        return arrivalAirportName;
    }

    public void setArrivalAirportName(String arrivalAirportName) {
        this.arrivalAirportName = arrivalAirportName;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public String getOriginDateTime() {
        return originDateTime;
    }

    public void setOriginDateTime(String originDateTime) {
        this.originDateTime = originDateTime;
    }

    public String getDestinationDateTime() {
        return destinationDateTime;
    }

    public void setDestinationDateTime(String destinationDateTime) {
        this.destinationDateTime = destinationDateTime;
    }

    public String getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(String airlineId) {
        this.airlineId = airlineId;
    }
}