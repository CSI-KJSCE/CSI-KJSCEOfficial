package org.csikjsce.csi_kjsceofficial.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.csikjsce.csi_kjsceofficial.POJO.CouncilMember;
import org.csikjsce.csi_kjsceofficial.R;
import org.csikjsce.csi_kjsceofficial.Utils;

import java.util.List;

/**
 * Created by sziraqui on 10/8/17.
 */

public class CouncilMemberAdapter extends RecyclerView.Adapter<CouncilMemberAdapter.CouncilMemberHolder> {
    List<CouncilMember> members;
    Context context;

    public CouncilMemberAdapter(Context context, List<CouncilMember> membersList) {
        members = membersList;
        this.context = context;
        for (CouncilMember cm : members)
            Log.d("CouncilMemberAdapter", "Member name: " + cm.getName());
    }

    public class CouncilMemberHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTv;
        ImageView img, profileIv;
        TextView postTv, deptTv;

        public CouncilMemberHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.member_name);
            postTv = (TextView) itemView.findViewById(R.id.member_post);
            deptTv = itemView.findViewById(R.id.member_dept_name);
            img = (ImageView) itemView.findViewById(R.id.member_pic);
            profileIv = itemView.findViewById(R.id.linkedin_profile_iv);
            img.setOnClickListener(this);
            profileIv.setOnClickListener(this);
        }

        public void bindEvent(CouncilMember member) {
            nameTv.setText(member.getName());
            postTv.setText(member.getPost());
            deptTv.setText(member.getDept());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_default_male_avatar);
            requestOptions.error(R.drawable.ic_default_male_avatar);
            Glide
                    .with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(member.getPicUrl())
                    .into(img);
        }

        @Override
        public void onClick(View v) {
            String url = members.get(getAdapterPosition()).getProfileUrl();
            if(url.isEmpty() || url==null)
                Toast.makeText(context,"Profile link not available", Toast.LENGTH_SHORT).show();
            else
                Utils.openLinkInCustomTab(context, url);
        }
    }

    @Override
    public CouncilMemberHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.council_member_listitem, parent, false);
        return new CouncilMemberHolder(view);
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
