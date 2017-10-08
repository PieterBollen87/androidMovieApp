package be.pxl.filmapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

import be.pxl.filmapp.adapters.MyArrayAdapter;
import be.pxl.filmapp.data.bean.MovieBean;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<MovieBean> adapterMovies;

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

        initializeDisplayContent();
    }

    private void initializeDisplayContent() {
        final ScrollView listMovies = (ScrollView) findViewById(R.id.list_fragment);
        final ListView listMovies2=(ListView) listMovies.findViewById(R.id.list_fragment2);
        final MainActivity context = this;
        String url =getResources().getString(R.string.api_url).toString() + "/api/films";

        JsonArrayRequest jsArrRequest = new JsonArrayRequest
                (url,new Response.Listener<JSONArray>(){

                     @Override
                    public void onResponse(JSONArray response) {
                        try {
                            final List<MovieBean> movies = new ObjectMapper().readValue(response.toString(), new TypeReference<List<MovieBean>>() {
                            });
                            response.toString();
                            adapterMovies = new MyArrayAdapter(context, R.layout.row_layout, movies);
                            listMovies2.setAdapter(adapterMovies);

                            listMovies2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                                    intent.putExtra(MovieDetailActivity.MOVIE_OBJECT, movies.get(position));
                                    startActivity(intent);
                                }
                            });
                        } catch (IOException e) {
                            e.toString();
                            Log.d("ERROR", e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", error.toString());
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(jsArrRequest);
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
}
