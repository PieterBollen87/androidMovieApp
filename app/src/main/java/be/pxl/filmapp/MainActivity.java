package be.pxl.filmapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentManager;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.List;

import be.pxl.filmapp.adapters.MovieAdapter;
import be.pxl.filmapp.data.bean.MovieBean;
import be.pxl.filmapp.services.IMovieService;
import be.pxl.filmapp.services.MovieService;
import be.pxl.filmapp.utility.AppHelper;
import be.pxl.filmapp.utility.UserSession;

public class MainActivity extends Activity {

    MovieListFragment listFragment = new MovieListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();




        fragmentTransaction.add(R.id.MainFragmentContainer, listFragment);
        fragmentTransaction.commit();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        Button b = (Button) findViewById(R.id.addFilmButton);
        retrieveLoginSession();
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
