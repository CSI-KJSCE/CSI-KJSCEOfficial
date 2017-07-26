package org.csikjsce.csi_kjsceofficial;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Utils EventJson;
    JSONArray Eventdetails;
    JSONObject Events;


    RecyclerView card1;
    RecyclerView.Adapter ev_adapter;
    RecyclerView.LayoutManager lm;
    ViewPager viewPager;
    SwipeCustomAdapter adapter;
    ArrayList<EventCard> list= new ArrayList<EventCard>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        viewPager=(ViewPager)findViewById(R.id.View_pager);
        adapter = new SwipeCustomAdapter(this);
        viewPager.setAdapter(adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        //toggle.setDrawerIndicatorEnabled(false);

        //toggle.setHomeAsUpIndicator(R.drawable.csi_ic_actionbar);
        toggle.syncState();
        EventJson = new Utils(this);

        try {
            Events = EventJson.fetchData("event_list");
            Log.e("MainActivity","Events:"+Events);
            Eventdetails = Events.getJSONArray("event_list");
            Log.e("MainActivity","Eventdetails:"+Eventdetails);
        } catch (JSONException e) {
            e.printStackTrace();

        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        String[] date = new String[Eventdetails.length()],name = new String[Eventdetails.length()];
        try {
            for(int i=0;i<Eventdetails.length();i++) {
                date[i] = Eventdetails.getJSONObject(i).getString("event_dt");
                name[i]=Eventdetails.getJSONObject(i).getString("title");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int count=0;
        for(int i=0;i<Eventdetails.length();i++){
            EventCard eventcard;
            eventcard= new EventCard(name[count],date[count]);
            count++;
            list.add(eventcard);
        }
        card1=(RecyclerView)findViewById(R.id.eventcard_recycle);
        lm=new LinearLayoutManager(this);
        card1.setLayoutManager(lm);
        card1.setHasFixedSize(true);

        ev_adapter = new EventsAdapter(list,this);
        card1.setAdapter(ev_adapter);



    }
    boolean doubleBackToExitPressedOnce=false;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else  if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent i;
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            i=new Intent(this,Council.class);
            startActivity(i);

        } else if (id == R.id.nav_slideshow) {
            i=new Intent(this,Feedback.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}