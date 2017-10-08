package be.pxl.filmapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;

import be.pxl.filmapp.adapters.spinnerArrayAdapter;

/**
 * Created by pierre on 03/10/2017.
 */

public class AddMovieActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    String[] genreList;
    EditText titleEditTextField;
    NumberPicker yearNumberPicker;
    EditText directorEditTextField;
    Spinner genreSpinner;
    ImageView downloadImageView;
    Button uploadButton;
    Button browseButton;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && requestCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            downloadImageView.setImageURI(selectedImage);
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
        downloadImageView = (ImageView) findViewById(R.id.ImageField);
        browseButton = (Button) findViewById(R.id.browseButton);
        uploadButton = (Button) findViewById(R.id.uploadButton);

        yearNumberPicker.setMinValue(1950);
        yearNumberPicker.setMaxValue(2020);

        browseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                postMovie();
            }
        });

        initializeDisplayContent();
    }


    private void initializeDisplayContent() {
        final AddMovieActivity context = this;
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
        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("title", titleEditTextField.getText().toString());
        params.put("year", yearNumberPicker.getValue() + "");
        params.put("director", directorEditTextField.getText().toString());
        params.put("genre", genreSpinner.getSelectedItem().toString());

        JsonObjectRequest requestJson = new JsonObjectRequest(URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(), "Movie added!", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        // add the request object to the queue to be executed
        VolleySingleton.getInstance(this).addToRequestQueue(requestJson);
    }
}
