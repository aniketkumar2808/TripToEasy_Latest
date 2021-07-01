package com.TripToEasy.model.parsingModel.hotel.search;

import java.util.List;

/**
 * Created by ptblr-1195 on 21/3/18.
 */

public class RoomGuest {
    private String NoOfAdults;
    private String NoOfChild;
    private List<String> ChildAge_1 = null;

    public RoomGuest(String noOfAdults, String noOfChild, List<String> childAge_1) {
        NoOfAdults = noOfAdults;
        NoOfChild = noOfChild;
        ChildAge_1 = childAge_1;
    }

}