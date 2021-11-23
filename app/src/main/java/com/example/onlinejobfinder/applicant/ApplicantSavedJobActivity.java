package com.example.onlinejobfinder.applicant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amar.library.ui.StickyScrollView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinejobfinder.ApplicantActivity;
import com.example.onlinejobfinder.CheckInternet;
import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.EmailActivity;
import com.example.onlinejobfinder.MainActivity;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.SessionManager;
import com.example.onlinejobfinder.adapter.jobadapter;
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

public class ApplicantSavedJobActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    RelativeLayout searchlayout;
    ImageView filterbutton;
    boolean expand = false;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    View ln_nobookmarkedjoblayout;
    EditText edt_search;
    TextView tv_networkerrorrefresh;
    LinearLayout ln_networkjobsearcherror;
    RecyclerView recyclerView;
    SharedPreferences userPref2;
    jobadapter.RecyclerViewClickListener listener;
    int position =0;
    int position2 =0;
    boolean[] selectedspecialization, selectedlocation;
    ArrayList<Integer> Specialization = new ArrayList<>();
    TextView btnfilter,tvsearchspecialization,tvsearchlocation,tvsearchsalaryminimum;
    ArrayList<job> arraylist;
    SessionManager sessionManager;
    ArrayList<job> arraylist2;
    ArrayList<String> category,location;
    JSONArray result,result2;
    SwipeRefreshLayout refreshLayout,networkrefresh;
    jobadapter jobadapter2;
    String name2, user_id,token,email;
    // Spinner spinnercategory, spinnerlocation;
    String catergoryString,yearString, salaryString;
    String [] specializationarray, locationarray;
    View ln_delay;
    LinearLayout main;
    CountDownTimer CDT;
    ImageView imageprofile;
    TextView textnameprofile;
    String rangesalary;
    int salary1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_saved_job);
        filterbutton = findViewById(R.id.filterbutton);
        searchlayout = findViewById(R.id.searchlayout);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.customactionbarbookmarkedjob);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView.setNavigationItemSelectedListener(this);
        edt_search = findViewById(R.id.search);
        btnfilter = findViewById(R.id.btn_filter);
        tvsearchspecialization = findViewById(R.id.tv_searchspecialization);
        tvsearchlocation  = findViewById(R.id.tv_searchlocation);
        tvsearchsalaryminimum = findViewById(R.id.tv_searchsalaryminimum);
        recyclerView = findViewById(R.id.recyclerview_savedjobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(ApplicantSavedJobActivity.this));
        refreshLayout = findViewById(R.id.swipe);
