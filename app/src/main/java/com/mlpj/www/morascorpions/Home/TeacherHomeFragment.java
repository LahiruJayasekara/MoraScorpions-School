package com.mlpj.www.morascorpions.Home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlpj.www.morascorpions.Attendance.TeacherMarkAttendanceFragment;
import com.mlpj.www.morascorpions.Chat.TeacherChatFragment;
import com.mlpj.www.morascorpions.ComplainHandling.PrincipalAndTeacherComplainViewFragment;
import com.mlpj.www.morascorpions.NoticeHandling.NoticeViewFragment;
import com.mlpj.www.morascorpions.R;
import com.mlpj.www.morascorpions.Profile.TeacherProfileFragment;


public class TeacherHomeFragment extends Fragment {

    private CardView profileBtn, attendanceBtn, noticeBtn, chatBtn, complainBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_teacher_home, container, false);

        profileBtn = view.findViewById(R.id.teacherProfileBtn);
        attendanceBtn = view.findViewById(R.id.teacherMarkAttendanceBtn);
        noticeBtn = view.findViewById(R.id.teacherNoticeBtn);
        chatBtn = view.findViewById(R.id.teacherChatBtn);
        complainBtn = view.findViewById(R.id.teacherComplainBtn);

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TeacherProfileFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
            }
        });

        attendanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TeacherMarkAttendanceFragment();
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

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TeacherChatFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
            }
        });

        complainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PrincipalAndTeacherComplainViewFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
            }
        });
        return view;
    }

}
