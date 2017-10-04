package com.mlpj.www.morascorpions;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
            switchUser(user.userType);
        }
    }

    public void onClickLogIn(View view){

        Toast.makeText(getBaseContext(), "clicked", Toast.LENGTH_LONG).show();
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();


        if(email.equals("")){
            etEmail.setError("Can't be empty");
        }else  if(password.equals("")){
            etPassword.setError("Can't be empty");
        }
        else{

            JSONObject js = new JSONObject();
            try {
                js.put("email",email);
                js.put("password",password);
            }catch(JSONException e){

            }

            String url = "http://10.0.2.2:49375/user/login/";

            Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>(){

                @Override
                public void onResponse(JSONObject jsonResponse) {
                    Toast.makeText(getBaseContext(), "Login Success", Toast.LENGTH_LONG).show();
                    JSONObject abc = jsonResponse;
                    try {
                        boolean success = jsonResponse.getBoolean("success");

                        String name = jsonResponse.getString("name");
                        String userType = jsonResponse.getString("userType").toString();
                        String className = jsonResponse.getString("className").toString();
                        String admitionDate = jsonResponse.getString("admitionDate").toString();

                        if(success){


                            user = new User(name,userType,className,admitionDate);
                            userLocalStore.setUserLoggedIn(true);
                            userLocalStore.setUserDetails(user);

                            switchUser(userType);


                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("Login failed")
                                    .setNegativeButton("Retry",null)
                                    .create()
                                    .show();

                        }
                    } catch (JSONException e) {

                        Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }

                }
            };

            VolleyPostJsonObjectRequest loginRequest = new VolleyPostJsonObjectRequest(js, url, responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        }


    }

    public void switchUser(String userType){
        Class context = UserAreaActivity.class;
        switch (userType){
            case "s":
                break;
            case "pa":
                break;
            case "pr":
                break;
            case "t":
                Toast.makeText(getBaseContext(), "Teacher", Toast.LENGTH_LONG).show();
                context = TeacherActivity.class;
                break;
        }

        Intent intent = new Intent(this,context);
        startActivity(intent);
        finish();
    }
}
