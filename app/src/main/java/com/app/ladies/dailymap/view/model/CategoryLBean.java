package com.app.ladies.dailymap.view.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Kyoko1 on 2016/07/02.
 */
public class CategoryLBean {

    @JsonProperty("category_l_code")
    private String code;

    @JsonProperty("category_l_name")
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
