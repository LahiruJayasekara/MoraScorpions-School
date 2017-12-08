package com.mlpj.www.morascorpions;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MessagesAdapter  extends RecyclerView.Adapter<MessagesAdapter.ViewHolder>{

    private List<MessageItem> messageItems;
    private Context context;

    public MessagesAdapter(List<MessageItem> messageItems, Context context) {
        this.messageItems = messageItems;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.message_layout,parent,false);
        return new MessagesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MessageItem messageItem = messageItems.get(position);
        holder.name.setText(messageItem.getSenderName());
        holder.message.setText(messageItem.getMessage());
        if(messageItem.getFrom() == new UserLocalStore(context).getUserDetails().getId()){
            holder.linearLayout.setGravity(Gravity.RIGHT);
            holder.linearLayoutMessageItem.setBackgroundColor(Color.parseColor("#72a6f9"));
            holder.cardViewMessage.setBackgroundColor(Color.parseColor("#72a6f9"));
        }else{
            holder.linearLayout.setGravity(Gravity.LEFT);
            holder.linearLayoutMessageItem.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.cardViewMessage.setBackgroundColor(Color.parseColor("#ffffff"));
        }

    }

    @Override
    public int getItemCount() {
        return messageItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, message, from;
        CardView cardViewMessage;
        LinearLayout linearLayout, linearLayoutMessageItem;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.tvName);
            message = (TextView)itemView.findViewById(R.id.tvMessage);
            from = (TextView)itemView.findViewById(R.id.tvFrom);
            cardViewMessage = (CardView)itemView.findViewById(R.id.cardViewMessage);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutCardView);
            linearLayoutMessageItem = (LinearLayout) itemView.findViewById(R.id.linearLayoutMessageItem);

        }
    }
}
