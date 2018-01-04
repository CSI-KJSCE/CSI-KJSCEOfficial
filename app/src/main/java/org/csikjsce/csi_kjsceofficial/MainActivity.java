package org.csikjsce.csi_kjsceofficial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.csikjsce.csi_kjsceofficial.POJO.Notification;

import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 171;
    Context context;
    private GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        try {
            saveLogcatToFile(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Ensure Google Play services framework is installed
        int gAAResult = GoogleApiAvailability
                .getInstance()
                .isGooglePlayServicesAvailable(this);
        if(gAAResult == ConnectionResult.SUCCESS){
            Log.d(TAG, "Play services available");
        } else {
            GoogleApiAvailability
                    .getInstance()
                    .getErrorDialog(this, gAAResult, REQUEST_CODE);
        }

        Toolbar toolbar = findViewById(R.id.event_details_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        //toggle.setDrawerIndicatorEnabled(false);

        //toggle.setHomeAsUpIndicator(R.drawable.csi_ic_actionbar);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Load profile picture and name of signed in use
        View navHeader = navigationView.getHeaderView(0);
        ImageView profilePic = navHeader.findViewById(R.id.user_dp_iv);
        TextView nameTv = navHeader.findViewById(R.id.user_name_tv);

        SharedPreferences sf = getSharedPreferences(getString(R.string.USER_INFO),MODE_PRIVATE);
        String name = sf.getString("name","CSI Fan");
        String picUrl = sf.getString("pic_url","default");

        nameTv.setText(name);
        profilePic.setOnClickListener(this);
        if(!picUrl.contains("http")){
            if(picUrl.equalsIgnoreCase("female"))
                profilePic.setImageDrawable(getResources().getDrawable(R.drawable.ic_default_female_avatar));
        }
        else {
            Glide
                    .with(this)
                    .load(picUrl)
                    .into(profilePic);
        }
        //Fetching events node from database;
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frames,homeFragment).commit();

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.user_dp_iv:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
        }
    }

    public void setActionBarTitle(String st){
        getSupportActionBar().setTitle(st);
    }
    //The back press exit
    boolean doubleBackToExitPressedOnce=false;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else  if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.msg_back_press, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent i;
        int id = item.getItemId();
        Fragment frag= null;
        switch(id)
        {
            case R.id.nav_home:
                frag = new HomeFragment();
                setActionBarTitle("CSI KJSCE");
                break;
            case R.id.nav_eureka:
                frag=new EurekaFragment();
                setActionBarTitle("Eureka");
                break;
            case R.id.nav_council:
                frag =new CouncilDetailsFragment();
                setActionBarTitle("The Council");
                break;
            case R.id.nav_aboutus:
                frag= new AboutUsFragment();
                setActionBarTitle("About Us");
                break;
            case R.id.rate_us_menu:
                Utils.openLinkInCustomTab(context, getString(R.string.app_download_link));
                break;
            case R.id.share_app_menu:
                String msg = getString(R.string.app_share_msg);
                Utils.onShareClick(context, msg,"");
                break;
            case R.id.logout_drawer_opt:
                signOut();
                finish();
                break;
            case R.id.disconnect_acc_drawer_opt:
                revokeAccess();
                finish();
                break;
        }

        if (frag != null){
            android.support.v4.app.FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frames,frag);
            ft.commit();

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.notification_menu:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.logout_opt:
                signOut();
                break;
            case R.id.disconnect_acc:
                revokeAccess();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void signOut(){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                       SharedPreferences pref = context.getSharedPreferences(getString(R.string.USER_INFO),Context.MODE_PRIVATE);
                        pref.edit().clear().apply();
                        finishAffinity();
                        Toast.makeText(getApplicationContext(),"Signed out",Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    private void revokeAccess(){
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        SharedPreferences pref = getSharedPreferences(getResources().getString(R.string.USER_INFO),0);
                        pref.edit().clear().apply();
                        finishAffinity();
                        Toast.makeText(getApplicationContext(),"Account disconnected",Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG,"onConnectionFailed: "+connectionResult);
    }
    public static void saveLogcatToFile(Context context) throws IOException {
        String fileName = "logcat_"+System.currentTimeMillis()+".txt";
        File outputFile = new File(context.getExternalCacheDir(),fileName);
        @SuppressWarnings("unused")
        Process process = Runtime.getRuntime().exec("logcat -df "+outputFile.getAbsolutePath());
    }
}