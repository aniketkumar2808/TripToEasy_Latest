package com.TripToEasy.model.parsingModel.bus;

/**
 * Created by ADMIN on 27-03-2018.
 */

public class BusListModel {
    String companyName;
    String companyID;
    String busType;
    String departureTime;
    String arrivalTime;
    String travelTime;
    String price;
    String seatCount;
    String routCode;
    String routScheduleId;
    String departDate;
    String resultToken;
    String arrTime24hr;
    String deptTime24hr;
    String isAC ,seatingType;

    public BusListModel(String companyName, String busType, String departureTime, String arrivalTime,
                        String travelTime, String price, String seatCount, String routCode) {
        this.companyName = companyName;
        this.busType = busType;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.travelTime = travelTime;
        this.price = price;
        this.seatCount = seatCount;
        this.routCode = routCode;
    }

    public BusListModel(String companyName,String companyID, String busType, String departureTime,String deptTime24hr,
                        String arrivalTime, String arrTime24hr,String travelTime, String price,
                        String seatCount, String routCode, String routScheduleId,
                        String departDate, String resultToken,String isAC,String seatingType) {
        this.companyName = companyName;
        this.companyID = companyID;
        this.busType = busType;
        this.departureTime = departureTime;
        this.deptTime24hr = deptTime24hr;
        this.arrivalTime = arrivalTime;
        this.arrTime24hr = arrTime24hr;
        this.travelTime = travelTime;
        this.price = price;
        this.seatCount = seatCount;
        this.routCode = routCode;
        this.routScheduleId = routScheduleId;
        this.departDate = departDate;
        this.resultToken = resultToken;
        this.isAC=isAC;
        this.seatingType=seatingType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(String seatCount) {
        this.seatCount = seatCount;
    }

    public String getRoutCode() {
        return routCode;
    }

    public void setRoutCode(String routCode) {
        this.routCode = routCode;
    }

    public String getRoutScheduleId() {
        return routScheduleId;
    }

    public void setRoutScheduleId(String routScheduleId) {
        this.routScheduleId = routScheduleId;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public String getResultToken() {
        return resultToken;
    }

    public void setResultToken(String resultToken) {
        this.resultToken = resultToken;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getArrTime24hr() {
        return arrTime24hr;
    }

    public void setArrTime24hr(String arrTime24hr) {
        this.arrTime24hr = arrTime24hr;
    }

    public String getDeptTime24hr() {
        return deptTime24hr;
    }

    public void setDeptTime24hr(String deptTime24hr) {
        this.deptTime24hr = deptTime24hr;
    }

    public String getIsAC() {
        return isAC;
    }

    public void setIsAC(String isAC) {
        this.isAC = isAC;
    }

    public String getSeatingType() {
        return seatingType;
    }

    public void setSeatingType(String seatingType) {
        this.seatingType = seatingType;
    }
}