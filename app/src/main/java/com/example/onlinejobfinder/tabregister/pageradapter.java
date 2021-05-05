package com.example.onlinejobfinder.tabregister;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

class pageradapter extends FragmentPagerAdapter {
    private int numoftabs;


    public pageradapter(@NonNull FragmentManager fm, int numoftabs) {
        super(fm);
        this.numoftabs = numoftabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ApplicantFragment();
            case 1:
                return new EmployerFragment();
            default: return null;
        }

    }

    @Override
    public int getCount() {
        return numoftabs;
    }
}
