package org.csikjsce.csi_kjsceofficial;

/**
 * Created by sumit on 18/7/17.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.csikjsce.csi_kjsceofficial.POJO.CouncilMember;

import java.util.ArrayList;

public class CouncilDetailsFragment extends Fragment {

    private View view;
    private final String TAG = "MemberListFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.council_details_fragment,container,false);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG,"In onActivityCreated");
        ArrayList<CouncilMember> members = new ArrayList<CouncilMember>();
        for (int i = 0; i < 10; i++) {
            members.add( new CouncilMember("NAMES","GSEC","2017"));
        }
        ListView membersListView;
        membersListView = (ListView)view.findViewById(R.id.listView2);
        CouncilAdapter mListAdapter = new CouncilAdapter(getActivity(),members);
        membersListView.setAdapter(mListAdapter);


    }}