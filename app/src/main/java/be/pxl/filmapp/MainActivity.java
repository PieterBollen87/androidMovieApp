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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import be.pxl.filmapp.adapters.MenuListAdapter;
import be.pxl.filmapp.adapters.MovieAdapter;
import be.pxl.filmapp.data.bean.MovieBean;
import be.pxl.filmapp.services.IMovieService;
import be.pxl.filmapp.services.MovieService;
import be.pxl.filmapp.utility.AppHelper;
import be.pxl.filmapp.utility.UserSession;

public class MainActivity extends Activity {

    MovieListFragment listFragment = new MovieListFragment();
    private String [] menuOptions ;
    private MenuListAdapter adapterMovies;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        menuOptions = getResources().getStringArray(R.array.menuOptions);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


            adapterMovies=new MenuListAdapter (this, R.layout.drawer_list_item, menuOptions);
             mDrawerList = (ListView) findViewById(R.id.left_drawer);
            mDrawerList.setAdapter(adapterMovies);
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());




        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.MainFragmentContainer, listFragment);
        fragmentTransaction.commit();


        retrieveLoginSession();

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
//                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

        };
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Fragment fragment= new Fragment();
        switch(position){
            case 0: getFragmentManager().popBackStack();
                break;
            case 1: fragment= new LoginFragment();
                break;
            case 2: fragment= new RegisterFragment();
                break;
            case 3: fragment= new AddMovieFragment();
                break;
//            case 4: fragment= new LoginFragment();
//                break;
            default: getFragmentManager().popBackStack();
        }

        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
        fragmentTransaction.add(R.id.MainFragmentContainer, fragment);
        fragmentTransaction.commit();


        // Create a new fragment and specify the planet to show based on position
//        Fragment fragment = new PlanetFragment();
//        Bundle args = new Bundle();
//        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//        fragment.setArguments(args);
//
//        // Insert the fragment by replacing any existing fragment
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.content_frame, fragment)
//                .commit();
//
//        // Highlight the selected item, update the title, and close the drawer
//        mDrawerList.setItemChecked(position, true);
//        setTitle(mPlanetTitles[position]);
//        mDrawerLayout.closeDrawer(mDrawerList);
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
//            menu.findItem(R.id.username).setTitle(UserSession.USER_NAME);
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
