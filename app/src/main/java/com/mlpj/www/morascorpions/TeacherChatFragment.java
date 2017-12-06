package com.mlpj.www.morascorpions;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TeacherChatFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter chatAdapter;
    List<ChatItem> chatList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_chat, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.rvChatList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        chatList = new ArrayList<>();
        chatList.add(new ChatItem("Asiri", "Father of Hemal", 12));     //implement the code call API
        chatList.add(new ChatItem("Athula", "Father of Lahiru", 13));

        chatAdapter = new ChatAdapter(chatList, getContext());
        recyclerView.setAdapter(chatAdapter);

        return view;
    }

}
