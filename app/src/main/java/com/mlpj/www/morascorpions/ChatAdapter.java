package com.mlpj.www.morascorpions;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by DELL on 12/1/2017.
 * All Rights Reserved by MLPJ©
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    public ChatAdapter(List<ChatItem> chatItems, Context context) {
        this.chatItems = chatItems;
        this.context = context;
    }

    private List<ChatItem> chatItems;
    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.chat_card_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ChatItem chatItem = chatItems.get(position);
        holder.tvName.setText(chatItem.getName());
        holder.tvDescription.setText(chatItem.getDescription());

        holder.linearLayoutChatItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "item" + position, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context,ChatActivity.class);
                //send the id of clicked user as an extra
                intent.putExtra("id",chatItem.getUserId());
                intent.putExtra("receiverName",chatItem.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return chatItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName;
        public TextView tvDescription;
        public LinearLayout linearLayoutChatItem;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView)itemView.findViewById(R.id.tvName);
            tvDescription = (TextView)itemView.findViewById(R.id.tvDescription);
            linearLayoutChatItem = (LinearLayout)itemView.findViewById(R.id.linearLayoutChatItem);
        }
    }
}
