package be.pxl.filmapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import be.pxl.filmapp.data.bean.MovieBean;

public class MovieDetailActivity extends AppCompatActivity {

    private MovieBean movie;
    public static final String MOVIE_OBJECT = "be.pxl.movieapp.MOVIE_OBJECT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        readDisplayStateValues();
        initializeDisplayContent();
    }

    private void readDisplayStateValues() {
        Intent intent = getIntent();
        this.movie = intent.getParcelableExtra(this.MOVIE_OBJECT);
    }

    private void initializeDisplayContent() {
        EditText textMovieTitle = (EditText) findViewById(R.id.text_movie_title);
        EditText textMovieGenre = (EditText) findViewById(R.id.text_movie_genre);

        textMovieTitle.setText(movie.getTitle());
        textMovieGenre.setText(movie.getGenre());
    }
}
