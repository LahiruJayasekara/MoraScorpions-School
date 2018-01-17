package com.mlpj.www.morascorpions;


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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


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

    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        Bundle args = getArguments();
        int noteId = args.getInt("noteId", 0);  //use to get the comment details from the api method

        mCommentItems = new ArrayList<>();
        mRecyclerViewComments = view.findViewById(R.id.recyclerViewComments);
        mRecyclerViewComments.setHasFixedSize(false);
        mRecyclerViewComments.setLayoutManager(new LinearLayoutManager(getContext()));

        //code to get the comment details via noteId from api method






        mCommentItems.add(new CommentItem(null,"Madhawa", "Hi there","2017-05-02 at 3.00 pm"));
        mCommentItems.add(new CommentItem(null,"Ruchira", "Hello gf kjhsdf kjhsdfb kjhdb hbdch c","2017-06-05 at 5.00 pm"));
        mCommentItems.add(new CommentItem(null,"Nuwan", "Hi there same here there by the way how are you","2017-05-31 at 3.30 am"));

        mCommentsAdapter = new CommentsAdapter(mCommentItems, getContext());
        mRecyclerViewComments.setAdapter(mCommentsAdapter);

        userLocalStore = new UserLocalStore(getContext());
        mEtComment = view.findViewById(R.id.etComment);
        mImageButtonSendComment = view.findViewById(R.id.imageButtonSendComment);
        mImageButtonSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = null;  //take this from shared preferences
                String commentedPersonName = userLocalStore.getUserDetails().getName();
                String comment = mEtComment.getText().toString();

                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm a");//dd/MM/yyyy
                Date now = new Date();
                String strDate = sdfDate.format(now);

                CommentItem newComment = new CommentItem(url,commentedPersonName,comment,strDate);
                mCommentItems.add(newComment);
                mCommentsAdapter.notifyDataSetChanged();
                mEtComment.setText("");
                //code to update database with noteId
                //code to set a notification probably updating the notification database table
            }
        });

        this.getDialog().setTitle("Comments...");

        return view;
    }

}
