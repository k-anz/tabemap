package com.app.ladies.dailymap.view.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Kyoko1 on 2016/03/02.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class RestaurantBean implements Serializable {

    @JsonProperty("id")
    private String storeId;

    @JsonProperty("name")
    private String storeName;

    private String categoryCd; // TODO どのような扱いにする？

    private String address;

    private String tel;

    @JsonProperty("url")
    private String storeUrl;

    private String latitude;

    private String longitude;

    private ImageUrlBean imgUrl;


    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCategoryCd() {
        return categoryCd;
    }

    public void setCategoryCd(String categoryCd) {
        this.categoryCd = categoryCd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public ImageUrlBean getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(ImageUrlBean imgUrl) {
        this.imgUrl = imgUrl;
    }
}
