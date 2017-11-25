package com.mlpj.www.morascorpions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectDateActivity extends AppCompatActivity {
    DatePicker datePicker;
    Calendar calendar;
    int year,month, day;
    UserLocalStore userLocalStore;
    User user;
    ArrayList<AttendanceDetail> attendanceDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);

        userLocalStore = new UserLocalStore(this);
        user = userLocalStore.getUserDetails();
        datePicker = (DatePicker)findViewById(R.id.datePicker);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        datePicker.init(year,month,day,null);
    }

    public void onSelectDate(View view){
        String date = datePicker.getYear() + "/" + (datePicker.getMonth()+1) + "/" + datePicker.getDayOfMonth();
        Toast.makeText(this, user.className, Toast.LENGTH_SHORT).show();

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
                Toast.makeText(SelectDateActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();
                attendanceDetails = response.body();

                Intent intent = new Intent(getBaseContext(),MarkAttendanceActivity.class);
                intent.putExtra("attendanceDetails",attendanceDetails);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ArrayList<AttendanceDetail>> call, Throwable t) {
                Toast.makeText(SelectDateActivity.this, "not success", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

}
