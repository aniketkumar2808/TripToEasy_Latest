package com.TripToEasy.model.requestModel.bus;

/**
 * Created by ptblr-1195 on 9/4/18.
 */

public class BusDetailRequest {

    private String route_schedule_id;
    private String journey_date;
    private String route_code;
    private String search_id;
    private String ResultToken;
    private String booking_source;

    public BusDetailRequest(String route_schedule_id, String journey_date,
                            String route_code, String search_id, String resultToken,
                            String booking_source) {
        this.route_schedule_id = route_schedule_id;
        this.journey_date = journey_date;
        this.route_code = route_code;
        this.search_id = search_id;
        ResultToken = resultToken;
        this.booking_source = booking_source;
    }
}