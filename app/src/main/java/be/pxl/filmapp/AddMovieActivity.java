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
import android.widget.ImageView;
import android.widget.Spinner;

import be.pxl.filmapp.adapters.spinnerArrayAdapter;

/**
 * Created by pierre on 03/10/2017.
 */

public class AddMovieActivity extends AppCompatActivity  {
    private static final int RESULT_LOAD_IMAGE=1;
    String[]genreList;
    ImageView downloadImageView;
    Button uploadButton;
    Button browseButton;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_LOAD_IMAGE && requestCode==RESULT_OK && data!=null){
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
        downloadImageView = (ImageView) findViewById(R.id.ImageField);
        browseButton = (Button) findViewById(R.id.browseButton);
        uploadButton = (Button) findViewById(R.id.uploadButton);

        browseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });
//        uploadButton.setOnClickListener(this);

        initializeDisplayContent();
    }




    private void initializeDisplayContent() {
        final AddMovieActivity context = this;
        genreList = getResources().getStringArray(R.array.movie_genres);
        Spinner sp = (Spinner) findViewById(R.id.genre);
        ArrayAdapter<String> myAdapter = new spinnerArrayAdapter(this, R.layout.spinner_item, genreList);
        sp.setAdapter(myAdapter);

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
