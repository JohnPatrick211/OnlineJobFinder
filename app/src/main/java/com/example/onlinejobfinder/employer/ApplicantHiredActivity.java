package com.example.onlinejobfinder.employer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.MainActivity;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.SessionManager;
import com.example.onlinejobfinder.adapter.appliedapplicantsadapter;
import com.example.onlinejobfinder.model.appliedapplicants;
import com.example.onlinejobfinder.model.job;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ApplicantHiredActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    RecyclerView recyclerView;
    appliedapplicantsadapter.RecyclerViewClickListener listener;
    View ln_noappplicantsappliedlayout;
    int position =0;
    int position2 =0;
    boolean[] selectedspecialization, selectedlocation;
    ArrayList<Integer> Specialization = new ArrayList<>();
    TextView btnfilter,tvsearchspecialization,tvsearchlocation;
    ArrayList<appliedapplicants> arraylist;
    ArrayList<job> arraylist2;
    ArrayList<String> category,location;
    JSONArray result,result2;
    SwipeRefreshLayout refreshLayout;
    appliedapplicantsadapter appliedapplicantsadapter2;
    // Spinner spinnercategory, spinnerlocation;
    String catergoryString,yearString, approved,id;
    String [] specializationarray, locationarray;
    View ln_delay;
    LinearLayout main;
    CountDownTimer CDT;
    SharedPreferences userPref2;
    String name2, user_id,token,email;
    SessionManager sessionManager;
    ImageView imageprofile;
    TextView textnameprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_hired);

        userPref2 = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        name2 = userPref2.getString("name","name");
        email = userPref2.getString("email","email");
        user_id = userPref2.getString("id","id");
        token = userPref2.getString("token","token");
        recyclerView = findViewById(R.id.recyclerview_appliedjobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout = findViewById(R.id.employerswipe);
//        spinnercategory = view.findViewById(R.id.spinner_category);
        //       spinnerlocation = view.findViewById(R.id.spinner_location);
        arraylist = new ArrayList<>();
        arraylist2 = new ArrayList<>();
        category = new ArrayList<String>();
        location = new ArrayList<String>();
        ln_noappplicantsappliedlayout = findViewById(R.id.ln_noapplicantsappliedlayout);
        approved = "Approved";

        refreshLayout.setRefreshing(true);
        setOnClickListener();
        ln_delay = findViewById(R.id.ln_delayloadinglayout);
        main = findViewById(R.id.bruh);
        main.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.GONE);
        drawerLayout = findViewById(R.id.drawerLayout2);
        navigationView = findViewById(R.id.navigation_view2);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.customactionbarapplyapplicants);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        sessionManager = new SessionManager(getApplicationContext());
        navigationView.setNavigationItemSelectedListener(this);

        delay();

        String intentname = getIntent().getExtras().getString("name");
        imageprofile = navigationView.getHeaderView(0).findViewById(R.id.drawerimageviewprofile);
        textnameprofile = navigationView.getHeaderView(0).findViewById(R.id.drawerprofilename);
        textnameprofile.setText(intentname);

        try {
            String checknull = getIntent().getExtras().getString("profile_pic");
            if (checknull.equals("null")) {
                imageprofile.setImageResource(R.drawable.img);
            } else {
                Picasso.get().load(getIntent().getStringExtra("profile_pic")).into( imageprofile);
            }
        } catch (Exception e) {
            imageprofile.setImageResource(R.drawable.img);
        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
                arraylist.clear();
                getPost();
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
                Intent ia1 = new Intent(ApplicantHiredActivity.this, EmployerActivity.class);
                ia1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ia1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(ia1);
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
                        Intent ia = new Intent(ApplicantHiredActivity.this, MainActivity.class);
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
            case R.id.navigation_applicanthired:
                drawerLayout.closeDrawers();
                break;
            case R.id.navigation_requestmaintenance:
                Intent ia12 = new Intent(ApplicantHiredActivity.this, RequestMaintenanceActivity.class);
                ia12.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ia12.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                ia12.putExtra("name",textnameprofile.getText().toString());
                ia12.putExtra("profile_pic", getIntent().getStringExtra("profile_pic"));
                startActivity(ia12);
                break;
        }
        return true;
    }


    private void getPost() {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.employerapplyhiredjobpost+"?job_id="+user_id, response ->{
            try{
                JSONObject object = new JSONObject(response);
                if(object.getBoolean("success"))
                {
                    JSONArray array = new JSONArray(object.getString("jobpost"));
                    for(int i = 0; i < array.length(); i++)
                    {
                        JSONObject postObject = array.getJSONObject(i);
                        //JSONObject getpostObject = postObject.getJSONObject("jobposts");

                        appliedapplicants appliedapplicants2 = new appliedapplicants();
                        appliedapplicants2.setId(postObject.getString("id"));
                        appliedapplicants2.setApplicantid(postObject.getString("applicant_id"));
                        appliedapplicants2.setJobapplyid(postObject.getString("applyjob_id"));
                        appliedapplicants2.setJobid(postObject.getString("job_id"));
                        appliedapplicants2.setProfilepic(postObject.getString("profile_pic"));
                        appliedapplicants2.setName(postObject.getString("name"));
                        appliedapplicants2.setEmail(postObject.getString("email"));
                        appliedapplicants2.setAddress(postObject.getString("address"));
                        appliedapplicants2.setContactno(postObject.getString("contactno"));
                        appliedapplicants2.setStatus(postObject.getString("status"));
                        appliedapplicants2.setGender(postObject.getString("gender"));


                        arraylist.add(appliedapplicants2);
                    }
                    appliedapplicantsadapter2 = new appliedapplicantsadapter(arraylist,ApplicantHiredActivity.this,listener);
                    recyclerView.setAdapter(appliedapplicantsadapter2);
                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    if(arraylist.isEmpty())
                    {
                        recyclerView.setVisibility(View.GONE);
                        ln_noappplicantsappliedlayout.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    Toast.makeText(ApplicantHiredActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                    ln_noappplicantsappliedlayout.setVisibility(View.GONE);
                }
            }catch(JSONException e)
            {
                e.printStackTrace();
                Toast.makeText(ApplicantHiredActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                ln_noappplicantsappliedlayout.setVisibility(View.GONE);
            }

            refreshLayout.setRefreshing(false);

        },error -> {
            error.printStackTrace();
            refreshLayout.setRefreshing(false);
            ln_noappplicantsappliedlayout.setVisibility(View.GONE);
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ApplicantHiredActivity.this);
        queue.add(request);
    }
    public void onResume() {
        super.onResume();
        drawerLayout.closeDrawers();
        delay();
        arraylist.clear();
        getPost();

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    private void setOnClickListener() {
        listener = new appliedapplicantsadapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(ApplicantHiredActivity.this, ViewApplyProfileActivity.class);
                //
                // intent.putExtra("intentjob_id",arraylist.get(position).getJobid());
                intent.putExtra("intentapplicant_id",arraylist.get(position).getApplicantid());
                intent.putExtra("intentid",arraylist.get(position).getId());
//                intent.putExtra("intentid",arraylist.get(position).getJobuniqueid());
//                intent.putExtra("intentjoblogo",Constant.URL+"/storage/profiles/"+arraylist.get(position).getJoblogo());
//                intent.putExtra("intentjobtitle",arraylist.get(position).getJobtitle());
//                intent.putExtra("intentjobcompany",arraylist.get(position).getJobcompany());
//                intent.putExtra("intentjobdescription",arraylist.get(position).getJobdescription());
//                intent.putExtra("intentjobcompanyoverview",arraylist.get(position).getCompanyoverview());
//                intent.putExtra("intentjoblocation",arraylist.get(position).getJoblocation());
//                intent.putExtra("intentjobaddress",arraylist.get(position).getJobaddress());
//                intent.putExtra("intentjobspecialization",arraylist.get(position).getJobcategory());
//                intent.putExtra("intentjobsalary",arraylist.get(position).getJobsalary());
//                intent.putExtra("intentjobposted",arraylist.get(position).getJobdateposted());
//                intent.putExtra("intentjobstatus",arraylist.get(position).getJobstatus());
//                intent.putExtra("jobcompanylogo2",arraylist.get(position).getJoblogo());
               // Toast.makeText(ApplicantHiredActivity.this,"Success",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        };
    }
    public void delay()
    {
        ln_noappplicantsappliedlayout.setVisibility(View.GONE);
        main.setVisibility(View.GONE);
        CDT = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {
                ln_delay.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                ln_delay.setVisibility(View.GONE);
                refreshLayout.setVisibility(View.VISIBLE);
                main.setVisibility(View.VISIBLE);

            }
        }.start();

    }

}