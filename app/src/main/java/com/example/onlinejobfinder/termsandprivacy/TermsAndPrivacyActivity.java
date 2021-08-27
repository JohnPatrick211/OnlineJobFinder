package com.example.onlinejobfinder.termsandprivacy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.onlinejobfinder.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class TermsAndPrivacyActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewpager;
    TabItem applicant,employer;
    pager2adapter pageradapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_privacy);

        tabLayout = findViewById(R.id.tab_layout);
        viewpager = findViewById(R.id.view_pager);
        applicant = findViewById(R.id.tabitem_applicant);
        employer = findViewById(R.id.tabitem_employer);
        pageradapter2 = new pager2adapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewpager.setAdapter(pageradapter2);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.customactionbarmaintitle);

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