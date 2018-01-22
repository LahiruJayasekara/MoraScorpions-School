package com.mlpj.www.morascorpions.ComplainHandling;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mlpj.www.morascorpions.ApiClient;
import com.mlpj.www.morascorpions.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TakeActionActivity extends AppCompatActivity {

    private EditText etAction;
    private Button onSubmitAction;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_action);

        final int actionId = getIntent().getIntExtra("actionId", 0);

        etAction = findViewById(R.id.etAction);
        onSubmitAction = findViewById(R.id.onSubmitAction);

        onSubmitAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etAction.getText().toString().equals("")) {
                    etAction.setError("Can not be Empty!!!");
                } else {

                    ComplainItem newComplain = new ComplainItem(actionId, etAction.getText().toString());
                    mProgressDialog = new ProgressDialog(TakeActionActivity.this);
                    mProgressDialog.setTitle("Loading. . .");
                    mProgressDialog.setMessage("Please wait...!");
                    mProgressDialog.show();

                    Retrofit.Builder builder = new Retrofit.Builder()
                            //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                            .baseUrl(getString(R.string.base_url_azure))    //remote localhost
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit = builder.build();

                    ApiClient client = retrofit.create(ApiClient.class);
                    Call<Void> call = client.takeAction(newComplain);

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Complain submitted Successfully", Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Error occured " + t.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });

    }
}
