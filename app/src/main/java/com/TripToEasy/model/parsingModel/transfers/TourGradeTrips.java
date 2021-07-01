package com.TripToEasy.model.parsingModel.transfers;

/**
 * Created by ptblr-1174 on 31/7/18.
 */

public class TourGradeTrips {
    String bookingDate,langServices,gradeCode,gradeTitle,gradeDescription,Currency,NetFare,TourUniqueId,TotalPax;

    public TourGradeTrips( String bookingDate,String langServices,String gradeCode,
                           String gradeTitle,String gradeDescription, String Currency,
                           String NetFare,String TourUniqueId,String TotalPax){
        this.bookingDate=bookingDate;
        this.langServices=langServices;
        this.gradeCode=gradeCode;
        this.gradeTitle=gradeTitle;
        this.gradeDescription=gradeDescription;
        this.Currency=Currency;
        this.NetFare=NetFare;
        this.TourUniqueId=TourUniqueId;
        this.TotalPax=TotalPax;

    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getLangServices() {
        return langServices;
    }

    public void setLangServices(String langServices) {
        this.langServices = langServices;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getNetFare() {
        return NetFare;
    }

    public void setNetFare(String netFare) {
        NetFare = netFare;
    }

    public String getTourUniqueId() {
        return TourUniqueId;
    }

    public void setTourUniqueId(String tourUniqueId) {
        TourUniqueId = tourUniqueId;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getGradeTitle() {
        return gradeTitle;
    }

    public void setGradeTitle(String gradeTitle) {
        this.gradeTitle = gradeTitle;
    }

    public String getGradeDescription() {
        return gradeDescription;
    }

    public void setGradeDescription(String gradeDescription) {
        this.gradeDescription = gradeDescription;
    }

    public String getTotalPax() {
        return TotalPax;
    }

    public void setTotalPax(String totalPax) {
        TotalPax = totalPax;
    }
}
