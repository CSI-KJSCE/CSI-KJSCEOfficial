package org.csikjsce.csi_kjsceofficial;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sumit on 19/7/17.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.Eventholder> {


    public EventsAdapter(ArrayList<EventCard> eventss){

        this.eventss=eventss;
    }

    ArrayList<EventCard> eventss = new ArrayList<EventCard>();
    @Override
    public Eventholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_cardlayout,parent,false);
        Eventholder hold= new Eventholder(view);
        return hold;
    }

    @Override
    public void onBindViewHolder(Eventholder holder, int position) {

          EventCard ev = eventss.get(position);
            holder.image.setImageResource(ev.getImage_id());
            holder.eventname.setText(ev.getEvent_name());
            holder.date.setText(ev.getDate());
    }

    @Override
    public int getItemCount() {
        return eventss.size();
    }

    public static class Eventholder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView eventname,date;

        public Eventholder(View itemView) {
            super(itemView);

            image= (ImageView) itemView.findViewById(R.id.event_card1_imageview);
            eventname=(TextView)itemView.findViewById(R.id.eventname_textview);
            date=(TextView)itemView.findViewById(R.id.date_textview);


        }
    }
}
