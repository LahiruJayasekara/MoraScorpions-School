package com.mlpj.www.morascorpions;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ParentChatFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter chatAdapter;
    private List<ChatItem> chatList;
    private ProgressDialog mProgressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent_chat, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.rvChatListParent);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadParents();

        return view;
    }

    public void loadParents(){
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Fetching Data...");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();

        chatList = new ArrayList<>();

        Retrofit.Builder builder = new Retrofit.Builder()
                //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                .baseUrl("http://sclmanagement.azurewebsites.net/")    //remote localhost
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);
        Call<ArrayList<ChatItem>> call =  client.getAllTeachersOfStudent(new UserLocalStore(getContext()).getUserDetails().getP_Id());

        call.enqueue(new Callback<ArrayList<ChatItem>>() {
            @Override
            public void onResponse(Call<ArrayList<ChatItem>> call, Response<ArrayList<ChatItem>> response) {
                mProgressDialog.dismiss();
                chatList = response.body();
                chatAdapter = new ParentChatAdapter(chatList, getContext());
                recyclerView.setAdapter(chatAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<ChatItem>> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(),"Error Loading " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}
