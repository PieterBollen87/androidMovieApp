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

import java.util.List;

import be.pxl.filmapp.R;
import be.pxl.filmapp.data.bean.MovieBean;
import be.pxl.filmapp.utility.VolleySingleton;

/**
 * Created by pierre on 30/10/2017.
 */

public class GridImageAdapter extends BaseAdapter {

        private Context mContext;
        private List<MovieBean> personalMovies;

        public GridImageAdapter(Context c, List<MovieBean> personalMovies) {
            mContext = c;
            this.personalMovies = personalMovies;
        }

        public int getCount() {
            return this.personalMovies.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            NetworkImageView posterImage;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                posterImage = new NetworkImageView(mContext);
                posterImage.setLayoutParams(new GridView.LayoutParams(200, 300));
                posterImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                posterImage.setMinimumHeight(75);
                posterImage.setPadding(8, 8, 8, 8);
            } else {
                posterImage = (NetworkImageView) convertView;
            }

            String name = this.personalMovies.get(position).getTitle().toLowerCase();
            String posterUrl = String.format("%s/public/images/%s.jpg", mContext.getResources().getString(R.string.api_url).toString(), name.replace(" ", "_"));

            posterImage.setImageUrl(posterUrl, VolleySingleton.getInstance(mContext).getImageLoader());
            return posterImage;
        }
    }
