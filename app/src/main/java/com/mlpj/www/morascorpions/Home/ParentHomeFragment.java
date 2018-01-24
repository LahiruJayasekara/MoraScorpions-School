package com.mlpj.www.morascorpions.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlpj.www.morascorpions.Attendance.ParentAttendanceViewFragment;
import com.mlpj.www.morascorpions.Chat.ParentChatFragment;
import com.mlpj.www.morascorpions.ComplainHandling.ParentComplainViewFragment;
import com.mlpj.www.morascorpions.NotesAndHwHandling.HwViewFragment;
import com.mlpj.www.morascorpions.NoticeHandling.NoticeViewFragment;
import com.mlpj.www.morascorpions.R;
import com.mlpj.www.morascorpions.Syllabus.SyllabusViewFragment;


public class ParentHomeFragment extends Fragment {

    private CardView attendanceBtn, hwBtn, noticeBtn, sylabusBtn, chatBtn, complainBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_parent_home, container, false);

        attendanceBtn = view.findViewById(R.id.parentAttendanceBtn);
        hwBtn = view.findViewById(R.id.parentHwBtn);
        noticeBtn = view.findViewById(R.id.parentNoticeBtn);
        sylabusBtn = view.findViewById(R.id.parentSyllabusBtn);
        chatBtn = view.findViewById(R.id.parentChatBtn);
        complainBtn = view.findViewById(R.id.parentComplainsBtn);

        attendanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ParentAttendanceViewFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
            }
        });

        hwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new HwViewFragment();
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

        sylabusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new SyllabusViewFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
            }
        });

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ParentChatFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
            }
        });

        complainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ParentComplainViewFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
            }
        });
        return view;
    }

}
