package com.TripToEasy.model.parsingModel;

public class LocationModel {
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    String locationName;
        Boolean isSelected;

        public LocationModel(String locationName, Boolean isSelected) {
            this.locationName = locationName;
            this.isSelected = isSelected;
        }


}
