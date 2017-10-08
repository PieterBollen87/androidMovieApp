package be.pxl.filmapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import be.pxl.filmapp.data.bean.MovieBean;

public class MovieDetailActivity extends AppCompatActivity {

    private MovieBean movie;
    public static final String MOVIE_OBJECT = "be.pxl.movieapp.MOVIE_OBJECT";
    TextView textMovieTitle;
    TextView textYearField;
    TextView ratingField;
    TextView descField;
    NetworkImageView backgroundImageField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textMovieTitle = (TextView) findViewById(R.id.titleField);
        textYearField = (TextView) findViewById(R.id.yearField);
        ratingField = (TextView) findViewById(R.id.ratingField);
        descField = (TextView) findViewById(R.id.descriptionField);
        backgroundImageField = (NetworkImageView) findViewById(R.id.backgroundImage);

        MovieBean Film;
        Film = readDisplayStateValues();
        initializeDisplayContent(Film);

    }

    private MovieBean readDisplayStateValues() {
        Intent intent = getIntent();
        movie = intent.getParcelableExtra(this.MOVIE_OBJECT);
        movie.toString();

        return movie;
    }

    private void initializeDisplayContent(MovieBean film) {

        textMovieTitle.setText(film.getTitle());
        textYearField.setText(film.getYear());
        ratingField.setText(film.getRating());
        descField.setText(film.getDirector());
        String name = film.getTitle().toLowerCase();
        String posterUrl = String.format("%s/public/images/%s.jpg", this.getResources().getString(R.string.api_url).toString(), name.replace(" ", "_"));

        backgroundImageField.setImageAlpha(80);
        backgroundImageField.setImageUrl(posterUrl, VolleySingleton.getInstance(this).getImageLoader());

    }


}