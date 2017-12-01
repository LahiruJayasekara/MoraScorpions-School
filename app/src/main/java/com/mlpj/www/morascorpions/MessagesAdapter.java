package com.mlpj.www.morascorpions;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DELL on 12/2/2017.
 * All Rights Reserved by MLPJ©
 */

public class MessagesAdapter  extends RecyclerView.Adapter<MessagesAdapter.ViewHolder>{

    private List<MessageItem> messageItems;
    //private Context context;

    public MessagesAdapter(List<MessageItem> messageItems) {
        this.messageItems = messageItems;
        //this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return messageItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, message;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.tvName);
            message = (TextView)itemView.findViewById(R.id.tvMessage);
        }
    }
}
