package com.TripToEasy.model.parsingModel.hotel;

/**
 * Created by ptblr-1195 on 23/3/18.
 */

public class AmenitiesModel {

    String amenityName;
    Boolean isSelected;

    public AmenitiesModel(String amenityName, Boolean isSelected) {
        this.amenityName = amenityName;
        this.isSelected = isSelected;
    }

    public String getAmenityName() {
        return amenityName;
    }

    public void setAmenityName(String amenityName) {
        this.amenityName = amenityName;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
