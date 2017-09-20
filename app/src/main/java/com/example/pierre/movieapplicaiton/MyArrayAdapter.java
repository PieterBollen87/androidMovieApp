package com.example.pierre.movieapplicaiton;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pierre on 20/09/2017.
 */

public class MyArrayAdapter extends ArrayAdapter<String> {


    private final Context context;
    private final String[] names;
    private final String[] genres;

    /* Deze constructor overnemen, de auto-generated constructors zien er anders uit.
    *  Verander de List<String> candidates en List<String> parties naar hetgeen nodig is voor
    *  jouw opdracht.
    */
    MyArrayAdapter(Context context, String[] names, String[] genres) {
        /*
        * Het maakt niet uit welke List<String> je meegeeft als derde parameter.
        */
        super(context, -1, names);
        this.context = context;
        this.names = names;
        this.genres = genres;
    }

    /*
     * Deze view wordt per row opgeroepen (in dit geval dus voor elke kandidaat)
     * position wordt met 1 opgehoogd per row die afgehandeld is
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Stel de custom row_layout in
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);
        // Haal ImageView en TextView op, deze komen uit row_layout
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        ImageView genreView = (ImageView) rowView.findViewById(R.id.genre);
        TextView textView = (TextView) rowView.findViewById(R.id.name);
        String img=names[position].toLowerCase();
        String afb=img.replace(" ","_");
      //  Log.d(afb,afb);
                Context context = imageView.getContext();
        int id = context.getResources().getIdentifier(afb, "drawable", context.getPackageName());
        imageView.setImageResource(id);

        // Stel de naam in
        textView.setText(names[position]);



        //Stel een aangepaste afbeelding in, afhankelijk van de partij waar ze bijzitten
       if (genres[position].equals("Comedy")) {
            genreView.setImageResource(R.drawable.comedy);
        } else if (genres[position].equals("Thriller")) {
           genreView.setImageResource(R.drawable.thriller);
       } else if (genres[position].equals("Action")) {
           genreView.setImageResource(R.drawable.horror);
       } else if (genres[position].equals("Animation")) {
           genreView.setImageResource(R.drawable.disney);
       } else if (genres[position].equals("Musical")) {
           genreView.setImageResource(R.drawable.musical);
       } else if (genres[position].equals("Drama")) {
           genreView.setImageResource(R.drawable.drama);
       } else {
            genreView.setImageResource(R.drawable.film);
        }



        return rowView;
    }

}
