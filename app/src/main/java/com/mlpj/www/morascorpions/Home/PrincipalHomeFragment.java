package com.mlpj.www.morascorpions.Home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlpj.www.morascorpions.ComplainHandling.PrincipalAndTeacherComplainViewFragment;
import com.mlpj.www.morascorpions.NoticeHandling.PrincipalNoticeFragment;
import com.mlpj.www.morascorpions.R;


public class PrincipalHomeFragment extends Fragment {

    private CardView noticeBtn, complainBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_principal_home, container, false);


        noticeBtn = view.findViewById(R.id.principalNoticeBtn);
        complainBtn = view.findViewById(R.id.principalComplainBtn);


        noticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PrincipalNoticeFragment();
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
