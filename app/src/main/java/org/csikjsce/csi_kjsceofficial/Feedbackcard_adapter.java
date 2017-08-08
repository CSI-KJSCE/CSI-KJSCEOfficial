package org.csikjsce.csi_kjsceofficial;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sumit on 23/7/17.
 */

public class Feedbackcard_adapter extends ArrayAdapter<FeedbackCard> {

    public Feedbackcard_adapter(Context context, ArrayList<FeedbackCard> feedCards) {
        super(context, 0, feedCards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.feedback_card, parent, false);
        }
        TextView Title = (TextView)listItemView.findViewById(R.id.feedback_title_textview);
        TextView submit = (TextView)listItemView.findViewById(R.id.submit_textview);
        ImageView image = (ImageView)listItemView.findViewById(R.id.feedback_imageview);

        Title.setText(getItem(position).getTitle());
        submit.setText("Give FeedbackFragment");
        image.setImageResource(getItem(position).getImage_id());
        return listItemView;
    }
}

