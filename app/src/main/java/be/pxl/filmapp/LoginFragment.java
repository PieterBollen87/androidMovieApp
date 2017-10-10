package be.pxl.filmapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by pierre on 10/10/2017.
 */

public class LoginFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);
        // Ophalen van de ListView zodat we custom adapter kunnen zetten (onderaan)
        RelativeLayout loginLayout = (RelativeLayout) view.findViewById(R.id.login_fragment);
        Button b = view.findViewById(R.id.loginButton);

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        return view;
    }
}
