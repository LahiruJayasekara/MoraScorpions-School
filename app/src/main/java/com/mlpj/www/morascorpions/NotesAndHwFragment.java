package com.mlpj.www.morascorpions;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class NotesAndHwFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private NotesHwPagerAdapter mNotesHwPagerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_and_hw, container, false);

        Bundle args = getArguments();
        int classId = args.getInt("classId");

        mTabLayout = view.findViewById(R.id.tabLayoutNotesHw);
        mViewPager = view.findViewById(R.id.viewPagerNotesHw);
        mNotesHwPagerAdapter = new NotesHwPagerAdapter(getFragmentManager(),classId);
        mViewPager.setAdapter(mNotesHwPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        return view;
    }

}
