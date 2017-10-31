package be.pxl.filmapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.List;

import be.pxl.filmapp.adapters.MovieAdapter;
import be.pxl.filmapp.data.bean.MovieBean;
import be.pxl.filmapp.services.IMovieService;
import be.pxl.filmapp.services.MovieService;
import be.pxl.filmapp.utility.AppHelper;


public class MovieListFragment extends Fragment {
    private MovieAdapter adapterMovies;
    private IMovieService movieService;
    private ScrollView scrollView;
    private ListView listViewMovies;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        listViewMovies = (ListView) view.findViewById(R.id.list_fragment2);
        EditText filterEdiText = (EditText) view.findViewById(R.id.filter);
        movieService = new MovieService(AppHelper.getDb(getContext()).movieDao(), getContext());

        listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment detailFragment = new MovieDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(MovieDetailFragment.MOVIE_OBJECT, adapterMovies.getFilteredData().get(position));
                detailFragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_fragmentLayout, detailFragment);
                fragmentTransaction.addToBackStack("FRAGMENT_TAG").commit();

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

        initializeDisplayContent();

        return view;
    }

    private void initializeDisplayContent() {
        new AsyncTask<Void, Void, List<MovieBean>>() {
            @Override
            protected List<MovieBean> doInBackground(Void... params) {
                return movieService.getAllMovies();
            }

            @Override
            protected void onPostExecute(List<MovieBean> movies) {
                adapterMovies = new MovieAdapter(getContext(), movies);
                listViewMovies.setAdapter(adapterMovies);

                movieService.updateMovieDatabaseFromApi(movies);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

        movieService.getAllMoviesObs().observe((AppCompatActivity) getActivity(), new Observer<List<MovieBean>>() {
            @Override
            public void onChanged(List<MovieBean> movies) {
                if (movies != null) {
                    adapterMovies = new MovieAdapter(getContext(), movies);
                    listViewMovies.setAdapter(adapterMovies);
                }
            }
        });
    }
}
