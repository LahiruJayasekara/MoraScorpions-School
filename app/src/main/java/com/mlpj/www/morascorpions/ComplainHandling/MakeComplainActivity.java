package com.mlpj.www.morascorpions.ComplainHandling;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mlpj.www.morascorpions.ApiClient;
import com.mlpj.www.morascorpions.R;
import com.mlpj.www.morascorpions.User;
import com.mlpj.www.morascorpions.UserLocalStore;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MakeComplainActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private User mCurrentUser;
    private PrincipalAndTeacherDetalis mPrincipalAndTeacherDetails;
    private EditText mEtComplainTopic, mEtComplainContent;
    private CheckBox mCheckBoxAnonymous;
    private Button mOnSubmitComplain;
    private Spinner mSpinnerComplainee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_complain);

        mCurrentUser = new UserLocalStore(getApplicationContext()).getUserDetails();
        mEtComplainTopic = findViewById(R.id.etComplainTopic);
        mEtComplainContent = findViewById(R.id.etComplainContent);
        mCheckBoxAnonymous = findViewById(R.id.checkBoxSetAnonymous);
        mOnSubmitComplain = findViewById(R.id.onSubmitComplain);
        mSpinnerComplainee = findViewById(R.id.spinnerSelectComplainee);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("Please wait...!");
        mProgressDialog.show();

            Retrofit.Builder builder = new Retrofit.Builder()
                    //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                    .baseUrl(getString(R.string.base_url_azure))    //remote localhost
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();

            ApiClient client = retrofit.create(ApiClient.class);
            Call<PrincipalAndTeacherDetalis> call =  client.getTeacherAndPrincipalForComplain(mCurrentUser.getP_Id());

            call.enqueue(new Callback<PrincipalAndTeacherDetalis>() {
                @Override
                public void onResponse(Call<PrincipalAndTeacherDetalis> call, Response<PrincipalAndTeacherDetalis> response) {
                    mProgressDialog.dismiss();
                    if(response != null && response.body() != null){
                        mPrincipalAndTeacherDetails = response.body();
                    }else {
                        Toast.makeText(getApplicationContext(),"Error occured", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<PrincipalAndTeacherDetalis> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Error loading data " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            mOnSubmitComplain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mEtComplainTopic.getText().toString().equals("")){
                        mEtComplainTopic.setError("Can not be Empty!!!");
                    }else if(mEtComplainContent.getText().toString().equals("")){
                        mEtComplainContent.setError("Can not be Empty!!!");
                    }else{
                        String complaineeId, complaineeName;
                        if(mSpinnerComplainee.getSelectedItem().equals("Principal")){
                            complaineeId = mPrincipalAndTeacherDetails.getPrincipalId();
                            complaineeName = mPrincipalAndTeacherDetails.getPrincipalName();
                        }else{
                            complaineeId = mPrincipalAndTeacherDetails.getTeacherId();
                            complaineeName = mPrincipalAndTeacherDetails.getTeacherName();
                        }
                        ComplainItem newComplain = new ComplainItem(
                                mEtComplainTopic.getText().toString(),
                                mEtComplainContent.getText().toString(),
                                complaineeId,
                                mCurrentUser.getP_Id(),
                                mCheckBoxAnonymous.isChecked(),
                                complaineeName
                        );

                        //mProgressDialog = new ProgressDialog(getApplicationContext());
                        mProgressDialog.setTitle("Loading. . .");
                        mProgressDialog.setMessage("Please wait...!");
                        mProgressDialog.show();
                        Retrofit.Builder builder = new Retrofit.Builder()
                                //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                                .baseUrl(getString(R.string.base_url_azure))    //remote localhost
                                .addConverterFactory(GsonConverterFactory.create());
                        Retrofit retrofit = builder.build();

                        ApiClient client = retrofit.create(ApiClient.class);
                        Call<Void> call =  client.makeComplain(newComplain);

                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                mProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Complain submitted Successfully",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                mProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Error occured " + t.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            });
    }
}
