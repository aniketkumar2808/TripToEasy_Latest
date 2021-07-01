package com.TripToEasy.model.parsingModel.flight;

import java.util.List;

public class TwoWayList {
    public List<OneWayList> oneWayLists;

    public TwoWayList(List<OneWayList> oneWayLists) {
        this.oneWayLists = oneWayLists;
    }

    public List<OneWayList> getOneWayLists() {
        return oneWayLists;
    }

    public void setOneWayLists(List<OneWayList> oneWayLists) {
        this.oneWayLists = oneWayLists;
    }
}