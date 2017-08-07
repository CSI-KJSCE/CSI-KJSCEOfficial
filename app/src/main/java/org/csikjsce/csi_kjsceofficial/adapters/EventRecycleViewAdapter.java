package org.csikjsce.csi_kjsceofficial.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.csikjsce.csi_kjsceofficial.POJO.*;
import org.csikjsce.csi_kjsceofficial.R;

import java.util.List;

/**
 * Created by sziraqui on 7/8/17.
 */

public class EventRecycleViewAdapter extends RecyclerView.Adapter<EventHolder> {
    List<Event> events;
    Context context;
    public EventRecycleViewAdapter(Context context,List<Event> eventsList){
        events = eventsList;
        this.context = context;
        for(Event e : events)
            Log.d("Event Adapter","Event name: "+e.getTitle());
    }
    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_cardlayout,parent,false);
        return new EventHolder(context,view);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        Event event = events.get(position);
        holder.bindEvent(event);
    }

    @Override
    public int getItemCount() {
        return events.size();     //Warning: List<> size is not reduced after removing items
    }
}
