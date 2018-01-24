package com.mlpj.www.morascorpions.Profile;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.mlpj.www.morascorpions.ApiClient;
import com.mlpj.www.morascorpions.R;
import com.mlpj.www.morascorpions.User;
import com.mlpj.www.morascorpions.UserLocalStore;
import com.squareup.picasso.Picasso;


import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentProfileFragment extends Fragment {
    private UserLocalStore userLocalStore;
    private User currentUser;
    private CircleImageView imgProfilePic;
    private TextView tvProfileStudentName, tvProfileStudentRoleAndClass, tvProfileStudentAdmissionNo, tvProfileAdmissionDate, tvProfileStudentEmail;
    private DonutProgress donutProgress;
    private ProgressDialog mProgressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_profile, container, false);

        userLocalStore = new UserLocalStore(getContext());
        currentUser = userLocalStore.getUserDetails();

        imgProfilePic = view.findViewById(R.id.imgProfileStudentPic);
        tvProfileStudentName = view.findViewById(R.id.tvProfileStudentName);
        tvProfileStudentRoleAndClass = view.findViewById(R.id.tvProfileStudentRoleAndClass);
        tvProfileStudentAdmissionNo = view.findViewById(R.id.tvProfileStudentAdmissionNo);
        tvProfileAdmissionDate = view.findViewById(R.id.tvProfileStudentAdmissionDate);
        tvProfileStudentEmail = view.findViewById(R.id.tvProfileStudentEmail);
        donutProgress = view.findViewById(R.id.donutProgressStudent);

        if(!currentUser.getPicUrl().equals("")){
            Picasso.with(getContext()).load(currentUser.getPicUrl()).into(imgProfilePic);
        }

        tvProfileStudentName.setText(currentUser.getName());
        tvProfileStudentRoleAndClass.setText("Student of Class " + currentUser.getClassRoomName());
        tvProfileStudentAdmissionNo.setText("Admission No- " + currentUser.getAdmissionNumber());
        tvProfileAdmissionDate.setText("Admission Date- " + currentUser.getAdmissionDate());
        tvProfileStudentEmail.setText("Email- " + currentUser.getEmail());

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Logging In...");
        mProgressDialog.setMessage("Please wait for the Authentication!");
        mProgressDialog.show();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_azure))
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);
        Call<Float> call =  client.getAttendancePercentageStudent(currentUser.getP_Id());

        call.enqueue(new Callback<Float>() {
            @Override
            public void onResponse(Call<Float> call, Response<Float> response) {
                mProgressDialog.dismiss();
                float percentage =response.body();
                donutProgress.setProgress((int)percentage);
            }

            @Override
            public void onFailure(Call<Float> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(),"Error " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
