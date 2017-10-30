package be.pxl.filmapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import be.pxl.filmapp.adapters.GridImageAdapter;
import be.pxl.filmapp.adapters.spinnerArrayAdapter;

/**
 * Created by pierre on 30/10/2017.
 */

public class MyListsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View  view= inflater.inflate(R.layout.my_lists_fragment, container, false);

           final GridView gridview = (GridView) view.findViewById(R.id.gridview);
            gridview.setAdapter(new GridImageAdapter(getContext()));

            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    Toast.makeText(gridview.getContext(), "" + position,
                            Toast.LENGTH_SHORT).show();
                }
            });
    return view;
    }

}
