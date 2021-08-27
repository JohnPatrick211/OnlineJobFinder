package com.example.onlinejobfinder.termsandprivacy;

import com.example.onlinejobfinder.tabregister.ApplicantFragment;
import com.example.onlinejobfinder.tabregister.EmployerFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class pager2adapter extends FragmentPagerAdapter {

    private int numoftabs;


    public pager2adapter(@NonNull FragmentManager fm, int numoftabs) {
        super(fm);
        this.numoftabs = numoftabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TermsAndConditionsFragment();
            case 1:
                return new PrivacyandPolicyFragment();
            default: return null;
        }

    }

    @Override
    public int getCount() {
        return numoftabs;
    }
}
