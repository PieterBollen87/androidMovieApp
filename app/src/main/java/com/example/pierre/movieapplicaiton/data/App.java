package com.sacha;

import com.sacha.bean.MovieBean;
import com.sacha.dao.MovieaApiDao;
import com.sacha.service.IMovieService;
import com.sacha.service.MovieService;

import java.util.List;

/**
 * Created by Sacha on 25-9-2017.
 */
public class App {

    public static void main(String[] args) {
        IMovieService movieService = new MovieService(new MovieaApiDao("3c0f0b4edd4c11618a02c07f207ab230"));
        MovieBean movie = movieService.getMovieById(550);
        List<MovieBean> popularMovies = movieService.getPopularMovies();

        System.out.println(movie.getTitle());
        System.out.println("Popular Movies: ");
        System.out.println();

        for (MovieBean m : popularMovies) {
            System.out.println(m.getTitle());
        }
    }

}
