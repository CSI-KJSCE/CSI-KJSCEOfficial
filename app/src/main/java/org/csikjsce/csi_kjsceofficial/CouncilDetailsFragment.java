package org.csikjsce.csi_kjsceofficial;

/**
 * Created by sumit on 18/7/17.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.csikjsce.csi_kjsceofficial.POJO.CouncilMember;
import org.csikjsce.csi_kjsceofficial.adapters.CouncilMemberAdapter;

import java.util.ArrayList;
import java.util.List;

public class CouncilDetailsFragment extends Fragment {

    private View view;
    private final String TAG = CouncilDetailsFragment.class.getSimpleName();
    private final Query cmDb = FirebaseDatabase.getInstance()
            .getReference("councilmember")
            .orderByChild("id");
    List<CouncilMember> list;
    RecyclerView listingsView;
    CouncilMemberAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_council_details,container,false);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_council));

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG,"In onActivityCreated");
        list = new ArrayList<>();
        adapter = new CouncilMemberAdapter(getContext(), list);
        listingsView = view.findViewById(R.id.council_members_listingsview);
        listingsView.setLayoutManager(new LinearLayoutManager(getContext()));
        listingsView.setAdapter(adapter);

        cmDb.keepSynced(true);
        cmDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    CouncilMember member = data.getValue(CouncilMember.class);
                    list.add(member);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}