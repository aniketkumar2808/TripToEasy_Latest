package com.TripToEasy.model.parsingModel.bus;

/**
 * Created by ptblr-1174 on 7/6/18.
 */

public class BusBookHistoryInfo {
    String status;
    String pnr;
    String ticket;
    String currency;
    String departure_from;
    String arrival_to;
    String operator;
    String fare;
    String journey_datetime;
    String departure_datetime;
    String arrival_datetime;
    String cancelPolicy;

    public String getApp_reference() {
        return app_reference;
    }

    public void setApp_reference(String app_reference) {
        this.app_reference = app_reference;
    }

    String app_reference;
    public BusBookHistoryInfo(String status,String pnr,String ticket,String app_reference,String currency,String departure_from,
                              String arrival_to, String operator,String fare,String journey_datetime,
                              String departure_datetime,String arrival_datetime,String cancelPolicy){
        this.status=status;
        this.pnr=pnr;
        this.ticket=ticket;
        this.app_reference=app_reference;
        this.currency=currency;
        this.departure_from=departure_from;
        this.arrival_to=arrival_to;
        this.operator=operator;
        this.fare=fare;
        this.journey_datetime=journey_datetime;
        this.departure_datetime=departure_datetime;
        this.arrival_datetime=arrival_datetime;
        this.cancelPolicy=cancelPolicy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDeparture_from() {
        return departure_from;
    }

    public void setDeparture_from(String departure_from) {
        this.departure_from = departure_from;
    }

    public String getArrival_to() {
        return arrival_to;
    }

    public void setArrival_to(String arrival_to) {
        this.arrival_to = arrival_to;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getJourney_datetime() {
        return journey_datetime;
    }

    public void setJourney_datetime(String journey_datetime) {
        this.journey_datetime = journey_datetime;
    }

    public String getDeparture_datetime() {
        return departure_datetime;
    }

    public void setDeparture_datetime(String departure_datetime) {
        this.departure_datetime = departure_datetime;
    }

    public String getArrival_datetime() {
        return arrival_datetime;
    }

    public void setArrival_datetime(String arrival_datetime) {
        this.arrival_datetime = arrival_datetime;
    }
}
