package com.example.pierre.movieapplicaiton;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by pierre on 21/09/2017.
 */

public class FilmLayout extends Fragment {
    private TextView titleField, descriptionField, dateField, ratingField;
    private ImageView genreField, backgroundField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Haal Fragment-layout op
        View fragmentView = inflater.inflate(R.layout.film_layout, container, false);


        // Haal alle verschillende views op
        backgroundField = (ImageView) fragmentView.findViewById(R.id.backgroundImage);
        titleField = (TextView) fragmentView.findViewById(R.id.titleField);
        descriptionField = (TextView) fragmentView.findViewById(R.id.descriptionField);
        dateField = (TextView) fragmentView.findViewById(R.id.yearField);
        ratingField = (TextView) fragmentView.findViewById(R.id.ratingField);
        genreField = (ImageView) fragmentView.findViewById(R.id.genre);
        return fragmentView;


}
    // Deze functie zal opgeroepen worden via DetailActivity en MainActivity, op een instantie van DetailFragment
    // Todo: Veel logica in één functie --> meerdere functies van maken
    public void setAllFieldsAndImageFromPosition(int position) {
        Resources res = getResources();
        String[] movies = res.getStringArray(R.array.titles);
        String[] genres = res.getStringArray(R.array.genres);
        String[] years = res.getStringArray(R.array.years);
        String[] ratings = res.getStringArray(R.array.ratings);

        String movie = movies[position];
        String year = years[position];
        String rating = ratings[position];

        String url = movie.toLowerCase().replace(" ","_");
        Context context = backgroundField.getContext();
        int id = context.getResources().getIdentifier(url, "drawable", context.getPackageName());
        backgroundField.setImageResource(id);
        titleField.setText(movie);
        dateField.setText(year);
        ratingField.setText(rating);
        if (genres[position].equals("Comedy")) {
            genreField.setImageResource(R.drawable.comedy);
        } else if (genres[position].equals("Thriller")) {
            genreField.setImageResource(R.drawable.thriller);
        } else if (genres[position].equals("Action")) {
            genreField.setImageResource(R.drawable.horror);
        } else if (genres[position].equals("Animation")) {
            genreField.setImageResource(R.drawable.disney);
        } else if (genres[position].equals("Musical")) {
            genreField.setImageResource(R.drawable.musical);
        } else if (genres[position].equals("Drama")) {
            genreField.setImageResource(R.drawable.drama);
        } else {
            genreField.setImageResource(R.drawable.film);
        }

    }

}


