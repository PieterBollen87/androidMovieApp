package be.pxl.filmapp.utility;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import be.pxl.filmapp.data.bean.MovieBean;
import be.pxl.filmapp.data.dao.MovieDao;

/**
 * Created by Sacha on 22-10-2017.
 */

@Database(entities = {MovieBean.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}
