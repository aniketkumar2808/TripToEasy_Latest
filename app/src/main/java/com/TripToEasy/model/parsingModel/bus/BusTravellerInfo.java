package com.TripToEasy.model.parsingModel.bus;

/**
 * Created by ptblr-1195 on 5/6/17.
 */

public class BusTravellerInfo {
    String titleValue = null;
    String ContactName = null;
    String ageValue = null;

    public BusTravellerInfo(String titleValue, String contactName, String ageValue) {
        this.titleValue = titleValue;
        ContactName = contactName;
        this.ageValue = ageValue;
    }

    public String getTitleValue() {
        return titleValue;
    }

    public void setTitleValue(String titleValue) {
        this.titleValue = titleValue;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getAgeValue() {
        return ageValue;
    }

    public void setAgeValue(String ageValue) {
        this.ageValue = ageValue;
    }

}
