package com.mlpj.www.morascorpions.NotesAndHwHandling;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mlpj.www.morascorpions.R;
import com.mlpj.www.morascorpions.User;
import com.mlpj.www.morascorpions.UserLocalStore;

import java.util.List;

/**
 * Created by DELL on 1/17/2018.
 * All Rights Reserved by MLPJ©
 */

public class HwAdapter extends RecyclerView.Adapter<HwAdapter.ViewHolder>{

    private List<HwItem> hwItems;
    private Context context;
    private User currentUser;

    public HwAdapter(List<HwItem> hwItems, Context context) {
        this.hwItems = hwItems;
        this.context = context;
        currentUser = new UserLocalStore(context).getUserDetails();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.hw_card_view,parent,false);
        return new HwAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HwItem hwItem = hwItems.get(position);
        holder.tvHwDeadline.setText("Deadline - " + hwItem.getDeadline());
        holder.tvHwContent.setText(hwItem.getContent());
        holder.tvHwHeading.setText(hwItem.getTopic());
        if(currentUser.getRoleName().equals("Teacher")){
            holder.tvHwVisibilityStart.setText("Visibility Start - " + hwItem.getVisibilityStartDate());
            holder.tvHwVisibilityEnd.setText("Visibility End - " + hwItem.getVisibilityEndDate());
        }else{
            holder.linearLayoutHw.removeView(holder.tvHwVisibilityStart);
            holder.linearLayoutHw.removeView(holder.tvHwVisibilityEnd);
        }
        holder.tvHwFileName.setText("Download " + hwItem.getTopic() + ".pdf");

        holder.tvHwFileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadManager downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(hwItem.getPdf());
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hwItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvHwDeadline, tvHwHeading, tvHwVisibilityStart, tvHwVisibilityEnd, tvHwFileName, tvHwContent;
        public ImageView imageViewHwFileIcon;
        public LinearLayout linearLayoutHw;
        public ViewHolder(View itemView) {
            super(itemView);
            tvHwDeadline = itemView.findViewById(R.id.tvHwDeadline);
            tvHwHeading = itemView.findViewById(R.id.tvHwHeading);
            tvHwVisibilityStart = itemView.findViewById(R.id.tvHwVisibilityStart);
            tvHwVisibilityEnd = itemView.findViewById(R.id.tvHwVisibilityEnd);
            tvHwFileName = itemView.findViewById(R.id.tvHwFileName);
            tvHwContent = itemView.findViewById(R.id.tvHwContent);
            imageViewHwFileIcon = itemView.findViewById(R.id.imageViewHwFileIcon);
            linearLayoutHw = itemView.findViewById(R.id.linearLayoutHw);
        }
    }
}
