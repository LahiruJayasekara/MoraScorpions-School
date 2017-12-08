package com.mlpj.www.morascorpions;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private int mReceiverId;
    private int mSenderId;
    private String mReceiverName;
    private String mSenderName;
    private UserLocalStore mUserLocalStore;
    private ImageButton mImageBtnSend;
    private EditText mEtMessage;
    private DatabaseReference mRootRef;

    private RecyclerView mRecyclerView;
    private List<MessageItem> mMessageItemsList;
    private RecyclerView.Adapter mMessageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        mReceiverId = intent.getIntExtra("id",-1);
        mReceiverName = intent.getStringExtra("receiverName");
        mUserLocalStore = new UserLocalStore(this);
        mSenderId = mUserLocalStore.getUserDetails().getId();
        mSenderName = mUserLocalStore.getUserDetails().getName();

        Toast.makeText(this,"ttt " + mReceiverId + " " + mSenderId,Toast.LENGTH_LONG).show();

        mRootRef = FirebaseDatabase.getInstance().getReference();

        mImageBtnSend = (ImageButton)findViewById(R.id.imageButtonSend);
        mEtMessage = (EditText) findViewById(R.id.etMessage);
        mImageBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        mRecyclerView = (RecyclerView)findViewById(R.id.RecyclerViewMessages);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMessageItemsList = new ArrayList<>();
        mMessageAdapter = new MessagesAdapter(mMessageItemsList, this);
        mRecyclerView.setAdapter(mMessageAdapter);

        loadMessages();

    }


    public void sendMessage(){
        mImageBtnSend.setClickable(false);
        mImageBtnSend.setBackgroundColor(Color.parseColor("#7f060026"));
        String message = mEtMessage.getText().toString();
        if(!TextUtils.isEmpty(message)){
            String senderRef = "messages/" + Integer.toString(mSenderId) + "/" + Integer.toString(mReceiverId);
            String receiverRef = "messages/" + Integer.toString(mReceiverId) + "/" + Integer.toString(mSenderId);

            String pushId = mRootRef.child("messages").child(Integer.toString(mSenderId)).child(Integer.toString(mReceiverId))
                    .push().getKey();

            Map messageMap = new HashMap();
            messageMap.put("senderName",mSenderName);
            messageMap.put("message",message);
            messageMap.put("seen",false);
            messageMap.put("time", ServerValue.TIMESTAMP);
            messageMap.put("from", mSenderId);

            Map messageUserMap = new HashMap();
            messageUserMap.put(senderRef + "/" + pushId, messageMap);
            messageUserMap.put(receiverRef + "/" + pushId, messageMap);

            mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    if(databaseError != null){
                        Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
                    }else{
                        mEtMessage.setText("");
                    }
                    mImageBtnSend.setClickable(true);
                    mImageBtnSend.setBackgroundColor(Color.parseColor("#ffffff"));
                }
            });
        }

    }

    private void loadMessages() {
        mRootRef.child("messages").child(Integer.toString(mSenderId)).child(Integer.toString(mReceiverId))
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        MessageItem message = dataSnapshot.getValue(MessageItem.class);
                        mMessageItemsList.add(message);
                        mMessageAdapter.notifyDataSetChanged();
                        mRecyclerView.scrollToPosition(mMessageAdapter.getItemCount()-1);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
