package com.TripToEasy.model.requestModel.bus.confirmSeat;

import java.util.List;

/**
 * Created by ptblr-1195 on 6/6/17.
 */

public class NewConfirmSeat {

    private String search_id;
    private String route_schedule_id;
    private String journey_date;
    private String pickup_id;
    private List<String> seat = null;
    private String booking_source;
    private String token;
    private String token_key;

    public NewConfirmSeat(String search_id, String route_schedule_id, String journey_date, String pickup_id,
                          List<String> seat, String booking_source, String token, String token_key) {
        this.search_id = search_id;
        this.route_schedule_id = route_schedule_id;
        this.journey_date = journey_date;
        this.pickup_id = pickup_id;
        this.seat = seat;
        this.booking_source = booking_source;
        this.token = token;
        this.token_key = token_key;
    }


}
