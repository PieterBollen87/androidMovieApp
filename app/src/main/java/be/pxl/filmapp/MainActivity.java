package be.pxl.filmapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

import be.pxl.filmapp.adapters.MovieAdapter;
import be.pxl.filmapp.data.bean.MovieBean;
import be.pxl.filmapp.utility.UserSession;
import be.pxl.filmapp.utility.VolleySingleton;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter adapterMovies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button b = (Button) findViewById(R.id.addFilmButton);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMovieActivity.class);
                startActivity(intent);
            }
        });

        // Add Text Change Listener to EditText
        EditText filterEdiText = (EditText) findViewById(R.id.filter);
        filterEdiText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                adapterMovies.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        initializeDisplayContent();

    }

    private void initializeDisplayContent() {
        final ScrollView scrollView = (ScrollView) findViewById(R.id.list_fragment);
        final ListView listViewMovies = (ListView) scrollView.findViewById(R.id.list_fragment2);
        String url = getResources().getString(R.string.api_url).toString() + "/api/films";

        JsonArrayRequest jsArrRequest = new JsonArrayRequest
                (url, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            final List<MovieBean> movies = new ObjectMapper().readValue(response.toString(), new TypeReference<List<MovieBean>>() {
                            });
                            adapterMovies = new MovieAdapter(getApplicationContext(), movies);
                            listViewMovies.setAdapter(adapterMovies);

                            listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                                    intent.putExtra(MovieDetailActivity.MOVIE_OBJECT, adapterMovies.getFilteredData().get(position));
                                    startActivity(intent);
                                }
                            });
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

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsArrRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (UserSession.USER_NAME == "") {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        } else {
            getMenuInflater().inflate(R.menu.logged_in_menu, menu);
            menu.findItem(R.id.username).setTitle(UserSession.USER_NAME);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String register = "Login/Register";
        if (item.getTitle().equals(register)) {
            Intent intent = new Intent(this, RegLogActivity.class);
            startActivity(intent);
        }
        if (item.getTitle().equals("Logout")) {
            UserSession.USER_NAME = "";
            Toast.makeText(this, "Logout successful", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
        return true;
    }

}
