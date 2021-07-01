package com.TripToEasy.model.parsingModel.sightSeeing;


public class HistoryData {


    private String Name;
    private String Date;
    private String bookingId;
    private String txtAddress;
    private String totalFare;
    private String bookingStatus;
    private String currency;


    public HistoryData(String name, String date, String bookingId, String txtAddress, String totalFare, String bookingStatus, String currency) {
        Name = name;
        Date = date;
        this.bookingId = bookingId;
        this.txtAddress = txtAddress;
        this.totalFare = totalFare;
        this.bookingStatus = bookingStatus;
        this.currency = currency;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getTxtAddress() {
        return txtAddress;
    }

    public void setTxtAddress(String txtAddress) {
        this.txtAddress = txtAddress;
    }

    public String getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(String totalFare) {
        this.totalFare = totalFare;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getCurrency() {
        return currency;
    }
}
