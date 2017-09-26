package com.sacha.service;

import com.sacha.bean.MovieBean;

import java.util.List;

/**
 * Created by Sacha on 25-9-2017.
 */
public interface IMovieService {
    public MovieBean getMovieById(int id);
    public List<MovieBean> getPopularMovies();
}
