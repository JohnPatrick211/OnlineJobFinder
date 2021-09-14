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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinejobfinder.ApplicantActivity;
import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.MainActivity;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EmployerActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    SharedPreferences userPref;
    String name2,user_id,token;
    String imgUrl;
    final Fragment fragment1 = new EmployerHomeFragment();
    final Fragment fragment2 = new EmployerJobFragment();
    final Fragment fragment3 = new ApplicantAppliedFragment();
    final Fragment fragment4 = new EmployerProfileFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
    SessionManager sessionManager;
    ImageView imageprofile;
    TextView textnameprofile;

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
        token = userPref.getString("token","token");
        SharedPreferences.Editor editor = userPref.edit();
        sessionManager = new SessionManager(getApplicationContext());
        editor.putString("name",name2);
        editor.putString("id",user_id);
        editor.apply();
        editor.commit();

        imageprofile = navigationView.getHeaderView(0).findViewById(R.id.drawerimageviewprofile);
        textnameprofile = navigationView.getHeaderView(0).findViewById(R.id.drawerprofilename);

//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        getSupportFragmentManager().beginTransaction().replace(R.id.container2,new EmployerHomeFragment()).commit();
        navigationView.setNavigationItemSelectedListener(this);

        fm.beginTransaction().add(R.id.container2, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.container2, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.container2, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.container2, fragment1, "1").commit();

        getPost();

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

    private void getPost() {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.EMPLOYER_POST+"?employer_id="+user_id, response -> {
            try{
                JSONObject object= new JSONObject(response);
                if(object.getBoolean("success")){
                    JSONObject user = object.getJSONObject("user");
                    textnameprofile.setText(user.get("name").toString());
                    imgUrl = Constant.URL+"/storage/profiles/"+user.getString("profile_pic");
                    Picasso.get().load(Constant.URL+"/storage/profiles/"+user.getString("profile_pic")).into(imageprofile);
                    if(user.getString("profile_pic").equals("null"))
                    {

                        imageprofile.setImageResource(R.drawable.img);

                    }
                }
                else
                {
                    Toast.makeText(EmployerActivity.this, "Network Error, Please Try Again", Toast.LENGTH_SHORT).show();
                    // progressDialog.cancel();
                }

            }catch(JSONException e)
            {
                //Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                textnameprofile.setText(name2);
               // txtemployeremail.setText(email);
                imageprofile.setImageResource(R.drawable.img);
                //  progressDialog.cancel();
            }
        },error ->{
            error.printStackTrace();
            Toast.makeText(EmployerActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
            textnameprofile.setText(name2);
            imageprofile.setImageResource(R.drawable.img);
            // progressDialog.cancel();
        })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                //String token = userPref.getString("token","token");
                map.put("Authorization","Bearer "+token);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(EmployerActivity.this);
        queue.add(request);
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
                ia.putExtra("name",textnameprofile.getText().toString());
                ia.putExtra("profile_pic", imgUrl);
                startActivity(ia);
                break;
            case R.id.navigation_applicanthired:
                Intent ia1 = new Intent(EmployerActivity.this, ApplicantHiredActivity.class);
                ia1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ia1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                ia1.putExtra("name",textnameprofile.getText().toString());
                ia1.putExtra("profile_pic", imgUrl);
                startActivity(ia1);
                break;
        }
        return true;
    }
    public void onResume() {
        super.onResume();
        getPost();
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