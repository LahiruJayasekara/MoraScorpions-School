package com.mlpj.www.morascorpions.Comment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mlpj.www.morascorpions.ApiClient;
import com.mlpj.www.morascorpions.R;
import com.mlpj.www.morascorpions.UserLocalStore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends DialogFragment {

    private List<CommentItem> mCommentItems;
    private RecyclerView mRecyclerViewComments;
    private RecyclerView.Adapter mCommentsAdapter;
    private EditText mEtComment;
    private ImageButton mImageButtonSendComment;
    private UserLocalStore userLocalStore;
    private ProgressDialog mProgressDialog;
    private int noteId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        Bundle args = getArguments();
        noteId = args.getInt("noteId", 0);  //use to get the comment details from the api method
        mCommentItems = new ArrayList<>();
        mRecyclerViewComments = view.findViewById(R.id.recyclerViewComments);
        mRecyclerViewComments.setHasFixedSize(false);
        mRecyclerViewComments.setLayoutManager(new LinearLayoutManager(getContext()));

        mProgressDialog = new ProgressDialog(getContext());

        loadComments();

        userLocalStore = new UserLocalStore(getContext());
        mEtComment = view.findViewById(R.id.etComment);
        mImageButtonSendComment = view.findViewById(R.id.imageButtonSendComment);
        mImageButtonSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEtComment.getText().toString().equals("")) {
                    mEtComment.setError("Can't be empty");
                } else {
                    mProgressDialog.setTitle("Submitting Comment...");
                    mProgressDialog.setMessage("Please wait...!");
                    mProgressDialog.show();
                    String url = null;  //take this from shared preferences
                    String commentedPersonName = userLocalStore.getUserDetails().getName();
                    String comment = mEtComment.getText().toString();

                    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
                    Date now = new Date();
                    String strDate = sdfDate.format(now);

                    CommentItem newComment = new CommentItem(noteId, comment, userLocalStore.getUserDetails().getP_Id());


                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl(getString(R.string.base_url_azure))
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit = builder.build();

                    ApiClient client = retrofit.create(ApiClient.class);
                    Call<Void> call = client.submitComment(newComment);

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            mProgressDialog.dismiss();
                            Toast.makeText(getContext(), "Successfully Commented", Toast.LENGTH_LONG).show();
                            loadComments();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            mProgressDialog.dismiss();
                            Toast.makeText(getContext(), "Connection Error" + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    mEtComment.setText("");
                }
            }
        });
        this.getDialog().setTitle("Comments...");

        return view;

    }

    public void loadComments() {
        mProgressDialog.setTitle("Fetching Comments...");
        mProgressDialog.setMessage("Please wait...!");
        mProgressDialog.show();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_azure))
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);
        Call<ArrayList<CommentItem>> call = client.getComments(noteId);

        call.enqueue(new Callback<ArrayList<CommentItem>>() {
            @Override
            public void onResponse(Call<ArrayList<CommentItem>> call, Response<ArrayList<CommentItem>> response) {
                mProgressDialog.dismiss();
                if (response.body().size() != 0) {
                    mCommentItems = response.body();
                    mCommentsAdapter = new CommentsAdapter(mCommentItems, getContext());
                    mRecyclerViewComments.setAdapter(mCommentsAdapter);
                } else {
                    Toast.makeText(getContext(), "No Comments yet", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CommentItem>> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(getContext(), "Connection Error" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
