package org.csikjsce.csi_kjsceofficial;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Event_details extends AppCompatActivity {

    Utils EventJson;
    JSONObject Event;
    JSONArray EventA;
    JSONObject obj;

    TextView head,date,details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        String title=getIntent().getStringExtra("Title");

        head=(TextView) findViewById(R.id.eventhead_textview);
        date=(TextView) findViewById(R.id.eventdate_textview);
        details=(TextView) findViewById(R.id.eventdetails_textview);
        EventJson= new Utils(this);

        try {
            Event=EventJson.fetchData("event_details");
            Log.e("Details", "onCreate: "+Event );
            EventA=Event.getJSONArray("event_details");
            Log.e("Details", "onCreate: "+EventA );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int i=0;
        for(;i<EventA.length();i++){
            try {
                 obj=EventA.getJSONObject(i);
                Log.e("Details", "onCreate: "+EventA );
                if(obj.getString("title").equals(title)){
                    head.setText(obj.getString("title"));
                    date.setText(obj.getString("event_dt"));
                    details.setText(obj.getString("description"));


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}