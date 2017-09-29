package be.pxl.filmapp;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import static be.pxl.filmapp.R.id.rating;
import static be.pxl.filmapp.R.id.year;

/**
 * Created by pierre on 28/09/2017.
 */

public class DetailFragment extends Fragment {

        private TextView titleField, descriptionField, dateField, ratingField;
        private ImageView genreField, backgroundField;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Haal Fragment-layout op
            View fragmentView = inflater.inflate(R.layout.content_movie_detail, container, false);


            // Haal alle verschillende views op
//        backgroundField = (ImageView) fragmentView.findViewById(R.id.backgroundImage);
            titleField = (TextView) fragmentView.findViewById(R.id.titleField);
            descriptionField = (TextView) fragmentView.findViewById(R.id.descriptionField);
            dateField = (TextView) fragmentView.findViewById(R.id.yearField);
            ratingField = (TextView) fragmentView.findViewById(R.id.ratingField);
            genreField = (ImageView) fragmentView.findViewById(R.id.genre);
            backgroundField=(ImageView) fragmentView.findViewById(R.id.backgroundImage);
            return fragmentView;


        }

    }

