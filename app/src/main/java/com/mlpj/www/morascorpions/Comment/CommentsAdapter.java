package com.mlpj.www.morascorpions.Comment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mlpj.www.morascorpions.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DELL on 12/20/2017.
 * All Rights Reserved by MLPJ©
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder>{

    private List<CommentItem> commentItems;
    private Context context;

    public CommentsAdapter(List<CommentItem> commentItems, Context context) {
        this.commentItems = commentItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.comments_card_view,parent,false);
        return new CommentsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CommentItem commentItem = commentItems.get(position);
        holder.tvCommentedPersonName.setText(commentItem.getName());
        holder.tvComment.setText(commentItem.getContent());
        holder.tvCommentTime.setText(commentItem.getCommentedTime());
        if(commentItem.getImage() != null)
            Picasso.with(context).load(commentItem.getImage()).into(holder.imageViewComment);

    }

    @Override
    public int getItemCount() {
        return commentItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageViewComment;
        public TextView tvCommentedPersonName, tvComment, tvCommentTime;
        public ViewHolder(View itemView) {
            super(itemView);
            imageViewComment = itemView.findViewById(R.id.imageViewComment);
            tvCommentedPersonName = itemView.findViewById(R.id.tvCommentedPerson);
            tvComment = itemView.findViewById(R.id.tvComment);
            tvCommentTime = itemView.findViewById(R.id.tvCommentTime);
        }
    }
}
