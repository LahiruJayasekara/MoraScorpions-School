package com.mlpj.www.morascorpions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by DELL on 12/17/2017.
 * All Rights Reserved by MLPJ©
 */

public class NotesHwPagerAdapter extends FragmentStatePagerAdapter{
    private int classId;
    public NotesHwPagerAdapter(FragmentManager fm, int classId) {
        super(fm);
        this.classId = classId;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position){
            case 0:
                fragment = new NotesFragment();
                break;
            case 1:
                fragment = new HwFragment();
                break;
            default:
                fragment = null;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("classId", classId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String tabName;
        switch (position){
            case 0:
                tabName = "Notes";
                break;
            case 1:
                tabName = "Homework";
                break;
            default:
                tabName = null;
        }
        return tabName;
    }
}
