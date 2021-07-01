package com.TripToEasy.model.parsingModel.transfers;

/**
 * Created by ptblr-1174 on 30/7/18.
 */

public class TransfersResults {
    String ProductName,ProductCode,ImageUrl,StarRating,ReviewCount,Duration,Cancellation_available,NetFare,Currency,ResultToken;
    public TransfersResults(String ProductName,String ProductCode,String ImageUrl,
                            String StarRating,String ReviewCount,
                            String Duration,String Cancellation_available,
                            String NetFare,String Currency,String ResultToken){
        this.ProductName=ProductName;
        this.ProductCode=ProductCode;
        this.ImageUrl=ImageUrl;
        this.StarRating=StarRating;
        this.ReviewCount=ReviewCount;
        this.Duration=Duration;
        this.Cancellation_available=Cancellation_available;
        this.NetFare=NetFare;
        this.Currency=Currency;
        this.ResultToken=ResultToken;
    }

    public String getResultToken() {
        return ResultToken;
    }

    public void setResultToken(String resultToken) {
        ResultToken = resultToken;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getStarRating() {
        return StarRating;
    }

    public void setStarRating(String starRating) {
        StarRating = starRating;
    }

    public String getReviewCount() {
        return ReviewCount;
    }

    public void setReviewCount(String reviewCount) {
        ReviewCount = reviewCount;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getCancellation_available() {
        return Cancellation_available;
    }

    public void setCancellation_available(String cancellation_available) {
        Cancellation_available = cancellation_available;
    }

    public String getNetFare() {
        return NetFare;
    }

    public void setNetFare(String netFare) {
        NetFare = netFare;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }
}
