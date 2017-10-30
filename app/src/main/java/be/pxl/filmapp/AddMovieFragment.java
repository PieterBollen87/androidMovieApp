package be.pxl.filmapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import be.pxl.filmapp.adapters.spinnerArrayAdapter;
import be.pxl.filmapp.utility.AppHelper;
import be.pxl.filmapp.utility.VolleyMultipartRequest;
import be.pxl.filmapp.utility.VolleySingleton;

/**
 * Created by pierre on 03/10/2017.
 */

public class AddMovieFragment extends Fragment {
    private static final int REQUEST_CODE = 1;
    String[] genreList;
    EditText titleEditTextField;
    NumberPicker yearNumberPicker;
    EditText directorEditTextField;
    EditText trailerEditTextField;
    Spinner genreSpinner;
    Button uploadButton;
    Button browseButton;
    ImageView downloadImageView;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {

                case REQUEST_CODE:
                    if (resultCode == Activity.RESULT_OK) {
                        //data gives you the image uri. Try to convert that to bitmap
                        Uri selectedImage = data.getData();
                        downloadImageView.setImageURI(selectedImage);
                        break;
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        Log.d("DEBUG", "Selecting picture cancelled");
                    }
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            Log.e("DEBUG", "Exception in onActivityResult : " + e.getMessage());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View  view= inflater.inflate(R.layout.add_movie_activity, container, false);

        titleEditTextField = (EditText) view.findViewById(R.id.titleField);
        yearNumberPicker = (NumberPicker) view.findViewById(R.id.yearPicker);
        directorEditTextField = (EditText) view.findViewById(R.id.directorField);
        trailerEditTextField = (EditText) view.findViewById(R.id.trailerField);
        browseButton = (Button) view.findViewById(R.id.browseButton);
        uploadButton = (Button) view.findViewById(R.id.uploadButton);
        downloadImageView = (ImageView) view.findViewById(R.id.downloadImageView);
        genreSpinner = (Spinner) view.findViewById(R.id.genre) ;
        yearNumberPicker.setMinValue(1950);
        yearNumberPicker.setMaxValue(2020);

        genreList = getResources().getStringArray(R.array.movie_genres);
        genreSpinner = (Spinner) view.findViewById(R.id.genre);
        ArrayAdapter<String> myAdapter = new spinnerArrayAdapter(getActivity(), R.layout.spinner_item, genreList);
        genreSpinner.setAdapter(myAdapter);

        browseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (downloadImageView == null)
                    Toast.makeText(getContext(), "Choose a poster image!", Toast.LENGTH_SHORT).show();
                else postMovie();
            }
        });

        initializeDisplayContent();
        return view;
    }


    private void initializeDisplayContent() {
        genreList = getResources().getStringArray(R.array.movie_genres);
//        genreSpinner = (Spinner) view.findViewById(R.id.genre);
        ArrayAdapter<String> myAdapter = new spinnerArrayAdapter(getActivity(), R.layout.spinner_item, genreList);

        genreSpinner.setAdapter(myAdapter);
    }


    public void postMovie() {
        final String URL = getResources().getString(R.string.api_url).toString() + "/api/addfilm";

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    Toast.makeText(getContext(), "Movie added!", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                android.app.FragmentManager fragmentManager = getFragmentManager();
                MovieListFragment fragment = new MovieListFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.addMovieLayout, fragment);
                fragmentTransaction.commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                Log.i("Error", error.getMessage());
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", titleEditTextField.getText().toString());
                params.put("year", yearNumberPicker.getValue() + "");
                params.put("director", directorEditTextField.getText().toString());
                params.put("genre", genreSpinner.getSelectedItem().toString());
                params.put("trailer", trailerEditTextField.getText().toString());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                params.put("poster", new DataPart(titleEditTextField.getText().toString().toLowerCase().replace(" ", "_"), AppHelper.getFileDataFromDrawable(getContext(), downloadImageView.getDrawable()), "image/jpeg"));

                return params;
            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(multipartRequest);
    }
}

