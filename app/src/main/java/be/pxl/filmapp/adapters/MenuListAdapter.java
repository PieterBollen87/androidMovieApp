package be.pxl.filmapp.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import be.pxl.filmapp.R;
import be.pxl.filmapp.utility.VolleySingleton;

public class MenuListAdapter extends ArrayAdapter<String> {

    private String[] options;
    private Context context;

    public MenuListAdapter(Context context, int resourceId,
                           String[] objects) {
        super(context, resourceId, objects);
        this.options = objects;
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

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.drawer_list_item, parent, false);
        TextView label = (TextView) row.findViewById(R.id.name);
//        NetworkImageView genreImageView = (NetworkImageView) row.findViewById(R.id.genre);
//        String genreUrl = String.format("%s/public/images/%s.png", row.getResources().getString(R.string.api_url).toString(), genres[position]);
        label.setText(options[position].toString());
//        genreImageView.setImageUrl(genreUrl, VolleySingleton.getInstance(this.getContext()).getImageLoader());
        if (position == 0) {//Special style for dropdown header
            label.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }

        return row;
    }
}
