package com.example.onlinejobfinder.applicant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.onlinejobfinder.adapter.applicanthistoryadapter;
import com.example.onlinejobfinder.adapter.employerjobcountadapter;
import com.example.onlinejobfinder.employer.ViewApplyApplicantActivity;
import com.example.onlinejobfinder.model.applicanthistory;
import com.example.onlinejobfinder.model.job;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ApplicantHistoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    SessionManager sessionManager;
    RecyclerView recyclerView;
    View ln_nojobappplicantlayout;
    EditText edt_search;
    SharedPreferences userPref2;
    String name2, user_id,token,email;
    applicanthistoryadapter.RecyclerViewClickListener listener;
    int position =0;
    int position2 =0;
    boolean[] selectedspecialization, selectedlocation;
    ArrayList<Integer> Specialization = new ArrayList<>();
    TextView btnfilter,tvsearchspecialization,tvsearchlocation;
    ArrayList<applicanthistory> arraylist;
    ArrayList<applicanthistory> arraylist2;
    ArrayList<String> category,location;
    JSONArray result,result2;
    SwipeRefreshLayout refreshLayout,networkrefresh;
    TextView tv_networkerrorrefresh;
    LinearLayout main;
    LinearLayout ln_networkjobsearcherror;
    applicanthistoryadapter jobadapter2;
    // Spinner spinnercategory, spinnerlocation;
    String catergoryString,yearString, approved;
    String [] specializationarray, locationarray;
    View ln_delay;
    CountDownTimer CDT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_history);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.customactionbarmaintitle);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        sessionManager = new SessionManager(getApplicationContext());
        btnfilter = findViewById(R.id.btn_employerfilter);
        tvsearchspecialization = findViewById(R.id.tv_searchemployerspecialization);
        tvsearchlocation  = findViewById(R.id.tv_searchemployerlocation);
        recyclerView = findViewById(R.id.recyclerview_jobs);
        edt_search = findViewById(R.id.search);
        recyclerView.setLayoutManager(new LinearLayoutManager(ApplicantHistoryActivity.this));
        refreshLayout = findViewById(R.id.employerswipe);
//        spinnercategory = view.findViewById(R.id.spinner_category);
        //       spinnerlocation = view.findViewById(R.id.spinner_location);
        arraylist = new ArrayList<>();
        arraylist2 = new ArrayList<>();
        category = new ArrayList<String>();
        location = new ArrayList<String>();
        approved = "Approved";
        userPref2 = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        name2 = userPref2.getString("name","name");
        email = userPref2.getString("email","email");
        user_id = userPref2.getString("id","id");
        token = userPref2.getString("token","token");
        networkrefresh = findViewById(R.id.networkswipe);
        ln_nojobappplicantlayout = findViewById(R.id.ln_nojobapplicantlayout);
        ln_delay = findViewById(R.id.ln_delayloadinglayout);
        main = findViewById(R.id.bruh);
        main.setVisibility(View.GONE);
        tv_networkerrorrefresh = findViewById(R.id.tv_networkjobsearcherrorrefresh);
        ln_networkjobsearcherror = findViewById(R.id.networkjobsearcherrorlayout);
        ln_networkjobsearcherror.setVisibility(View.GONE);
        main.setVisibility(View.GONE);

        navigationView.setNavigationItemSelectedListener(this);

        delay();

        tv_networkerrorrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ln_networkjobsearcherror.setVisibility(View.GONE);
                networkrefresh.setRefreshing(true);
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
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                jobadapter2.getFilter().filter(charSequence.toString().toLowerCase());
                tvsearchspecialization.setText("Specialization");
                tvsearchlocation.setText("Region");
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
        tvsearchspecialization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        ApplicantHistoryActivity.this
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
                        ApplicantHistoryActivity.this
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
                catergoryString = tvsearchspecialization.getText().toString();
                yearString = tvsearchlocation.getText().toString();
                ArrayList<applicanthistory> w = new ArrayList<>();
                if(catergoryString.equals("Specialization") && yearString.equals("Region"))
                {

                    w.addAll(arraylist);
                }
                else
                {
                    for(applicanthistory details : arraylist)
                    {
                        if(catergoryString.equals("Specialization") && !TextUtils.isEmpty(yearString))
                        {
                            if(details.getJoblocation().contains(yearString))
                            {
                                w.add(details);
                            }
                        }
                        else if(yearString.equals("Region") && !TextUtils.isEmpty(catergoryString))
                        {
                            if(details.getJobcategory().contains(catergoryString))
                            {
                                w.add(details);
                            }
                        }
                        else
                        {
                            if(details.getJobcategory().contains(catergoryString) && details.getJoblocation().contains(yearString))
                            {
                                w.add(details);
                            }
                        }

                    }
                }
                jobadapter2.setWinnerDetails(w);
            }
        });
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
                Toast.makeText(ApplicantHistoryActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                main.setVisibility(View.GONE);

                ln_networkjobsearcherror.setVisibility(View.VISIBLE);
                networkrefresh.setVisibility(View.VISIBLE);
                ln_nojobappplicantlayout.setVisibility(View.GONE);
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
            ln_nojobappplicantlayout.setVisibility(View.GONE);
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ApplicantHistoryActivity.this);
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
                ln_nojobappplicantlayout.setVisibility(View.GONE);
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
                Toast.makeText(ApplicantHistoryActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                main.setVisibility(View.GONE);
                ln_networkjobsearcherror.setVisibility(View.VISIBLE);
                networkrefresh.setVisibility(View.VISIBLE);
                ln_nojobappplicantlayout.setVisibility(View.GONE);

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
            ln_nojobappplicantlayout.setVisibility(View.GONE);
            ln_networkjobsearcherror.setVisibility(View.VISIBLE);
            networkrefresh.setVisibility(View.VISIBLE);
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ApplicantHistoryActivity.this);
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
                ln_nojobappplicantlayout.setVisibility(View.GONE);
                ln_networkjobsearcherror.setVisibility(View.VISIBLE);

            }
        }
        locationarray = location.toArray(new String[0]);
        //Toast.makeText(AddWorkExperience.this, specializationarray[ai], Toast.LENGTH_SHORT).show();
        selectedlocation = new boolean[locationarray.length];
