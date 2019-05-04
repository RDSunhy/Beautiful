package com.shy.beautiful.fragment.category;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {

    //存放fragment的集合
    private List<Fragment> mList;

    public FragmentAdapter(FragmentManager fragmentManager, List<Fragment> mList) {
        super(fragmentManager);
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    public Fragment getItem(int position) {
        return mList.get(position);
    }

}
