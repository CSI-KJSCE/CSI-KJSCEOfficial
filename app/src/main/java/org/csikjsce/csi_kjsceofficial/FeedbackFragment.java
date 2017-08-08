package org.csikjsce.csi_kjsceofficial;

/**
 * Created by sumit on 18/7/17.
 */

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeedbackFragment extends Fragment {

    JSONArray Feeddetails;
    JSONObject Feeds;
    Utils FeedJson;
    ListView liss;
    ArrayList<FeedbackCard> list= new ArrayList<FeedbackCard>();
    private View view ;
    private final String TAG = "FeedbackListFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_feedback, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FeedJson =new Utils(this.getContext());
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


       liss =(ListView)view.findViewById(R.id.feedback_listview);
        Feedbackcard_adapter ada= new Feedbackcard_adapter(getActivity(),list);
        liss.setAdapter(ada);
    }

}