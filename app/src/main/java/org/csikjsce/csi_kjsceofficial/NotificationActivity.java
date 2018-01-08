package org.csikjsce.csi_kjsceofficial;

import android.content.SharedPreferences;
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

public class NotificationActivity extends AppCompatActivity  {

    public static final String TAG = NotificationActivity.class.getSimpleName();

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
        // Database is ordered by ids in ascending so we reverse the rendering
        llm.setReverseLayout(true);
        recyclerView.setLayoutManager(llm);

        //To prevent onBindViewHolder to be called twice on onClick
        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        notifAdapter = new NotificationAdapter(this, notifications);
        recyclerView.setAdapter(notifAdapter);
        SharedPreferences notifi = getApplicationContext().getSharedPreferences("CSI",MODE_PRIVATE);

        SharedPreferences.Editor notificationedit = notifi.edit();
        notificationedit.putInt("n_length",notifications.size());
        notificationedit.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        notifications.clear();
        notifications.addAll(dbHelper.selectAllNotifications());
        notifAdapter.notifyDataSetChanged();
    }
}
