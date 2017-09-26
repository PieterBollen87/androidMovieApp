package com.sacha.bean;

/**
 * Created by Sacha on 25-9-2017.
 */
public class GenreBean {
    private int id;
    private String name;

    public GenreBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenreBean() {
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
