package be.pxl.filmapp.services;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

import be.pxl.filmapp.R;
import be.pxl.filmapp.data.bean.MovieBean;
import be.pxl.filmapp.data.dao.MovieDao;
import be.pxl.filmapp.utility.VolleySingleton;

/**
 * Created by Sacha on 22-10-2017.
 */

public class MovieService implements IMovieService {
    private MovieDao movieDao;
    private Context context;

    public MovieService(MovieDao movieDao, Context context) {
        this.movieDao = movieDao;
        this.context = context;
    }

    @Override
    public List<MovieBean> getAllMovies() {
        return movieDao.getAll();
    }
    public LiveData<List<MovieBean>> getAllMoviesObs() {
        return movieDao.getMovieLiveData();
    }

    @Override
    public void updateMovieDatabaseFromApi(final List<MovieBean> existingMovies) {
        JsonArrayRequest jsArrRequest = new JsonArrayRequest
                (context.getResources().getString(R.string.api_url).toString() + "/api/films", new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<MovieBean> newMovies = new ObjectMapper().readValue(response.toString(), new TypeReference<List<MovieBean>>() {
                            });

                            updateMovieDatabase(existingMovies, newMovies).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
                        } catch (IOException e) {
                            Log.d("ERROR", e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", error.toString());
                    }
                });
        VolleySingleton.getInstance(context).addToRequestQueue(jsArrRequest);
    }

    private AsyncTask<Void, Void, Void> updateMovieDatabase(final List<MovieBean> existingMovies, final List<MovieBean> newMovieList) {
        return new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                for (MovieBean movie : newMovieList) {
                    if (!existingMovies.contains(movie))
                        movieDao.insertAll(movie);
                }
                return null;
            }
        };
    }
}
