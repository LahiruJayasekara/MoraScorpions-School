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
        mRootRef.child("messages").child("8121b123-2cee-4268-9b7f-ebb01888c8c6").child("0f436fbe-0129-48ff-9cc8-2fc4e608faad")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        MessageItem message = dataSnapshot.getValue(MessageItem.class);
                        Toast.makeText(getBaseContext(), "received" + message.getBody(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        MessageItem message = dataSnapshot.getValue(MessageItem.class);
                        Toast.makeText(getBaseContext(), "received" + message.getBody(),Toast.LENGTH_LONG).show();
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
