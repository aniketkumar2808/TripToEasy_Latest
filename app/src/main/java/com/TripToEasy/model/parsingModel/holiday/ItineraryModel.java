package com.TripToEasy.model.parsingModel.holiday;

/**
 * Created by ptblr-1195 on 17/4/18.
 */

public class ItineraryModel {
    String itineraryId;
    String packageId;
    String dayText;
    String packageCity;
    String place;
    String descriptionText;
    String itineraryPic;

    public ItineraryModel(String itineraryId, String packageId, String dayText,
                          String packageCity, String place, String descriptionText,
                          String itineraryPic) {
        this.itineraryId = itineraryId;
        this.packageId = packageId;
        this.dayText = dayText;
        this.packageCity = packageCity;
        this.place = place;
        this.descriptionText = descriptionText;
        this.itineraryPic = itineraryPic;
    }

    public String getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(String itineraryId) {
        this.itineraryId = itineraryId;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getDayText() {
        return dayText;
    }

    public void setDayText(String dayText) {
        this.dayText = dayText;
    }

    public String getPackageCity() {
        return packageCity;
    }

    public void setPackageCity(String packageCity) {
        this.packageCity = packageCity;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getItineraryPic() {
        return itineraryPic;
    }

    public void setItineraryPic(String itineraryPic) {
        this.itineraryPic = itineraryPic;
    }

}