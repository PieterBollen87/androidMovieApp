package be.pxl.filmapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import be.pxl.filmapp.data.bean.MovieBean;

public class TrailerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener  {
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private MovieBean movie;
    public static final String MOVIE_OBJECT = "be.pxl.filmapp.MOVIE_OBJECT2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(getResources().getString(R.string.youtube_api_key), this);

        readDisplayStateValues();
    }

    private void readDisplayStateValues() {
        Intent intent = getIntent();
        movie = intent.getParcelableExtra(this.MOVIE_OBJECT);
    }

    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(movie.getTrailer());
        }
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(getResources().getString(R.string.youtube_api_key), this);
        }
    }

    protected Provider getYouTubePlayerProvider() {
        return youTubeView;
    }
}