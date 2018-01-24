package com.mlpj.www.morascorpions.NoticeHandling;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mlpj.www.morascorpions.ApiClient;
import com.mlpj.www.morascorpions.R;
import com.mlpj.www.morascorpions.User;
import com.mlpj.www.morascorpions.UserLocalStore;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddNoticeActivity extends AppCompatActivity {

    private Calendar calendar;
    private int year,month, day;
    private ProgressDialog mProgressDialog;
    private User mCurrentUser;
    private EditText mEtNoticeTopic, mEtNoticeContent;
    private TextView mTvNoticeEndDate;
    private CheckBox mCheckBoxTeachers, mCheckBoxStudents, mCheckBoxParents;
    private Button mOnSelectDate, mOnAddNotice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

        mCurrentUser = new UserLocalStore(getApplicationContext()).getUserDetails();
        mEtNoticeTopic = findViewById(R.id.etNoticeTopic);
        mEtNoticeContent = findViewById(R.id.etNoticeContent);
        mCheckBoxTeachers = findViewById(R.id.checkBoxSetTeachers);
        mCheckBoxStudents = findViewById(R.id.checkBoxSetStudents);
        mCheckBoxParents = findViewById(R.id.checkBoxSetParents);
        mOnSelectDate = findViewById(R.id.onSelectDate);
        mOnAddNotice = findViewById(R.id.onAddNotice);
        mTvNoticeEndDate = findViewById(R.id.tvNoticeEndDate);


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        mTvNoticeEndDate.setText(year + "-" + (month + 1) + "-" + day);

        mOnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNoticeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        year = datePicker.getYear();
                        month = datePicker.getMonth();
                        day = datePicker.getDayOfMonth();

                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
                mTvNoticeEndDate.setText(year + "-" + (month + 1) + "-" + day);
            }
        });

        mOnAddNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEtNoticeTopic.getText().toString().equals("")){
                    mEtNoticeTopic.setError("Can not be Empty!!!");
                }else if(mEtNoticeContent.getText().toString().equals("")){
                    mEtNoticeContent.setError("Can not be Empty!!!");
                }else{

                    NoticeItem newNotice = new NoticeItem(
                            mEtNoticeTopic.getText().toString(),
                            mEtNoticeContent.getText().toString(),
                            year + "-" + (month + 1) + "-" + day,
                            mCheckBoxStudents.isChecked(),
                            mCheckBoxTeachers.isChecked(),
                            mCheckBoxParents.isChecked()
                    );

                    mProgressDialog = new ProgressDialog(AddNoticeActivity.this);
                    mProgressDialog.setTitle("Loading. . .");
                    mProgressDialog.setMessage("Please wait...!");
                    mProgressDialog.show();
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl(getString(R.string.base_url_azure))
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit = builder.build();

                    ApiClient client = retrofit.create(ApiClient.class);
                    Call<Void> call =  client.addNotice(newNotice);

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Notice Added Successfully",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Error Occured " + t.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
