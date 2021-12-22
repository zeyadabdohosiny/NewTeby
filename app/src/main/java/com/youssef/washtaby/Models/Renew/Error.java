package com.youssef.washtaby.Models.Renew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Error {
    @SerializedName("subscription_id")
    @Expose
    private List<String> subscriptionId = null;

    public List<String> getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(List<String> subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
}
