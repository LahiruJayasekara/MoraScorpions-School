package com.mlpj.www.morascorpions.ComplainHandling;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mlpj.www.morascorpions.ApiClient;
import com.mlpj.www.morascorpions.CommentsAdapter;
import com.mlpj.www.morascorpions.NoteItem;
import com.mlpj.www.morascorpions.NotesAdapter;
import com.mlpj.www.morascorpions.R;
import com.mlpj.www.morascorpions.UploadNotesActivity;
import com.mlpj.www.morascorpions.User;
import com.mlpj.www.morascorpions.UserLocalStore;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParentComplainViewFragment extends Fragment {

    private RecyclerView.Adapter mComplainAdapter;
    private RecyclerView mRecyclerView;
    private List<ComplainItem> mComplainItems;
    private FloatingActionButton mFloatingActionButtonMakeComplain;
    private User mCurrentUser;
    private ProgressDialog mProgressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent_complain_view, container, false);


        mComplainItems = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.complainRecyclerView);
        mFloatingActionButtonMakeComplain  = view.findViewById(R.id.floatingActionButtonComplains);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCurrentUser = new UserLocalStore(getContext()).getUserDetails();

        mProgressDialog = new ProgressDialog(getContext());
        loadComplains();

        mFloatingActionButtonMakeComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MakeComplainActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadComplains();

    }

    public void loadComplains(){
        mProgressDialog.setTitle("Logging In...");
        mProgressDialog.setMessage("Please wait for the Authentication!");
        mProgressDialog.show();

        Retrofit.Builder builder = new Retrofit.Builder()
                //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                .baseUrl(getString(R.string.base_url_azure))    //remote localhost
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);
        Call<ArrayList<ComplainItem>> call =  client.getComplainsForParent(mCurrentUser.getP_Id());

        call.enqueue(new Callback<ArrayList<ComplainItem>>() {
            @Override
            public void onResponse(Call<ArrayList<ComplainItem>> call, Response<ArrayList<ComplainItem>> response) {
                mProgressDialog.dismiss();
                if(response.body()!=null && response.body().size()!=0){

                    mComplainItems = response.body();
                    mComplainAdapter = new ParentComplainAdapter(mComplainItems,getContext());
                    mRecyclerView.setAdapter(mComplainAdapter);
                }else{
                    Toast.makeText(getContext(),"Error Loading Data ", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<ComplainItem>> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(),"Error Loading data " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
