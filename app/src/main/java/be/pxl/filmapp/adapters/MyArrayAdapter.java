package be.pxl.filmapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import be.pxl.filmapp.R;
import be.pxl.filmapp.VolleySingleton;
import be.pxl.filmapp.data.bean.MovieBean;

/**
 * Created by pierre on 20/09/2017.
 */

public class MyArrayAdapter extends ArrayAdapter<MovieBean> {


    private final Context context;
    private final List<MovieBean> movieList;
    private List<String>nameList=new ArrayList<>();
    private List<String>genreList=new ArrayList<>();

    public MyArrayAdapter(Context context, int x, List<MovieBean> lijst) {

        super(context, -1, lijst);
        this.context = context;
        this.movieList = lijst;
        for (MovieBean movie:movieList) {
            nameList.add(movie.getTitle());
        }
        for (MovieBean movie:movieList) {
            genreList.add(movie.getGenre());
        }


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Stel de custom row_layout in
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);

        // Haal ImageView en TextView op, deze komen uit row_layout
        NetworkImageView posterImageView = (NetworkImageView) rowView.findViewById(R.id.icon);
        NetworkImageView genreImageView = (NetworkImageView) rowView.findViewById(R.id.genre);
        TextView textView = (TextView) rowView.findViewById(R.id.name);

        String name = nameList.get(position).toLowerCase();
        String posterUrl = String.format("%s/public/images/%s.jpg", rowView.getResources().getString(R.string.api_url).toString(), name.replace(" ","_"));
        String genreUrl = String.format("%s/public/images/%s.png", rowView.getResources().getString(R.string.api_url).toString(), genreList.get(position));

        posterImageView.setImageUrl(posterUrl, VolleySingleton.getInstance(this.getContext()).getImageLoader());
        genreImageView.setImageUrl(genreUrl, VolleySingleton.getInstance(this.getContext()).getImageLoader());
        textView.setText(name);

        return rowView;
    }

}
