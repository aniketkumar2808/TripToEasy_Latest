package com.TripToEasy.model.parsingModel.holiday;

/**
 * Created by ptblr-1195 on 10/4/18.
 */

public class PackageListModel {
    String packageName;
    String packageTypeId;
    String duration;
    String countryId;

    public PackageListModel(String packageName, String packageTypeId,
                            String duration, String countryId) {
        this.packageName = packageName;
        this.packageTypeId = packageTypeId;
        this.duration = duration;
        this.countryId = countryId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageTypeId() {
        return packageTypeId;
    }

    public void setPackageTypeId(String packageTypeId) {
        this.packageTypeId = packageTypeId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
}