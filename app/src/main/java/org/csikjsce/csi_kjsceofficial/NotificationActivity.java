package org.csikjsce.csi_kjsceofficial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.csikjsce.csi_kjsceofficial.POJO.Notification;
import org.csikjsce.csi_kjsceofficial.adapters.NotificationAdapter;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity implements ChildEventListener {

    public static final String TAG = NotificationActivity.class.getSimpleName();
    private final Query dbRef = FirebaseDatabase
            .getInstance()
            .getReference("notifications")
            .orderByChild("id");
    ArrayList<Notification> notifications;
    NotificationAdapter notifAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.notification_recycler_view);

        notifications = new ArrayList<>();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        // Firebase uses ascending ordering on ids so we reverse the rendering
        llm.setReverseLayout(true);
        recyclerView.setLayoutManager(llm);

        //To prevent onBindViewHolder to be called twice on onClick
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        notifAdapter = new NotificationAdapter(this, notifications);
        recyclerView.setAdapter(notifAdapter);

        dbRef.addChildEventListener(this);
    }


    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Notification notif = dataSnapshot.getValue(Notification.class);
        notifications.add(notif);
        notifAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        Notification notif = dataSnapshot.getValue(Notification.class);
        notifications.remove(notif);
        notifAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.d(TAG,"Database Error:"+databaseError.getDetails());
    }
}
