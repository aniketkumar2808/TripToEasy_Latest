package com.TripToEasy.model.parsingModel.transfers;

/**
 * Created by ptblr-1174 on 12/9/18.
 */

public class TransfersHotelInfo
{
   String hotel_name,hotel_id;
   public TransfersHotelInfo(String hotel_name,String hotel_id){
       this.hotel_name=hotel_name;
       this.hotel_id=hotel_id;
   }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }
}
