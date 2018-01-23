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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private String mReceiverId;
    private String mSenderId;
    private String mReceiverName;
    private String mSenderName;
    private String mSentImage;
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
        mReceiverId = intent.getStringExtra("id");
        mReceiverName = intent.getStringExtra("receiverName");
        mUserLocalStore = new UserLocalStore(this);
        mSenderId = mUserLocalStore.getUserDetails().getP_Id();
        mSenderName = mUserLocalStore.getUserDetails().getName();
        mSentImage = mUserLocalStore.getUserDetails().getPicUrl();

        //Toast.makeText(this,"ttt " + mReceiverId + " " + mSenderId,Toast.LENGTH_LONG).show();

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
        mMessageAdapter = new MessagesAdapter(mMessageItemsList, this, mReceiverName);
        mRecyclerView.setAdapter(mMessageAdapter);

        loadMessages();

    }


    public void sendMessage(){
        mImageBtnSend.setClickable(false);
        mImageBtnSend.setBackgroundColor(Color.parseColor("#7f060026"));
        String message = mEtMessage.getText().toString();
        if(!TextUtils.isEmpty(message)){
            String senderRef = "messages/" + mSenderId + "/" + mReceiverId;
            String receiverRef = "messages/" + mReceiverId + "/" + mSenderId;
            //String notificationSenderRef = "notifications/" + "chat/" + mSenderId + "/" + mReceiverId;
            String notificationReceiverRef = "notifications/" + "chat/" + mReceiverId + "/" + mSenderId;


            String pushIdSender = mRootRef.child("messages").child(mSenderId).child(mReceiverId)
                    .push().getKey();

            String pushIdReceiver = mRootRef.child("messages").child(mReceiverId).child(mSenderId)
                    .push().getKey();

            Map messageMap = new HashMap();
            //messageMap.put("senderName",mSenderName);
            messageMap.put("SentImage",mSentImage);
            messageMap.put("body",message);
            messageMap.put("seen",false);
            messageMap.put("timestamp", ServerValue.TIMESTAMP);
            messageMap.put("sentBy", mSenderId);
            messageMap.put("sentName", mSenderName);
            messageMap.put("senderMsgKey", pushIdReceiver);

            Map messageMapReceiver = new HashMap();
            //messageMap.put("senderName",mSenderName);
            messageMapReceiver.put("SentImage",mSentImage);
            messageMapReceiver.put("body",message);
            messageMapReceiver.put("seen",false);
            messageMapReceiver.put("timestamp", ServerValue.TIMESTAMP);
            messageMapReceiver.put("sentBy", mSenderId);
            messageMapReceiver.put("sentName", mSenderName);
            messageMapReceiver.put("senderMsgKey", pushIdSender);

            Map messageUserMap = new HashMap();
            messageUserMap.put(senderRef + "/" + pushIdSender, messageMap);
            messageUserMap.put(receiverRef + "/" + pushIdReceiver, messageMapReceiver);
            //messageUserMap.put(notificationSenderRef, messageMap);
            messageUserMap.put(notificationReceiverRef, messageMapReceiver);

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
/*
            String notificationSenderRef = "notifications/" + mSenderId + "/" + mReceiverId;
            String notificationReceiverRef = "notifications/" + mReceiverId + "/" + mSenderId;



            Map notificationMap = new HashMap();
            //messageMap.put("senderName",mSenderName);
            notificationMap.put("body",message);
            notificationMap.put("seen",false);
            notificationMap.put("timestamp", ServerValue.TIMESTAMP);
            notificationMap.put("sentBy", mSenderId);
            notificationMap.put("senderMsgKey", pushIdReceiver);

            Map messageMapReceiver = new HashMap();
            //messageMap.put("senderName",mSenderName);
            messageMapReceiver.put("body",message);
            messageMapReceiver.put("seen",false);
            messageMapReceiver.put("timestamp", ServerValue.TIMESTAMP);
            messageMapReceiver.put("sentBy", mSenderId);
            messageMapReceiver.put("senderMsgKey", pushIdSender);

            Map messageUserMap = new HashMap();
            messageUserMap.put(senderRef + "/" + pushIdSender, messageMap);
            messageUserMap.put(receiverRef + "/" + pushIdReceiver, messageMapReceiver);

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
            */
        }
    }

    private void loadMessages() {
        mRootRef.child("messages").child(mSenderId).child(mReceiverId)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        MessageItem message = dataSnapshot.getValue(MessageItem.class);
                        //Toast.makeText(getApplicationContext(),message.getBody().toString(),Toast.LENGTH_LONG).show();
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
        mRootRef.child("notifications").child("chat").child(mSenderId).child(mReceiverId).child("seen").setValue(true);
    }
}
