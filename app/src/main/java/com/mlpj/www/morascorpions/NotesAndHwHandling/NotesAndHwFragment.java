package com.mlpj.www.morascorpions.NotesAndHwHandling;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mlpj.www.morascorpions.ApiClient;
import com.mlpj.www.morascorpions.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotesAndHwFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private NotesHwPagerAdapter mNotesHwPagerAdapter;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_and_hw, container, false);

        Bundle args = getArguments();
        final int ternaryId = args.getInt("ternaryId");

        mTabLayout = view.findViewById(R.id.tabLayoutNotesHw);
        mViewPager = view.findViewById(R.id.viewPagerNotesHw);

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Loading...");
        mProgressDialog.setMessage("Please wait...!");
        mProgressDialog.show();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_azure))
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        final ApiClient client = retrofit.create(ApiClient.class);

        Call<ArrayList<HwItem>> call = client.getHomeworksForTeacher(ternaryId);
        call.enqueue(new Callback<ArrayList<HwItem>>() {
            @Override
            public void onResponse(Call<ArrayList<HwItem>> call, Response<ArrayList<HwItem>> response) {
                if(response.body().size() == 0){
                    mProgressDialog.dismiss();
                    Toast.makeText(getContext(),"No HomeWorks Available", Toast.LENGTH_LONG).show();
                }else{
                    final List<HwItem> hwItems = response.body();
                    Call<ArrayList<NoteItem>> call2 = client.getNotesForTeacher(ternaryId);
                    call2.enqueue(new Callback<ArrayList<NoteItem>>() {
                        @Override
                        public void onResponse(Call<ArrayList<NoteItem>> call, Response<ArrayList<NoteItem>> response) {
                            mProgressDialog.dismiss();
                            if(response.body().size() == 0){
                                Toast.makeText(getContext(),"No Notes Available", Toast.LENGTH_LONG).show();
                            }
                            mNotesHwPagerAdapter = new NotesHwPagerAdapter(getFragmentManager(), hwItems, response.body());
                            mViewPager.setAdapter(mNotesHwPagerAdapter);
                            mTabLayout.setupWithViewPager(mViewPager);
                        }

                        @Override
                        public void onFailure(Call<ArrayList<NoteItem>> call, Throwable t) {
                            mProgressDialog.dismiss();
                            Toast.makeText(getContext(), "failed Loading Notes " + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<ArrayList<HwItem>> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(), "failed Loading Hw " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }
}
