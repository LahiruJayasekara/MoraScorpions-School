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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MarkAttendanceActivity extends AppCompatActivity {

    ArrayList attendanceDetails;
    ListView listView;
    private CustomAdapter adapter;


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
                attendanceDetail.checked = !attendanceDetail.checked;
                ((AttendanceDetail) attendanceDetails.get(position)).checked =attendanceDetail.checked;
                adapter.notifyDataSetChanged();


            }
        });

    }

    public void onMarkAttendance(View view){
        String txt = "";
        JSONArray ja = new JSONArray();
        for(int i = 0; i <attendanceDetails.size(); i++){
            AttendanceDetail row = (AttendanceDetail)attendanceDetails.get(i);
            txt += row.name + " " +row.checked + " ";
            try {
                JSONObject jo = new JSONObject();
                jo.put("date", row.date);
                jo.put("present", row.checked);
                jo.put("studentId", row.studentId);
                ja.put(jo);
            }catch (JSONException e){
                Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        }
        //Toast.makeText(getBaseContext(), ja.toString(), Toast.LENGTH_SHORT).show();
        Log.d("JSON", ja.toString());

        String url = "http://10.0.2.2:49375/user/markattendance/";

        Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>(){

            @Override
            public void onResponse(JSONArray jsonResponse) {
                Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_LONG).show();
            }
        };

        VolleyPostJsonArrayRequest markAttendanceRequest = new VolleyPostJsonArrayRequest(ja, url, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MarkAttendanceActivity.this);
        queue.add(markAttendanceRequest);

    }

}
