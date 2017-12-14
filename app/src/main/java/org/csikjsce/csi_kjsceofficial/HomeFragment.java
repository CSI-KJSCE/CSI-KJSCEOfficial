package org.csikjsce.csi_kjsceofficial;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

/**
 * Created by sumit on 2/8/17.
 */
public class HomeFragment extends Fragment {
    public static final String TAG = HomeFragment.class.getName();
    private View view;
    RecyclerView eventsRecyclerView;
    DatabaseReference dbRef;
    ProgressBar progress;

    ViewPager viewPager;
    SwipeCustomAdapter adapter;
    private ArrayList<Event> majorEvents = new ArrayList<>();
    private int majorEventsCount;
    DatabaseReference majorEventsDb;
    private int currentPage;
    private final int SWIPE_DELAY = 2000;
    private final int SWIPE_PERIOD = 5000;

    EventRecycleViewAdapter ed;
    DatabaseReference eventsDb;
    ArrayList<Event> list = new ArrayList<>();
    Set<Event> uniqueEvents;

    public HomeFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        dbRef = FirebaseDatabase.getInstance().getReference();
        eventsDb = dbRef.child("event");
        majorEventsDb = dbRef.child("major-events");
        view =  inflater.inflate(R.layout.home_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Populate viewpager
        viewPager=(ViewPager)view.findViewById(R.id.View_pager);
        adapter = new SwipeCustomAdapter(getActivity(), majorEvents);
        viewPager.setAdapter(adapter);
        majorEventsCount = 1;
        majorEventsDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Event event = dataSnapshot.getValue(Event.class);
                majorEvents.add(event);
                majorEventsCount = majorEvents.size();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                majorEventsCount = majorEvents.size();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                majorEventsCount = majorEvents.size();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                majorEventsCount = majorEvents.size();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getDetails());
            }
        });

        progress=(ProgressBar)view.findViewById(R.id.center_progressbar);

        currentPage = 0;
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                currentPage%=majorEventsCount;
                viewPager.setCurrentItem(currentPage++, true);
                 }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, SWIPE_DELAY, SWIPE_PERIOD);
        //Populate Events
        eventsRecyclerView = (RecyclerView)view.findViewById(R.id.eventcard_listview);
        uniqueEvents = new HashSet<>();

        list.addAll(uniqueEvents);
        ed = new EventRecycleViewAdapter(getContext(),list);
        eventsRecyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
        eventsRecyclerView.setAdapter(ed);
        eventsDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Event event = dataSnapshot.getValue(Event.class);
                Log.d(TAG,"Event: "+event.getTitle());
                uniqueEvents.add(event);
                list.clear();
                Log.d(TAG,"onResume(): uniqueEvents.size():  "+uniqueEvents.size());
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
                Log.e(TAG,databaseError.getDetails());
            }
        });
    }
}

