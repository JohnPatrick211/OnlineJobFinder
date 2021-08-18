package com.example.onlinejobfinder.employer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.adapter.appliedapplicantsadapter;
import com.example.onlinejobfinder.adapter.jobadapter;
import com.example.onlinejobfinder.applicant.ApplicantJobDescriptionActivity;
import com.example.onlinejobfinder.model.appliedapplicants;
import com.example.onlinejobfinder.model.job;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewApplyApplicantActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_apply_applicant);


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
        id = getIntent().getExtras().getString("intentid");
        approved = "Approved";

//        category.add("Category");
//        category.add("Accountant");
//        category.add("Programmer");
        //  location.add("Region");
        //  location.add("Region 4-A");
        //  location.add("Region 3");
        //  spinnerlocation.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, location));
        refreshLayout.setRefreshing(true);
        setOnClickListener();
        ln_delay = findViewById(R.id.ln_delayloadinglayout);
        main = findViewById(R.id.bruh);
        main.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.GONE);
        getSupportActionBar().setCustomView(R.layout.customactionbarapplyapplicants);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        delay();
        //getCategory();
        // getLocation();
//        getPost();
        //SharedPreferences sharedPreferences = getContext().getApplicationContext().getSharedPreferences("jobpost", Context.MODE_PRIVATE);
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

    private void getPost() {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.employerapplyjobpost+"?applyjob_id="+id, response ->{
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
                    appliedapplicantsadapter2 = new appliedapplicantsadapter(arraylist,ViewApplyApplicantActivity.this,listener);
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
                        Toast.makeText(ViewApplyApplicantActivity.this,"arraylist is empty",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ViewApplyApplicantActivity.this,"error",Toast.LENGTH_SHORT).show();
                    ln_noappplicantsappliedlayout.setVisibility(View.GONE);
                }
            }catch(JSONException e)
            {
                e.printStackTrace();
                Toast.makeText(ViewApplyApplicantActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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

        RequestQueue queue = Volley.newRequestQueue(ViewApplyApplicantActivity.this);
        queue.add(request);
    }
    public void onResume() {
        super.onResume();
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
                Intent intent = new Intent(ViewApplyApplicantActivity.this, ViewAcceptRejectApplicantActivity.class);//                intent.putExtra("intentjob_id",arraylist.get(position).getJobid());
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
                Toast.makeText(ViewApplyApplicantActivity.this,"Success",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        };
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
                ln_delay.setVisibility(View.GONE);
                refreshLayout.setVisibility(View.VISIBLE);
                main.setVisibility(View.VISIBLE);

            }
        }.start();

    }
}