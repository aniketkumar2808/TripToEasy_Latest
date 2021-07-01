package com.TripToEasy.model.requestModel.multiCity;

/**
 * Created by ptblr-1195 on 1/3/18.
 */

public class MultiCitySegment {
    private String Origin;
    private String Destination;
    private String CabinClass;
    private String DepartureDate;

    public MultiCitySegment(String origin, String destination, String cabinClass,
                            String departureDate) {
        Origin = origin;
        Destination = destination;
        CabinClass = cabinClass;
        DepartureDate = departureDate;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getCabinClass() {
        return CabinClass;
    }

    public void setCabinClass(String cabinClass) {
        CabinClass = cabinClass;
    }

    public String getDepartureDate() {
        return DepartureDate;
    }

    public void setDepartureDate(String departureDate) {
        DepartureDate = departureDate;
    }
}