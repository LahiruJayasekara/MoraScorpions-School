package com.mlpj.www.morascorpions;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    User user;
    EditText etEmail, etPassword;
    Button bLogIn;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogIn = (Button)findViewById(R.id.bLogIn);


        userLocalStore = new UserLocalStore(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(userLocalStore.getUserLoggedIn() == true){
            user = userLocalStore.getUserDetails();
            Intent intent = new Intent(this,UserAreaActivity.class);
            intent.putExtra("name",user.name);
            intent.putExtra("userType",user.userType);
            intent.putExtra("className",user.className);
            intent.putExtra("admitionDate",user.admitionDate);
            startActivity(intent);
            finish();
        }



    }

    public void onClickLogIn(View view){


        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        JSONObject js = new JSONObject();
        try {
            js.put("email",email);
            js.put("password",password);
        }catch(JSONException e){

        }

        if(email.equals("")){
            etEmail.setError("Can't be empty");
        }else  if(password.equals("")){
            etPassword.setError("Can't be empty");
        }
        else{
            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>(){
                @Override
                public void onResponse(JSONObject jsonResponse) {

                    try {
                        //Toast.makeText(getBaseContext(), response, Toast.LENGTH_LONG).show();
                        //JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if(success){

                            String name = jsonResponse.getString("name");
                            String userType = jsonResponse.getString("userType");
                            String className = jsonResponse.getString("className");
                            String admitionDate = jsonResponse.getString("admitionDate");

                            user = new User(name,userType,className,admitionDate);

                            userLocalStore.setUserLoggedIn(true);
                            userLocalStore.setUserDetails(user);

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
                                    switchUser(TeacherActivity.class);

                                    break;
                                case "a":
                                    role = "admin";
                                    break;
                            }

                           // Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);


                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("Login failed")
                                    .setNegativeButton("Retry",null)
                                    .create()
                                    .show();

                        }
                    } catch (JSONException e) {


                        e.printStackTrace();
                    }

                }
            };

            LoginRequest loginRequest = new LoginRequest(email, password, js, responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        }


    }

    public void switchUser(Class context){
        Intent intent = new Intent(this,context);
        intent.putExtra("user",user);
        startActivity(intent);
        finish();

    }
}
