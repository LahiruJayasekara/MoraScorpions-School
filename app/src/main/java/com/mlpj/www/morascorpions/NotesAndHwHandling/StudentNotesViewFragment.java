package com.mlpj.www.morascorpions.NotesAndHwHandling;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.mlpj.www.morascorpions.ApiClient;
import com.mlpj.www.morascorpions.R;
import com.mlpj.www.morascorpions.Syllabus.SubjectItem;
import com.mlpj.www.morascorpions.User;
import com.mlpj.www.morascorpions.UserLocalStore;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentNotesViewFragment extends Fragment {

    private Spinner mSpinnerSubjectsNotes;
    private RecyclerView mRecyclerViewNotes;
    private User mCurrentUser;
    private List<NoteItem> mNoteItems;
    private ProgressDialog mProgressDialog;
    private RecyclerView.Adapter notesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_notes_view, container, false);

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Fetching Data...");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();

        mSpinnerSubjectsNotes = view.findViewById(R.id.spinnerSubjectsOfAStudentNotes);
        mRecyclerViewNotes = view.findViewById(R.id.recyclerViewStudentNotes);

        mCurrentUser = new UserLocalStore(getContext()).getUserDetails();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_azure))
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
                mSpinnerSubjectsNotes.setAdapter(subjectListAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<SubjectItem>> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        mSpinnerSubjectsNotes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadNotes(mSpinnerSubjectsNotes.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mRecyclerViewNotes.setHasFixedSize(true);
        mRecyclerViewNotes.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }

    public void loadNotes(String subjectName) {
        mProgressDialog.setTitle("Fetching Data...");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();

        mNoteItems = new ArrayList<>();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_azure))
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);

        Call<ArrayList<NoteItem>> call = client.getNotesForStudent(mCurrentUser.getClassRoomName(),subjectName);

        call.enqueue(new Callback<ArrayList<NoteItem>>() {
            @Override
            public void onResponse(Call<ArrayList<NoteItem>> call, Response<ArrayList<NoteItem>> response) {
                mProgressDialog.dismiss();
                if(response.body().size() != 0){
                    mNoteItems = response.body();
                    FragmentManager fragmentManager = getFragmentManager();
                    notesAdapter = new NotesAdapter(mNoteItems, getContext(), fragmentManager);
                    mRecyclerViewNotes.setAdapter(notesAdapter);
                }else{
                    Toast.makeText(getContext(), "No Notes available", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<NoteItem>> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
