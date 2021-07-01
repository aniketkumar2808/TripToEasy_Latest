package com.TripToEasy.model.parsingModel.sightSeeing;

import org.json.JSONArray;

import java.io.Serializable;

public class TripData implements Serializable {


    private String gradeTitle;
    private String gradeCode;
    private String gradeDescription;
    private String gradeDepartureTime;
    private String TotalPax;
    private String TotalDisplayFare;
    private String TourUniqueId;
    private String bookingDate;

    private String productCode;

    JSONArray AgeBands;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public JSONArray getAgeBands() {
        return AgeBands;
    }

    public void setAgeBands(JSONArray ageBands) {
        AgeBands = ageBands;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getTourUniqueId() {
        return TourUniqueId;
    }

    public void setTourUniqueId(String tourUniqueId) {
        TourUniqueId = tourUniqueId;
    }

    public String getGradeTitle() {
        return gradeTitle;
    }

    public void setGradeTitle(String gradeTitle) {
        this.gradeTitle = gradeTitle;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getGradeDescription() {
        return gradeDescription;
    }

    public void setGradeDescription(String gradeDescription) {
        this.gradeDescription = gradeDescription;
    }

    public String getGradeDepartureTime() {
        return gradeDepartureTime;
    }

    public void setGradeDepartureTime(String gradeDepartureTime) {
        this.gradeDepartureTime = gradeDepartureTime;
    }

    public String getTotalPax() {
        return TotalPax;
    }

    public void setTotalPax(String totalPax) {
        TotalPax = totalPax;
    }

    public String getTotalDisplayFare() {
        return TotalDisplayFare;
    }

    public void setTotalDisplayFare(String totalDisplayFare) {
        TotalDisplayFare = totalDisplayFare;
    }
}
