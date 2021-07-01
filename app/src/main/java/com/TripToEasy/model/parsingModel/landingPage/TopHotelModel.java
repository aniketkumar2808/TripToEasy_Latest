package com.TripToEasy.model.parsingModel.landingPage;

/**
 * Created by ptblr-1195 on 20/3/18.
 */

public class TopHotelModel {

    String destinationName;
    String destinationImage;
    String hotelCount;
    String cityID;


    public TopHotelModel(String destinationName, String destinationImage,
                         String hotelCount,String cityID) {
        this.destinationName = destinationName;
        this.destinationImage = destinationImage;
        this.hotelCount = hotelCount;
        this.cityID=cityID;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDestinationImage() {
        return destinationImage;
    }

    public void setDestinationImage(String destinationImage) {
        this.destinationImage = destinationImage;
    }

    public String getHotelCount() {
        return hotelCount;
    }

    public void setHotelCount(String hotelCount) {
        this.hotelCount = hotelCount;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }
}