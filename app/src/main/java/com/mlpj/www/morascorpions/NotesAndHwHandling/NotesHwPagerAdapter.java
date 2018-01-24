package com.mlpj.www.morascorpions.NotesAndHwHandling;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import java.util.List;

/**
 * Created by DELL on 12/17/2017.
 * All Rights Reserved by MLPJ©
 */

public class NotesHwPagerAdapter extends FragmentStatePagerAdapter{
    private List<HwItem> hwList;
    private List<NoteItem> noteItems;
    private HwListSerializable hwListSerializable;
    private NoteListSerializable noteListSerializable;
    public NotesHwPagerAdapter(FragmentManager fm, List<HwItem> hwList, List<NoteItem> noteItems) {
        super(fm);
        this.hwList = hwList;
        this.noteItems = noteItems;
        hwListSerializable = new HwListSerializable(hwList);
        noteListSerializable = new NoteListSerializable(noteItems);

        }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position){
            case 0:
                fragment = new NotesFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("noteList",noteListSerializable);
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new HwFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("hwList",hwListSerializable);
                fragment.setArguments(bundle2);
                break;
            default:
                fragment = null;
        }

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
