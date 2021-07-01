package com.TripToEasy.model.parsingModel.transfers;

/**
 * Created by ptblr-1195 on 5/6/17.
 */

public class TransfersTravellerInfo {
    String titleValue = null;
    String typeValue = null;
    String firstName = null;
    String lastName = null;

    public TransfersTravellerInfo(String titleValue,String typeValue, String firstName, String lastName) {
        this.titleValue = titleValue;
        this.typeValue = typeValue;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getTitleValue() {
        return titleValue;
    }

    public void setTitleValue(String titleValue) {
        this.titleValue = titleValue;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
