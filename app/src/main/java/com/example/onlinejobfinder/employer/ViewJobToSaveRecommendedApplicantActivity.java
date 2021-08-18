package com.example.onlinejobfinder.employer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.adapter.employerjobadapter;
import com.example.onlinejobfinder.adapter.viewemployerjobadapter;
import com.example.onlinejobfinder.applicant.ApplicantFinalCheckProfileActivity;
import com.example.onlinejobfinder.model.job;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewJobToSaveRecommendedApplicantActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SharedPreferences userPref2;
    View ln_nojobappplicantlayout;
    String name2, user_id,token,email;
    viewemployerjobadapter.RecyclerViewClickListener listener;
    int position =0;
    int position2 =0;
    boolean[] selectedspecialization, selectedlocation;
    ArrayList<Integer> Specialization = new ArrayList<>();
    TextView btnfilter,tvsearchspecialization,tvsearchlocation;
    ArrayList<job> arraylist;
    ArrayList<job> arraylist2;
    ArrayList<String> category,location;
    JSONArray result,result2;
    SwipeRefreshLayout refreshLayout;
    viewemployerjobadapter jobadapter2;
    // Spinner spinnercategory, spinnerlocation;
    String catergoryString,yearString, approved;
    String [] specializationarray, locationarray;
    String iapplicant_id,iname,iemail,iaddress,igender,icontactno,istatus,iprofile_pic,jobapply_id;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job_to_save_recommended_applicant);


        recyclerView = findViewById(R.id.recyclerview_saverecommendedapplicants);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewJobToSaveRecommendedApplicantActivity.this));
        refreshLayout = findViewById(R.id.saverecommendedswipe);
//        spinnercategory = view.findViewById(R.id.spinner_category);
        //       spinnerlocation = view.findViewById(R.id.spinner_location);
        arraylist = new ArrayList<>();
        arraylist2 = new ArrayList<>();
        category = new ArrayList<String>();
        location = new ArrayList<String>();
        ln_nojobappplicantlayout = findViewById(R.id.ln_nojobapplicantlayout);
        approved = "Approved";
        userPref2 = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        name2 = userPref2.getString("name","name");
        email = userPref2.getString("email","email");
        user_id = userPref2.getString("id","id");
        token = userPref2.getString("token","token");
        iapplicant_id = getIntent().getExtras().getString("intentapplicant_id");
        iemail = getIntent().getExtras().getString("intentemail");
        iname = getIntent().getExtras().getString("intentname");
        igender = getIntent().getExtras().getString("intentgender");
        iprofile_pic = getIntent().getExtras().getString("intentprofile_pic");
        iaddress = getIntent().getExtras().getString("intentaddress");
        istatus = getIntent().getExtras().getString("intentstatus");
        icontactno = getIntent().getExtras().getString("intentcontactno");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        getSupportActionBar().setCustomView(R.layout.customactionbarmaintitle);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        category.add("Category");
