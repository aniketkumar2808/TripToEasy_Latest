package com.TripToEasy.model.parsingModel.flight;

/**
 * Created by ptblr-1174 on 11/5/18.
 */

public class MealInfo{
    String mealId,origin,destination,discription,code;
    public MealInfo(){

    }

    public MealInfo(String mealId,String origin,String destination,String discription,String code){
        this.mealId=mealId;
        this.origin=origin;
        this.destination=destination;
        this.discription=discription;
        this.code=code;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
