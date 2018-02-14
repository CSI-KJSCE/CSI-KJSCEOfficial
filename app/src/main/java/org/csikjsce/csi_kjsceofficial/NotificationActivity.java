package org.csikjsce.csi_kjsceofficial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.csikjsce.csi_kjsceofficial.POJO.Notification;
import org.csikjsce.csi_kjsceofficial.adapters.NotificationAdapter;

import java.util.ArrayList;
import java.util.LinkedList;

public class NotificationActivity extends AppCompatActivity  {

    public static final String TAG = NotificationActivity.class.getSimpleName();
    RecyclerView recyclerView;
    LinkedList<Notification> notifications;
    NotificationAdapter notifAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.notification_recycler_view);

        notifications = new LinkedList<>();

        LinearLayoutManager llm = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(llm);

        //To prevent onBindViewHolder to be called twice on onClick
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        notifAdapter = new NotificationAdapter(this, notifications);
        recyclerView.setAdapter(notifAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        final DatabaseHelper dbHelper = new DatabaseHelper(this);
        Query listenNewNotif = FirebaseDatabase
                .getInstance()
                .getReference(getString(R.string.firebase_key_last_notif))
                .child(getString(R.string.firebase_key_notif_id));
        listenNewNotif.keepSynced(true);

        listenNewNotif.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                notifications.clear();
                notifications.addAll(dbHelper.selectAllNotifications());
                if(notifications.isEmpty()){
                    TextView notifTv = findViewById(R.id.no_notif_tv);
                    notifTv.setVisibility(View.VISIBLE);
                } else {
                    notifAdapter.notifyDataSetChanged();
                    // Scroll to top
                    ((LinearLayoutManager)recyclerView.getLayoutManager()).scrollToPositionWithOffset(0,0);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,databaseError.getMessage());
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