//        category.add("Accountant");
//        category.add("Programmer");
        //  location.add("Region");
        //  location.add("Region 4-A");
        //  location.add("Region 3");
        //  spinnerlocation.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, location));
        refreshLayout.setRefreshing(true);
        setOnClickListener();


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
        StringRequest request = new StringRequest(Request.Method.GET, Constant.intenttoapprovejobpost+"?jobstatus="+approved+"&job_id="+user_id, response ->{
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

                        arraylist.add(job2);
                        arraylist2.add(job2);
                    }
                    jobadapter2 = new viewemployerjobadapter(arraylist,ViewJobToSaveRecommendedApplicantActivity.this,listener);
                    recyclerView.setAdapter(jobadapter2);
                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    if(arraylist.isEmpty())
                    {
                        recyclerView.setVisibility(View.GONE);
                        ln_nojobappplicantlayout.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        recyclerView.setVisibility(View.VISIBLE);
                        ln_nojobappplicantlayout.setVisibility(View.GONE);
                    }
                }
                else {
                    Toast.makeText(ViewJobToSaveRecommendedApplicantActivity.this,"error",Toast.LENGTH_SHORT).show();
                }
            }catch(JSONException e)
            {
                e.printStackTrace();
                Toast.makeText(ViewJobToSaveRecommendedApplicantActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            refreshLayout.setRefreshing(false);

        },error -> {
            error.printStackTrace();
            refreshLayout.setRefreshing(false);
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ViewJobToSaveRecommendedApplicantActivity.this);
        queue.add(request);
    }

    public void onResume() {
        super.onResume();
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        getPost();
        arraylist.clear();

    }

    private void setOnClickListener() {
        listener = new viewemployerjobadapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                progressDialog.setMessage("Checking");
                progressDialog.show();
                jobapply_id = arraylist.get(position).getJobuniqueid();
                StringRequest request = new StringRequest(Request.Method.POST, Constant.checkrecommendedapplicants+"?applyjob_id="+jobapply_id+"&applicant_id="+iapplicant_id, response -> {
                    try{
                        JSONObject object= new JSONObject(response);
                        if(object.getBoolean("success")){
                            postSaved();
                            progressDialog.cancel();
                        }
                        else
                        {
                            Toast.makeText(ViewJobToSaveRecommendedApplicantActivity.this,"Applicant Already Saved On This Job",Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }

                    }catch(JSONException e)
                    {
                        Toast.makeText(ViewJobToSaveRecommendedApplicantActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                        refreshLayout.setRefreshing(false);
                    }
                },error ->{
                    error.printStackTrace();
                    Toast.makeText(ViewJobToSaveRecommendedApplicantActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                    refreshLayout.setRefreshing(false);

                })
                {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> map = new HashMap<>();
                        map.put("applicant_id",iapplicant_id);
                        map.put("applyjob_id",jobapply_id);
                        return map;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(ViewJobToSaveRecommendedApplicantActivity.this);
                queue.add(request);
//                Intent intent = new Intent(ViewJobToSaveRecommendedApplicantActivity.this, ViewApplyApplicantActivity.class);
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
//                startActivity(intent);
            }
        };
    }

    private void postSaved() {
        progressDialog.setMessage("Saving");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constant.applyjob, response -> {
//                StringRequest request = new StringRequest(Request.Method.POST, Constant.SAVE_USER_PROFILE, response -> {
            try{
                JSONObject object= new JSONObject(response);
                if(object.getBoolean("success")){
                    JSONObject user = object.getJSONObject("update");
//                                SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = userPref.edit();
//                                editor.putString("id",user.getString("applicant_id"));
                    //editor.putString("file_path",user.getString("file_path"));
                    //                           Intent i = new Intent(UploadProfileRegister.this, MainActivity.class);
                    //                           startActivity(i);
                    progressDialog.cancel();

//                                editor.apply();
//                                editor.commit();
                    Toast.makeText(ViewJobToSaveRecommendedApplicantActivity.this,"Saved Successfully",Toast.LENGTH_SHORT).show();
                    // if(role.equals("employer"))
                    // {
                    //   Intent ia = new Intent(EditProfileActivity.this, GuestActivity.class);
                    // startActivity(ia);
                    //   finish();
                    // }
                    // else
                    // {
                    //      Intent i = new Intent(UploadProfileRegister.this, MainActivity.class);
                    //      startActivity(i);
                    //      finish();
                    //  }

//                            Intent i = new Intent(RegisterActivity.this,UploadProfileRegister.class);
//                            startActivity(i);
                    onBackPressed();


                }
                else
                {
                    Toast.makeText(ViewJobToSaveRecommendedApplicantActivity.this, "Error occurred, Please Try Again", Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                }

            }catch(JSONException e)
            {
                Toast.makeText(ViewJobToSaveRecommendedApplicantActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
            }
        },error ->{
            error.printStackTrace();
            Toast.makeText(ViewJobToSaveRecommendedApplicantActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            progressDialog.cancel();
        })
        {
            //  public Map<String, String> getHeaders() throws AuthFailureError {
            //     HashMap<String,String> map = new HashMap<>();
            //    String token = userPref.getString("token","");
            //    map.put("Authorization","Bearer"+token);
            //     return map;
            //}

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                //map.put("id",user_id);
                map.put("applicant_id",iapplicant_id);
                map.put("job_id",user_id);
                map.put("applyjob_id", jobapply_id);
                map.put("name",iname);
                map.put("email",iemail);
                map.put("contactno",icontactno);
                map.put("address",iaddress);
                map.put("gender",igender);
                map.put("status","pending");
                map.put("profile_pic",iprofile_pic);
                return map;
            }
//                    public byte [] getBody()
//                    {
//                        Map <String,String> map = new HashMap<>();
//                        map.put("id",user_id);
//                        map.put("file_path",bitmapToString(bitmap));
//                        return map[user_id,bitmapToString(bitmap)];
//                    }
        };

        RequestQueue queue = Volley.newRequestQueue(ViewJobToSaveRecommendedApplicantActivity.this);
        queue.add(request);
    }
}