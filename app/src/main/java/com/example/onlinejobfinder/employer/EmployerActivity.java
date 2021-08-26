package com.example.onlinejobfinder.employer;

import androidx.annotation.NonNull;
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

import com.example.onlinejobfinder.ApplicantActivity;
import com.example.onlinejobfinder.MainActivity;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class EmployerActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    SharedPreferences userPref;
    String name2,user_id;
    final Fragment fragment1 = new EmployerHomeFragment();
    final Fragment fragment2 = new EmployerJobFragment();
    final Fragment fragment3 = new ApplicantAppliedFragment();
    final Fragment fragment4 = new EmployerProfileFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer);

        BottomNavigationView navView = findViewById(R.id.nav_view_employer);
        drawerLayout = findViewById(R.id.drawerLayout2);
        navigationView = findViewById(R.id.navigation_view2);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.customactionbarmaintitle);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        name2 = userPref.getString("name","name");
        user_id = userPref.getString("id","id");
        SharedPreferences.Editor editor = userPref.edit();
        sessionManager = new SessionManager(getApplicationContext());
        editor.putString("name",name2);
        editor.putString("id",user_id);
        editor.apply();
        editor.commit();

//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        getSupportFragmentManager().beginTransaction().replace(R.id.container2,new EmployerHomeFragment()).commit();
        navigationView.setNavigationItemSelectedListener(this);

        fm.beginTransaction().add(R.id.container2, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.container2, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.container2, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.container2, fragment1, "1").commit();

//        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Fragment selectedFragment = null;
//                switch(item.getItemId()){
//                    case R.id.navigation_employerhome:
//                        selectedFragment = new EmployerHomeFragment();
//                        break;
//                    case R.id.navigation_employerapplicants:
//                        selectedFragment = new ApplicantAppliedFragment();
//                        break;
//                }
//                getSupportFragmentManager().beginTransaction().replace(R.id.container2,selectedFragment).commit();
//                return true;
//            }
//        });
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navigation_employerhome:
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;
                        return true;
                    case R.id.navigation_employerjob:
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;
                        return true;
                    case R.id.navigation_employerapplicants:
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        active = fragment3;
                        return true;
                    case R.id.navigation_employerprofile:
                        fm.beginTransaction().hide(active).show(fragment4).commit();
                        active = fragment4;
                        return true;

                }
                return false;
            }
        });


    }
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
            case R.id.navigation_home_employer:
                drawerLayout.closeDrawers();
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
                        sessionManager.setEmployerLogin(false);
                        Intent ia = new Intent(EmployerActivity.this, MainActivity.class);
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
            case R.id.navigation_requestmaintenance:
                Intent ia = new Intent(EmployerActivity.this, RequestMaintenanceActivity.class);
                ia.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ia.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(ia);
                break;
            case R.id.navigation_applicanthired:
                Intent ia1 = new Intent(EmployerActivity.this, ApplicantHiredActivity.class);
                ia1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ia1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(ia1);
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
                sessionManager.setEmployerLogin(false);
                Intent ia = new Intent(EmployerActivity.this, MainActivity.class);
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