//        spinnercategory = view.findViewById(R.id.spinner_category);
        //       spinnerlocation = view.findViewById(R.id.spinner_location);
        arraylist = new ArrayList<>();
        arraylist2 = new ArrayList<>();
        category = new ArrayList<String>();
        location = new ArrayList<String>();
        ln_nobookmarkedjoblayout = findViewById(R.id.ln_nobookmarkedjoblayout);
        sessionManager = new SessionManager(getApplicationContext());
        String intentname = getIntent().getExtras().getString("name");
        imageprofile = navigationView.getHeaderView(0).findViewById(R.id.drawerimageviewprofile);
        textnameprofile = navigationView.getHeaderView(0).findViewById(R.id.drawerprofilename);
        textnameprofile.setText(intentname);


        try {
            String checknull = getIntent().getExtras().getString("profile_pic");
            if (checknull.equals(Constant.URL+"/storage/profiles/null")) {
                imageprofile.setImageResource(R.drawable.img);
            } else {
                Picasso.get().load(getIntent().getStringExtra("profile_pic")).into( imageprofile);
            }
        } catch (Exception e) {
            imageprofile.setImageResource(R.drawable.img);
        }
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0)
                {
                    safefilter();
                }
                else
                {
                    jobadapter2.getFilter().filter(charSequence.toString().toLowerCase());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

//        category.add("Category");
//        category.add("Accountant");
//        category.add("Programmer");
        //  location.add("Region");
        //  location.add("Region 4-A");
        //  location.add("Region 3");
        //  spinnerlocation.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, location));
        refreshLayout.setRefreshing(true);
        setOnClickListener();
        //getCategory();
        // getLocation();
        userPref2 = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        name2 = userPref2.getString("name","name");
        email = userPref2.getString("email","email");
        user_id = userPref2.getString("id","id");
        token = userPref2.getString("token","token");
        networkrefresh = findViewById(R.id.networkswipe);
        main = findViewById(R.id.bruh);
        tv_networkerrorrefresh = findViewById(R.id.tv_networkjobsearcherrorrefresh);
        ln_networkjobsearcherror = findViewById(R.id.networkjobsearcherrorlayout);
        ln_networkjobsearcherror.setVisibility(View.GONE);
        ln_delay = findViewById(R.id.ln_delayloadinglayout);
        main = findViewById(R.id.bruh);
        main.setVisibility(View.GONE);

        delay();

        filterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!expand)
                {
                    ValueAnimator va = ValueAnimator.ofInt(100,260);
                    va.setDuration(400);
                    va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            Integer value = (Integer) valueAnimator.getAnimatedValue();
                            searchlayout.getLayoutParams().height = value.intValue();
                            searchlayout.requestLayout();
                        }
                    });
                    va.start();
                    expand = true;
                }
                else
                {
                    ValueAnimator va = ValueAnimator.ofInt(260,100);
                    va.setDuration(400);
                    va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            Integer value = (Integer) valueAnimator.getAnimatedValue();
                            searchlayout.getLayoutParams().height = value.intValue();
                            searchlayout.requestLayout();
                        }
                    });
                    va.start();
                    expand = false;
                }
            }
        });


        tv_networkerrorrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ln_networkjobsearcherror.setVisibility(View.GONE);
