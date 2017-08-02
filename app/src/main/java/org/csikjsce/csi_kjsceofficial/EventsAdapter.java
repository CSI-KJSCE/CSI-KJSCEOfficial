package org.csikjsce.csi_kjsceofficial;

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

public class EventsAdapter extends ArrayAdapter<EventCard> {

    public EventsAdapter(Context context, ArrayList<EventCard> eventCards) {
        super(context, 0, eventCards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.event_cardlayout, parent, false);
        }
        TextView Title = (TextView)listItemView.findViewById(R.id.eventname_textview);
        TextView Date = (TextView)listItemView.findViewById(R.id.date_textview);
        ImageView image = (ImageView)listItemView.findViewById(R.id.event_card1_imageview);

        Title.setText(getItem(position).getEvent_name());
        Date.setText(getItem(position).getDate());
        image.setImageResource(getItem(position).getImage_id());
        return listItemView;
    }
}













