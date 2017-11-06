package be.pxl.filmapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

import be.pxl.filmapp.adapters.GridImageAdapter;
import be.pxl.filmapp.data.bean.MovieBean;
import be.pxl.filmapp.utility.UserSession;
import be.pxl.filmapp.utility.VolleySingleton;

/**
 * Created by pierre on 30/10/2017.
 */

public class MyListsFragment extends Fragment {
    private GridView gridview;
    private GridImageAdapter gridImageAdapter;
    private Button shareButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_lists_fragment, container, false);
        gridview = (GridView) view.findViewById(R.id.gridview);
        shareButton = (Button) view.findViewById(R.id.shareButton);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Fragment detailFragment = new MovieDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(MovieDetailFragment.MOVIE_OBJECT, gridImageAdapter.getPersonalMovies().get(position));
                detailFragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.MainFragmentContainer, detailFragment);
                fragmentTransaction.addToBackStack("FRAGMENT_TAG").commit();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TweetComposer.Builder builder = new TweetComposer.Builder(getContext())
                        .text(buildShareMoviesString());
                builder.show();
            }
        });

        initializeDisplayContent();

        return view;
    }

    private void initializeDisplayContent() {
        JsonArrayRequest jsArrRequest = new JsonArrayRequest
                (getResources().getString(R.string.api_url).toString() + "/api/usermovies/" + UserSession.EMAIL, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<MovieBean> movies = new ObjectMapper().readValue(response.toString(), new TypeReference<List<MovieBean>>() {
                            });
                            gridImageAdapter = new GridImageAdapter(getContext(), movies);
                            gridview.setAdapter(gridImageAdapter);
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
        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsArrRequest);
    }

    private String buildShareMoviesString() {
        StringBuilder sb = new StringBuilder("I have seen the following movies: ");

        for (MovieBean m : gridImageAdapter.getPersonalMovies()) {
            sb.append(m.getTitle() + ", ");
        }
        return sb.toString().substring(0, sb.toString().length() - 2);
    }

}
