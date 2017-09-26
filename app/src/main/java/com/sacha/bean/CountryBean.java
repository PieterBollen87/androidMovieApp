package com.sacha.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Created by Sacha on 25-9-2017.
 */
public class CountryBean {
    private int id;
    @JsonProperty("iso_3166_1")
    private String iso;
    private String name;

    public CountryBean(String iso, String name) {
        this.iso = iso;
        this.name = name;
    }

    public CountryBean(int id, String iso, String name) {
        this.id = id;
        this.iso = iso;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CountryBean() {
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
