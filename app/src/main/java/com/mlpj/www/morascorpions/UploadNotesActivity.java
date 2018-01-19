package com.mlpj.www.morascorpions;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.PriorityQueue;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadNotesActivity extends AppCompatActivity {

    private static final int FILE_REQUEST = 777;
    private EditText mEtNoteTitle, mEtNoteDescription;
    private TextView mTvNoteFileName;
    private Button mBtnOnAddNoteFile, mBtnOnUploadNote;
    private CheckBox mCheckBoxNoteVisible;
    private LinearLayout mLinearLayoutNote;
    private FileUtils fileUtils;
    private File originalFile;
    private final static int My_PERMISSIONS_REQUEST = 444;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notes);

        int ternaryId = getIntent().getIntExtra("ternaryId", -1);     //use this when uploading the note

        mEtNoteTitle = findViewById(R.id.etNoteTitle);
        mEtNoteDescription = findViewById(R.id.etNoteDescription);
        mTvNoteFileName = findViewById(R.id.tvNoteFileName2);
        mBtnOnAddNoteFile = findViewById(R.id.btnAddNoteFile);
        mBtnOnUploadNote = findViewById(R.id.btnOnUploadNote);
        mCheckBoxNoteVisible = findViewById(R.id.checkBoxVisibility);
        mLinearLayoutNote = findViewById(R.id.linearLayoutNote);
        mLinearLayoutNote.setVisibility(View.INVISIBLE);

        fileUtils = new FileUtils(this);

        mBtnOnAddNoteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, FILE_REQUEST);
            }
        });

        mBtnOnUploadNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile(originalFile);
            }
        });

        if(ContextCompat.checkSelfPermission(UploadNotesActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(UploadNotesActivity.this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},My_PERMISSIONS_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(getApplicationContext(),data.getData().getPath(),Toast.LENGTH_LONG).show();
        if(requestCode == FILE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            String path = fileUtils.getRealPathFromUri(uri);
            originalFile = new File(path);
            //Toast.makeText(getApplicationContext(), originalFile.getName(), Toast.LENGTH_LONG).show();
            mTvNoteFileName.setText(originalFile.getName());
            mLinearLayoutNote.setVisibility(View.VISIBLE);
        }
    }

    public void uploadFile(File originalFile){

        Calendar calendar = Calendar.getInstance();
        String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        String month = Integer.toString(calendar.get(Calendar.MONTH)+1);
        String year = Integer.toString(calendar.get(Calendar.YEAR));
        String date = day+" "+month+" "+year;
        Toast.makeText(getApplicationContext(),day+" "+month+" "+year,Toast.LENGTH_LONG).show();
        String noteTitle = mEtNoteTitle.getText().toString();
        String noteDescription = mEtNoteDescription.getText().toString();
        String noteFileName = originalFile.getName();
        boolean visible = mCheckBoxNoteVisible.isChecked();
        NoteItem newNote = new NoteItem(date,noteTitle,noteDescription,noteFileName,visible);

        try {
            //RequestBody noteTitleParameter = RequestBody.create(MultipartBody.FORM, noteTitle);

            //Toast.makeText(getApplicationContext(),originalFile.getName(),Toast.LENGTH_LONG).show();
            RequestBody filePart = RequestBody.create(
                    MediaType.parse("multipart/form-data"),originalFile
            );

            MultipartBody.Part file = MultipartBody.Part.createFormData("file",originalFile.getName(),filePart);

            Retrofit.Builder builder = new Retrofit.Builder()
                    //.baseUrl(getString(R.string.base_url_localhost))       //localhost
                    .baseUrl(getString(R.string.base_url_remote_localhost))    //remote localhost
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();

            ApiClient client = retrofit.create(ApiClient.class);
            Call<ResponseBody> call = client.uploadFile(noteTitle, file);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(getApplicationContext(),"success " + response.body().toString(),Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"failed" + t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent("NOTE");
        intent.putExtra("NEW_NOTE",newNote);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }



}
