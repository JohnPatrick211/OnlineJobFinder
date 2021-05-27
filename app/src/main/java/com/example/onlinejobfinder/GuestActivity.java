package com.example.onlinejobfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.onlinejobfinder.employer.EmployerHomeFragment;
import com.example.onlinejobfinder.guest.HomeFragment;
import com.example.onlinejobfinder.guest.ProfileFragment;
import com.example.onlinejobfinder.guest.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class GuestActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    SharedPreferences userPref;
    String name2,user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawerLayout);
         navigationView = findViewById(R.id.navigation_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

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
        Toast.makeText(GuestActivity.this,user_id,Toast.LENGTH_SHORT).show();

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();

        navigationView.setNavigationItemSelectedListener(this);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch(item.getItemId()){
                    case R.id.navigation_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.navigation_dashboard:
                        selectedFragment = new SearchFragment();
                        break;
                    case R.id.navigation_notifications:
                        selectedFragment = new ProfileFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,selectedFragment).commit();
                return true;
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
            case R.id.navigation_logout:
                Intent intent = new Intent(GuestActivity.this,EmailActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}