package com.mlpj.www.morascorpions;


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

public class HwFragment extends Fragment {
    private RecyclerView.Adapter mHwAdapter;
    private RecyclerView mRecyclerView;
    private List<HwItem> mHwItems;
    private FloatingActionButton mFloatingActionButtonUploadHw;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hw, container, false);

        Bundle args = getArguments();
        final String classId = args.getString("class");     //use this to get the set of Homeworks along with teacherId

        mHwItems = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.RecyclerViewHw);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        //code for getting the related Hw set from Database


        mHwItems.add(new HwItem(1,1,"2017/05/12","2017/12/12","Algo","algo.pdf", "hcdhvhdc","2017/2/8","2017/8/8"));
        mHwItems.add(new HwItem(1,1,"2017/06/15","2017/1/2","C","c.pdf", "hcdhvhdc","2017/2/8","2017/8/8"));
        mHwItems.add(new HwItem(1,1,"2017/06/18","2017/2/2","C#","csharp.pdf", "hcdhvhdc","2017/2/8","2017/8/8"));
        mHwItems.add(new HwItem(1,1,"2017/07/02","2017/3/2","Java","java.pdf", "hcdhvhdc","2017/2/8","2017/8/8"));

        FragmentManager fragmentManager = getFragmentManager();
        mHwAdapter = new HwAdapter(mHwItems, getContext());
        mRecyclerView.setAdapter(mHwAdapter);



        mFloatingActionButtonUploadHw = view.findViewById(R.id.floatingActionButtonHwUpload);
        mFloatingActionButtonUploadHw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UploadNotesActivity.class);
                intent.putExtra("classId",classId);
                startActivity(intent);
            }
        });


        return view;
    }

}
