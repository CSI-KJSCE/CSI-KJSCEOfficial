package org.csikjsce.csi_kjsceofficial.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.csikjsce.csi_kjsceofficial.EventDetailsActivity;
import org.csikjsce.csi_kjsceofficial.POJO.Event;
import org.csikjsce.csi_kjsceofficial.R;

/**
 * Created by sziraqui on 7/8/17.
 */

public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
  //  TextView eventidTv;
    TextView titleTv;
    ImageView img;
    TextView eventdtTv;
    Context context;

    public EventHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        titleTv = (TextView)itemView.findViewById(R.id.eventname_textview);
        eventdtTv = (TextView)itemView.findViewById(R.id.date_textview);
        img = (ImageView)itemView.findViewById(R.id.event_card1_imageview);
        img.setOnClickListener(this);
    }
    public void bindEvent(Event event){
        titleTv.setText(event.getTitle());
        eventdtTv.setText(event.getEventdt());
        Glide.with(context).load(event.getImg_url()).into(img);
        Log.d("EventHolder:","bindEvent(): imgurl = "+event.getImg_url());
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(context, EventDetailsActivity.class);
        context.startActivity(i);
    }
}
