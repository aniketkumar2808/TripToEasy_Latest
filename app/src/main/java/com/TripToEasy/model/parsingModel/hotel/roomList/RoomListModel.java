package com.TripToEasy.model.parsingModel.hotel.roomList;

/**
 * Created by ptblr-1195 on 27/3/18.
 */

public class RoomListModel {
    String roomName;
    String amenityOne;
    String amenityTwo;
    String price;
    String nightCount;
    Boolean isRefundable;
    Boolean isSelected;
    String searchId;
    String cancelPolicy;
    String token;
    String tokenKey;
    String resultIndex;

    public RoomListModel(String roomName, String amenityOne,
                         String amenityTwo, String price, String nightCount,
                         Boolean isRefundable,String resultIndex,String searchId,String cancelPolicy,String token,String tokenKey, Boolean isSelected) {
        this.roomName = roomName;
        this.amenityOne = amenityOne;
        this.amenityTwo = amenityTwo;
        this.price = price;
        this.nightCount = nightCount;
        this.isRefundable = isRefundable;
        this.isSelected = isSelected;
        this.searchId=searchId;
        this.cancelPolicy=cancelPolicy;
        this.token=token;
        this.tokenKey=tokenKey;
        this.resultIndex=resultIndex;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getAmenityOne() {
        return amenityOne;
    }

    public void setAmenityOne(String amenityOne) {
        this.amenityOne = amenityOne;
    }

    public String getAmenityTwo() {
        return amenityTwo;
    }

    public void setAmenityTwo(String amenityTwo) {
        this.amenityTwo = amenityTwo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNightCount() {
        return nightCount;
    }

    public void setNightCount(String nightCount) {
        this.nightCount = nightCount;
    }

    public Boolean getRefundable() {
        return isRefundable;
    }

    public void setRefundable(Boolean refundable) {
        isRefundable = refundable;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public String getCancelPolicy() {
        return cancelPolicy;
    }

    public void setCancelPolicy(String cancelPolicy) {
        this.cancelPolicy = cancelPolicy;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public String getResultIndex() {
        return resultIndex;
    }

    public void setResultIndex(String resultIndex) {
        this.resultIndex = resultIndex;
    }
}