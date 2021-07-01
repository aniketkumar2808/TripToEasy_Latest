package com.TripToEasy.model.parsingModel.sightSeeing;


public class TRData {

    String Header="", FirstName="", LastName="", DOB="";


    String PassportExpiryDate="",Height="",Weight="", HeightType="", WeightType="";


    int Title=0;

    boolean showHeight,showWeight,showDOB,showPassportExpire;


    public boolean isShowHeight() {
        return showHeight;
    }

    public void setShowHeight(boolean showHeight) {
        this.showHeight = showHeight;
    }

    public boolean isShowWeight() {
        return showWeight;
    }

    public void setShowWeight(boolean showWeight) {
        this.showWeight = showWeight;
    }

    public boolean isShowDOB() {
        return showDOB;
    }

    public void setShowDOB(boolean showDOB) {
        this.showDOB = showDOB;
    }

    public boolean isShowPassportExpire() {
        return showPassportExpire;
    }

    public void setShowPassportExpire(boolean showPassportExpire) {
        this.showPassportExpire = showPassportExpire;
    }

    public String getHeader() {
        return Header;
    }

    public void setHeader(String header) {
        Header = header;
    }

    public int getTitle() {
        return Title;
    }

    public void setTitle(int title) {
        Title = title;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }


    public String getPassportExpiryDate() {
        return PassportExpiryDate;
    }

    public void setPassportExpiryDate(String passportExpiryDate) {
        PassportExpiryDate = passportExpiryDate;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getHeightType() {
        return HeightType;
    }

    public void setHeightType(String heightType) {
        HeightType = heightType;
    }

    public String getWeightType() {
        return WeightType;
    }

    public void setWeightType(String weightType) {
        WeightType = weightType;
    }
}
