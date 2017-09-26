package com.sacha.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Sacha on 25-9-2017.
 */
public class CompanyBean {
    private int id;
    @JsonProperty("iso_3166_1")
    private String iso;
    private String name;

    public CompanyBean(int id, String iso, String name) {
        this.id = id;
        this.name = name;
        this.iso = iso;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public CompanyBean() {
        // default constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
