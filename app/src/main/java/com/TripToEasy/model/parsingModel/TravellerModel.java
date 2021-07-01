package com.TripToEasy.model.parsingModel;

/**
 * Created by ptblr-1195 on 17/4/18.
 */

/**
 * Gender
 * 1 - Male
 * 2 - Female
 *
 * action type
 * 1 - Adult
 * 2 - Child
 * 3 - Infant
 * */
public class TravellerModel {
    Integer id;
    String firstName;
    String lastName;
    Integer genderType;
    Integer actionType;
    Integer isSelected;
    String dateOfBirth;
    String passportNumber;
    String passportExpiry;
    String passportCountry;

    public TravellerModel(String firstName, String lastName,
                          Integer genderType, Integer actionType,
                          Integer isSelected) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.genderType = genderType;
        this.actionType = actionType;
        this.isSelected = isSelected;
    }

    public TravellerModel() {

    }

    public TravellerModel(String firstName, String lastName,
                          Integer genderType, Integer actionType,
                          Integer isSelected,String dateOfBirth,String passportNumber,String passportExpiry,String passportCountry) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.genderType = genderType;
        this.actionType = actionType;
        this.isSelected = isSelected;
        this.dateOfBirth=dateOfBirth;
        this.passportNumber=passportNumber;
        this.passportExpiry=passportExpiry;
        this.passportCountry=passportCountry;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getGenderType() {
        return genderType;
    }

    public void setGenderType(Integer genderType) {
        this.genderType = genderType;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public Integer getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Integer isSelected) {
        this.isSelected = isSelected;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPassportExpiry() {
        return passportExpiry;
    }

    public void setPassportExpiry(String passportExpiry) {
        this.passportExpiry = passportExpiry;
    }

    public String getPassportCountry() {
        return passportCountry;
    }

    public void setPassportCountry(String passportCountry) {
        this.passportCountry = passportCountry;
    }
}