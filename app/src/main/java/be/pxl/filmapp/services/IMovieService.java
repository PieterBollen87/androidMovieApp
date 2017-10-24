package be.pxl.filmapp.services;

import android.arch.lifecycle.LiveData;

import java.util.List;

import be.pxl.filmapp.data.bean.MovieBean;

/**
 * Created by Sacha on 22-10-2017.
 */

public interface IMovieService {
    List<MovieBean> getAllMovies();
    LiveData<List<MovieBean>> getAllMoviesObs();
    void updateMovieDatabaseFromApi(List<MovieBean> existingMovies);
}
