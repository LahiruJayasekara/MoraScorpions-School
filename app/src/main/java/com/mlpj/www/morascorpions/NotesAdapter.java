package com.mlpj.www.morascorpions;


import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DELL on 12/17/2017.
 * All Rights Reserved by MLPJ©
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder>{
    private List<NoteItem> noteItems;
    private Context context;
    private FragmentManager fragmentManager;

    public NotesAdapter(List<NoteItem> noteItems, Context context, FragmentManager fragmentManager) {

        this.noteItems = noteItems;
        this.context = context;
        this.fragmentManager = fragmentManager;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.notes_card_view,parent,false);
        return new NotesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final NoteItem noteItem = noteItems.get(position);
        holder.tvNoteDate.setText(noteItem.getUploadedDate());
        holder.tvNoteTitle.setText(noteItem.getNoteTitle());
        holder.tvNoteDescription.setText(noteItem.getDescription());
        holder.tvNoteFileName.setText(noteItem.getNoteTitle() + ".pdf");



        holder.tvNoteTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(noteItem.getNoteId() != -1){
                    Bundle bundle = new Bundle();
                    bundle.putInt("noteId", noteItem.getNoteId() );

                    CommentFragment commentFragment = new CommentFragment();
                    commentFragment.setArguments(bundle);

                    commentFragment.show(fragmentManager,"CommentDialog");
                }
            }
        });
        holder.tvNoteFileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(noteItem.getNoteUrl());
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvNoteDate, tvNoteTitle, tvNoteDescription, tvNoteFileName;
        public ImageView imageViewNoteFileIcon;
        public LinearLayout linearLayoutNotes;
        public ViewHolder(View itemView) {
            super(itemView);
            tvNoteDate = itemView.findViewById(R.id.tvNoteDate);
            tvNoteTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvNoteDescription = itemView.findViewById(R.id.tvNoteDescription);
            tvNoteFileName = itemView.findViewById(R.id.tvNoteFileName);
            imageViewNoteFileIcon = itemView.findViewById(R.id.imageViewNoteFileIcon);
            linearLayoutNotes = itemView.findViewById(R.id.linearLayoutNotes);
        }
    }
}
