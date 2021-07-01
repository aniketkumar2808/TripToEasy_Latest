package com.TripToEasy.model.parsingModel.hotel.search;

import java.util.List;

/**
 * Created by ptblr-1195 on 21/3/18.
 */

public class SearchMain {
    private List<RoomGuest> RoomGuests = null;
    private String NoOfRooms;
    private String NoOfNights;
    private String CityId;
    private String CheckInDate;
    private String CheckOutDate;

    public SearchMain(List<RoomGuest> roomGuests, String noOfRooms,
                      String noOfNights, String cityId, String checkInDate,
                      String checkOutDate) {
        RoomGuests = roomGuests;
        NoOfRooms = noOfRooms;
        NoOfNights = noOfNights;
        CityId = cityId;
        CheckInDate = checkInDate;
        CheckOutDate = checkOutDate;
    }

}