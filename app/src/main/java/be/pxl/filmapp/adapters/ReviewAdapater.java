package be.pxl.filmapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import be.pxl.filmapp.R;
import be.pxl.filmapp.data.bean.ReviewBean;
import be.pxl.filmapp.utility.VolleySingleton;

/**
 * Created by Sacha on 7-11-2017.
 */

public class ReviewAdapater extends BaseAdapter {
    private List<ReviewBean> reviews = null;
    private LayoutInflater inflater;
    private Context context;

    public ReviewAdapater (Context context, List<ReviewBean> reviewList) {
        reviews = reviewList;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int i) {
        return reviews.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ReviewAdapater.ViewHolder holder = null;

        if (convertView == null) {
            holder = new ReviewAdapater.ViewHolder();
            // Stel de custom row_layout in
            convertView = inflater.inflate(R.layout.review_row, parent, false);

            // Haal ImageView en TextView op, deze komen uit row_layout
            holder.avatarImageView = (NetworkImageView) convertView.findViewById(R.id.avatar);
            holder.userNameText = (TextView) convertView.findViewById(R.id.name);
            holder.descriptionText = (TextView) convertView.findViewById(R.id.descriptionReview);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingReview);

            convertView.setTag(holder);
        } else {
            holder = (ReviewAdapater.ViewHolder) convertView.getTag();
        }
        ReviewBean selectedReview = (ReviewBean) getItem(i);

        holder.avatarImageView.setImageUrl(selectedReview.getAvatar(), VolleySingleton.getInstance(inflater.getContext()).getImageLoader());
        holder.userNameText.setText(selectedReview.getUserName());
        holder.descriptionText.setText(selectedReview.getDescription());
        holder.ratingBar.setRating(selectedReview.getRating());

        return convertView;
    }
    static class ViewHolder {
        NetworkImageView avatarImageView;
        TextView userNameText;
        TextView descriptionText;
        RatingBar ratingBar;
    }

}
