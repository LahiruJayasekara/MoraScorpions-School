package com.mlpj.www.morascorpions;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HwFragment extends Fragment {
    private RecyclerView.Adapter mHwAdapter;
    private RecyclerView mRecyclerView;
    private List<HwItem> mHwItems;
    private FloatingActionButton mFloatingActionButtonUploadHw;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hw, container, false);

        Bundle args = getArguments();
        final int ternaryId = args.getInt("ternaryId");     //use this to upload hw

        mHwItems = new ArrayList<>();
        HwListSerializable hwListSerializable = (HwListSerializable)args.getSerializable("hwList");
        mHwItems = hwListSerializable.getHwList();
        mRecyclerView = view.findViewById(R.id.RecyclerViewHw);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        FragmentManager fragmentManager = getFragmentManager();
        mHwAdapter = new HwAdapter(mHwItems, getContext());
        mRecyclerView.setAdapter(mHwAdapter);



        mFloatingActionButtonUploadHw = view.findViewById(R.id.floatingActionButtonHwUpload);
        mFloatingActionButtonUploadHw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UploadNotesActivity.class);
                intent.putExtra("ternaryId",ternaryId);
                startActivity(intent);
            }
        });


        return view;
    }

}
