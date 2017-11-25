package com.mlpj.www.morascorpions;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MarkAttendanceActivity extends AppCompatActivity {

    ArrayList attendanceDetails;
    ListView listView;
    private CustomAdapter adapter;
    ArrayList<AttendanceDetail> markedList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        listView = (ListView) findViewById(R.id.lvAttendance);

        Intent intent = getIntent();
        attendanceDetails = (ArrayList)intent.getSerializableExtra("attendanceDetails");

        //attendanceDetails = new ArrayList();
/*
        attendanceDetails.add(new AttendanceDetail(1,"Apple Pie", false));
        attendanceDetails.add(new AttendanceDetail(2,"Banana Bread", false));
        attendanceDetails.add(new AttendanceDetail(3,"Cupcake", false));
        attendanceDetails.add(new AttendanceDetail(4,"Donut", true));
        attendanceDetails.add(new AttendanceDetail(5,"Eclair", true));
        attendanceDetails.add(new AttendanceDetail(6,"Froyo", true));
        attendanceDetails.add(new AttendanceDetail(7,"Gingerbread", true));
        attendanceDetails.add(new AttendanceDetail(8,"Honeycomb", false));
        attendanceDetails.add(new AttendanceDetail(9,"Ice Cream Sandwich", false));
        attendanceDetails.add(new AttendanceDetail(10,"Jelly Bean", false));
        attendanceDetails.add(new AttendanceDetail(11,"Kitkat", false));
        attendanceDetails.add(new AttendanceDetail(12,"Lollipop", false));
        attendanceDetails.add(new AttendanceDetail(13,"Marshmallow", false));
        attendanceDetails.add(new AttendanceDetail(14,"Nougat", false));
*/
        adapter = new CustomAdapter(attendanceDetails, getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), ""+position, Toast.LENGTH_SHORT).show();
                AttendanceDetail attendanceDetail= (AttendanceDetail)attendanceDetails.get(position);
                attendanceDetail.present = !attendanceDetail.present;
                ((AttendanceDetail) attendanceDetails.get(position)).present =attendanceDetail.present;
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void onMarkAttendance(View view){
        //attendanceDetails = new ArrayList<AttendanceDetail>();
        markedList = new ArrayList<AttendanceDetail>();
        for(int i = 0; i <attendanceDetails.size(); i++){
            AttendanceDetail row = (AttendanceDetail)attendanceDetails.get(i);
            markedList.add(new AttendanceDetail(row.studentId, row.present, row.date));
        }

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:49375/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build());

        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);
        Call<Void> call = client.markAttendance(markedList);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                Toast.makeText(MarkAttendanceActivity.this, "Success " + response.code(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MarkAttendanceActivity.this, "Not Success " + t.getMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

}
