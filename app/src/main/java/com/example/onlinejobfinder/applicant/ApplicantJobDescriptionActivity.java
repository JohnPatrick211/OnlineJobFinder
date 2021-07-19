package com.example.onlinejobfinder.applicant;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amar.library.ui.StickyScrollView;
import com.example.onlinejobfinder.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

public class ApplicantJobDescriptionActivity extends AppCompatActivity {

    StickyScrollView scrollview;
    String job_id,id;
    LinearLayout jobdescription, companyoverview;
    TabLayout tablayout_jobdescription;
    CircleImageView img_joblogo;
    TextView tv_jobtitle,tv_jobcompany,tv_jobsalary,tv_jobdescription,tv_jobcompanyoverview,tv_joblocation,tv_jobaddress,tv_jobspecialization,tv_jobdateposted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_job_description);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        scrollview = findViewById(R.id.stickyscrollview);
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
        id = getIntent().getExtras().getString("intentid");
        Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
        tablayout_jobdescription.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition())
                {
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
                switch (tab.getPosition())
                {
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
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(scrollview,"scrollY",scrollview.getScrollY(),jobdescription.getTop()-80)
                        .setDuration(800);
                objectAnimator.start();
            }
        });
    }
    private void focusOnCompanyOverView() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(scrollview,"scrollY",scrollview.getScrollY(),companyoverview.getTop()-80)
                        .setDuration(800);
                objectAnimator.start();
            }
        });
    }
}