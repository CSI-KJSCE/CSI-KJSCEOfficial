package org.csikjsce.csi_kjsceofficial.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.csikjsce.csi_kjsceofficial.POJO.CouncilMember;
import org.csikjsce.csi_kjsceofficial.R;

import java.util.List;

/**
 * Created by sziraqui on 10/8/17.
 */

public class CouncilMemberAdapter extends RecyclerView.Adapter<CouncilMemberHolder> {
    List<CouncilMember> members;
    Context context;
    public CouncilMemberAdapter(Context context,List<CouncilMember> membersList){
        members = membersList;
        this.context = context;
        for(CouncilMember cm : members)
            Log.d("CouncilMemberAdapter","Member name: "+cm.getName());
    }
    @Override
    public CouncilMemberHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.council_member_listitem,parent,false);
        return new CouncilMemberHolder(context,view);
    }

    @Override
    public void onBindViewHolder(CouncilMemberHolder holder, int position) {
        CouncilMember member = members.get(position);
        holder.bindEvent(member);
    }

    @Override
    public int getItemCount() {
        return members.size();     //Warning: List<> size is not reduced after removing items
    }
}
