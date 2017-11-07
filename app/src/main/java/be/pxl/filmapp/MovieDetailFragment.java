package be.pxl.filmapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import be.pxl.filmapp.data.bean.MovieBean;
import be.pxl.filmapp.utility.VolleySingleton;

/**
 * Created by pierre on 24/10/2017.
 */

public class MovieDetailFragment extends Fragment {

    private MovieBean movie;
    public static final String MOVIE_OBJECT = "be.pxl.filmapp.MOVIE_OBJECT";
    TextView textMovieTitle;
    TextView textYearField;
    TextView ratingField;
    NetworkImageView backgroundImageField;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_movie_detail, container, false);
        textMovieTitle = (TextView) view.findViewById(R.id.titleField);
        textYearField = (TextView) view.findViewById(R.id.yearField);
        ratingField = (TextView) view.findViewById(R.id.ratingField);

        backgroundImageField = (NetworkImageView) view.findViewById(R.id.backgroundImage);
        readDisplayStateValues();
        initializeDisplayContent();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle args = new Bundle();
        args.putString(TrailerFragment.TRAILER_STRING, movie.getTrailer());

        TrailerFragment trailerFragment = new TrailerFragment();
        trailerFragment.setArguments(args);
        fragmentTransaction.add(R.id.trailerFragmentContainer, trailerFragment);
        fragmentTransaction.commit();

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    private void readDisplayStateValues() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            movie = bundle.getParcelable(MOVIE_OBJECT);
        }
    }

    private void initializeDisplayContent() {
        textMovieTitle.setText(movie.getTitle());
        textYearField.setText(String.valueOf(movie.getYear()));
        ratingField.setText(String.valueOf(movie.getRating()));
        String name = movie.getTitle().toLowerCase();
        String posterUrl = String.format("%s/public/images/%s.jpg", this.getResources().getString(R.string.api_url).toString(), name.replace(" ", "_"));

        backgroundImageField.setImageAlpha(80);
        backgroundImageField.setImageUrl(posterUrl, VolleySingleton.getInstance(getContext()).getImageLoader());
    }
}