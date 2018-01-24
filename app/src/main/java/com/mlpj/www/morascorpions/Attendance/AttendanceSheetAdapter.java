package com.mlpj.www.morascorpions.Attendance;

/**
 * Created by DELL on 9/21/2017.
 * All Rights Reserved by MLPJ©
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mlpj.www.morascorpions.R;

import java.util.ArrayList;


public class AttendanceSheetAdapter extends RecyclerView.Adapter<AttendanceSheetAdapter.ViewHolder>{

    private ArrayList<AttendanceDetail> attendanceDetails;

    public AttendanceSheetAdapter(ArrayList<AttendanceDetail> attendanceDetails) {
        this.attendanceDetails = attendanceDetails;
    }

    public ArrayList<AttendanceDetail> getAttendanceDetails() {
        return attendanceDetails;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.attendance_card_view,parent,false);
        return new AttendanceSheetAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AttendanceDetail attendanceDetail = attendanceDetails.get(position);
        holder.tvAttendanceName.setText(attendanceDetail.getName());
        holder.checkBoxAttendance.setChecked(attendanceDetail.isPresentAbsent());
        holder.checkBoxAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendanceDetails.get(position).setPresentAbsent(holder.checkBoxAttendance.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return attendanceDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvAttendanceName;
        private CheckBox checkBoxAttendance;
        public ViewHolder(View itemView) {
            super(itemView);
            tvAttendanceName = itemView.findViewById(R.id.tvAttendanceName);
            checkBoxAttendance = itemView.findViewById(R.id.checkBoxAttendance);

        }
    }
}