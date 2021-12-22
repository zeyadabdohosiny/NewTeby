package com.youssef.washtaby.Models.PackageModle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pivot__1 {

    @SerializedName("package_id")
    @Expose
    private Integer packageId;
    @SerializedName("subscription_type_id")
    @Expose
    private Integer subscriptionTypeId;
    @SerializedName("cost")
    @Expose
    private Integer cost;

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Integer getSubscriptionTypeId() {
        return subscriptionTypeId;
    }

    public void setSubscriptionTypeId(Integer subscriptionTypeId) {
        this.subscriptionTypeId = subscriptionTypeId;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

}
