package be.pxl.filmapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

import be.pxl.filmapp.data.bean.MovieBean;

public class MovieDetailActivity extends AppCompatActivity  {

    private MovieBean movie;
    public static final String MOVIE_OBJECT = "be.pxl.movieapp.MOVIE_OBJECT";
    TextView textMovieTitle;
    TextView textYearField;
    TextView ratingField ;
    TextView descField ;
    ImageView backgroundImageField;
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
        backgroundImageField = (ImageView) findViewById(R.id.backgroundImage);

        MovieBean Film;
        Film=readDisplayStateValues();
        initializeDisplayContent(Film);

    }

    private MovieBean readDisplayStateValues() {
        Intent intent = getIntent();
//        final int position = intent.getIntExtra("position", -1);
        movie = intent.getParcelableExtra(this.MOVIE_OBJECT);
        movie.toString();

return movie;
    }

    private void initializeDisplayContent(MovieBean film) {

//        View view=(View) findViewById(R.id.filmDetailLayout);

        film.getTitle().toString();
        film.getRating().toString();
        film.getYear().toString();
        film.getDirector().toString();

        textMovieTitle.setText(film.getTitle());
        textYearField.setText(film.getYear());
        ratingField.setText(film.getRating());
        descField.setText(film.getDirector());
        String name=film.getTitle().toLowerCase();
        String afb=name.replace(" ","_");
        Context context = backgroundImageField.getContext();
        int id = context.getResources().getIdentifier(afb, "drawable", context.getPackageName());
        backgroundImageField.setImageResource(id);

    }



}