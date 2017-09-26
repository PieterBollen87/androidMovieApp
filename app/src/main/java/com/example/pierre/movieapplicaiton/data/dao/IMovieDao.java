package com.example.pierre.movieapplicaiton.data.dao;

import com.example.pierre.movieapplicaiton.data.bean.MovieBean;

import java.util.List;

/**
 * Created by Sacha on 25-9-2017.
 */
public interface IMovieDao {
    public MovieBean getMovieById(int id);
    public List<MovieBean> getPopularMovies();
    public List<MovieBean> getAllMovies();

}
