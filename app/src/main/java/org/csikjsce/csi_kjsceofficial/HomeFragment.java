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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.csikjsce.csi_kjsceofficial.POJO.Event;
import org.csikjsce.csi_kjsceofficial.adapters.EventRecycleViewAdapter;
import org.csikjsce.csi_kjsceofficial.adapters.SwipeCustomAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sumit on 2/8/17.
 */
public class HomeFragment extends Fragment {
    public static final String TAG = HomeFragment.class.getSimpleName();
    private View view;
    RecyclerView eventsRecyclerView;
    DatabaseReference dbRef;
    ProgressBar progress;

    ViewPager viewPager;
    SwipeCustomAdapter adapter;
    private ArrayList<Event> majorEvents;
    private int majorEventsCount;
    private final Query majorEventsDb = FirebaseDatabase
            .getInstance()
            .getReference(getString(R.string.firebase_key_highlights))
            .orderByChild(getString(R.string.firebase_key_event_id));
    private int currentPage;
    private final int SWIPE_DELAY = 2000;
    private final int SWIPE_PERIOD = 5000;

    EventRecycleViewAdapter ed;
    private final Query eventsDb = FirebaseDatabase
            .getInstance()
            .getReference(getString(R.string.firebase_key_event))
            .orderByChild(getString(R.string.firebase_key_event_id));

    ArrayList<Event> list;

    public HomeFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        view =  inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewPager = view.findViewById(R.id.View_pager);
        progress = view.findViewById(R.id.center_progressbar);
        eventsRecyclerView = view.findViewById(R.id.eventcard_listview);

        majorEvents = new ArrayList<>();
        list = new ArrayList<>();

        //Setup view pager
        adapter = new SwipeCustomAdapter(getActivity(), majorEvents);
        viewPager.setAdapter(adapter);
        majorEventsCount = 1;
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

        //Setup recycler view
        ed = new EventRecycleViewAdapter(getContext(),list);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        // Firebase uses ascending ordering on ids so we reverse the rendering
        llm.setReverseLayout(true);
        eventsRecyclerView.setLayoutManager(llm);
        eventsRecyclerView.setAdapter(ed);

        //Add database listeners
        majorEventsDb.keepSynced(true);
        majorEventsDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Event event = data.getValue(Event.class);
                    majorEvents.add(event);
                    majorEventsCount = majorEvents.size();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getDetails());
            }
        });

        eventsDb.keepSynced(true);
        eventsDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Event event = data.getValue(Event.class);
                    Log.d(TAG,"Event: "+event.getTitle());
                    list.add(event);
                    ed.notifyDataSetChanged();
                }
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG,databaseError.getDetails());
            }
        });
    }
}

