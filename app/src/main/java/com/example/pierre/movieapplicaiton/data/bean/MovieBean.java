package com.example.pierre.movieapplicaiton.data.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.util.List;

/**
 * Created by Sacha on 25-9-2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieBean {
    public int id;
    public String titel;
    public String director;

    public MovieBean(int id, String titel, String director) {
        this.id = id;
        this.titel = titel;
        this.director = director;
    }

    public  MovieBean() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}