package com.mlpj.www.morascorpions.Syllabus;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.mlpj.www.morascorpions.ApiClient;
import com.mlpj.www.morascorpions.R;
import com.mlpj.www.morascorpions.User;
import com.mlpj.www.morascorpions.UserLocalStore;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SyllabusViewFragment extends Fragment {

    private Spinner mSpinnerSubjects;
    private RecyclerView mRecyclerViewSyllabus;
    private User mCurrentUser;
    private List<SyllabusOutLineItem> mSyllabusOutLineList;
    private ProgressDialog mProgressDialog;
    private RecyclerView.Adapter syllabusAdapter;
    private DonutProgress donutProgress;
    private TextView syllabusProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_syllabus_view, container, false);

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Fetching Data...");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();

        mSpinnerSubjects = view.findViewById(R.id.spinnerSubjectsOfAStudent);
        mRecyclerViewSyllabus = view.findViewById(R.id.recyclerViewSyllabus);
        donutProgress = view.findViewById(R.id.donutProgressSyllabus);
        syllabusProgress = view.findViewById(R.id.tvSyllabusProgress);

        mCurrentUser = new UserLocalStore(getContext()).getUserDetails();

        Retrofit.Builder builder = new Retrofit.Builder()
                //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                .baseUrl(getString(R.string.base_url_azure))    //remote localhost
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);
        Call<ArrayList<SubjectItem>> call = client.getSubjectsOfAStudent(mCurrentUser.getP_Id());

        call.enqueue(new Callback<ArrayList<SubjectItem>>() {
            @Override
            public void onResponse(Call<ArrayList<SubjectItem>> call, Response<ArrayList<SubjectItem>> response) {
                mProgressDialog.dismiss();
                List<String> subjectNameList = new ArrayList<>();
                List<Integer> subjectIdList = new ArrayList<>();
                for (int i = 0; i < response.body().size(); i++) {
                    subjectIdList.add(response.body().get(i).getSubjectId());
                    subjectNameList.add(response.body().get(i).getSubjectName());
                }
                ArrayAdapter<String> subjectListAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, subjectNameList);
                subjectListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerSubjects.setAdapter(subjectListAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<SubjectItem>> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        mSpinnerSubjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadSyllabus(mSpinnerSubjects.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mRecyclerViewSyllabus.setHasFixedSize(true);
        mRecyclerViewSyllabus.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }

    public void loadSyllabus(String subjectName) {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Fetching Data...");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();

        mSyllabusOutLineList = new ArrayList<>();

        Retrofit.Builder builder = new Retrofit.Builder()
                //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                .baseUrl(getString(R.string.base_url_azure))    //remote localhost
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);

        if(mCurrentUser.getRoleName().equals("Student")){
            Call<ArrayList<SyllabusOutLineItem>> call = client.getSyllabusForStudent(subjectName, mCurrentUser.getClassRoomName());

            call.enqueue(new Callback<ArrayList<SyllabusOutLineItem>>() {
                @Override
                public void onResponse(Call<ArrayList<SyllabusOutLineItem>> call, Response<ArrayList<SyllabusOutLineItem>> response) {
                    mProgressDialog.dismiss();
                    if(response.body().size() != 0){
                        mSyllabusOutLineList = response.body();
                        syllabusAdapter= new SyllabusOutLineAdapter(mSyllabusOutLineList);
                        mRecyclerViewSyllabus.setAdapter(syllabusAdapter);
                        int count = 0;
                        for (int i = 0; i < mSyllabusOutLineList.size(); i++){
                            if(mSyllabusOutLineList.get(i).isDoneOrNot()){
                                count++;
                            }
                        }
                        int percentage = count*100/mSyllabusOutLineList.size();
                        donutProgress.setProgress(percentage);


                    }else {
                        Toast.makeText(getContext(), "No Outline specified", Toast.LENGTH_LONG).show();
                        mSyllabusOutLineList = new ArrayList<>();
                        syllabusAdapter= new SyllabusOutLineAdapter(mSyllabusOutLineList);
                        mRecyclerViewSyllabus.setAdapter(syllabusAdapter);
                        donutProgress.setVisibility(View.INVISIBLE);
                        syllabusProgress.setVisibility(View.INVISIBLE);
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<SyllabusOutLineItem>> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }else if(mCurrentUser.getRoleName().equals("Parent")){
            Call<ArrayList<SyllabusOutLineItem>> call = client.getSyllabusForParent(mCurrentUser.getP_Id(),subjectName);

            call.enqueue(new Callback<ArrayList<SyllabusOutLineItem>>() {
                @Override
                public void onResponse(Call<ArrayList<SyllabusOutLineItem>> call, Response<ArrayList<SyllabusOutLineItem>> response) {
                    mProgressDialog.dismiss();
                    if(response.body().size() != 0){
                        mSyllabusOutLineList = response.body();
                        syllabusAdapter= new SyllabusOutLineAdapter(mSyllabusOutLineList);
                        mRecyclerViewSyllabus.setAdapter(syllabusAdapter);
                        int count = 0;
                        for (int i = 0; i < mSyllabusOutLineList.size(); i++){
                            if(mSyllabusOutLineList.get(i).isDoneOrNot()){
                                count++;
                            }
                        }
                        int percentage = count*100/mSyllabusOutLineList.size();
                        donutProgress.setProgress(percentage);

                        Toast.makeText(getContext(), count + " " + percentage + " " + mSyllabusOutLineList.size(), Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(getContext(), "No Outline specified", Toast.LENGTH_LONG).show();
                        mSyllabusOutLineList = new ArrayList<>();
                        syllabusAdapter= new SyllabusOutLineAdapter(mSyllabusOutLineList);
                        mRecyclerViewSyllabus.setAdapter(syllabusAdapter);
                        donutProgress.setVisibility(View.INVISIBLE);
                        syllabusProgress.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<SyllabusOutLineItem>> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
