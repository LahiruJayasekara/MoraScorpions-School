package com.mlpj.www.morascorpions;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParentAttendanceViewFragment extends Fragment {

    private DonutProgress donutProgress;
    private TextView tvWarning;
    private User mCurrentUser;
    private ProgressDialog mProgressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent_attendance_view, container, false);

        mCurrentUser = new UserLocalStore(getContext()).getUserDetails();
        donutProgress = view.findViewById(R.id.parentAttendanceProgressBar);
        tvWarning = view.findViewById(R.id.tvWarningParent);


        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();

        Retrofit.Builder builder = new Retrofit.Builder()
                //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                .baseUrl("http://sclmanagement.azurewebsites.net/")    //remote localhost
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);
        Call<Float> call =  client.getAttendancePercentageStudentForParent("1b9cd4a5-d446-47cd-bdf8-feba752e9057"); //change this

        call.enqueue(new Callback<Float>() {
            @Override
            public void onResponse(Call<Float> call, Response<Float> response) {
                mProgressDialog.dismiss();
                float percentage = response.body();
                if((int)percentage>80){
                    tvWarning.setVisibility(View.INVISIBLE);
                }
                donutProgress.setProgress((int)percentage);
            }

            @Override
            public void onFailure(Call<Float> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

}