//                networkrefresh.setRefreshing(true);
                recyclerView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
                arraylist.clear();
                delay();
                getPost();
            }
        });
        tvsearchsalaryminimum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        ApplicantSavedJobActivity.this
                );
                builder.setTitle("Enter Minimum Salary");
                final View customdialog = getLayoutInflater().inflate(R.layout.dialog_edittext,null);
                builder.setView(customdialog);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText edt_entersalaryminimum = customdialog.findViewById(R.id.edittext_salaryminimum);
                        if(edt_entersalaryminimum.getText().toString().trim().isEmpty())
                        {
                            tvsearchsalaryminimum.setText("Salary/Minimum");
                            dialogInterface.dismiss();
                        }
                        else
                        {
                            tvsearchsalaryminimum.setText(edt_entersalaryminimum.getText().toString().trim());
                            dialogInterface.dismiss();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        tvsearchsalaryminimum.setText("Salary/Minimum");
                    }
                });
                builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        tvsearchsalaryminimum.setText("Salary/Minimum");
                        tvsearchlocation.setText("Region");
                    }
                });
                builder.show();
            }
        });
        tvsearchspecialization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        ApplicantSavedJobActivity.this
                );
                tvsearchspecialization.setText(specializationarray[position]);
                builder.setTitle("Select Specialization");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(specializationarray, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        position = i;
                        tvsearchspecialization.setText(specializationarray[i]);
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        StringBuilder stringBuilder = new StringBuilder();
//                        for(int j=0; j<Specialization.size(); j++)
//                        {
//                            stringBuilder.append(specializationarray[Specialization.get(j)]);
//                            if(j != Specialization.size()-1)
//                            {
//                                stringBuilder.append(", ");
//                            }
//                        }
//                        tvworkspecialization.setText(stringBuilder.toString());
                        //tvworkspecialization.setText(Specialization.get(position));
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        tvsearchspecialization.setText("Specialization");
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j=0; j<selectedspecialization.length; j++)
                        {
                            selectedspecialization[j] = false;
                            // Specialization.clear();
                            tvsearchspecialization.setText("Specialization");
                        }
                    }
                });
                builder.show();
            }
        });
        tvsearchlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        ApplicantSavedJobActivity.this
                );
                tvsearchlocation.setText(locationarray[position2]);
                builder.setTitle("Select Region");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(locationarray, position2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        position2 = i;
                        tvsearchlocation.setText(locationarray[i]);
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        StringBuilder stringBuilder = new StringBuilder();
//                        for(int j=0; j<Specialization.size(); j++)
//                        {
//                            stringBuilder.append(specializationarray[Specialization.get(j)]);
//                            if(j != Specialization.size()-1)
//                            {
//                                stringBuilder.append(", ");
//                            }
//                        }
//                        tvworkspecialization.setText(stringBuilder.toString());
                        //tvworkspecialization.setText(Specialization.get(position));
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        tvsearchlocation.setText("Region");
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j=0; j<selectedlocation.length; j++)
                        {
                            selectedlocation[j] = false;
                            // Specialization.clear();
                            tvsearchlocation.setText("Region");
                        }
                    }
                });
                builder.show();
            }
        });
        btnfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFilterDelayAnimation();
                catergoryString = tvsearchspecialization.getText().toString();
                yearString = tvsearchlocation.getText().toString();
                salaryString = tvsearchsalaryminimum.getText().toString();
                setColorAnimation();
                ArrayList<job> w = new ArrayList<>();
                if(catergoryString.equals("Specialization") && yearString.equals("Region") && salaryString.equals("Salary/Minimum"))
                {

                    w.addAll(arraylist);
                }
                else
                {
                    for(job details : arraylist)
                    {
                        rangesalary = details.getJobsalary();
                        rangesalary = rangesalary.replace(",","");
                        rangesalary = rangesalary.replace(".","");
                        rangesalary = rangesalary.replace("PHP","");
                        rangesalary = rangesalary.replace("Php","");
                        if(rangesalary.contains("-"))
                        {
                            String []parts = rangesalary.split("-");
                            salary1 = Integer.valueOf(parts[0].trim());
                            System.out.println(salary1);
                        }
                        else
                        {
                            salary1 = Integer.valueOf(rangesalary.trim());
                            System.out.println(salary1);
                        }
                        if(catergoryString.equals("Specialization") && salaryString.equals("Salary/Minimum") && !TextUtils.isEmpty(yearString))
                        {
                            if(details.getJoblocation().contains(yearString))
                            {
                                w.add(details);
                            }
                        }
                        else if(yearString.equals("Region") && salaryString.equals("Salary/Minimum") && !TextUtils.isEmpty(catergoryString))
                        {
                            if(details.getJobcategory().contains(catergoryString))
                            {
                                w.add(details);
                            }
                        }
                        else if(yearString.equals("Region") && catergoryString.equals("Specialization")&&!TextUtils.isEmpty(salaryString))
                        {
                            if(String.valueOf(salary1).contains(salaryString))
                            {
                                w.add(details);
                                // Toast.makeText(getContext(),"add statonly",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(catergoryString.equals("Specialization")  && !TextUtils.isEmpty(yearString) && !TextUtils.isEmpty(salaryString))
                        {
                            if(details.getJoblocation().contains(yearString) && String.valueOf(salary1).contains(salaryString))
                            {
                                w.add(details);
                                // Toast.makeText(getContext(),"add spe",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(yearString.equals("Region")  && !TextUtils.isEmpty(catergoryString) && !TextUtils.isEmpty(salaryString))
                        {
                            if(details.getJobcategory().contains(catergoryString) && String.valueOf(salary1).contains(salaryString))
                            {
                                w.add(details);
                                // Toast.makeText(getContext(),"add reg",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(salaryString.equals("Salary/Minimum")  && !TextUtils.isEmpty(catergoryString) && !TextUtils.isEmpty(yearString))
                        {
                            if(details.getJobcategory().contains(catergoryString) && details.getJoblocation().contains(yearString))
                            {
                                w.add(details);
                                // Toast.makeText(getContext(),"add stat",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            if(details.getJobcategory().contains(catergoryString) && details.getJoblocation().contains(yearString) && String.valueOf(salary1).contains(salaryString))
                            {
                                w.add(details);
                            }
                        }

                    }
                }
                jobadapter2.setWinnerDetails(w);
            }
        });
  //      getPost();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("jobpost", Context.MODE_PRIVATE);
//        spinnercategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                catergoryString = (String) parent.getItemAtPosition(position);
//                spinnercategory.setSelection(position);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                catergoryString = "";
//
//            }
//        });
//        spinnerlocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                yearString = (String) parent.getItemAtPosition(position);
//                spinnerlocation.setSelection(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                yearString = "";
//
//            }
//        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ln_networkjobsearcherror.setVisibility(View.GONE);
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

    private void getCategory() {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.categoryfilter, response ->{
            JSONObject j = null;
            try{
                j = new JSONObject(response);
                result = j.getJSONArray("categories");
                getSubCategory(result);
//                main.setVisibility(View.VISIBLE);
                ln_networkjobsearcherror.setVisibility(View.GONE);


            }catch(JSONException e)
            {
                e.printStackTrace();
                Toast.makeText(ApplicantSavedJobActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                main.setVisibility(View.GONE);
                ln_networkjobsearcherror.setVisibility(View.VISIBLE);
                networkrefresh.setVisibility(View.VISIBLE);
            }

            refreshLayout.setRefreshing(false);
            networkrefresh.setRefreshing(false);

        },error -> {
            error.printStackTrace();
            refreshLayout.setRefreshing(false);
            networkrefresh.setRefreshing(false);

            main.setVisibility(View.GONE);
            ln_networkjobsearcherror.setVisibility(View.VISIBLE);
            networkrefresh.setVisibility(View.VISIBLE);
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ApplicantSavedJobActivity.this);
        queue.add(request);
    }

    private void getSubCategory(JSONArray j) {
        for(int ai=0;ai<j.length();ai++)
        {
            try{
                JSONObject json = j.getJSONObject(ai);
                category.add(json.getString("category"));
//                main.setVisibility(View.VISIBLE);
                ln_networkjobsearcherror.setVisibility(View.GONE);
            }catch (JSONException e)
            {
                e.printStackTrace();
                main.setVisibility(View.GONE);
                ln_networkjobsearcherror.setVisibility(View.VISIBLE);
            }
        }
        specializationarray = category.toArray(new String[0]);
        //Toast.makeText(AddWorkExperience.this, specializationarray[ai], Toast.LENGTH_SHORT).show();
        selectedspecialization = new boolean[specializationarray.length];
//        spinnercategory.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, category));
    }

    private void getLocation() {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.locationfilter, response ->{
            JSONObject j = null;
            try{
                j = new JSONObject(response);
                result2 = j.getJSONArray("locations");
                getSubLocation(result2);
//                main.setVisibility(View.VISIBLE);
                ln_networkjobsearcherror.setVisibility(View.GONE);


            }catch(JSONException e)
            {
                e.printStackTrace();
                Toast.makeText(ApplicantSavedJobActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                main.setVisibility(View.GONE);
                ln_networkjobsearcherror.setVisibility(View.VISIBLE);
                networkrefresh.setVisibility(View.VISIBLE);
            }

            refreshLayout.setRefreshing(false);
            networkrefresh.setRefreshing(false);

            recyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });

        },error -> {
            error.printStackTrace();
            refreshLayout.setRefreshing(false);
            networkrefresh.setRefreshing(false);

            main.setVisibility(View.GONE);
            ln_networkjobsearcherror.setVisibility(View.VISIBLE);
            networkrefresh.setVisibility(View.VISIBLE);
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ApplicantSavedJobActivity.this);
        queue.add(request);
    }

    private void getSubLocation(JSONArray j) {
        for(int ai=0;ai<j.length();ai++)
        {
            try{
                JSONObject json = j.getJSONObject(ai);
                location.add(json.getString("region"));
//                main.setVisibility(View.VISIBLE);
                ln_networkjobsearcherror.setVisibility(View.GONE);
            }catch (JSONException e)
            {
                e.printStackTrace();
                main.setVisibility(View.GONE);
                ln_networkjobsearcherror.setVisibility(View.VISIBLE);
            }
        }
        locationarray = location.toArray(new String[0]);
        //Toast.makeText(AddWorkExperience.this, specializationarray[ai], Toast.LENGTH_SHORT).show();
        selectedlocation = new boolean[locationarray.length];
//        spinnercategory.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, category));
    }

    private void getPost() {
        if(new CheckInternet().checkInternet(ApplicantSavedJobActivity.this))
        {
            StringRequest request = new StringRequest(Request.Method.GET, Constant.postsavedjob+"?applicant_id="+user_id, response ->{
                try{
                    JSONObject object = new JSONObject(response);
                    if(object.getBoolean("success"))
                    {
                        JSONArray array = new JSONArray(object.getString("jobpost"));
                        for(int i = 0; i < array.length(); i++)
                        {
                            JSONObject postObject = array.getJSONObject(i);
                            //JSONObject getpostObject = postObject.getJSONObject("jobposts");

                            job job2 = new job();
                            job2.setJoblogo(postObject.getString("logo"));
                            job2.setJobtitle(postObject.getString("jobtitle"));
                            job2.setJobcompany(postObject.getString("companyname"));
                            job2.setJoblocation(postObject.getString("location"));
                            job2.setJobsalary(postObject.getString("salary"));
                            job2.setJobdateposted(postObject.getString("created_at"));
                            job2.setJobaddress(postObject.getString("address"));
                            job2.setCompanyoverview(postObject.getString("companyoverview"));
                            job2.setJobcategory(postObject.getString("category"));
                            job2.setJobid(postObject.getString("job_id"));
                            job2.setJobdescription(postObject.getString("jobdescription"));
                            job2.setJobuniqueid(postObject.getString("id"));
                            job2.setJobstatus(postObject.getString("jobstatus"));
                            job2.setSavedid(postObject.getString("saved_id"));

                            arraylist.add(job2);
                            arraylist2.add(job2);
                        }
                        jobadapter2 = new jobadapter(arraylist,ApplicantSavedJobActivity.this,listener);
                        recyclerView.setAdapter(jobadapter2);
                        recyclerView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return false;
                            }
                        });
                        if(arraylist.isEmpty())
                        {
                            main.setVisibility(View.GONE);
                            ln_nobookmarkedjoblayout.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                           // main.setVisibility(View.VISIBLE);
                            ln_nobookmarkedjoblayout.setVisibility(View.GONE);
                            ln_networkjobsearcherror.setVisibility(View.GONE);
                            networkrefresh.setVisibility(View.GONE);
                            safefilter();
                        }
                    }
                    else {
                        Toast.makeText(ApplicantSavedJobActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                        main.setVisibility(View.GONE);
                        ln_networkjobsearcherror.setVisibility(View.VISIBLE);
                        networkrefresh.setVisibility(View.VISIBLE);
                    }
                }catch(JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(ApplicantSavedJobActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                    main.setVisibility(View.GONE);
                    ln_networkjobsearcherror.setVisibility(View.VISIBLE);
                    networkrefresh.setVisibility(View.VISIBLE);
                }

                refreshLayout.setRefreshing(false);
                networkrefresh.setRefreshing(false);

            },error -> {
                error.printStackTrace();
                refreshLayout.setRefreshing(false);
                networkrefresh.setRefreshing(false);
                main.setVisibility(View.GONE);
                ln_networkjobsearcherror.setVisibility(View.VISIBLE);
                networkrefresh.setVisibility(View.VISIBLE);
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> map = new HashMap<>();
                    return map;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(ApplicantSavedJobActivity.this);
            queue.add(request);
        }
        else
        {
            main.setVisibility(View.GONE);
            ln_networkjobsearcherror.setVisibility(View.VISIBLE);
            refreshLayout.setRefreshing(false);
            networkrefresh.setRefreshing(false);
            networkrefresh.setVisibility(View.VISIBLE);
        }
    }
    public void onResume() {
        super.onResume();
        delay();
        drawerLayout.closeDrawers();
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        getCategory();
        getLocation();
        getPost();
        arraylist.clear();
        category.clear();
        location.clear();
    }

    private void setOnClickListener() {
        listener = new jobadapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(ApplicantSavedJobActivity.this, ApplicantJobDescriptionActivity.class);
                intent.putExtra("intentjob_id",arraylist.get(position).getJobid());
                intent.putExtra("intentid",arraylist.get(position).getJobuniqueid());
                intent.putExtra("intentjoblogo",Constant.URL+"/storage/profiles/"+arraylist.get(position).getJoblogo());
                intent.putExtra("jobcompanylogo2",arraylist.get(position).getJoblogo());
                intent.putExtra("intentjobtitle",arraylist.get(position).getJobtitle());
                intent.putExtra("intentjobcompany",arraylist.get(position).getJobcompany());
                intent.putExtra("intentjobdescription",arraylist.get(position).getJobdescription());
                intent.putExtra("intentjobcompanyoverview",arraylist.get(position).getCompanyoverview());
                intent.putExtra("intentjoblocation",arraylist.get(position).getJoblocation());
                intent.putExtra("intentjobaddress",arraylist.get(position).getJobaddress());
                intent.putExtra("intentjobspecialization",arraylist.get(position).getJobcategory());
                intent.putExtra("intentjobsalary",arraylist.get(position).getJobsalary());
                intent.putExtra("intentjobposted",arraylist.get(position).getJobdateposted());
                intent.putExtra("intentjobstatus",arraylist.get(position).getJobstatus());
                intent.putExtra("intentsavedid",arraylist.get(position).getSavedid());
                intent.putExtra("intentremove","Remove");
                intent.putExtra("intent1","1");
                startActivity(intent);
            }
        };
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
                Intent intent = new Intent(ApplicantSavedJobActivity.this, ApplicantActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.navigation_savedjob:
                drawerLayout.closeDrawers();
                break;
            case R.id.navigation_applicationhistory:
                Intent intent3 = new Intent(ApplicantSavedJobActivity.this, ApplicantHistoryActivity.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent3.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent3.putExtra("name",textnameprofile.getText().toString());
                intent3.putExtra("profile_pic", getIntent().getStringExtra("profile_pic"));
                startActivity(intent3);
                break;
            case R.id.navigation_logout:
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
                builder.setMessage("Do you want to logout?");
                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        SharedPreferences.Editor editor = userPref.edit();
                        //                       editor.clear();
                        //                      editor.apply();
                        sessionManager.setApplicantLogin(false);
                        Intent ia = new Intent(ApplicantSavedJobActivity.this, MainActivity.class);
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
    private void safefilter()
    {
        catergoryString = tvsearchspecialization.getText().toString();
        yearString = tvsearchlocation.getText().toString();
        salaryString = tvsearchsalaryminimum.getText().toString();
        ArrayList<job> w = new ArrayList<>();
        if(catergoryString.equals("Specialization") && yearString.equals("Region") && salaryString.equals("Salary/Minimum"))
        {

            w.addAll(arraylist);
        }
        else
        {
            for(job details : arraylist)
            {
                rangesalary = details.getJobsalary();
                rangesalary = rangesalary.replace(",","");
                rangesalary = rangesalary.replace(".","");
                rangesalary = rangesalary.replace("PHP","");
                rangesalary = rangesalary.replace("Php","");
                if(rangesalary.contains("-"))
                {
                    String []parts = rangesalary.split("-");
                    salary1 = Integer.valueOf(parts[0].trim());
                    System.out.println(salary1);
                }
                else
                {
                    salary1 = Integer.valueOf(rangesalary.trim());
                    System.out.println(salary1);
                }
                if(catergoryString.equals("Specialization") && salaryString.equals("Salary/Minimum") && !TextUtils.isEmpty(yearString))
                {
                    if(details.getJoblocation().contains(yearString))
                    {
                        w.add(details);
                    }
                }
                else if(yearString.equals("Region") && salaryString.equals("Salary/Minimum") && !TextUtils.isEmpty(catergoryString))
                {
                    if(details.getJobcategory().contains(catergoryString))
                    {
                        w.add(details);
                    }
                }
                else if(yearString.equals("Region") && catergoryString.equals("Specialization")&&!TextUtils.isEmpty(salaryString))
                {
                    if(String.valueOf(salary1).contains(salaryString))
                    {
                        w.add(details);
                        // Toast.makeText(getContext(),"add statonly",Toast.LENGTH_SHORT).show();
                    }
                }
                else if(catergoryString.equals("Specialization")  && !TextUtils.isEmpty(yearString) && !TextUtils.isEmpty(salaryString))
                {
                    if(details.getJoblocation().contains(yearString) && String.valueOf(salary1).contains(salaryString))
                    {
                        w.add(details);
                        // Toast.makeText(getContext(),"add spe",Toast.LENGTH_SHORT).show();
                    }
                }
                else if(yearString.equals("Region")  && !TextUtils.isEmpty(catergoryString) && !TextUtils.isEmpty(salaryString))
                {
                    if(details.getJobcategory().contains(catergoryString) && String.valueOf(salary1).contains(salaryString))
                    {
                        w.add(details);
                        // Toast.makeText(getContext(),"add reg",Toast.LENGTH_SHORT).show();
                    }
                }
                else if(salaryString.equals("Salary/Minimum")  && !TextUtils.isEmpty(catergoryString) && !TextUtils.isEmpty(yearString))
                {
                    if(details.getJobcategory().contains(catergoryString) && details.getJoblocation().contains(yearString))
                    {
                        w.add(details);
                        // Toast.makeText(getContext(),"add stat",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    if(details.getJobcategory().contains(catergoryString) && details.getJoblocation().contains(yearString) && String.valueOf(salary1).contains(salaryString))
                    {
                        w.add(details);
                    }
                }

            }
        }
        jobadapter2.setWinnerDetails(w);
    }
    public void delay()
    {
        main.setVisibility(View.GONE);
        ln_nobookmarkedjoblayout.setVisibility(View.GONE);
        CDT = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {
                ln_delay.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                if(new CheckInternet().checkInternet(getApplicationContext())) {
                    ln_delay.setVisibility(View.GONE);
                    main.setVisibility(View.VISIBLE);
                    ln_networkjobsearcherror.setVisibility(View.GONE);
                }
                else
                {
                    ln_delay.setVisibility(View.GONE);
                    main.setVisibility(View.GONE);
                    ln_networkjobsearcherror.setVisibility(View.VISIBLE);
                }


            }
        }.start();

    }
    public void setFilterDelayAnimation()
    {
        ln_delay.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        CDT = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {
                ln_delay.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                ln_delay.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

            }
        }.start();
        if(!expand)
        {
            ValueAnimator va = ValueAnimator.ofInt(100,260);
            va.setDuration(400);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    Integer value = (Integer) valueAnimator.getAnimatedValue();
                    searchlayout.getLayoutParams().height = value.intValue();
                    searchlayout.requestLayout();
                }
            });
            va.start();
            expand = true;
        }
        else
        {
            ValueAnimator va = ValueAnimator.ofInt(260,100);
            va.setDuration(400);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    Integer value = (Integer) valueAnimator.getAnimatedValue();
                    searchlayout.getLayoutParams().height = value.intValue();
                    searchlayout.requestLayout();
                }
            });
            va.start();
            expand = false;
        }
    }
    public void setColorAnimation()
    {
        if(catergoryString.equals("Specialization") && yearString.equals("Region") && salaryString.equals("Salary/Minimum"))
        {
            int color = Color.parseColor("#FF6200EE");
            filterbutton.setColorFilter(color);
        }
        else
        {
            int color2 = Color.parseColor("#E91E47");
            filterbutton.setColorFilter(color2);
        }
    }
}