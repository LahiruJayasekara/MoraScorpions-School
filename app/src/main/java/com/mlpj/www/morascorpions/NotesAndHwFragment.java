package com.mlpj.www.morascorpions;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotesAndHwFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private NotesHwPagerAdapter mNotesHwPagerAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_and_hw, container, false);

        Bundle args = getArguments();
        final int ternaryId = args.getInt("ternaryId");

        mTabLayout = view.findViewById(R.id.tabLayoutNotesHw);
        mViewPager = view.findViewById(R.id.viewPagerNotesHw);
        //mRecyclerView = view.findViewById(R.id.RecyclerViewHw);

        Retrofit.Builder builder = new Retrofit.Builder()
                //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                .baseUrl(getString(R.string.base_url_azure))    //remote localhost
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);

        Call<ArrayList<HwItem>> call = client.getHomeworksForTeacher(ternaryId);
        call.enqueue(new Callback<ArrayList<HwItem>>() {
            @Override
            public void onResponse(Call<ArrayList<HwItem>> call, Response<ArrayList<HwItem>> response) {
                if(response.body().size() == 0){
                    Toast.makeText(getContext(),"No HomeWorks Available", Toast.LENGTH_LONG).show();
                }
                mNotesHwPagerAdapter = new NotesHwPagerAdapter(getFragmentManager(), ternaryId, response.body());
                mViewPager.setAdapter(mNotesHwPagerAdapter);
                mTabLayout.setupWithViewPager(mViewPager);
            }

            @Override
            public void onFailure(Call<ArrayList<HwItem>> call, Throwable t) {
                Toast.makeText(getContext(), "failed Loading Hw " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }
}
