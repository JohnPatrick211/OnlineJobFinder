package com.example.onlinejobfinder.applicant;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amar.library.ui.StickyScrollView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.adapter.jobadapter;
import com.example.onlinejobfinder.employer.EmployerAddJobActivity;
import com.example.onlinejobfinder.model.job;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ApplicantJobDescriptionActivity extends AppCompatActivity {

    StickyScrollView scrollview;
    SharedPreferences userPref2;
    String name2, user_id, token, email;
    String job_id, id, logo, jobstatus,saved_id,intent1;
    LinearLayout jobdescription, companyoverview;
    TabLayout tablayout_jobdescription;
    CircleImageView img_joblogo;
    TextView tv_jobtitle, tv_jobcompany, tv_jobsalary, tv_jobdescription, tv_jobcompanyoverview, tv_joblocation, tv_jobaddress, tv_jobspecialization, tv_jobdateposted;
    Button btnapply, btnsavedjob;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_job_description);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        scrollview = findViewById(R.id.stickyscrollview);
        btnsavedjob = findViewById(R.id.btn_savejobdesc);
        btnapply = findViewById(R.id.btn_applyjobdesc);
        jobdescription = findViewById(R.id.layout_jobdescription);
        companyoverview = findViewById(R.id.layout_companyoverview);
        tablayout_jobdescription = findViewById(R.id.tab_layout_jobdescription);
        img_joblogo = findViewById(R.id.img_jobdesc_logo);
        tv_jobtitle = findViewById(R.id.tv_jobdesc_jobtitle);
        tv_jobcompany = findViewById(R.id.tv_jobdesc_jobcompany);
        tv_jobsalary = findViewById(R.id.tv_jobdesc_jobsalary);
        tv_jobdescription = findViewById(R.id.tv_jobdesc_jobdesc);
        tv_jobcompanyoverview = findViewById(R.id.tv_jobdesc_jobcompanyoverview);
        tv_joblocation = findViewById(R.id.tv_jobdesc_joblocation);
        tv_jobaddress = findViewById(R.id.tv_jobdesc_jobaddress);
        tv_jobspecialization = findViewById(R.id.tv_jobdesc_jobspecialization);
        tv_jobdateposted = findViewById(R.id.tv_jobdesc_jobdateposted);
        Picasso.get().load(getIntent().getStringExtra("intentjoblogo")).into(img_joblogo);
        tv_jobtitle.setText(getIntent().getExtras().getString("intentjobtitle"));
        tv_jobcompany.setText(getIntent().getExtras().getString("intentjobcompany"));
        tv_jobsalary.setText(getIntent().getExtras().getString("intentjobsalary"));
        tv_jobdescription.setText(getIntent().getExtras().getString("intentjobdescription"));
        tv_jobcompanyoverview.setText(getIntent().getExtras().getString("intentjobcompanyoverview"));
        tv_joblocation.setText(getIntent().getExtras().getString("intentjoblocation"));
        tv_jobaddress.setText(getIntent().getExtras().getString("intentjobaddress"));
        tv_jobspecialization.setText(getIntent().getExtras().getString("intentjobspecialization"));
        tv_jobdateposted.setText(getIntent().getExtras().getString("intentjobposted").trim());
        job_id = getIntent().getExtras().getString("intentjob_id");
        saved_id = getIntent().getExtras().getString("intentsavedid");
        intent1 = getIntent().getExtras().getString("intent1");
        id = getIntent().getExtras().getString("intentid");
        logo = getIntent().getExtras().getString("jobcompanylogo2");
        jobstatus = getIntent().getExtras().getString("intentjobstatus");
        userPref2 = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        name2 = userPref2.getString("name", "name");
        email = userPref2.getString("email", "email");
        user_id = userPref2.getString("id", "id");
        token = userPref2.getString("token", "token");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        getSupportActionBar().setCustomView(R.layout.customactionbarmaintitle);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        try {
            String checknull = getIntent().getExtras().getString("intentremove").trim();
            if (checknull.equals("null")) {
                btnsavedjob.setText("Save");
            } else {
                btnsavedjob.setText(checknull);
            }
        } catch (Exception e) {
            btnsavedjob.setText("Save");
        }
        btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ApplicantJobDescriptionActivity.this,ApplicantFinalCheckProfileActivity.class);
                i.putExtra("intentjob_id",job_id);
                i.putExtra("intentid",id);
                i.putExtra("intentsavedid",saved_id);
                startActivity(i);
            }
        });
        btnsavedjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnsavedjob.getText().toString().equals("Save")) {
                    progressDialog.setMessage("Saving");
                    progressDialog.show();
                    StringRequest request = new StringRequest(Request.Method.POST, Constant.savedjob, response -> {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getBoolean("success")) {
                                //JSONObject user = object.getJSONObject("user");
                                Toast.makeText(ApplicantJobDescriptionActivity.this, "Job Saved In bookmarked", Toast.LENGTH_SHORT).show();
                                btnsavedjob.setText("Remove");

//                            SharedPreferences userPref = getActivity().getApplicationContext().getSharedPreferences("user",getContext().MODE_PRIVATE);
//                            SharedPreferences.Editor editor = userPref.edit();
//                            editor.putString("token",object.getString("token"));
//                            editor.putString("email",user.getString("email"));
//                            editor.putString("name",user.getString("name"));
//                            editor.putString("role",user.getString("role"));
//                            editor.apply();
//                            Toast.makeText(getContext(),"Register Successfully",Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            } else if (object.getString("Status").equals("202")) {
                                Toast.makeText(ApplicantJobDescriptionActivity.this, "Job Vacant Already Bookmarked", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            } else {
                                Toast.makeText(ApplicantJobDescriptionActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(ApplicantJobDescriptionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    }, error -> {
                        error.printStackTrace();
                        progressDialog.cancel();
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("saved_id", id);
                            map.put("job_id", job_id);
                            map.put("applicant_id", user_id);
                            map.put("companyname", tv_jobcompany.getText().toString().trim());
                            map.put("jobtitle", tv_jobtitle.getText().toString().trim());
                            map.put("category", tv_jobspecialization.getText().toString());
                            map.put("companyoverview", tv_jobcompanyoverview.getText().toString().trim());
                            map.put("jobdescription", tv_jobdescription.getText().toString().trim());
                            map.put("location", tv_joblocation.getText().toString());
                            map.put("salary", tv_jobsalary.getText().toString().trim());
                            map.put("address", tv_jobaddress.getText().toString().trim());
                            map.put("logo", logo.trim());
                            map.put("jobstatus", jobstatus);
                            return map;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(ApplicantJobDescriptionActivity.this);
                    queue.add(request);
                } else {
                    //btnsavedjob.setText("Remove");
                    progressDialog.setMessage("Removing");
                    progressDialog.show();
                    StringRequest request = new StringRequest(Request.Method.POST, Constant.deletejob, response -> {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getBoolean("success")) {
                                btnsavedjob.setText("Save");
                                //JSONObject user = object.getJSONObject("update");
                                //SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                                // SharedPreferences.Editor editor = userPref.edit();
                                //editor.putString("id",user.getString("educational_id"));
                                progressDialog.cancel();

                                //editor.apply();
                                //  editor.commit();
                                Toast.makeText(ApplicantJobDescriptionActivity.this, "Remove Successfully", Toast.LENGTH_SHORT).show();

                                try{
                                    if(intent1.equals("1"))
                                    {
                                        onBackPressed();
                                    }
                                }catch (Exception e)
                                {

                                }
                                //onBackPressed();


                            } else {
                                Toast.makeText(ApplicantJobDescriptionActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(ApplicantJobDescriptionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    }, error -> {
                        error.printStackTrace();
                        Toast.makeText(ApplicantJobDescriptionActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> map = new HashMap<>();
                            //map.put("id",user_id);
                            map.put("id", id);
                            map.put("saved_id", id);
                            return map;

                        }

                    };

                    RequestQueue queue = Volley.newRequestQueue(ApplicantJobDescriptionActivity.this);
                    queue.add(request);
                }
            }
        });
        tablayout_jobdescription.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        focusOnJobDescView();
                        break;
                    case 1:
                        focusOnCompanyOverView();
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        focusOnJobDescView();
                        break;
                    case 1:
                        focusOnCompanyOverView();
                        break;

                }
            }
        });
    }

    private void focusOnJobDescView() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(scrollview, "scrollY", scrollview.getScrollY(), jobdescription.getTop() - 80)
                        .setDuration(800);
                objectAnimator.start();
            }
        });
    }

    private void focusOnCompanyOverView() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(scrollview, "scrollY", scrollview.getScrollY(), companyoverview.getTop() - 80)
                        .setDuration(800);
                objectAnimator.start();
            }
        });
    }

    private void getPost() {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.postsavedjob + "?applicant_id=" + user_id, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")) {
                    JSONArray array = new JSONArray(object.getString("jobpost"));
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject postObject = array.getJSONObject(i);
                        //JSONObject getpostObject = postObject.getJSONObject("jobposts");

//                        job job2 = new job();
//                        job2.setJoblogo(postObject.getString("logo"));
//                        job2.setJobtitle(postObject.getString("jobtitle"));
//                        job2.setJobcompany(postObject.getString("companyname"));
//                        job2.setJoblocation(postObject.getString("location"));
//                        job2.setJobsalary(postObject.getString("salary"));
//                        job2.setJobdateposted(postObject.getString("created_at"));
//                        job2.setJobaddress(postObject.getString("address"));
//                        job2.setCompanyoverview(postObject.getString("companyoverview"));
//                        job2.setJobcategory(postObject.getString("category"));
//                        job2.setJobid(postObject.getString("job_id"));
//                        job2.setJobdescription(postObject.getString("jobdescription"));
//                        job2.setJobuniqueid(postObject.getString("id"));
//                        job2.setJobstatus(postObject.getString("jobstatus"));
                        if (id.equals(postObject.getString("saved_id"))) {
                            btnsavedjob.setText("Remove");
                        }

                    }

                } else {
                    Toast.makeText(ApplicantJobDescriptionActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(ApplicantJobDescriptionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }, error -> {
            error.printStackTrace();

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ApplicantJobDescriptionActivity.this);
        queue.add(request);
    }

    public void onResume() {
        super.onResume();
        getPost();
    }

    public void onBackPressed()
    {
        super.onBackPressed();
    }

}