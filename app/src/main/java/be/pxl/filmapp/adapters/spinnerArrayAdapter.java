package be.pxl.filmapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import be.pxl.filmapp.R;
import be.pxl.filmapp.utility.VolleySingleton;

/**
 * Created by pierre on 03/10/2017.
 */

public class spinnerArrayAdapter extends ArrayAdapter<String> {

    private String[] genres;
    private Context context;

    public spinnerArrayAdapter(Context context, int resourceId,
                               String[] objects) {
        super(context, resourceId, objects);
        this.genres = objects;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }


    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(  Context.LAYOUT_INFLATER_SERVICE );
        View row=inflater.inflate(R.layout.spinner_item, parent, false);
        TextView label=(TextView)row.findViewById(R.id.name);
        NetworkImageView genreImageView = (NetworkImageView) row.findViewById(R.id.genre);
        String genreUrl = String.format("%s/public/images/%s.png", row.getResources().getString(R.string.api_url).toString(), genres[position]);
        label.setText(genres[position].toString());
        genreImageView.setImageUrl(genreUrl, VolleySingleton.getInstance(this.getContext()).getImageLoader());
            label.setTextColor(context.getResources().getColor(R.color.colorAccent));


        return row;
    }

}