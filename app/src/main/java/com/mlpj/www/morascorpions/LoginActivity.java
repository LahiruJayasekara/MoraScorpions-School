package com.mlpj.www.morascorpions;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    User user;
    EditText etEmail, etPassword;
    Button bLogIn;
    UserLocalStore userLocalStore;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogIn = (Button) findViewById(R.id.bLogIn);

        userLocalStore = new UserLocalStore(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (userLocalStore.getUserLoggedIn() == true) {
            user = userLocalStore.getUserDetails();
            Intent intent = new Intent(this, UserAreaActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void onClickLogIn(View view) {

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Logging In...");
        mProgressDialog.setMessage("Please wait for the Authentication!");
        mProgressDialog.show();
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        User newUser = new User(email, password);

        if (email.equals("")) {
            etEmail.setError("Can't be empty");
        } else if (password.equals("")) {
            etPassword.setError("Can't be empty");
        } else {

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(getString(R.string.base_url_azure))
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();

            ApiClient client = retrofit.create(ApiClient.class);
            Call<ArrayList<User>> call = client.login(email, password);

            call.enqueue(new Callback<ArrayList<User>>() {
                @Override
                public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                    if (response != null && response.body().size() != 0) {
                        mProgressDialog.dismiss();
                        User user = response.body().get(0);
                        userLocalStore.setUserLoggedIn(true);
                        userLocalStore.setUserDetails(user);

                        Intent intent = new Intent(getApplicationContext(), UserAreaActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Connection Error", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                    Log.d("loginError", t.getMessage());
                }
            });
        }
    }
}
