package com.TripToEasy.model.parsingModel.hotel.listing;

/**
 * Created by ptblr-1195 on 22/3/18.
 */

public class HotelListingMain {

    String hotelName;
    String hotelAddress;
    String hotelImage;
    Integer hotelRating;
    String hotelPrice;
    Integer hotelResultIndex;
    String hotelCode;
    Boolean wifi;
    Boolean breakFast;
    Boolean parking;
    Boolean pool;
    String token;
    String bookingSource;
    String searchId;

    public String getHotelLocation() {
        return HotelLocation;
    }

    public void setHotelLocation(String hotelLocation) {
        HotelLocation = hotelLocation;
    }

    String HotelLocation;

    public HotelListingMain(String hotelName, String hotelAddress, String hotelImage,
                            Integer hotelRating, String hotelPrice, Integer hotelResultIndex,
                            String hotelCode) {
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.hotelImage = hotelImage;
        this.hotelRating = hotelRating;
        this.hotelPrice = hotelPrice;
        this.hotelResultIndex = hotelResultIndex;
        this.hotelCode = hotelCode;
    }

    public HotelListingMain(String hotelName, String hotelAddress, String hotelImage,
                            Integer hotelRating, String hotelPrice,
                            Integer hotelResultIndex, String hotelCode,
                            Boolean wifi, Boolean breakFast, Boolean parking,
                            Boolean pool) {
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.hotelImage = hotelImage;
        this.hotelRating = hotelRating;
        this.hotelPrice = hotelPrice;
        this.hotelResultIndex = hotelResultIndex;
        this.hotelCode = hotelCode;
        this.wifi = wifi;
        this.breakFast = breakFast;
        this.parking = parking;
        this.pool = pool;
    }

    public HotelListingMain(String hotelName,String HotelLocation, String hotelAddress, String hotelImage,
                            Integer hotelRating, String hotelPrice,
                            Integer hotelResultIndex, String hotelCode, Boolean wifi,
                            Boolean breakFast, Boolean parking, Boolean pool, String token,
                            String bookingSource, String searchId) {
        this.hotelName = hotelName;
        this.HotelLocation = HotelLocation;
        this.hotelAddress = hotelAddress;
        this.hotelImage = hotelImage;
        this.hotelRating = hotelRating;
        this.hotelPrice = hotelPrice;
        this.hotelResultIndex = hotelResultIndex;
        this.hotelCode = hotelCode;
        this.wifi = wifi;
        this.breakFast = breakFast;
        this.parking = parking;
        this.pool = pool;
        this.token = token;
        this.bookingSource = bookingSource;
        this.searchId = searchId;
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

    public String getHotelImage() {
        return hotelImage;
    }

    public void setHotelImage(String hotelImage) {
        this.hotelImage = hotelImage;
    }

    public Integer getHotelRating() {
        return hotelRating;
    }

    public void setHotelRating(Integer hotelRating) {
        this.hotelRating = hotelRating;
    }

    public String getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(String hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

    public Integer getHotelResultIndex() {
        return hotelResultIndex;
    }

    public void setHotelResultIndex(Integer hotelResultIndex) {
        this.hotelResultIndex = hotelResultIndex;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public void setWifi(Boolean wifi) {
        this.wifi = wifi;
    }

    public Boolean getBreakFast() {
        return breakFast;
    }

    public void setBreakFast(Boolean breakFast) {
        this.breakFast = breakFast;
    }

    public Boolean getParking() {
        return parking;
    }

    public void setParking(Boolean parking) {
        this.parking = parking;
    }

    public Boolean getPool() {
        return pool;
    }

    public void setPool(Boolean pool) {
        this.pool = pool;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBookingSource() {
        return bookingSource;
    }

    public void setBookingSource(String bookingSource) {
        this.bookingSource = bookingSource;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }
}