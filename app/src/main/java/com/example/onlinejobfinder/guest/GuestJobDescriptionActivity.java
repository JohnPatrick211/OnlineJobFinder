package com.example.onlinejobfinder.guest;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.onlinejobfinder.MainActivity;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.applicant.ApplicantFinalCheckProfileActivity;
import com.example.onlinejobfinder.applicant.ApplicantJobDescriptionActivity;
import com.example.onlinejobfinder.tabregister.RegisterActivity;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GuestJobDescriptionActivity extends AppCompatActivity {

    StickyScrollView scrollview;
    SharedPreferences userPref2;
    String name2, user_id, token, email;
    String job_id, id, logo, jobstatus,saved_id,intent1;
    LinearLayout jobdescription, companyoverview;
    TabLayout tablayout_jobdescription;
    ImageView img_joblogo;
    TextView tv_content, tv_maybelater, tv_jobtitle, tv_jobcompany, tv_jobsalary, tv_jobdescription, tv_jobcompanyoverview, tv_joblocation, tv_jobaddress, tv_jobspecialization, tv_jobdateposted;
    Button btnapply, btnsavedjob, btnloginhere,btnsignuphere;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_job_description);

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
        btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(GuestJobDescriptionActivity.this, "Please Login To Apply Job", Toast.LENGTH_SHORT).show();
                builder = new AlertDialog.Builder(GuestJobDescriptionActivity.this);
                ViewGroup viewGroup = findViewById(R.id.content);
                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_login,viewGroup,false);
                btnloginhere = dialogView.findViewById(R.id.btn_gotologin);
                btnsignuphere = dialogView.findViewById(R.id.btn_gotosignup);
                tv_maybelater = dialogView.findViewById(R.id.btn_maybedismiss);
                builder.setView(dialogView);
                alertDialog = builder.create();
                btnloginhere.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        Intent i = new Intent(GuestJobDescriptionActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                });
                btnsignuphere.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        Intent i = new Intent(GuestJobDescriptionActivity.this, RegisterActivity.class);
                        startActivity(i);
                    }
                });
                tv_maybelater.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        btnsavedjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(GuestJobDescriptionActivity.this, "Please Login To BookMark Job", Toast.LENGTH_SHORT).show();
                builder = new AlertDialog.Builder(GuestJobDescriptionActivity.this);
                ViewGroup viewGroup = findViewById(R.id.content);
                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_login,viewGroup,false);
                btnloginhere = dialogView.findViewById(R.id.btn_gotologin);
                btnsignuphere = dialogView.findViewById(R.id.btn_gotosignup);
                tv_maybelater = dialogView.findViewById(R.id.btn_maybedismiss);
                tv_content = dialogView.findViewById(R.id.tv_contentdialog);
                tv_content.setText("Log in or Sign up to save this job");
                builder.setView(dialogView);
                alertDialog = builder.create();
                btnloginhere.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(GuestJobDescriptionActivity.this, MainActivity.class);
                        alertDialog.dismiss();
                        startActivity(i);


                    }
                });
                btnsignuphere.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        Intent i = new Intent(GuestJobDescriptionActivity.this, RegisterActivity.class);
                        startActivity(i);
                    }
                });
                tv_maybelater.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
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
}

