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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        User newUser = new User(email,password);

        if(email.equals("")){
            etEmail.setError("Can't be empty");
        }else  if(password.equals("")){
            etPassword.setError("Can't be empty");
        }
        else{

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:49375/user/")
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();

            ApiClient client = retrofit.create(ApiClient.class);
            Call<User> call = client.login(newUser);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, retrofit2.Response<User> response) {

                    Toast.makeText(getBaseContext(), "Login Success", Toast.LENGTH_LONG).show();

                        boolean success = response.body().isSuccess();

                        String name = response.body().getName();
                        String userType = response.body().getUserType();
                        String className = response.body().getClassName();
                        String admitionDate = response.body().getAdmitionDate();

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
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(LoginActivity.this,"Not Successful",Toast.LENGTH_LONG).show();
                }
            });
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
