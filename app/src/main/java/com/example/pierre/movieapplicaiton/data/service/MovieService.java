package com.example.pierre.movieapplicaiton.data.service;

import com.example.pierre.movieapplicaiton.data.bean.MovieBean;
import com.example.pierre.movieapplicaiton.data.dao.IMovieDao;

import java.util.List;

/**
 * Created by Sacha on 25-9-2017.
 */
public class MovieService implements IMovieService {
    private IMovieDao movieDao;

    public MovieService(IMovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public MovieBean getMovieById(int id) {
        return movieDao.getMovieById(id);
    }

    @Override
    public List<MovieBean> getPopularMovies() {
        return movieDao.getPopularMovies();
    }

    @Override
    public List<MovieBean> getAllMovies() {
        return movieDao.getAllMovies();
    }

}
