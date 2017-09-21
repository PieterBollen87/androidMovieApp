package com.example.pierre.movieapplicaiton;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by pierre on 20/09/2017.
 */

public class ListFragment extends Fragment  {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        // Ophalen van de ListView zodat we custom adapter kunnen zetten (onderaan)
        ListView listView = (ListView) view.findViewById(R.id.list_fragment);


        String[] movies = getResources().getStringArray(R.array.titles);
        String[] genres = getResources().getStringArray(R.array.genres);


        MyArrayAdapter cumstomAdapter = new MyArrayAdapter(getActivity(), movies, genres);
        listView.setAdapter(cumstomAdapter);

        // Deze code pas toevoegen wanneer je het tegenkomt in het stappenplan (DetailListener)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // Wanneer er een rij aangeklikt wordt, sturen we de positie door naar de Listener
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FilmListener filmListener = (FilmListener) getActivity();
                filmListener.setFieldsAndPicture(position);
            }
        });
        return view;
    }
}
