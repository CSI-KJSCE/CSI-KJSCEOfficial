package org.csikjsce.csi_kjsceofficial;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.csikjsce.csi_kjsceofficial.POJO.Event;
import org.csikjsce.csi_kjsceofficial.adapters.EventRecycleViewAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by sumit on 2/8/17.
 */
public class HomeFragment extends Fragment {
    private View view;
    RecyclerView eventlists;
    ViewPager viewPager;
    Toolbar toolbar;
    ProgressBar progress;

    SwipeCustomAdapter adapter;
    CircleIndicator indicate;
    private static final Integer[] images= {R.drawable.handover,R.drawable.csi_ic_splash_screen};
    private int currentPage=0;
    private ArrayList<Integer> imgarray=new ArrayList<Integer>();
    ArrayList<Event> list= new ArrayList<>();
    DatabaseReference dbRef;
    DatabaseReference eventsDb;
    Set<Event> uniqueEvents;
    EventRecycleViewAdapter ed;
    private FragmentTransaction fragmentTransaction;
    public HomeFragment(){

    }
    HomeFragment(FragmentTransaction fragmentTransaction){
        this.fragmentTransaction = fragmentTransaction;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        dbRef = FirebaseDatabase.getInstance().getReference();
        eventsDb = dbRef.child("event");
        view =  inflater.inflate(R.layout.home_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Populate viewpager
        for(int i=0;i<images.length;i++)
            imgarray.add(images[i]);
        viewPager=(ViewPager)view.findViewById(R.id.View_pager);
        progress=(ProgressBar)view.findViewById(R.id.center_progressbar);

        //indicate=(CircleIndicator) view.findViewById(R.id.indicator);
        //indicate.setViewPager(viewPager);
        adapter = new SwipeCustomAdapter(getActivity(),imgarray);
        viewPager.setAdapter(adapter);
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
        //Populate Events
        eventlists = (RecyclerView)view.findViewById(R.id.eventcard_listview);
        uniqueEvents = new HashSet<>();
       /* for(int i=0;i<5;i++){
            uniqueEvents.add(new Event(1,"Title","1/7/2017","url"));
        }*/
        list.addAll(uniqueEvents);
        ed = new EventRecycleViewAdapter(getContext(),list);
        eventlists.setLayoutManager( new LinearLayoutManager(getContext()));
        eventlists.setAdapter(ed);
        eventsDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Event event = dataSnapshot.getValue(Event.class);
                Log.d("ChildEventListener","Event: "+event.getTitle());
                uniqueEvents.add(event);
                list.clear();
                Log.d("HomeFragment","onResume(): uniqueEvents.size():  "+uniqueEvents.size());
                list.addAll(uniqueEvents);
                ed.notifyDataSetChanged();
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

