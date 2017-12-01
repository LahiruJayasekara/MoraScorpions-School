package com.mlpj.www.morascorpions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter chatAdapter;
    List<ChatItem> chatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        recyclerView = (RecyclerView)findViewById(R.id.rvChatList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        chatList = new ArrayList<>();
        chatList.add(new ChatItem("Asiri", "Father of Hemal", 12));
        chatList.add(new ChatItem("Athula", "Father of Lahiru", 13));

        chatAdapter = new ChatAdapter(chatList, this);
        recyclerView.setAdapter(chatAdapter);


    }
}
