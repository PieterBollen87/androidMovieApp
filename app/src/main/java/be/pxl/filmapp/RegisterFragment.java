package be.pxl.filmapp;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.pxl.filmapp.R;
import be.pxl.filmapp.utility.AppHelper;
import be.pxl.filmapp.utility.VolleyMultipartRequest;
import be.pxl.filmapp.utility.VolleySingleton;

/**
 * Created by pierre on 10/10/2017.
 */

public class RegisterFragment extends Fragment {

    EditText password;
    EditText confPassword;
    EditText username;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.register, container, false);
            RelativeLayout registerLayout = (RelativeLayout) view.findViewById(R.id.register_fragment);
            Button regButton = registerLayout.findViewById(R.id.registerButton);
             username = registerLayout.findViewById(R.id.nameField);
             password = registerLayout.findViewById(R.id.passField);
             confPassword=registerLayout.findViewById(R.id.confirmPassField);
            regButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if ((password.getText().toString()).equals(confPassword.getText().toString())) {
                        registerUser();
                    }
                }
            });

            return view;
    }

    public void registerUser() {
        final String URL = getResources().getString(R.string.api_url).toString() + "/api/register";

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        String resultResponse = new String(response);
                        try {
                            JSONObject result = new JSONObject(resultResponse);
                            Toast.makeText(getContext(), "User added!", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
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
                params.put("username", username.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };


        VolleySingleton.getInstance(getContext()).addToRequestQueue(postRequest);
    }
}
