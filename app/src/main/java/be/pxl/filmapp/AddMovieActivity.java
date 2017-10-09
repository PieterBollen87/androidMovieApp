package be.pxl.filmapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class AddMovieActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    String[] genreList;
    EditText titleEditTextField;
    NumberPicker yearNumberPicker;
    EditText directorEditTextField;
    Spinner genreSpinner;
    Button uploadButton;
    Button browseButton;
    ImageView downloadImageView;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
            Log.e("DEBUG", "Exception in onActivityResult : " + e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_movie_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleEditTextField = (EditText) findViewById(R.id.titleField);
        yearNumberPicker = (NumberPicker) findViewById(R.id.yearPicker);
        directorEditTextField = (EditText) findViewById(R.id.directorField);
        browseButton = (Button) findViewById(R.id.browseButton);
        uploadButton = (Button) findViewById(R.id.uploadButton);
        downloadImageView = (ImageView) findViewById(R.id.downloadImageView);

        yearNumberPicker.setMinValue(1950);
        yearNumberPicker.setMaxValue(2020);

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
                    Toast.makeText(getApplicationContext(), "Choose a poster image!", Toast.LENGTH_SHORT).show();
                else postMovie();
            }
        });

        initializeDisplayContent();
    }


    private void initializeDisplayContent() {
        genreList = getResources().getStringArray(R.array.movie_genres);
        genreSpinner = (Spinner) findViewById(R.id.genre);
        ArrayAdapter<String> myAdapter = new spinnerArrayAdapter(this, R.layout.spinner_item, genreList);
        genreSpinner.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void postMovie() {
        final String URL = getResources().getString(R.string.api_url).toString() + "/api/addfilm";

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    Toast.makeText(getApplicationContext(), "Movie added!", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
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
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                params.put("poster", new DataPart(titleEditTextField.getText().toString().toLowerCase().replace(" ", "_"), AppHelper.getFileDataFromDrawable(getBaseContext(), downloadImageView.getDrawable()), "image/jpeg"));

                return params;
            }
        };

        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
    }
}

