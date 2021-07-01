package com.TripToEasy.model.parsingModel.flight;

/**
 * Created by ptblr-1174 on 18/5/18.
 */

public class BookingHistoryInfo {
    String transaction_origin,status,trip_type,journey_start,journey_end,from_loc,to_loc,pnr,total_fare,currency,app_reference,booking_source;

    public BookingHistoryInfo(){

    }

    public BookingHistoryInfo(String transaction_origin,String status, String trip_type, String journey_start, String journey_end, String from_loc, String to_loc, String pnr, String total_fare, String currency, String app_reference, String booking_source){
        this.transaction_origin=transaction_origin;
        this.status=status;
        this.trip_type=trip_type;
        this.journey_start=journey_start;
        this.journey_end=journey_end;
        this.from_loc=from_loc;
        this.to_loc=to_loc;
        this.pnr=pnr;
        this.total_fare=total_fare;
        this.currency=currency;
        this.app_reference=app_reference;
        this.booking_source=booking_source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrip_type() {
        return trip_type;
    }

    public void setTrip_type(String trip_type) {
        this.trip_type = trip_type;
    }

    public String getJourney_start() {
        return journey_start;
    }

    public void setJourney_start(String journey_start) {
        this.journey_start = journey_start;
    }

    public String getJourney_end() {
        return journey_end;
    }

    public void setJourney_end(String journey_end) {
        this.journey_end = journey_end;
    }

    public String getFrom_loc() {
        return from_loc;
    }

    public void setFrom_loc(String from_loc) {
        this.from_loc = from_loc;
    }

    public String getTo_loc() {
        return to_loc;
    }

    public void setTo_loc(String to_loc) {
        this.to_loc = to_loc;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getTotal_fare() {
        return total_fare;
    }

    public void setTotal_fare(String total_fare) {
        this.total_fare = total_fare;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getApp_reference() {
        return app_reference;
    }

    public void setApp_reference(String app_reference) {
        this.app_reference = app_reference;
    }

    public String getBooking_source() {
        return booking_source;
    }

    public void setBooking_source(String booking_source) {
        this.booking_source = booking_source;
    }

    public String getTransaction_origin() {
        return transaction_origin;
    }

    public void setTransaction_origin(String transaction_origin) {
        this.transaction_origin = transaction_origin;
    }
}
