package com.mlpj.www.morascorpions.ComplainHandling;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mlpj.www.morascorpions.R;
import com.mlpj.www.morascorpions.User;
import com.mlpj.www.morascorpions.UserLocalStore;

import java.util.List;

/**
 * Created by DELL on 1/22/2018.
 * All Rights Reserved by MLPJ©
 */

public class ComplainAdapter extends RecyclerView.Adapter<ComplainAdapter.ViewHolder>{

    private List<ComplainItem> complainItems;
    private User mCurrentUser;
    private Context context;

    public ComplainAdapter(List<ComplainItem> complainItems, Context context) {
        this.complainItems = complainItems;
        this.context = context;
        mCurrentUser = new UserLocalStore(context).getUserDetails();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.complain_card_view,parent,false);
        return new ComplainAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ComplainItem complainItem = complainItems.get(position);
        holder.tvComplainTopic.setText(complainItem.getTopic());
        holder.tvComplainContent.setText(complainItem.getContent());
        holder.tvComplainedDate.setText("Complained On - " + complainItem.getComplainDate());
        if(mCurrentUser.getRoleName().equals("Parent")){
            holder.tvComplainedBy.setText("Complained By - " + complainItem.getComplaineeName());
        }else{
            holder.tvComplainedBy.setText("Complained By - " + complainItem.getComplainerName());
            holder.complainCrdView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TakeActionActivity.class);
                    intent.putExtra("actionId",complainItem.getActionId());
                    context.startActivity(intent);
                }
            });
        }

        if(complainItem.getAction() != null){
            holder.tvActionContent.setText(complainItem.getAction());
            holder.tvActionTakenDate.setText("Action Taken On - " + complainItem.getActionDate());
        }else{
            holder.tvActionContent.setText("Action Pending");
            holder.complainCrdView.removeView(holder.tvActionTakenDate);
        }
    }

    @Override
    public int getItemCount() {
        return complainItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvComplainTopic, tvComplainContent, tvComplainedDate, tvComplainedBy, tvActionContent, tvActionTakenDate;
        private CardView complainCrdView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvComplainTopic = itemView.findViewById(R.id.complainTopic);
            tvComplainContent = itemView.findViewById(R.id.complainContent);
            tvComplainedDate = itemView.findViewById(R.id.complainedDate);
            tvComplainedBy = itemView.findViewById(R.id.complainedBy);
            tvActionContent = itemView.findViewById(R.id.actionContent);
            tvActionTakenDate = itemView.findViewById(R.id.actionTakenDate);
            complainCrdView = itemView.findViewById(R.id.complainCardView);

        }
    }
}
