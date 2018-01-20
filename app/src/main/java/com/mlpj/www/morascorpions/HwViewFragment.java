package com.mlpj.www.morascorpions;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HwViewFragment extends Fragment {

    private RecyclerView.Adapter mHwAdapter;
    private RecyclerView mRecyclerView;
    private List<HwItem> mHwItems;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hw_view, container, false);

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();

        mHwItems = new ArrayList<>();

        mRecyclerView = view.findViewById(R.id.RecyclerViewHwView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final User currentUser = new UserLocalStore(getContext()).getUserDetails();

        if(currentUser.getRoleName().equals("Parent")){
            Retrofit.Builder builder = new Retrofit.Builder()
                    //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                    .baseUrl(getString(R.string.base_url_azure))    //remote localhost
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();

            ApiClient client = retrofit.create(ApiClient.class);

            Call<ArrayList<HwItem>> call = client.getHomeworksForParent(currentUser.getP_Id());
            call.enqueue(new Callback<ArrayList<HwItem>>() {
                @Override
                public void onResponse(Call<ArrayList<HwItem>> call, Response<ArrayList<HwItem>> response) {
                    mHwItems =response.body();
                    if(mHwItems.size() == 0){
                        Toast.makeText(getContext(),"No HomeWorks Available", Toast.LENGTH_LONG).show();
                    }
                    mHwAdapter = new HwAdapter(mHwItems, getContext());
                    mRecyclerView.setAdapter(mHwAdapter);
                    mProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ArrayList<HwItem>> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getContext(), "failed Loading Hw " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

        else if(currentUser.getRoleName().equals("Student")){
            Retrofit.Builder builder = new Retrofit.Builder()
                    //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                    .baseUrl(getString(R.string.base_url_azure))    //remote localhost
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();

            ApiClient client = retrofit.create(ApiClient.class);

            Call<ArrayList<HwItem>> call = client.getHomeworksForStudent(currentUser.getP_Id());
            call.enqueue(new Callback<ArrayList<HwItem>>() {
                @Override
                public void onResponse(Call<ArrayList<HwItem>> call, Response<ArrayList<HwItem>> response) {
                    mProgressDialog.dismiss();
                    mHwItems =response.body();
                    if(mHwItems.size() == 0){
                        Toast.makeText(getContext(),"No HomeWorks Available", Toast.LENGTH_LONG).show();
                    }
                    mHwAdapter = new HwAdapter(mHwItems, getContext());
                    mRecyclerView.setAdapter(mHwAdapter);
                }

                @Override
                public void onFailure(Call<ArrayList<HwItem>> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getContext(), "failed Loading Hw " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

        return view;
    }

}
