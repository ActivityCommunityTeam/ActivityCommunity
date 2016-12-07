package com.example.administrator.activitycommunity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Daniel on 2016/11/15.
 */

public class MY_FragmentPageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments ;

    public MY_FragmentPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments==null?0:fragments.size();
    }

    public void update(List<Fragment> fragments){
        this.fragments = fragments;
        notifyDataSetChanged();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0)
            return "我参见的活动";
        else if(position == 1)
            return "我关注的活动";
        return null;
    }
}
