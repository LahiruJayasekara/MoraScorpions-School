package com.mlpj.www.morascorpions;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DELL on 1/17/2018.
 * All Rights Reserved by MLPJ©
 */

public class HwAdapter extends RecyclerView.Adapter<HwAdapter.ViewHolder>{

    private List<HwItem> hwItems;
    private Context context;

    public HwAdapter(List<HwItem> hwItems, Context context) {
        this.hwItems = hwItems;
        this.context = context;
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
        holder.tvHwDeadline.setText("Deadline - " + hwItem.getHwDeadline());
        holder.tvHwHeading.setText(hwItem.getHwHeading());
        holder.tvHwVisibilityStart.setText("Visibility Start - " + hwItem.getHwVisibilityEndDate());
        holder.tvHwVisibilityEnd.setText("Visibility End - " + hwItem.getHwVisibilityStartDate());
        holder.tvHwFileName.setText(hwItem.getHwFileName());

    }

    @Override
    public int getItemCount() {
        return hwItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvHwDeadline, tvHwHeading, tvHwVisibilityStart, tvHwVisibilityEnd, tvHwFileName;
        public ImageView imageViewHwFileIcon;
        public LinearLayout linearLayoutHw;
        public ViewHolder(View itemView) {
            super(itemView);
            tvHwDeadline = itemView.findViewById(R.id.tvHwDeadline);
            tvHwHeading = itemView.findViewById(R.id.tvHwHeading);
            tvHwVisibilityStart = itemView.findViewById(R.id.tvHwVisibilityStart);
            tvHwVisibilityEnd = itemView.findViewById(R.id.tvHwVisibilityEnd);
            tvHwFileName = itemView.findViewById(R.id.tvHwFileName);
            imageViewHwFileIcon = itemView.findViewById(R.id.imageViewHwFileIcon);
            linearLayoutHw = itemView.findViewById(R.id.linearLayoutHw);
        }
    }
}
