package org.csikjsce.csi_kjsceofficial.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.csikjsce.csi_kjsceofficial.EventDetailsActivity;
import org.csikjsce.csi_kjsceofficial.POJO.Event;
import org.csikjsce.csi_kjsceofficial.R;

/**
 * Created by sziraqui on 7/8/17.
 */

public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView titleTv;
    ImageView img;
    TextView eventdtTv;
    TextView eventCategoryTv;
    TextView eventAudienceTv;
    Context context;
    Event event;
    final String TAG = "EventHolder";
    public EventHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        titleTv = itemView.findViewById(R.id.eventname_textview);
        eventdtTv = itemView.findViewById(R.id.event_date_textview);
        eventCategoryTv = itemView.findViewById(R.id.category_tag);
        eventAudienceTv = itemView.findViewById(R.id.audience_tag);
        img = itemView.findViewById(R.id.event_card1_imageview);
        itemView.setOnClickListener(this);
    }
    public void bindEvent(Event event){
        this.event = event;
        titleTv.setText(event.getTitle());
        eventdtTv.setText(event.getEventdt());
        eventCategoryTv.setText(event.getCategory());
        eventAudienceTv.setText(event.getAudience());
        Glide.with(context).load(event.getImg_url()).into(img);
        Log.d(TAG,"bindEvent(): imgurl = "+event.getImg_url());
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(context, EventDetailsActivity.class);
        i.putExtra("Event",event);
        Log.e(TAG, "onClick() : "+event.getTitle());
        context.startActivity(i);
    }
}
