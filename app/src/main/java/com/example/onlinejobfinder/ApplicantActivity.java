package com.example.onlinejobfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.onlinejobfinder.applicant.ApplicantSavedJobActivity;
import com.example.onlinejobfinder.applicant.HomeFragment;
import com.example.onlinejobfinder.applicant.ProfileFragment;
import com.example.onlinejobfinder.applicant.SearchFragment;
import com.example.onlinejobfinder.employer.EmployerActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class ApplicantActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    SharedPreferences userPref;
    String name2,user_id;
    final Fragment fragment1 = new HomeFragment();
    final Fragment fragment2 = new SearchFragment();
    final Fragment fragment3 = new ProfileFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
    SessionManager sessionManager;
    BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant);
        navView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        sessionManager = new SessionManager(getApplicationContext());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.customactionbarmaintitle);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//           name2 = bundle.getString("name");
//
//       }

        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        name2 = userPref.getString("name","name");
        user_id = userPref.getString("id","id");
        SharedPreferences.Editor editor = userPref.edit();
        editor.putString("name",name2);
        editor.putString("id",user_id);
        editor.apply();
        editor.commit();
        //check ID debugging//
        Toast.makeText(ApplicantActivity.this,user_id,Toast.LENGTH_SHORT).show();

//        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
//
        navigationView.setNavigationItemSelectedListener(this);

        fm.beginTransaction().add(R.id.container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.container, fragment1, "1").commit();

//        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment selectedFragment = null;
//                switch(item.getItemId()){
//                    case R.id.navigation_home:
//                        selectedFragment = new HomeFragment();
//                        break;
//                    case R.id.navigation_dashboard:
//                        selectedFragment = new SearchFragment();
//                        break;
//                    case R.id.navigation_notifications:
//                        selectedFragment = new ProfileFragment();
//                        break;
//
//                }
//                getSupportFragmentManager().beginTransaction().replace(R.id.container,selectedFragment).commit();
//                return true;
//            }
//        });
        //noreload//
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navigation_home:
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;
                        return true;
                    case R.id.navigation_dashboard:
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;
                        return true;
                    case R.id.navigation_notifications:
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        active = fragment3;
                        return true;

                }
                return false;
            }
        });

//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        //TO HIDE DRAWER BUTTON, uncomment below:
////        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return true;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.navigation_home:
                drawerLayout.closeDrawers();
                break;
            case R.id.navigation_testemail:
                Intent intent1 = new Intent(ApplicantActivity.this,EmailActivity.class);
                startActivity(intent1);
                break;
            case R.id.navigation_savedjob:
                Intent intent2 = new Intent(ApplicantActivity.this, ApplicantSavedJobActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent2);
                break;
            case R.id.navigation_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Do you want to logout?");
                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        SharedPreferences.Editor editor = userPref.edit();
                        //                       editor.clear();
                        //                      editor.apply();
                        sessionManager.setApplicantLogin(false);
                        Intent ia = new Intent(ApplicantActivity.this, MainActivity.class);
                        startActivity(ia);
                        finish();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                break;
        }
        return true;
    }
    public void onResume() {
        super.onResume();
        drawerLayout.closeDrawers();
    }
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to logout?");
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                        SharedPreferences.Editor editor = userPref.edit();
                //                       editor.clear();
                //                      editor.apply();
                sessionManager.setApplicantLogin(false);
                Intent ia = new Intent(ApplicantActivity.this, MainActivity.class);
                startActivity(ia);
                finish();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
}