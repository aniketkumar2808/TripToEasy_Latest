package com.TripToEasy.model.parsingModel.flight;

/**
 * Created by ptblr-1195 on 13/3/18.
 */

public class FlightList {
    String airlineName;
    String airlineCode;
    Boolean isSelected;

    public FlightList(String airlineName, String airlineCode, Boolean isSelected) {
        this.airlineName = airlineName;
        this.airlineCode = airlineCode;
        this.isSelected = isSelected;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

}