package be.pxl.filmapp;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

import be.pxl.filmapp.adapters.GridImageAdapter;
import be.pxl.filmapp.data.bean.MovieBean;
import be.pxl.filmapp.utility.AppHelper;
import be.pxl.filmapp.utility.UserSession;
import be.pxl.filmapp.utility.VolleySingleton;

/**
 * Created by pierre on 30/10/2017.
 */

public class MyListsFragment extends Fragment {
    private GridView gridview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_lists_fragment, container, false);
        gridview = (GridView) view.findViewById(R.id.gridview);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(gridview.getContext(), "" + position,
                        Toast.LENGTH_SHORT).show();
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
                            gridview.setAdapter(new GridImageAdapter(getContext(), movies));
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

}
