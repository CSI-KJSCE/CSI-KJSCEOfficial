package org.csikjsce.csi_kjsceofficial;

/**
 * Created by sumit on 2/8/17.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sumit on 19/7/17.
 */

public class CouncilAdapter extends ArrayAdapter<CouncilMember> {

    public CouncilAdapter(Context context, ArrayList<CouncilMember> members) {
        super(context, 0, members);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_council, parent, false);
        }
        TextView name = (TextView)listItemView.findViewById(R.id.member_name);
        TextView Post = (TextView)listItemView.findViewById(R.id.member_post);
        TextView year = (TextView)listItemView.findViewById(R.id.member_year);
        ImageView image = (ImageView)listItemView.findViewById(R.id.member_pic);

        name.setText(getItem(position).getName());
        Post.setText(getItem(position).getPost());
        year.setText(getItem(position).getYear());
        image.setImageResource(getItem(position).getDp());
        return listItemView;
    }
}












