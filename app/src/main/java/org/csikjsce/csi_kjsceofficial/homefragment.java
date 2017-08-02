package org.csikjsce.csi_kjsceofficial;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by sumit on 2/8/17.
 */
public class homefragment extends Fragment {
    private View view;
    ListView eventlists;
    ViewPager viewPager;

    SwipeCustomAdapter adapter;
    CircleIndicator indicate;
    private static final Integer[] images= {R.drawable.csi_ic_splash_screen,R.drawable.csi_ic_splash_screen};
    private int currentPage=0;
    private ArrayList<Integer> imgarray=new ArrayList<Integer>();
    ArrayList<EventCard> list= new ArrayList<EventCard>();

    private FragmentTransaction fragmentTransaction;
    public homefragment(){

    }
    homefragment (FragmentTransaction fragmentTransaction){
        this.fragmentTransaction = fragmentTransaction;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view =  inflater.inflate(R.layout.content_main,container,false);
        return view;
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        for(int i=0;i<images.length;i++)
            imgarray.add(images[i]);
        viewPager=(ViewPager)view.findViewById(R.id.View_pager);
        indicate=(CircleIndicator) view.findViewById(R.id.indicator);
        indicate.setViewPager(viewPager);
        adapter = new SwipeCustomAdapter(getActivity(),imgarray);
        viewPager.setAdapter(adapter);
        eventlists=(ListView)view.findViewById(R.id.eventcard_listview);
        for(int i=0;i<5;i++){
            list.add(new EventCard(R.drawable.handover,"Title","1/7/2017"));


        }
        EventsAdapter ed =new EventsAdapter(getActivity(),list);
        eventlists.setAdapter(ed);
        eventlists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Event Details","In onItemClick");
                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Event_details.class));
            }
        });

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);

    }
}

