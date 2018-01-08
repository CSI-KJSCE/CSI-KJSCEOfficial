package org.csikjsce.csi_kjsceofficial.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.csikjsce.csi_kjsceofficial.DatabaseHelper;
import org.csikjsce.csi_kjsceofficial.POJO.Notification;
import org.csikjsce.csi_kjsceofficial.R;
import org.csikjsce.csi_kjsceofficial.Utils;

import java.util.List;

/**
 * Created by sziraqui on 22/12/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {
    Context context;
    List<Notification> notifications;

    public NotificationAdapter(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notifications = notificationList;
    }
    public class NotificationHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View view;
        ImageView iconIv;
        TextView titleTv, descTv, timeTv,extraTv;

        public NotificationHolder(View itemView) {
            super(itemView);
            iconIv = itemView.findViewById(R.id.notification_item_iv);
            titleTv = itemView.findViewById(R.id.notification_subject_tv);
            descTv = itemView.findViewById(R.id.notification_body_tv);
            timeTv = itemView.findViewById(R.id.notification_time_tv);
            extraTv = itemView.findViewById(R.id.notification_extra_info_tv);

            iconIv.setOnClickListener(this);
            titleTv.setOnClickListener(this);
            descTv.setOnClickListener(this);
            extraTv.setOnClickListener(this);
            this.view = itemView;
        }
        public void bindNotification(Notification notification){
            if(notification.getType()==Notification.SEMINAR_TYPE)
                iconIv.setImageResource(R.drawable.ic_seminar_notification);
            else if(notification.getType()==Notification.WORKSHOP_TYPE)
                iconIv.setImageResource(R.drawable.ic_workshop_notification);
            else iconIv.setImageResource(R.drawable.ic_general_notification);
            titleTv.setText(notification.getTitle());
            descTv.setText(Html.fromHtml(notification.getDescription()));
            timeTv.setText(notification.getTime());
            if(notification.getType()==Notification.GENERAL_TYPE)
                extraTv.setText("Know more");
            else extraTv.setText("Register");

        }
        public void collapseView(){
            descTv.setVisibility(View.GONE);
            extraTv.setVisibility(View.GONE);
        }
        public void expandView(){
            descTv.setVisibility(View.VISIBLE);
            extraTv.setVisibility(View.VISIBLE);

        }
        @Override
        public void onClick(View view){

            if(view.getId()==R.id.notification_extra_info_tv){

                    Utils.openLinkInCustomTab(context, notifications.get(getAdapterPosition()).getExtraUrl());

            } else {

                if(descTv.getVisibility()==View.GONE){
                    expandView();
                    if(notifications.get(getAdapterPosition()).isRead() == Notification.NOT_READ){
                        DatabaseHelper dbHelper = new DatabaseHelper(context);
                        dbHelper.markRead(notifications.get(getAdapterPosition()).getId());
                        notifyDataSetChanged();
                    }
                }
                else collapseView();
                notifyViewToggle(getAdapterPosition());
            }

        }
    }
    @Override
    public NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item,parent,false);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.bindNotification(notification);
    }
    public void notifyViewToggle(int position){

            notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}
