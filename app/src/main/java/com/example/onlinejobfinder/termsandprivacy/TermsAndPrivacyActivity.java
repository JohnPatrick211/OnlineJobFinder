package com.example.onlinejobfinder.termsandprivacy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.onlinejobfinder.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class TermsAndPrivacyActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewpager;
    TabItem applicant,employer;
    pager2adapter pageradapter2;
    String privacy = "";
    int selected;

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
        privacy = getIntent().getExtras().getString("privacy");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.customactionbarmaintitle);

            if (privacy.equals("empty")) {
                privacy = "empty";
            } else {
                privacy = "privacy";
            }
        if(privacy.equals("privacy"))
        {
            viewpager.setCurrentItem(1, true);
            Toast.makeText(TermsAndPrivacyActivity.this, "success", Toast.LENGTH_SHORT).show();
            selected = 1;
        }
        else
        {
            viewpager.setCurrentItem(0, true);
            Toast.makeText(TermsAndPrivacyActivity.this, "failed", Toast.LENGTH_SHORT).show();
            selected = 0;
        }

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
                viewpager.setCurrentItem(tab.getPosition());
            }
        });
    }
}