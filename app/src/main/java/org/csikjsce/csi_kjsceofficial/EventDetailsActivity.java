package org.csikjsce.csi_kjsceofficial;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.csikjsce.csi_kjsceofficial.POJO.Event;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.csikjsce.csi_kjsceofficial.POJO.Event;
public class EventDetailsActivity extends AppCompatActivity {
    Event event;
    TextView eventTitle;
    TextView eventDate;
    TextView eventDescrip;
    ImageView eventImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        event = intent.getParcelableExtra("Event");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(getApplicationContext(),Register_webview.class);
                j.putExtra("registerionLink",event.getRegister());
                startActivity(j);
            }
        });
        eventTitle = (TextView)findViewById(R.id.eventhead_textview);
        eventDate = (TextView)findViewById(R.id.eventdate_textview);
        eventDescrip = (TextView)findViewById(R.id.eventdetails_textview);
        eventImage = (ImageView)findViewById(R.id.Event_imageview);

        eventTitle.setText(event.getTitle());
        eventDate.setText(event.getEventdt());
        eventDescrip.setText(event.getDesc());
        Glide.with(this)
                .load(event.getImg_url())
                .into(eventImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_eventdetail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.home)
            finish();
        else if(item.getItemId()==R.id.feedback){
            Intent i =new Intent(this,Feedback_webview.class);
            startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }
}