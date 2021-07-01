package com.TripToEasy.model.parsingModel.flight;

/**
 * Created by ptblr-1174 on 11/5/18.
 */

public class BaggageSelectionInfo {
    String BaggageId,Origin,Destination,Price,Code,Weight;

    public BaggageSelectionInfo(){

    }
    public BaggageSelectionInfo(String BaggageId,String Origin, String Destination,String Price,String Code,String Weight){
        this.BaggageId=BaggageId;
        this.Origin=Origin;
        this.Destination=Destination;
        this.Price=Price;
        this.Code=Code;
        this.Weight=Weight;
    }

    public String getBaggageId() {
        return BaggageId;
    }

    public void setBaggageId(String baggageId) {
        BaggageId = baggageId;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }
}
