package com.mlpj.www.morascorpions;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TeacherMarkAttendanceFragment extends Fragment {

    private Calendar calendar;
    private int year,month, day;
    private UserLocalStore userLocalStore;
    private User user;
    private ArrayList<AttendanceDetail> attendanceDetails;
    private Button mOnSelectDate, mOnMarkAttendance;
    private ListView mLvAttendanceSheet;
    private CustomAdapterAttendanceSheet adapter;
    private ProgressDialog mProgressDialog;
    private TextView mTvAttendanceDate;
    private ArrayList<AttendanceDetail> markedList;
    private String date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_teacher_mark_attendance, container, false);
        userLocalStore = new UserLocalStore(getContext());
        user = userLocalStore.getUserDetails();

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        mTvAttendanceDate = view.findViewById(R.id.tvAttendanceDate);

        mOnSelectDate = (Button)view.findViewById(R.id.btnOnSelectDate);
        mOnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        year = datePicker.getYear();
                        month = datePicker.getMonth();
                        day = datePicker.getDayOfMonth();
                        updateList();
                    }
                }, year, month, day).show();
            }
        });

        mOnMarkAttendance = view.findViewById(R.id.onMarkAttendance);
        mOnMarkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressDialog = new ProgressDialog(getContext());
                mProgressDialog.setTitle("Please wait we mark the attendance");
                mProgressDialog.setMessage("Marking...");
                mProgressDialog.show();
                markedList = new ArrayList<AttendanceDetail>();
                for(int i = 0; i <attendanceDetails.size(); i++){
                    AttendanceDetail row = (AttendanceDetail)attendanceDetails.get(i);
                    markedList.add(new AttendanceDetail(row.getP_Id(), row.isPresentAbsent(), date));
                }

                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(getString(R.string.base_url_azure))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClientBuilder.build());

                Retrofit retrofit = builder.build();

                ApiClient client = retrofit.create(ApiClient.class);
                Call<Void> call = client.markAttendance(markedList);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                        mProgressDialog.dismiss();
                        Toast.makeText(getContext(), "Marking Success", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        mProgressDialog.dismiss();
                        Toast.makeText(getContext(), "Not Success " + t.getMessage(), Toast.LENGTH_LONG).show();
                        t.printStackTrace();
                    }
                });
            }
        });

        mLvAttendanceSheet = view.findViewById(R.id.lvAttendance);

        updateList();

        return view;
    }

    public void updateList(){
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Please wait for the attendance sheet");
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        date = year + "-" + (month + 1) + "-" + day;
                // datePicker.getYear() + "-" + (datePicker.getMonth()+1) + "-" + datePicker.getDayOfMonth();
        mTvAttendanceDate.setText(date);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_azure))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);
        Call<ArrayList<AttendanceDetail>> call = client.getAttendanceSheet(date,user.getClassRoomName());
        call.enqueue(new Callback<ArrayList<AttendanceDetail>>() {
            @Override
            public void onResponse(Call<ArrayList<AttendanceDetail>> call, Response<ArrayList<AttendanceDetail>> response) {
                mProgressDialog.dismiss();

                attendanceDetails = response.body();

                adapter = new CustomAdapterAttendanceSheet(attendanceDetails, getContext());

                mLvAttendanceSheet.setAdapter(adapter);
                mLvAttendanceSheet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView parent, View view, int position, long id) {
                        Toast.makeText(getContext(), ""+position, Toast.LENGTH_SHORT).show();
                        AttendanceDetail attendanceDetail= (AttendanceDetail)attendanceDetails.get(position);
                        attendanceDetail.setPresentAbsent(!attendanceDetail.isPresentAbsent());
                        ((AttendanceDetail) attendanceDetails.get(position)).setPresentAbsent(attendanceDetail.isPresentAbsent());
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<AttendanceDetail>> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
