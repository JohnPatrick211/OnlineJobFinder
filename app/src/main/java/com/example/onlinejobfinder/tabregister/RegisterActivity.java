package com.example.onlinejobfinder.tabregister;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.onlinejobfinder.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
        TabLayout tabLayout;
        ViewPager viewpager;
        TabItem applicant,employer;
        pageradapter pageradapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tabLayout = findViewById(R.id.tab_layout);
        viewpager = findViewById(R.id.view_pager);
        applicant = findViewById(R.id.tabitem_applicant);
        employer = findViewById(R.id.tabitem_employer);
        pageradapter2 = new pageradapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewpager.setAdapter(pageradapter2);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


}