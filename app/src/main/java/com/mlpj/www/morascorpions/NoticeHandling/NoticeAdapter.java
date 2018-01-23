package com.mlpj.www.morascorpions.NoticeHandling;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mlpj.www.morascorpions.ComplainHandling.ComplainAdapter;
import com.mlpj.www.morascorpions.R;

import java.util.List;

/**
 * Created by DELL on 1/23/2018.
 * All Rights Reserved by MLPJ©
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder>{

    List<NoticeItem> noticeItems;

    public NoticeAdapter(List<NoticeItem> noticeItems) {
        this.noticeItems = noticeItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.notice_card_view,parent,false);
        return new NoticeAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NoticeItem noticeItem = noticeItems.get(position);
        holder.tvNoticeTopic.setText(noticeItem.getTopic());
        holder.tvNoticeContent.setText(noticeItem.getContent());
        holder.tvNoticeEndDate.setText("End Date- " + noticeItem.getEndDate());
        holder.tvNoticeUploadedDate.setText("Uploaded Date " + noticeItem.getUploadDate());
        holder.tvNoticeLastUpdatedDate.setText("Last Updated Date " + noticeItem.getLastUpdatedDate());
    }

    @Override
    public int getItemCount() {
        return noticeItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvNoticeTopic, tvNoticeContent, tvNoticeEndDate, tvNoticeUploadedDate, tvNoticeLastUpdatedDate;
        private CardView noticeCrdView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNoticeTopic = itemView.findViewById(R.id.noticeTopic);
            tvNoticeContent = itemView.findViewById(R.id.noticeContent);
            tvNoticeEndDate = itemView.findViewById(R.id.noticeEndDate);
            tvNoticeUploadedDate = itemView.findViewById(R.id.noticeUploadedDate);
            tvNoticeLastUpdatedDate = itemView.findViewById(R.id.noticeLastUpdatedDate);

            noticeCrdView = itemView.findViewById(R.id.noticeCardView);

        }
    }
}
