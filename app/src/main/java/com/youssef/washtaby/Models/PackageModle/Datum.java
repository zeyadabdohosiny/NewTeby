package com.youssef.washtaby.Models.PackageModle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Datum implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title_en")
    @Expose
    private String titleEn;
    @SerializedName("title_ar")
    @Expose
    private String titleAr;
    @SerializedName("description_en")
    @Expose
    private String descriptionEn;
    @SerializedName("description_ar")
    @Expose
    private String descriptionAr;
    @SerializedName("min_number_of_ads")
    @Expose
    private Integer minNumberOfAds;
    @SerializedName("max_number_of_ads")
    @Expose
    private Integer maxNumberOfAds;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("features")
    @Expose
    private List<Feature> features = null;
    @SerializedName("subscription_types")
    @Expose
    private List<SubscriptionType> subscriptionTypes = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public Integer getMinNumberOfAds() {
        return minNumberOfAds;
    }

    public void setMinNumberOfAds(Integer minNumberOfAds) {
        this.minNumberOfAds = minNumberOfAds;
    }

    public Integer getMaxNumberOfAds() {
        return maxNumberOfAds;
    }

    public void setMaxNumberOfAds(Integer maxNumberOfAds) {
        this.maxNumberOfAds = maxNumberOfAds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public List<SubscriptionType> getSubscriptionTypes() {
        return subscriptionTypes;
    }

    public void setSubscriptionTypes(List<SubscriptionType> subscriptionTypes) {
        this.subscriptionTypes = subscriptionTypes;
    }

}
