package com.mlpj.www.morascorpions;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DELL on 12/17/2017.
 * All Rights Reserved by MLPJ©
 */

public class NotesHwPagerAdapter extends FragmentStatePagerAdapter{
    private int ternaryId;
    private List<HwItem> hwList;
    private HwListSerializable hwListSerializable;
    public NotesHwPagerAdapter(FragmentManager fm, int ternaryId, List<HwItem> hwList) {
        super(fm);
        this.ternaryId = ternaryId;
        this.hwList = hwList;
        hwListSerializable = new HwListSerializable(hwList);
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
        bundle.putInt("ternaryId", ternaryId);
        bundle.putSerializable("hwList",hwListSerializable);
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
