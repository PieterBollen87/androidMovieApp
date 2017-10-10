package be.pxl.filmapp;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.List;

import be.pxl.filmapp.R;

/**
 * Created by pierre on 10/10/2017.
 */

public class RegisterFragment extends Fragment {


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.register, container, false);
            // Ophalen van de ListView zodat we custom adapter kunnen zetten (onderaan)
            RelativeLayout registerLayout = (RelativeLayout) view.findViewById(R.id.login_fragment);
            return view;
    }

}
