package org.csikjsce.csi_kjsceofficial;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sumit on 23/7/17.
 */

public class Feedbackcard_adapter extends RecyclerView.Adapter<Feedbackcard_adapter.Feedbackholder> {


public Feedbackcard_adapter(ArrayList<FeedbackCard> eventss){

        this.eventss=eventss;
        }

        ArrayList<FeedbackCard> eventss = new ArrayList<FeedbackCard>();
@Override
public Feedbackholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_card,parent,false);
        Feedbackholder hold= new Feedbackholder(view);
        return hold;
        }

@Override
public void onBindViewHolder(Feedbackholder holder, int position) {

        FeedbackCard ev = eventss.get(position);
        holder.image.setImageResource(ev.getImage_id());
        holder.eventname.setText(ev.getTitle());
        holder.submit.setText("Submit");

        }

@Override
public int getItemCount() {
        return eventss.size();
        }

public static class Feedbackholder extends RecyclerView.ViewHolder{
    ImageView image;
    TextView eventname;
    Button submit;

    public Feedbackholder(View itemView) {
        super(itemView);

        image= (ImageView) itemView.findViewById(R.id.feedback_imageview);
        eventname=(TextView)itemView.findViewById(R.id.feedback_title_textview);
        submit=(Button)itemView.findViewById(R.id.submit_button);


    }
}
}
