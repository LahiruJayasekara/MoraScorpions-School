package com.mlpj.www.morascorpions;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotificationService extends IntentService {

    private DatabaseReference mRootRef;

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        loadMessages();
    }

    private void loadMessages() {
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.child("messages").child("1f1c4de9-b42d-485c-b8f1-de28059aac36")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        MessageItem message = dataSnapshot.getValue(MessageItem.class);
                        Toast.makeText(getBaseContext(), "received" + message.getMessage(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        MessageItem message = dataSnapshot.getValue(MessageItem.class);
                        Toast.makeText(getBaseContext(), "received" + message.getMessage(),Toast.LENGTH_LONG).show();
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
