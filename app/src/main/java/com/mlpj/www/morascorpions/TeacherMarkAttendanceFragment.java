package com.mlpj.www.morascorpions;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TeacherMarkAttendanceFragment extends Fragment {

    DatePicker datePicker;
    Calendar calendar;
    int year,month, day;
    UserLocalStore userLocalStore;
    User user;
    ArrayList<AttendanceDetail> attendanceDetails;
    private Button mOnSelectDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_teacher_mark_attendance, container, false);
        userLocalStore = new UserLocalStore(getContext());
        user = userLocalStore.getUserDetails();
        datePicker = (DatePicker)view.findViewById(R.id.datePicker);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        datePicker.init(year,month,day,null);

        mOnSelectDate = (Button)view.findViewById(R.id.btnOnSelectDate);
        mOnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String date = datePicker.getYear() + "/" + (datePicker.getMonth()+1) + "/" + datePicker.getDayOfMonth();
                Toast.makeText(getContext(), user.className, Toast.LENGTH_SHORT).show();

                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:49375/user/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClientBuilder.build());

                Retrofit retrofit = builder.build();

                ApiClient client = retrofit.create(ApiClient.class);
                Call<ArrayList<AttendanceDetail>> call = client.getAttendanceSheet(user.className, date);
                call.enqueue(new Callback<ArrayList<AttendanceDetail>>() {
                    @Override
                    public void onResponse(Call<ArrayList<AttendanceDetail>> call, retrofit2.Response<ArrayList<AttendanceDetail>> response) {
                        Toast.makeText(getContext(), response.body().toString(), Toast.LENGTH_LONG).show();
                        attendanceDetails = response.body();

                        Intent intent = new Intent(getContext(),MarkAttendanceActivity.class);
                        intent.putExtra("attendanceDetails",attendanceDetails);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<AttendanceDetail>> call, Throwable t) {
                        Toast.makeText(getContext(), "not success", Toast.LENGTH_LONG).show();
                        t.printStackTrace();
                    }
                });


            }
        });

        return view;
    }
    
}
