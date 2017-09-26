package com.sacha.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Sacha on 25-9-2017.
 */
public class LanguageBean {
    @JsonProperty("iso_639_1")
    private String iso;
    private String name;

    public LanguageBean(String iso, String name) {
        this.iso = iso;
        this.name = name;
    }

    public LanguageBean() {
        // default constructor
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
