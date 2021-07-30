package com.example.onlinejobfinder.guest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.applicant.HomeFragment;
import com.example.onlinejobfinder.applicant.ProfileFragment;
import com.example.onlinejobfinder.applicant.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class GuestActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    SharedPreferences userPref;
    String name2,user_id;
    final Fragment fragment1 = new GuestHomeFragment();
    final Fragment fragment2 = new GuestSearchFragment();
    final Fragment fragment3 = new GuestProfileFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        BottomNavigationView navView = findViewById(R.id.nav_guestview);
//        drawerLayout = findViewById(R.id.drawerLayout);
//        navigationView = findViewById(R.id.navigation_view);
//        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        fm.beginTransaction().add(R.id.container3, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.container3, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.container3, fragment1, "1").commit();

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navigation_guesthome:
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;
                        return true;
                    case R.id.navigation_guestdashboard:
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;
                        return true;
                    case R.id.navigation_guestnotifications:
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        active = fragment3;
                        return true;

                }
                return false;
            }
        });
    }
}