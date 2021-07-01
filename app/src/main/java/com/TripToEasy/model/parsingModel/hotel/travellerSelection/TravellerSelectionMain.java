package com.TripToEasy.model.parsingModel.hotel.travellerSelection;

/**
 * Created by ptblr-1195 on 22/3/18.
 */

public class TravellerSelectionMain {
    Integer adultValue;
    Integer childValue;
    String childAgeOne;
    String childAgeTwo;

    public TravellerSelectionMain(Integer adultValue, Integer childValue,
                                  String childAgeOne, String childAgeTwo) {
        this.adultValue = adultValue;
        this.childValue = childValue;
        this.childAgeOne = childAgeOne;
        this.childAgeTwo = childAgeTwo;
    }

    public TravellerSelectionMain(Integer adultValue, Integer childValue) {
        this.adultValue = adultValue;
        this.childValue = childValue;
    }

    public Integer getAdultValue() {
        return adultValue;
    }

    public void setAdultValue(Integer adultValue) {
        this.adultValue = adultValue;
    }

    public Integer getChildValue() {
        return childValue;
    }

    public void setChildValue(Integer childValue) {
        this.childValue = childValue;
    }

    public String getChildAgeOne() {
        return childAgeOne;
    }

    public void setChildAgeOne(String childAgeOne) {
        this.childAgeOne = childAgeOne;
    }

    public String getChildAgeTwo() {
        return childAgeTwo;
    }

    public void setChildAgeTwo(String childAgeTwo) {
        this.childAgeTwo = childAgeTwo;
    }

}