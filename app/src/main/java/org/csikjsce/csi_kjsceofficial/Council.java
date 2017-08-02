package org.csikjsce.csi_kjsceofficial;

/**
 * Created by sumit on 18/7/17.
 */

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Council extends Fragment {

    private View view;
    private final String TAG = "MemberListFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.council_member_list,container,false);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        Log.d(TAG,"In onActivityCreated");
        ArrayList<CouncilMember> members = new ArrayList<CouncilMember>();
        for (int i = 0; i < 10; i++) {
            members.add( new CouncilMember("NAMES","GSEC","2017"));
        }
        ListView membersListView;
        membersListView = (ListView)view.findViewById(R.id.listView2);
        Log.d(TAG,"In onActivityCreateds"+ members);
        CouncilAdapter mListAdapter = new CouncilAdapter(getActivity(),members);

        membersListView.setAdapter(mListAdapter);
        Log.d(TAG,"In onActivityCreated"+mListAdapter);


    }}