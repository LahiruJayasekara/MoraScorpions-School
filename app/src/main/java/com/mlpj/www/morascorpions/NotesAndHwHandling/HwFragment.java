package com.mlpj.www.morascorpions.NotesAndHwHandling;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlpj.www.morascorpions.R;

import java.util.ArrayList;
import java.util.List;

public class HwFragment extends Fragment {
    private RecyclerView.Adapter mHwAdapter;
    private RecyclerView mRecyclerView;
    private List<HwItem> mHwItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hw, container, false);

        Bundle args = getArguments();

        mHwItems = new ArrayList<>();
        HwListSerializable hwListSerializable = (HwListSerializable)args.getSerializable("hwList");

        mHwItems = hwListSerializable.getHwList();
        mRecyclerView = view.findViewById(R.id.RecyclerViewHw);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mHwAdapter = new HwAdapter(mHwItems, getContext());
        mRecyclerView.setAdapter(mHwAdapter);

        return view;
    }

}
