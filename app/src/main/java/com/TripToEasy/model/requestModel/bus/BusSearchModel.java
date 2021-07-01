package com.TripToEasy.model.requestModel.bus;

/**
 * Created by ptblr-1195 on 27/3/18.
 */

public class BusSearchModel {
    private String bus_station_from;
    private String from_station_id;
    private String bus_station_to;
    private String to_station_id;
    private String bus_date_1;

    public BusSearchModel(String bus_station_from, String from_station_id,
                          String bus_station_to, String to_station_id,
                          String bus_date_1) {
        this.bus_station_from = bus_station_from;
        this.from_station_id = from_station_id;
        this.bus_station_to = bus_station_to;
        this.to_station_id = to_station_id;
        this.bus_date_1 = bus_date_1;
    }

    public String getBus_station_from() {
        return bus_station_from;
    }

    public void setBus_station_from(String bus_station_from) {
        this.bus_station_from = bus_station_from;
    }

    public String getFrom_station_id() {
        return from_station_id;
    }

    public void setFrom_station_id(String from_station_id) {
        this.from_station_id = from_station_id;
    }

    public String getBus_station_to() {
        return bus_station_to;
    }

    public void setBus_station_to(String bus_station_to) {
        this.bus_station_to = bus_station_to;
    }

    public String getTo_station_id() {
        return to_station_id;
    }

    public void setTo_station_id(String to_station_id) {
        this.to_station_id = to_station_id;
    }

    public String getBus_date_1() {
        return bus_date_1;
    }

    public void setBus_date_1(String bus_date_1) {
        this.bus_date_1 = bus_date_1;
    }

}