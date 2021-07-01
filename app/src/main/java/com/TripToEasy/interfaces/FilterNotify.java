package com.TripToEasy.interfaces;

import com.TripToEasy.model.parsingModel.bus.BusOperatorsInfo;
import com.TripToEasy.model.parsingModel.flight.FlightList;

import java.util.List;

/**
 * Created by ptblr-1195 on 13/3/18.
 */

public interface FilterNotify {

    public void filterTypeOne(List<FlightList> flightLists,
                              List<Integer> stopCount,
                              List<Integer> departure,
                              List<Integer> arrival,
                              String minPrice,
                              String maxPrice);

    public void filterTypeTwo(List<FlightList> flightLists,
                              List<Integer> stopCount,
                              String minPrice,
                              String maxPrice);

    public void sortingNotify(int price, int departure, int arrival, int duration);

    public void filterTypeBus(List<BusOperatorsInfo> busOprList,
                              List<Integer> departure,
                              List<Integer> arrival,
                              String minPrice,
                              String maxPrice,
                              String selectBusType);
}