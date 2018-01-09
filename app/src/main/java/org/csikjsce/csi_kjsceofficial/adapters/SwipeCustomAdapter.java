package org.csikjsce.csi_kjsceofficial.adapters;

/**
 * Created by sumit on 18/7/17.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.csikjsce.csi_kjsceofficial.EventDetailsActivity;
import org.csikjsce.csi_kjsceofficial.POJO.Event;
import org.csikjsce.csi_kjsceofficial.R;

import java.util.ArrayList;
import java.util.List;

public class SwipeCustomAdapter extends  PagerAdapter {
    public static final String TAG = SwipeCustomAdapter.class.getSimpleName();
    private Context context;
    private LayoutInflater inflater;
    private List<Event> events;

    public SwipeCustomAdapter(Context context, List<Event> majorEvents){

        this.context = context;
        events = majorEvents;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return (view.equals(object));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View swiper_view = inflater.inflate(R.layout.swiper_layout,container ,false);
        ImageView sliderimage = swiper_view.findViewById(R.id.swiper_imageview);
        Glide
                .with(context)
                .load(events.get(position).getImg_url())
                .into(sliderimage);

        container.addView(swiper_view,0);
        swiper_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EventDetailsActivity.class);
                i.putExtra("Event",events.get(position));
                Log.e(TAG, "onClick() : "+events.get(position).getTitle());
                context.startActivity(i);
            }
        });
        return swiper_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout)object);
    }
}
