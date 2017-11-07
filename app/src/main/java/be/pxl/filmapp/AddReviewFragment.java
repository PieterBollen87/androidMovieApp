package be.pxl.filmapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import be.pxl.filmapp.data.bean.MovieBean;
import be.pxl.filmapp.utility.UserSession;
import be.pxl.filmapp.utility.VolleySingleton;

public class AddReviewFragment extends Fragment {
    private MovieBean movie;
    private EditText descriptionEditTextField;
    private NumberPicker ratingNumberPicker;
    private Button sendButton;

    public static final String MOVIE_OBJECT = "be.pxl.filmapp.MOVIE_OBJECT";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_review, container, false);

        descriptionEditTextField = (EditText) view.findViewById(R.id.descriptionField);
        ratingNumberPicker = (NumberPicker) view.findViewById(R.id.ratingPicker);
        sendButton = (Button) view.findViewById(R.id.sendButton);

        ratingNumberPicker.setMinValue(0);
        ratingNumberPicker.setMaxValue(5);

        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendReview();
            }
        });

        readDisplayStateValues();

        return view;
    }

    private void sendReview() {
        final String URL = getActivity().getResources().getString(R.string.api_url).toString() + "/api/addfilmreview";

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                        getFragmentManager().popBackStack();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getActivity(),  new String(error.networkResponse.data), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("token", UserSession.TOKEN);
                params.put("filmid", movie.getId() + "");
                params.put("rating", ratingNumberPicker.getValue() + "");
                params.put("description", descriptionEditTextField.getText().toString());

                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(postRequest);
    }

    private void readDisplayStateValues() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            movie = bundle.getParcelable(MOVIE_OBJECT);
        }
    }
}
