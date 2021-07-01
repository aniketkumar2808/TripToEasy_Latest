package com.TripToEasy.model.parsingModel.holiday;

/**
 * Created by ptblr-1195 on 11/4/18.
 */

public class PackageMainModel {
    private String packageId;
    private String packageCode;
    private String supplierId;
    private String tourTypes;
    private String packageName;
    private String packageTourCode;
    private String duration;
    private String packageDescription;
    private String image;
    private String packageCountry;
    private String packageState;
    private String packageCity;
    private String packageLocation;
    private String packageType;
    private String priceIncludes;
    private String deals;
    private String noQue;
    private String homePage;
    private String rating;
    private String status;
    private String price;
    private String display;
    private String domainListFk;
    private String topDestination;

    public PackageMainModel(String packageId, String packageCode, String supplierId,
                            String tourTypes, String packageName,
                            String packageTourCode, String duration,
                            String packageDescription, String image,
                            String packageCountry, String packageState,
                            String packageCity, String packageLocation,
                            String packageType, String priceIncludes, String deals,
                            String noQue, String homePage, String rating,
                            String status, String price, String display,
                            String domainListFk, String topDestination) {
        this.packageId = packageId;
        this.packageCode = packageCode;
        this.supplierId = supplierId;
        this.tourTypes = tourTypes;
        this.packageName = packageName;
        this.packageTourCode = packageTourCode;
        this.duration = duration;
        this.packageDescription = packageDescription;
        this.image = image;
        this.packageCountry = packageCountry;
        this.packageState = packageState;
        this.packageCity = packageCity;
        this.packageLocation = packageLocation;
        this.packageType = packageType;
        this.priceIncludes = priceIncludes;
        this.deals = deals;
        this.noQue = noQue;
        this.homePage = homePage;
        this.rating = rating;
        this.status = status;
        this.price = price;
        this.display = display;
        this.domainListFk = domainListFk;
        this.topDestination = topDestination;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getTourTypes() {
        return tourTypes;
    }

    public void setTourTypes(String tourTypes) {
        this.tourTypes = tourTypes;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageTourCode() {
        return packageTourCode;
    }

    public void setPackageTourCode(String packageTourCode) {
        this.packageTourCode = packageTourCode;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPackageDescription() {
        return packageDescription;
    }

    public void setPackageDescription(String packageDescription) {
        this.packageDescription = packageDescription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPackageCountry() {
        return packageCountry;
    }

    public void setPackageCountry(String packageCountry) {
        this.packageCountry = packageCountry;
    }

    public String getPackageState() {
        return packageState;
    }

    public void setPackageState(String packageState) {
        this.packageState = packageState;
    }

    public String getPackageCity() {
        return packageCity;
    }

    public void setPackageCity(String packageCity) {
        this.packageCity = packageCity;
    }

    public String getPackageLocation() {
        return packageLocation;
    }

    public void setPackageLocation(String packageLocation) {
        this.packageLocation = packageLocation;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getPriceIncludes() {
        return priceIncludes;
    }

    public void setPriceIncludes(String priceIncludes) {
        this.priceIncludes = priceIncludes;
    }

    public String getDeals() {
        return deals;
    }

    public void setDeals(String deals) {
        this.deals = deals;
    }

    public String getNoQue() {
        return noQue;
    }

    public void setNoQue(String noQue) {
        this.noQue = noQue;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getDomainListFk() {
        return domainListFk;
    }

    public void setDomainListFk(String domainListFk) {
        this.domainListFk = domainListFk;
    }

    public String getTopDestination() {
        return topDestination;
    }

    public void setTopDestination(String topDestination) {
        this.topDestination = topDestination;
    }
}