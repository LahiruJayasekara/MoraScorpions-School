package com.mlpj.www.morascorpions;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SelectDateActivity extends AppCompatActivity {
    DatePicker datePicker;
    Calendar calendar;
    int year,month, day;
    UserLocalStore userLocalStore;
    User user;
    ArrayList attendanceDetails;
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


        JSONObject js = new JSONObject();
        try {
            js.put("className",user.className);
            js.put("date", date);

        }catch(JSONException e){
            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        String url = "http://10.0.2.2:49375/user/getAttendanceSheet/";

        try {
            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject jsonResponse) {
                    //Toast.makeText(getBaseContext(), jsonResponse.toString(), Toast.LENGTH_LONG).show();
               try {

                    //boolean success = jsonResponse.getBoolean("success");
                   JSONArray ja = jsonResponse.getJSONArray("attendanceList");
                   attendanceDetails = new ArrayList();
                   for(int i = 0; i < ja.length(); i++){
                       JSONObject js = ja.getJSONObject(i);
                       attendanceDetails.add(new AttendanceDetail(js.getInt("studentId"), js.getString("name"), js.getBoolean("present"), js.getString("date")));

                       Toast.makeText(getBaseContext(), js.toString(), Toast.LENGTH_LONG).show();

                   }

                   Intent intent = new Intent(getBaseContext(),MarkAttendanceActivity.class);
                   intent.putExtra("attendanceDetails",attendanceDetails);
                   startActivity(intent);
                    /*
                    String name = jsonResponse.getString("name");
                    String userType = jsonResponse.getString("userType").toString();
                    String className = jsonResponse.getString("className").toString();
                    String admitionDate = jsonResponse.getString("admitionDate").toString();
*/

                } catch (JSONException e) {

                    Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                }
            };

        VolleyPostJsonObjectRequest attendanceSheetRequest = new VolleyPostJsonObjectRequest(js, url, responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelectDateActivity.this);
        queue.add(attendanceSheetRequest);

        }catch (Exception e){
            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


/*
        Intent intent = new Intent(this,MarkAttendanceActivity.class);
        intent.putExtra("Date",date);
        startActivity(intent);
*/
    }
}
