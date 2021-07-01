package com.TripToEasy.model.requestModel.oneWay;

/**
 * Created by ptblr-1195 on 27/2/18.
 */

public class OneWaySegment {
    private String Origin;
    private String Destination;
    private String CabinClass;
    private String DepartureDate;
    private String return_date;

    public OneWaySegment(String origin, String destination, String cabinClass,
                         String departureDate) {
        this.Origin = origin;
        this.Destination = destination;
        this.CabinClass = cabinClass;
        this.DepartureDate = departureDate;
    }

    public OneWaySegment(String origin, String destination, String cabinClass,
                         String departureDate, String return_date) {
        Origin = origin;
        Destination = destination;
        CabinClass = cabinClass;
        DepartureDate = departureDate;
        this.return_date = return_date;
    }
}