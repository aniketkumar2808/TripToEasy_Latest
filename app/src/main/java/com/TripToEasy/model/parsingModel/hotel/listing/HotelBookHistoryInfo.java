package com.TripToEasy.model.parsingModel.hotel.listing;

/**
 * Created by ptblr-1174 on 7/6/18.
 */

public class HotelBookHistoryInfo {
    String bookingStatus,booking_id,hotel_check_in,hotel_check_out,currency,
    location,room_type_name,total_fare,hotelName,hotelAddress,cancelPolicy;

    public HotelBookHistoryInfo( String bookingStatus,String booking_id,
                                 String hotel_check_in,String hotel_check_out,String currency,
                                 String location,String room_type_name,String total_fare,
                                 String hotelName,String hotelAddress,String cancelPolicy){
            this.bookingStatus=bookingStatus;
            this.booking_id=booking_id;
            this.hotel_check_in=hotel_check_in;
            this.hotel_check_out=hotel_check_out;
            this.currency=currency;
            this.location=location;
            this.room_type_name=room_type_name;
            this.total_fare=total_fare;
            this.hotelName=hotelName;
            this.hotelAddress=hotelAddress;
            this.cancelPolicy=cancelPolicy;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getHotel_check_in() {
        return hotel_check_in;
    }

    public void setHotel_check_in(String hotel_check_in) {
        this.hotel_check_in = hotel_check_in;
    }

    public String getHotel_check_out() {
        return hotel_check_out;
    }

    public void setHotel_check_out(String hotel_check_out) {
        this.hotel_check_out = hotel_check_out;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRoom_type_name() {
        return room_type_name;
    }

    public void setRoom_type_name(String room_type_name) {
        this.room_type_name = room_type_name;
    }

    public String getTotal_fare() {
        return total_fare;
    }

    public void setTotal_fare(String total_fare) {
        this.total_fare = total_fare;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public String getCancelPolicy() {
        return cancelPolicy;
    }

    public void setCancelPolicy(String cancelPolicy) {
        this.cancelPolicy = cancelPolicy;
    }
}
