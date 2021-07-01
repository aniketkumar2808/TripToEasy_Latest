package com.TripToEasy.sight_seeing.sightseeing_adapters;

public class SightseeingListBean {

    public String productName;
    public String productCode;
    public String image;
    public String bigImage;
    public String destinationName;
    public String price;
    public String description;
    public String duration;
    public String resultToken;
    public String reviewCount;
    public String ratings;
    public boolean refundable;

    public SightseeingListBean(String productName, String productCode, String image, String bigImage, String destinationName, String price, String description, String duration, String resultToken, String reviewCount, String ratings, boolean refundable) {
        this.productName = productName;
        this.productCode = productCode;
        this.image = image;
        this.bigImage = bigImage;
        this.destinationName = destinationName;
        this.price = price;
        this.description = description;
        this.duration = duration;
        this.resultToken = resultToken;
        this.reviewCount = reviewCount;
        this.ratings = ratings;
        this.refundable = refundable;
    }

    public boolean isRefundable() {
        return refundable;
    }

    public void setRefundable(boolean refundable) {
        this.refundable = refundable;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBigImage() {
        return bigImage;
    }

    public void setBigImage(String bigImage) {
        this.bigImage = bigImage;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getResultToken() {
        return resultToken;
    }

    public void setResultToken(String resultToken) {
        this.resultToken = resultToken;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }
}
