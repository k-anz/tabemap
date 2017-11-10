package com.app.ladies.dailymap.view.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Kyoko1 on 2016/11/02.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ImageUrlBean implements Serializable {
    @JsonProperty("shop_image1")
    private String shopImage1;

    @JsonProperty("shop_image2")
    private String shopImage2;

    private String qrcode;

    public String getShopImage2() {
        return shopImage2;
    }

    public void setShopImage2(String shopImage2) {
        this.shopImage2 = shopImage2;
    }

    public String getShopImage1() {
        return shopImage1;
    }

    public void setShopImage1(String shopImage1) {
        this.shopImage1 = shopImage1;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
}
