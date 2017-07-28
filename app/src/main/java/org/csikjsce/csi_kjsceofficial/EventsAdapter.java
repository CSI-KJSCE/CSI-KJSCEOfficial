package org.csikjsce.csi_kjsceofficial;

import android.content.Context;
import android.content.Intent;
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
    Context ctx;

    public EventsAdapter(ArrayList<EventCard> eventss, Context ctx){

        this.eventss=eventss;
        this.ctx=ctx;
    }

    ArrayList<EventCard> eventss = new ArrayList<EventCard>();
    @Override
    public Eventholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_cardlayout,parent,false);
        Eventholder hold= new Eventholder(view,eventss,ctx);
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

    public static class Eventholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView eventname,date;
        ArrayList<EventCard> eventss=new ArrayList<EventCard>();
        Context ctx;
        public Eventholder(View itemView,ArrayList<EventCard> eventss,Context ctx) {
            super(itemView);
            this.eventss=eventss;
            this.ctx=ctx;
            itemView.setOnClickListener(this);
            image= (ImageView) itemView.findViewById(R.id.event_card1_imageview);
            eventname=(TextView)itemView.findViewById(R.id.eventname_textview);
            date=(TextView)itemView.findViewById(R.id.date_textview);


        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {

            int position=getAdapterPosition();
            EventCard eventss=this.eventss.get(position);
            Intent intent=new Intent(this.ctx,Event_details.class);
            intent.putExtra("Title",eventss.getEvent_name());
            intent.putExtra("Image",eventss.getImage_id());
            this.ctx.startActivity(intent);
        }
    }
}
