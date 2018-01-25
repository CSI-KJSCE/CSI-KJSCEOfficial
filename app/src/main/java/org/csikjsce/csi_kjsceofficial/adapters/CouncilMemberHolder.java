package org.csikjsce.csi_kjsceofficial.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.csikjsce.csi_kjsceofficial.EventDetailsActivity;
import org.csikjsce.csi_kjsceofficial.POJO.CouncilMember;
import org.csikjsce.csi_kjsceofficial.POJO.Event;
import org.csikjsce.csi_kjsceofficial.R;

/**
 * Created by sziraqui on 10/8/17.
 */

public class CouncilMemberHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView nameTv;
    ImageView img;
    TextView postTv, deptTv;
    Context context;

    public CouncilMemberHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        nameTv = (TextView)itemView.findViewById(R.id.member_name);
        postTv = (TextView)itemView.findViewById(R.id.member_post);
        deptTv = itemView.findViewById(R.id.member_dept_name);
        img = (ImageView)itemView.findViewById(R.id.member_pic);
        img.setOnClickListener(this);
    }
    public void bindEvent(CouncilMember member){
        nameTv.setText(member.getName());
        postTv.setText(member.getPost());
        deptTv.setText(member.getDept());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_default_male_avatar);
        requestOptions.error(R.drawable.ic_default_male_avatar);
        Glide
                .with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(member.getPic_url())
                .into(img);
        Toast.makeText(context,member.getPic_url(),Toast.LENGTH_SHORT);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(context,"No more info",Toast.LENGTH_SHORT).show();
    }
}
