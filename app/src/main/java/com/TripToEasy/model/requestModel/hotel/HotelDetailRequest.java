package com.TripToEasy.model.requestModel.hotel;

/**
 * Created by ptblr-1195 on 27/3/18.
 */

public class HotelDetailRequest {
    String ResultIndex;
    String HotelCode;
    String TraceId;
    String booking_source;
    String op;
    String search_id;

    public HotelDetailRequest(String resultIndex, String hotelCode, String traceId,
                              String booking_source, String op, String search_id) {
        ResultIndex = resultIndex;
        HotelCode = hotelCode;
        TraceId = traceId;
        this.booking_source = booking_source;
        this.op = op;
        this.search_id = search_id;
    }

    public String getResultIndex() {
        return ResultIndex;
    }

    public void setResultIndex(String resultIndex) {
        ResultIndex = resultIndex;
    }

    public String getHotelCode() {
        return HotelCode;
    }

    public void setHotelCode(String hotelCode) {
        HotelCode = hotelCode;
    }

    public String getTraceId() {
        return TraceId;
    }

    public void setTraceId(String traceId) {
        TraceId = traceId;
    }

    public String getBooking_source() {
        return booking_source;
    }

    public void setBooking_source(String booking_source) {
        this.booking_source = booking_source;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getSearch_id() {
        return search_id;
    }

    public void setSearch_id(String search_id) {
        this.search_id = search_id;
    }
}