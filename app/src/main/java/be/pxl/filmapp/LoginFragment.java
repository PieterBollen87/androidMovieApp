package be.pxl.filmapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import be.pxl.filmapp.utility.UserSession;
import be.pxl.filmapp.utility.VolleySingleton;

/**
 * Created by pierre on 10/10/2017.
 */

public class LoginFragment extends Fragment {

    EditText passwordfield;
    EditText usernamefield;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);
        RelativeLayout loginLayout = (RelativeLayout) view.findViewById(R.id.login_fragment);
        Button loginButton = loginLayout.findViewById(R.id.loginButton);
        usernamefield = loginLayout.findViewById(R.id.nameField);
        passwordfield = loginLayout.findViewById(R.id.passField);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username=usernamefield.getText().toString();
                String password=passwordfield.getText().toString();
                loginUser();
                }
        });

        return view;
    }

    public void loginUser() {
        final String URL = getResources().getString(R.string.api_url).toString() + "/api/login";

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        String resultResponse = new String(response);
                            Toast.makeText(getContext(), "welcome "+usernamefield.getText().toString()+", enjoy your stay.", Toast.LENGTH_LONG).show();
                        UserSession.USER_NAME=usernamefield.getText().toString();
                        UserSession.TOKEN=resultResponse;
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                Log.i("Error", error.getMessage());
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", usernamefield.getText().toString());
                params.put("password", passwordfield.getText().toString());
                return params;
            }
        };


        VolleySingleton.getInstance(getContext()).addToRequestQueue(postRequest);
    }
}

