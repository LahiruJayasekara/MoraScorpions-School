package com.mlpj.www.morascorpions;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TeacherChatFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter chatAdapter;
    private List<ChatItem> chatList;
    private Spinner mSpinnerClasses;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_chat, container, false);

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Fetching Data...");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();

        mSpinnerClasses = view.findViewById(R.id.spinnerClasses);

        Retrofit.Builder builder = new Retrofit.Builder()
                //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                .baseUrl(getString(R.string.base_url_azure))    //remote localhost
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);
        Call<ArrayList<ClassRoomItem>> call =  client.getClassListOfTeacher(new UserLocalStore(getContext()).getUserDetails().getP_Id());

        call.enqueue(new Callback<ArrayList<ClassRoomItem>>() {
            @Override
            public void onResponse(Call<ArrayList<ClassRoomItem>> call, Response<ArrayList<ClassRoomItem>> response) {
                mProgressDialog.dismiss();
                List<String> classList = new ArrayList<>();
                for (int i = 0 ; i < response.body().size(); i++){
                    classList.add(response.body().get(i).getClassRoomName());
                }
                ArrayAdapter<String> classListAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,classList);
                classListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerClasses.setAdapter(classListAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<ClassRoomItem>> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(),"Error occured" + t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });



        mSpinnerClasses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadParents(mSpinnerClasses.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        recyclerView = (RecyclerView)view.findViewById(R.id.rvChatList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));




        return view;
    }

    public void loadParents(String className){
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Fetching Data...");
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();

        chatList = new ArrayList<>();

        Retrofit.Builder builder = new Retrofit.Builder()
                //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                .baseUrl(getString(R.string.base_url_azure))    //remote localhost
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);
        Call<ArrayList<ChatItem>> call =  client.getAllParentsOfClass(className);

        call.enqueue(new Callback<ArrayList<ChatItem>>() {
            @Override
            public void onResponse(Call<ArrayList<ChatItem>> call, Response<ArrayList<ChatItem>> response) {
                mProgressDialog.dismiss();
                chatList = response.body();
                chatAdapter = new TeacherChatAdapter(chatList, getContext());
                recyclerView.setAdapter(chatAdapter);

                //Toast.makeText(getContext(),response.body().get(0).getName(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ArrayList<ChatItem>> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(),"Error Loading " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
