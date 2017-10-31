package be.pxl.filmapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.messaging.FirebaseMessaging;

import be.pxl.filmapp.adapters.MenuListAdapter;
import be.pxl.filmapp.utility.UserSession;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    MovieListFragment listFragment = new MovieListFragment();
    private String[] menuOptions;
    private MenuListAdapter adapterMenu;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        retrieveLoginSession();
        buildDrawerLayout();

        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.MainFragmentContainer, listFragment);
        fragmentTransaction.commit();


        // Google authentication

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void buildDrawerLayout() {
        menuOptions = UserSession.EMAIL.length() < 1 ? getResources().getStringArray(R.array.menuOptions) : getResources().getStringArray(R.array.menuOptionsLoggedIn);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        adapterMenu = new MenuListAdapter(this, R.layout.drawer_list_item, menuOptions);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(adapterMenu);
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
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
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Fragment fragment = null;

        switch (menuOptions[position]) {
            case "Home":
                getFragmentManager().popBackStack();
                break;
            case "Login":
                signIn();
                break;
            case "Add":
                fragment = new AddMovieFragment();
                break;
            case "My Lists":
                fragment = new MyListsFragment();
                break;
            case "Settings":
                // handle settings
                break;
            case "Logout":
                logout();
                break;
        }

        if (fragment != null) {
            android.app.FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().addToBackStack(null);
            fragmentTransaction.add(R.id.MainFragmentContainer, fragment);
            fragmentTransaction.commit();
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Toast.makeText(this, String.format("Logged in as %s!", acct.getDisplayName()), Toast.LENGTH_LONG).show();

            UserSession.USER_NAME = acct.getDisplayName();
            UserSession.EMAIL = acct.getEmail();
            UserSession.TOKEN = "";

            SharedPreferences sharedPreferences = this.getSharedPreferences("Login", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Username", UserSession.USER_NAME);
            editor.putString("Email", UserSession.EMAIL);
            editor.putString("Token", UserSession.TOKEN);
            editor.commit();

            buildDrawerLayout();
        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(this, "Something went wrong! :(", Toast.LENGTH_LONG).show();
        }
    }

    private void retrieveLoginSession() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("Login", MODE_PRIVATE);

        UserSession.USER_NAME = sharedPreferences.getString("Username", "");
        UserSession.EMAIL = sharedPreferences.getString("Email", "");
        UserSession.TOKEN = sharedPreferences.getString("Token", "");

        buildDrawerLayout();
    }

    private void logout() {
        UserSession.USER_NAME = "";
        UserSession.EMAIL = "";
        UserSession.TOKEN = "";

        SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();

        Toast.makeText(this, "Logout successful!", Toast.LENGTH_LONG).show();
        buildDrawerLayout();
    }
}
