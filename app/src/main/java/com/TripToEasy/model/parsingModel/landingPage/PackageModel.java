package com.TripToEasy.model.parsingModel.landingPage;

/**
 * Created by ptblr-1195 on 20/3/18.
 */

public class PackageModel {
    String packageID;
    String packageName;
    String packageImage;

    public PackageModel(String packageID,String packageName, String packageImage) {
        this.packageID=packageID;
        this.packageName = packageName;
        this.packageImage = packageImage;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageImage() {
        return packageImage;
    }

    public void setPackageImage(String packageImage) {
        this.packageImage = packageImage;
    }

    public String getPackageID() {
        return packageID;
    }

    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }
}