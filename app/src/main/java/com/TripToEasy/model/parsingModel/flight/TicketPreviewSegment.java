package com.TripToEasy.model.parsingModel.flight;

/**
 * Created by ptblr-1174 on 13/7/18.
 */

public class TicketPreviewSegment {
    String  AirlineName,originCode,originName,destinationCode,destinationName,
            DepartureTime,DepartureDate,ArrivalTime,ArrivalDate;
    public TicketPreviewSegment(String  AirlineName,String originCode,String originName,
                                String destinationCode, String destinationName,
                                String DepartureTime,String DepartureDate,
                                String ArrivalTime,String ArrivalDate){
        this.AirlineName=AirlineName;
        this.originCode=originCode;
        this.originName=originName;
        this.destinationCode=destinationCode;
        this.destinationName=destinationName;
        this.DepartureTime=DepartureTime;
        this.DepartureDate=DepartureDate;
        this.ArrivalTime=ArrivalTime;
        this.ArrivalDate=ArrivalDate;
    }

    public String getAirlineName() {
        return AirlineName;
    }

    public void setAirlineName(String airlineName) {
        AirlineName = airlineName;
    }

    public String getOriginCode() {
        return originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDepartureTime() {
        return DepartureTime;
    }

    public void setDepartureTime(String departureTime) {
        DepartureTime = departureTime;
    }

    public String getDepartureDate() {
        return DepartureDate;
    }

    public void setDepartureDate(String departureDate) {
        DepartureDate = departureDate;
    }

    public String getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        ArrivalTime = arrivalTime;
    }

    public String getArrivalDate() {
        return ArrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        ArrivalDate = arrivalDate;
    }
}
