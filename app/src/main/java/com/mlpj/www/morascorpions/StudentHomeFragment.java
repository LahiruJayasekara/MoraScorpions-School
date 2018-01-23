package com.mlpj.www.morascorpions;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlpj.www.morascorpions.ComplainHandling.PrincipalAndTeacherComplainViewFragment;
import com.mlpj.www.morascorpions.NotesHandling.StudentNotesViewFragment;
import com.mlpj.www.morascorpions.NoticeHandling.NoticeViewFragment;
import com.mlpj.www.morascorpions.Syllabus.SyllabusViewFragment;

public class StudentHomeFragment extends Fragment {
    private CardView profileBtn, hWBtn, notesBtn, noticeBtn, syllabusBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_home, container, false);

        profileBtn = view.findViewById(R.id.studentProfileBtn);
        hWBtn = view.findViewById(R.id.studentHwBtn);
        notesBtn = view.findViewById(R.id.studentNotesBtn);
        noticeBtn = view.findViewById(R.id.studentNoticeBtn);
        syllabusBtn = view.findViewById(R.id.studentSyllabusBtn);

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new StudentProfileFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
            }
        });

        hWBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new HwViewFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
            }
        });

        notesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new StudentNotesViewFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
            }
        });

        noticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new NoticeViewFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
            }
        });

        syllabusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new SyllabusViewFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
            }
        });
        return view;
    }


}
