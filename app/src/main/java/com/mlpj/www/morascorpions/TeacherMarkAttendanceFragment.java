package com.mlpj.www.morascorpions;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mlpj.www.morascorpions.Syllabus.SyllabusOutLineItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    private List<AttendanceDetail> attendanceDetails;
    private Button mOnSelectDate, mOnMarkAttendance;


    private ProgressDialog mProgressDialog;
    private TextView mTvAttendanceDate;
    private ArrayList<AttendanceDetail> markedList;
    private String date;

    private RecyclerView mRecyclerViewAttendance;


    private RecyclerView.Adapter attendanceAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_teacher_mark_attendance, container, false);
        userLocalStore = new UserLocalStore(getContext());
        user = userLocalStore.getUserDetails();
        mRecyclerViewAttendance = view.findViewById(R.id.recyclerViewAttendance);

        mRecyclerViewAttendance.setHasFixedSize(true);
        mRecyclerViewAttendance.setLayoutManager(new LinearLayoutManager(getContext()));

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        mProgressDialog = new ProgressDialog(getContext());

        mTvAttendanceDate = view.findViewById(R.id.tvAttendanceDate);

        updateList();
        Toast.makeText(getContext(),"rrr",Toast.LENGTH_LONG).show();
        Toast.makeText(getContext(),"bvnv",Toast.LENGTH_LONG).show();


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
                        if (datePicker.isShown()) {
                            updateList();
                        }
                    }
                }, year, month, day).show();

            }


        });

        mOnMarkAttendance = view.findViewById(R.id.onMarkAttendance);
        mOnMarkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressDialog.setTitle("Please wait we mark the attendance");
                mProgressDialog.setMessage("Marking...");
                mProgressDialog.show();

                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(getString(R.string.base_url_azure))
                        .addConverterFactory(GsonConverterFactory.create());

                Retrofit retrofit = builder.build();

                ApiClient client = retrofit.create(ApiClient.class);
                Call<Void> call = client.markAttendance(((AttendanceSheetAdapter)attendanceAdapter).getAttendanceDetails());
                Toast.makeText(getContext(), "" +((AttendanceSheetAdapter)attendanceAdapter).getAttendanceDetails().get(0).isPresentAbsent(), Toast.LENGTH_LONG).show();
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        mProgressDialog.dismiss();
                        Toast.makeText(getContext(), "Marked Successfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        mProgressDialog.dismiss();
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });






        return view;
    }


    public void updateList(){
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Please wait for the attendance sheet");
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        attendanceDetails = new ArrayList<>();
        markedList = new ArrayList<>();


        date = year + "-" + (month + 1) + "-" + day;
                // datePicker.getYear() + "-" + (datePicker.getMonth()+1) + "-" + datePicker.getDayOfMonth();
        mTvAttendanceDate.setText(date);

        Retrofit.Builder builder = new Retrofit.Builder()
                //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                .baseUrl(getString(R.string.base_url_azure))    //remote localhost
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);
        Call<ArrayList<AttendanceDetail>> call = client.getAttendanceSheet(date,user.getClassRoomName());
        call.enqueue(new Callback<ArrayList<AttendanceDetail>>() {
            @Override
            public void onResponse(Call<ArrayList<AttendanceDetail>> call, Response<ArrayList<AttendanceDetail>> response) {
                mProgressDialog.dismiss();
                if(response != null){
                    attendanceDetails = response.body();
                    for(int i = 0; i <attendanceDetails.size(); i++){
                        AttendanceDetail row = (AttendanceDetail)attendanceDetails.get(i);
                       // Toast.makeText(getContext(), attendanceDetails.get(0).getP_Id(), Toast.LENGTH_LONG).show();
                        markedList.add(new AttendanceDetail(row.getP_Id(),row.getName(), row.isPresentAbsent(), date));
                    }
                    attendanceAdapter = new AttendanceSheetAdapter(markedList);
                    mRecyclerViewAttendance.setAdapter(attendanceAdapter);
                    Toast.makeText(getContext(), "" + markedList.get(0).isPresentAbsent()+markedList.get(0).getDate()+markedList.get(0).getP_Id(), Toast.LENGTH_LONG).show();

                    //attendanceAdapter.notifyDataSetChanged();

                }else{
                    Toast.makeText(getContext(), "Error occured", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AttendanceDetail>> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