//        spinnercategory.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, category));
    }

    private void getPost() {
        if(new CheckInternet().checkInternet(ApplicantHistoryActivity.this))
        {
            StringRequest request = new StringRequest(Request.Method.GET, Constant.applicationhistory+"?applicant_id="+user_id, response ->{
                try{
                    JSONObject object = new JSONObject(response);
                    if(object.getBoolean("success"))
                    {
                        JSONArray array = new JSONArray(object.getString("jobpost"));
                        for(int i = 0; i < array.length(); i++)
                        {
                            JSONObject postObject = array.getJSONObject(i);
                            //JSONObject getpostObject = postObject.getJSONObject("jobposts");

                            applicanthistory job2 = new applicanthistory();
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
                            job2.setJobstatus(postObject.getString("status"));

                            arraylist.add(job2);
                            //arraylist2.add(job2);
                        }
                        jobadapter2 = new applicanthistoryadapter(arraylist,ApplicantHistoryActivity.this,listener);
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
                            ln_nojobappplicantlayout.setVisibility(View.VISIBLE);
                        }
                        else {
                            ln_nojobappplicantlayout.setVisibility(View.GONE);
                            main.setVisibility(View.VISIBLE);
                            ln_networkjobsearcherror.setVisibility(View.GONE);
                            networkrefresh.setVisibility(View.GONE);
                            safefilter();
                        }

                    }
                    else {
                        Toast.makeText(ApplicantHistoryActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                        main.setVisibility(View.GONE);
                        ln_nojobappplicantlayout.setVisibility(View.GONE);
                        ln_networkjobsearcherror.setVisibility(View.VISIBLE);
                        networkrefresh.setVisibility(View.VISIBLE);
                    }
                }catch(JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(ApplicantHistoryActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                    main.setVisibility(View.GONE);
                    ln_nojobappplicantlayout.setVisibility(View.GONE);
                    ln_networkjobsearcherror.setVisibility(View.VISIBLE);
                    networkrefresh.setVisibility(View.VISIBLE);
                }

                refreshLayout.setRefreshing(false);
                networkrefresh.setRefreshing(false);

            },error -> {
                error.printStackTrace();
                ln_nojobappplicantlayout.setVisibility(View.GONE);
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

            RequestQueue queue = Volley.newRequestQueue(ApplicantHistoryActivity.this);
            queue.add(request);
        }
        else
        {
            main.setVisibility(View.GONE);
            ln_nojobappplicantlayout.setVisibility(View.GONE);
            ln_networkjobsearcherror.setVisibility(View.VISIBLE);
            refreshLayout.setRefreshing(false);
            networkrefresh.setRefreshing(false);
            networkrefresh.setVisibility(View.VISIBLE);
        }

    }
    public void onResume() {
        super.onResume();
        drawerLayout.closeDrawers();
        delay();
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        arraylist.clear();
        category.clear();
        location.clear();
        getCategory();
        getLocation();
        getPost();

    }

    private void setOnClickListener() {
        listener = new  applicanthistoryadapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(ApplicantHistoryActivity.this, ApplicantHistoryViewJobActivity.class);
                intent.putExtra("intentjob_id",arraylist.get(position).getJobid());
                intent.putExtra("intentid",arraylist.get(position).getJobuniqueid());
                intent.putExtra("intentjoblogo",Constant.URL+"/storage/profiles/"+arraylist.get(position).getJoblogo());
                intent.putExtra("intentjobtitle",arraylist.get(position).getJobtitle());
                intent.putExtra("intentjobcompany",arraylist.get(position).getJobcompany());
                intent.putExtra("intentjobdescription",arraylist.get(position).getJobdescription());
                intent.putExtra("intentjobcompanyoverview",arraylist.get(position).getCompanyoverview());
                intent.putExtra("intentjoblocation",arraylist.get(position).getJoblocation());
                intent.putExtra("intentjobaddress",arraylist.get(position).getJobaddress());
                intent.putExtra("intentjobspecialization",arraylist.get(position).getJobcategory());
                intent.putExtra("intentjobsalary",arraylist.get(position).getJobsalary());
                SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = null;
                try {
                    date = df.parse(arraylist.get(position).getJobdateposted());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                df.applyPattern("MMM dd, yyyy");
                String newDate = df.format(date);  //Output: newDate = "13/09/2014"
                intent.putExtra("intentjobposted",newDate);
                intent.putExtra("intentjobstatus",arraylist.get(position).getJobstatus());
                intent.putExtra("jobcompanylogo2",arraylist.get(position).getJoblogo());
                startActivity(intent);
            }
        };
    }
    private void safefilter()
    {
        catergoryString = tvsearchspecialization.getText().toString();
        yearString = tvsearchlocation.getText().toString();
        ArrayList<applicanthistory> w = new ArrayList<>();
        if(catergoryString.equals("Specialization") && yearString.equals("Region"))
        {

            w.addAll(arraylist);
        }
        else
        {
            for(applicanthistory details : arraylist)
            {
                if(catergoryString.equals("Specialization") && !TextUtils.isEmpty(yearString))
                {
                    if(details.getJoblocation().contains(yearString))
                    {
                        w.add(details);
                    }
                }
                else if(yearString.equals("Region") && !TextUtils.isEmpty(catergoryString))
                {
                    if(details.getJobcategory().contains(catergoryString))
                    {
                        w.add(details);
                    }
                }
                else
                {
                    if(details.getJobcategory().contains(catergoryString) && details.getJoblocation().contains(yearString))
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
        CDT = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {
                ln_delay.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                if(new CheckInternet().checkInternet(ApplicantHistoryActivity.this)) {
                    ln_delay.setVisibility(View.GONE);

                }
                else
                {
                    ln_delay.setVisibility(View.GONE);
                    main.setVisibility(View.GONE);
                    ln_networkjobsearcherror.setVisibility(View.VISIBLE);
                    networkrefresh.setVisibility(View.VISIBLE);
                }

            }
        }.start();

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
                Intent intent3 = new Intent(ApplicantHistoryActivity.this, ApplicantActivity.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent3.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent3);
                break;
            case R.id.navigation_testemail:
                Intent intent1 = new Intent(ApplicantHistoryActivity.this, EmailActivity.class);
                startActivity(intent1);
                break;
            case R.id.navigation_savedjob:
                Intent intent2 = new Intent(ApplicantHistoryActivity.this, ApplicantSavedJobActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent2);
                break;
            case R.id.navigation_applicationhistory:
                drawerLayout.closeDrawers();
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
                        Intent ia = new Intent(ApplicantHistoryActivity.this, MainActivity.class);
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
}