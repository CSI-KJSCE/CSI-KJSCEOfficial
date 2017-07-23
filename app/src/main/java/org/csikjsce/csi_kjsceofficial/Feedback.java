package org.csikjsce.csi_kjsceofficial;

/**
 * Created by sumit on 18/7/17.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Feedback extends AppCompatActivity {

    JSONArray Feeddetails;
    JSONObject Feeds;
    Utils FeedJson;

    RecyclerView feedbackcard;
    RecyclerView.Adapter fd_adapter;
    RecyclerView.LayoutManager lmf;
    ArrayList<FeedbackCard> list= new ArrayList<FeedbackCard>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
         FeedJson =new Utils(this);
        Log.e("MainActivity","Eventdetails:"+FeedJson);

            try {
                Feeds = FeedJson.fetchData("feedback");
                Log.e("MainActivity","Eventdetails:"+Feeds);
                Feeddetails = Feeds.getJSONArray("feedback");
                Log.e("MainActivity","Eventdetails:"+Feeddetails);

            } catch (JSONException e) {
                e.printStackTrace();

            }


        String[] Title = new String[Feeddetails.length()];
        try {
            for(int i=0;i<Feeddetails.length();i++) {

                Title[i]=Feeddetails.getJSONObject(i).getString("Text");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int count=0;
        for(int i=0;i<Feeddetails.length();i++){
            FeedbackCard feedcard;
            feedcard= new FeedbackCard(Title[count]);
            count++;
            list.add(feedcard);
        }

        feedbackcard=(RecyclerView)findViewById(R.id.feedback_recyclerview);
        lmf=new LinearLayoutManager(this);
        feedbackcard.setLayoutManager(lmf);
        feedbackcard.setHasFixedSize(true);

        fd_adapter = new Feedbackcard_adapter(list);
        feedbackcard.setAdapter(fd_adapter);

    }
}
