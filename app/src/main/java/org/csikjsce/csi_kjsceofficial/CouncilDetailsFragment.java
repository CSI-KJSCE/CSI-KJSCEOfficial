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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

        final Query cmDb = FirebaseDatabase.getInstance()
                .getReference(getString(R.string.firebase_key_council_member))
                .orderByChild(getString(R.string.firebase_key_member_id));
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
                Log.d(TAG,databaseError.getDetails());
            }
        });
        final Query metaDb = FirebaseDatabase.getInstance()
                .getReference(getString(R.string.firebase_key_meta))
                .child(getString(R.string.firebase_key_coucil_pic_url));
        metaDb.keepSynced(true);
        metaDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String councilPicUrl = dataSnapshot.getValue(String.class);
                ImageView councilImageView = view.findViewById(R.id.council_imageview);
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.kjsce_and_csi_bg);
                requestOptions.error(R.drawable.default_event_pic);

                Glide
                        .with(getActivity())
                        .setDefaultRequestOptions(requestOptions)
                        .load(councilPicUrl)
                        .into(councilImageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,databaseError.getDetails());
            }
        });
    }
}