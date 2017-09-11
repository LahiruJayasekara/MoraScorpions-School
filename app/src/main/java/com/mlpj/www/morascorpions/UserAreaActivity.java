package com.mlpj.www.morascorpions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UserAreaActivity extends AppCompatActivity {


    TextView tvDisplay, tvClassName, tvAdmitionDate;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        tvDisplay = (TextView)findViewById(R.id.tvDisplay);
        tvClassName = (TextView)findViewById(R.id.tvClassName);
        tvAdmitionDate = (TextView)findViewById(R.id.tvAdmitionDate);
        userLocalStore = new UserLocalStore(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String userType = intent.getStringExtra("userType");
        String className = intent.getStringExtra("className");
        String admitionDate = intent.getStringExtra("admitionDate");
        String role = "", text;

        switch (userType){
            case "s":
                role = "student";
                break;
            case "pa":
                role = "parent";
                break;
            case "pr":
                role = "principal";
                break;
            case "t":
                role = "teacher";
                break;
            case "a":
                role = "admin";
                break;
        }

        setTitle("Hi " + name);
        text = "You are logged in as a " + role + ".";
        tvDisplay.setText(text);

        if(userType.equals("s")){
            tvClassName.setText("Class - " + className);
            tvAdmitionDate.setText("Admition Date - " + admitionDate);
        }

    }
/*
    public void onClickLogOut(View view){
        //Toast.makeText(getBaseContext(), "test", Toast.LENGTH_LONG).show();
        userLocalStore.clearUser();
        userLocalStore.setUserLoggedIn(false);
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_LogOut:
                userLocalStore.clearUser();
                userLocalStore.setUserLoggedIn(false);
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
