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
        //implement the code call API

        if(new UserLocalStore(getContext()).getUserDetails().getRoleName().equals("Teacher")){
            chatList.add(new ChatItem("TKT Hansani", "Father of Hemal", "3c90d4ab-7a62-48a4-9f01-d217b9a7fae1"));
            chatList.add(new ChatItem("Nimal Weerasinghe", "Father of Lahiru", "49f13909-48cb-49b4-9cf5-53b52eed0469"));
        }else{
            chatList.add(new ChatItem("Thathsarani", "Maths Teacher", "1f1c4de9-b42d-485c-b8f1-de28059aac36"));
            chatList.add(new ChatItem("Madushika", "IT Teacher", "c7d8ada4-5b4c-40f8-ae51-6765cadaa3cc"));
        }


        chatAdapter = new ChatAdapter(chatList, getContext());
        recyclerView.setAdapter(chatAdapter);

        return view;
    }

}
