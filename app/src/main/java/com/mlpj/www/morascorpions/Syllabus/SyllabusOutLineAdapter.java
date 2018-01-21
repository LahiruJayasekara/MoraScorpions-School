package com.mlpj.www.morascorpions.Syllabus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mlpj.www.morascorpions.HwAdapter;
import com.mlpj.www.morascorpions.R;

import java.util.List;

/**
 * Created by DELL on 1/21/2018.
 * All Rights Reserved by MLPJ©
 */

public class SyllabusOutLineAdapter extends RecyclerView.Adapter<SyllabusOutLineAdapter.ViewHolder>{

    List<SyllabusOutLineItem> syllabusOutLineItems;


    public SyllabusOutLineAdapter(List<SyllabusOutLineItem> syllabusOutLineItems) {
        this.syllabusOutLineItems = syllabusOutLineItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.syllabus_outline_card_view,parent,false);
        return new SyllabusOutLineAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SyllabusOutLineItem syllabusOutLineItem = syllabusOutLineItems.get(position);
        holder.tvSyllabusOutLine.setText(syllabusOutLineItem.getOutline());
        if(syllabusOutLineItem.isDoneOrNot()){

            holder.imgSyllabusOutlineChecked.setImageResource(R.drawable.ic_check_box_checked);
        }else {
            holder.imgSyllabusOutlineChecked.setImageResource(R.drawable.ic_check_box_unchecked);
        }
    }

    @Override
    public int getItemCount() {
        return syllabusOutLineItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvSyllabusOutLine;
        private ImageView imgSyllabusOutlineChecked;
        public ViewHolder(View itemView) {
            super(itemView);
            tvSyllabusOutLine = itemView.findViewById(R.id.tvSyllabusOutLine);
            imgSyllabusOutlineChecked = itemView.findViewById(R.id.imageSyllabus);
        }
    }
}
