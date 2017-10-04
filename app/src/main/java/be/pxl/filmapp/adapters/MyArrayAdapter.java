package be.pxl.filmapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import be.pxl.filmapp.R;
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
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        ImageView genreView = (ImageView) rowView.findViewById(R.id.genre);
        TextView textView = (TextView) rowView.findViewById(R.id.name);
        String name=nameList.get(position).toLowerCase();
        String afb=name.replace(" ","_");
      //  Log.d(afb,afb);
                Context context = imageView.getContext();
        int id = context.getResources().getIdentifier(afb, "drawable", context.getPackageName());
        imageView.setImageResource(id);

        // Stel de naam in
        textView.setText(name);



        //Stel een aangepaste afbeelding in, afhankelijk van de partij waar ze bijzitten
       if (genreList.get(position).equals("Comedy")) {
            genreView.setImageResource(R.drawable.comedy);
        } else if (genreList.get(position).equals("Thriller")) {
           genreView.setImageResource(R.drawable.thriller);
       } else if (genreList.get(position).equals("Action")) {
           genreView.setImageResource(R.drawable.horror);
       } else if (genreList.get(position).equals("Animation")) {
           genreView.setImageResource(R.drawable.disney);
       } else if (genreList.get(position).equals("Musical")) {
           genreView.setImageResource(R.drawable.musical);
       } else if (genreList.get(position).equals("Drama")) {
           genreView.setImageResource(R.drawable.drama);
       } else {
            genreView.setImageResource(R.drawable.film);
        }



        return rowView;
    }

}
