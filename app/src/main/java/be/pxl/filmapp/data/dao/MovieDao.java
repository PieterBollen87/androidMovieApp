package be.pxl.filmapp.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import be.pxl.filmapp.data.bean.MovieBean;

/**
 * Created by Sacha on 22-10-2017.
 */

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    List<MovieBean> getAll();

    @Query("SELECT * FROM movies")
    LiveData<List<MovieBean>> getMovieLiveData();

    @Query("SELECT * FROM movies WHERE id = :id LIMIT 1")
    MovieBean findById(int id);

    @Insert
    void insertAll(MovieBean... movies);

    @Delete
    void delete(MovieBean movie);
}
