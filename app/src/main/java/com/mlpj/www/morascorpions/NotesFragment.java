package com.mlpj.www.morascorpions;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerNonConfig;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {

    private RecyclerView.Adapter mNotesAdapter;
    private RecyclerView mRecyclerView;
    private List<NoteItem> mNoteItems;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        Bundle args = getArguments();

        mNoteItems = new ArrayList<>();
        NoteListSerializable noteListSerializable = (NoteListSerializable) args.getSerializable("noteList");
        mNoteItems = noteListSerializable.getNoteList();
        mRecyclerView = view.findViewById(R.id.RecyclerViewNotes);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        FragmentManager fragmentManager = getFragmentManager();
        mNotesAdapter = new NotesAdapter(mNoteItems, getContext(), fragmentManager);
        mRecyclerView.setAdapter(mNotesAdapter);



        return view;
    }


}
