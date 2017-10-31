package be.pxl.filmapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import be.pxl.filmapp.R;
import be.pxl.filmapp.utility.VolleySingleton;

/**
 * Created by pierre on 30/10/2017.
 */

public class GridImageAdapter extends BaseAdapter {

        private Context mContext;

        public GridImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(200, 300));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setMinimumHeight(75);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

        // references to our images
        private Integer[] mThumbIds = {
                R.drawable.comedy, R.drawable.film,
                R.drawable.film, R.drawable.comedy,
                R.drawable.comedy, R.drawable.film,
                R.drawable.film, R.drawable.comedy,
                R.drawable.comedy, R.drawable.film,
                R.drawable.film, R.drawable.comedy,
                R.drawable.comedy, R.drawable.film,
                R.drawable.film, R.drawable.comedy,
                R.drawable.comedy, R.drawable.film,
                R.drawable.film, R.drawable.comedy,
                R.drawable.comedy, R.drawable.film
        };
    }
