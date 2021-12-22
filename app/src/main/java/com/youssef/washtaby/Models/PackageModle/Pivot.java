package com.youssef.washtaby.Models.PackageModle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pivot {
    @SerializedName("package_id")
    @Expose
    private Integer packageId;
    @SerializedName("feature_id")
    @Expose
    private Integer featureId;
    @SerializedName("number_of_ads")
    @Expose
    private Integer numberOfAds;

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Integer getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Integer featureId) {
        this.featureId = featureId;
    }

    public Integer getNumberOfAds() {
        return numberOfAds;
    }

    public void setNumberOfAds(Integer numberOfAds) {
        this.numberOfAds = numberOfAds;
    }

}
