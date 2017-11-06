package be.pxl.filmapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.pxl.filmapp.R;
import be.pxl.filmapp.data.bean.MovieBean;
import be.pxl.filmapp.utility.UserSession;
import be.pxl.filmapp.utility.VolleySingleton;

public class MovieAdapter extends BaseAdapter implements Filterable {

    private List<MovieBean> originalValues = null;
    private List<MovieBean> filteredData = null;
    private LayoutInflater inflater;
    private Context context;

    public MovieAdapter(Context context, List<MovieBean> movieList) {
        filteredData = movieList;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public List<MovieBean> getFilteredData() {
        return filteredData;
    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
           final int pos =position;
            // Stel de custom row_layout in
            convertView = inflater.inflate(R.layout.row_layout, parent, false);

            // Haal ImageView en TextView op, deze komen uit row_layout
            holder.posterImageView = (NetworkImageView) convertView.findViewById(R.id.icon);
            holder.genreImageView = (NetworkImageView) convertView.findViewById(R.id.genre);
            holder.watcheyeImageView = (NetworkImageView) convertView.findViewById(R.id.watch);
            holder.textView = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String name = filteredData.get(position).getTitle().toLowerCase();
        final int filmId = filteredData.get(position).getId();
        String posterUrl = String.format("%s/public/images/%s.jpg", convertView.getResources().getString(R.string.api_url).toString(), name.replace(" ", "_"));
        String genreUrl = String.format("%s/public/images/%s.png", convertView.getResources().getString(R.string.api_url).toString(), filteredData.get(position).getGenre());
        String watchUrl = String.format("%s/public/images/%s.png", convertView.getResources().getString(R.string.api_url).toString(), "watcheye");

        holder.posterImageView.setImageUrl(posterUrl, VolleySingleton.getInstance(inflater.getContext()).getImageLoader());
        holder.genreImageView.setImageUrl(genreUrl, VolleySingleton.getInstance(inflater.getContext()).getImageLoader());
        holder.watcheyeImageView.setImageUrl(watchUrl, VolleySingleton.getInstance(inflater.getContext()).getImageLoader());
        holder.textView.setText(name);

        holder.watcheyeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMovieToPersonalList(filmId);
            }
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filteredData = (List<MovieBean>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<MovieBean> FilteredArrList = new ArrayList<MovieBean>();

                if (originalValues == null) {
                    originalValues = new ArrayList<MovieBean>(filteredData); // saves the original data in originalValues
                }

                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = originalValues.size();
                    results.values = originalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < originalValues.size(); i++) {
                        String data = originalValues.get(i).getTitle();
                        if (data.toLowerCase().contains(constraint)) {
                            FilteredArrList.add(originalValues.get(i));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    private void addMovieToPersonalList(final int filmId) {
        final String URL = context.getResources().getString(R.string.api_url).toString() + "/api/addusermovie";

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                        Toast.makeText(context, "Something went wrong! :(", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("token", UserSession.TOKEN);
                params.put("filmid", filmId + "");

                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(postRequest);
    }

    static class ViewHolder {
        NetworkImageView posterImageView;
        NetworkImageView genreImageView;
        NetworkImageView watcheyeImageView;
        TextView textView;
    }
}
