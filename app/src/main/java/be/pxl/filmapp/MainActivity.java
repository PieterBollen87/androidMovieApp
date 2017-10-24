package be.pxl.filmapp;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.List;

import be.pxl.filmapp.adapters.MovieAdapter;
import be.pxl.filmapp.data.bean.MovieBean;
import be.pxl.filmapp.services.IMovieService;
import be.pxl.filmapp.services.MovieService;
import be.pxl.filmapp.utility.AppHelper;
import be.pxl.filmapp.utility.UserSession;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter adapterMovies;
    private IMovieService movieService;
    private ScrollView scrollView;
    private ListView listViewMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button b = (Button) findViewById(R.id.addFilmButton);
        scrollView = (ScrollView) findViewById(R.id.list_fragment);
        listViewMovies = (ListView) scrollView.findViewById(R.id.list_fragment2);
        EditText filterEdiText = (EditText) findViewById(R.id.filter);
        movieService = new MovieService(AppHelper.getDb(getApplicationContext()).movieDao(), getApplicationContext());

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMovieActivity.class);
                startActivity(intent);
            }
        });

        listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.MOVIE_OBJECT, adapterMovies.getFilteredData().get(position));
                startActivity(intent);
            }
        });

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

        retrieveLoginSession();
        initializeDisplayContent();
    }

    private void initializeDisplayContent() {
        new AsyncTask<Void, Void, List<MovieBean>>() {
            @Override
            protected List<MovieBean> doInBackground(Void... params) {
                return movieService.getAllMovies();
            }

            @Override
            protected void onPostExecute(List<MovieBean> movies) {
                adapterMovies = new MovieAdapter(getApplicationContext(), movies);
                listViewMovies.setAdapter(adapterMovies);

                movieService.updateMovieDatabaseFromApi(movies);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

        movieService.getAllMoviesObs().observe(this, new Observer<List<MovieBean>>() {
            @Override
            public void onChanged(@Nullable List<MovieBean> movies) {
                if (movies != null) {
                    adapterMovies = new MovieAdapter(getApplicationContext(), movies);
                    listViewMovies.setAdapter(adapterMovies);
                }
            }
        });
    }

    private void retrieveLoginSession() {
        SharedPreferences sharedPreferences =this.getSharedPreferences("Login", MODE_PRIVATE);

        UserSession.USER_NAME = sharedPreferences.getString("Username", "");
        UserSession.TOKEN = sharedPreferences.getString("Token", "");
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
            UserSession.TOKEN = "";

            SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
            sharedPreferences.edit().clear().commit();

            Toast.makeText(this, "Logout successful", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
        return true;
    }
}
