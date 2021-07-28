package com.example.onlinejobfinder.employer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.adapter.educationalbackgroundadapter;
import com.example.onlinejobfinder.adapter.vieweducationalbackgroundadapter;
import com.example.onlinejobfinder.adapter.viewworkexperienceadapter;
import com.example.onlinejobfinder.adapter.workexperienceadapter;
import com.example.onlinejobfinder.applicant.ApplicantFinalCheckProfileActivity;
import com.example.onlinejobfinder.model.educationalbackground;
import com.example.onlinejobfinder.model.workexperience;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewRecommendedApplicantProfileActivity extends AppCompatActivity {

    TextView txtmanageaccount, txtname, txtemail, txtcontactno, txtaddress, txtgender, txtspecialization, txtresume,txtintentresume, txtviewresume, txtaddeduc,txtaddwork;
    ImageView editprofile;
    com.example.onlinejobfinder.adapter.vieweducationalbackgroundadapter educationalbackgroundadapter;
    viewworkexperienceadapter workexperienceadapter2;
    String profile_pic;
    // RecyclerView recyclerview;
    //  jobadapter jobadapter2;
    //ArrayList<job> arraylist;
    //SwipeRefreshLayout refreshLayout;
    //    String[] jobtitle = {"Junior Programmer", "Assistant Manager", "Photographer"};
//    int[] images = {R.drawable.samplelogo, R.drawable.samplelogo, R.drawable.samplelogo};
//    String[] jobcompany = {"Codify Ltd.", "Jollibee Antartica", "JBC Firm"};
//    String[] jobaddress = {"Caloocan, Balayan, Batangas", "Antartica", "Ermita, Balayan, Batangas"};
//    String[] jobsalary = {"PHP 100000-200000", "PHP 250000-500000", "PHP 50000-10000"};
//    String[] jobdateposted = {"1 hours ago", "30 Apr 2021", "29 Apr 2021"};
    SharedPreferences userPref,userPref2;
    //  TextView textView_name;
    // TextView textView_manage;
    //jobadapter.RecyclerViewClickListener listener;
    ImageView imageview_user;
    String name2, user_id,token,permaid, address, email, contactno, background,applicant_id;
    String val_contactno = "";
    String val_address = "";
    String val_gender = "";
    String val_specialization = "";
    String val_resume = "";
    ArrayList<educationalbackground> arraylist;
    ArrayList<workexperience> arraylist2;
    RecyclerView recyclerView, recyclerView2;
    JSONArray result;
    Button btnsaveapplicant;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recommended_applicant_profile);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        txtintentresume = findViewById(R.id.textView_IntentResume);
        txtviewresume = findViewById(R.id.textView_ViewResume);
        editprofile = findViewById(R.id.image_manageaccountprofile);
        txtname = findViewById(R.id.textView_NameProfile);
        txtemail = findViewById(R.id.textView_EmailProfile);
        txtaddress = findViewById(R.id.textView_AddressProfile);
        txtcontactno = findViewById(R.id.textView_ContactNoProfile);
        txtgender = findViewById(R.id.textView_GenderProfile);
        txtresume = findViewById(R.id.textView_ResumeView);
        txtspecialization = findViewById(R.id.textView_SpecializationProfile);
        txtaddeduc = findViewById(R.id.textView_addeducation);
        txtaddwork = findViewById(R.id.textView_addworkexperience);
        recyclerView = findViewById(R.id.recyclerview_educationalbackground);
        recyclerView2 = findViewById(R.id.recyclerview_workexperience);
        recyclerView2.setLayoutManager(new LinearLayoutManager(ViewRecommendedApplicantProfileActivity.this));
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewRecommendedApplicantProfileActivity.this));
        arraylist = new ArrayList<>();
        arraylist2 = new ArrayList<>();
        btnsaveapplicant = findViewById(R.id.btn_saverecommendedapplicant);
        //   textView_manage = root.findViewById(R.id.textView_Manage);
        imageview_user = findViewById(R.id.imguserprofile);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        //   recyclerview = root.findViewById(R.id.recyclerview_jobs_recommended);
        //   recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        //  refreshLayout = root.findViewById(R.id.swipe2);
        //   arraylist = new ArrayList<>();
        //  refreshLayout.setRefreshing(true);
        //   userPref = getContext().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        userPref2 = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPref2.edit();
        name2 = userPref2.getString("name","name");
        email = userPref2.getString("email","email");
        user_id = userPref2.getString("id","id");
        token = userPref2.getString("token","token");
        permaid = user_id;
        applicant_id = getIntent().getExtras().getString("intentapplicant_id");

        btnsaveapplicant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewRecommendedApplicantProfileActivity.this, ViewJobToSaveRecommendedApplicantActivity.class);
                  intent.putExtra("intentapplicant_id",applicant_id);
                intent.putExtra("intentname",txtname.getText().toString());
                intent.putExtra("intentemail",txtemail.getText().toString());
                intent.putExtra("intentcontactno",txtcontactno.getText().toString());
                intent.putExtra("intentaddress",txtaddress.getText().toString());
                intent.putExtra("intentgender",txtgender.getText().toString());
                intent.putExtra("intentstatus","pending");
                intent.putExtra("intentprofile_pic",profile_pic);
                startActivity(intent);
            }
        });


        //getData
        StringRequest request = new StringRequest(Request.Method.GET, Constant.MY_POST+"?applicant_id="+applicant_id, response -> {
            try{
                JSONObject object= new JSONObject(response);
                if(object.getBoolean("success")){
                    JSONObject user = object.getJSONObject("user");
                    txtname.setText(user.get("name").toString());
                    txtemail.setText(user.get("email").toString());
                    txtcontactno.setText(user.get("contactno").toString());
                    val_contactno = txtcontactno.getText().toString();
                    txtaddress.setText(user.get("address").toString());
                    val_address = txtaddress.getText().toString();
                    txtspecialization.setText(user.get("Specialization").toString());
                    val_specialization = txtspecialization.getText().toString();
                    txtgender.setText(user.get("gender").toString());
                    val_gender = txtgender.getText().toString();
                    txtresume.setText(user.get("resume").toString());
                    val_resume = txtresume.getText().toString();
                    Picasso.get().load(Constant.URL+"/storage/profiles/"+user.getString("profile_pic")).into(imageview_user);
                    profile_pic = user.getString("profile_pic");
                    SharedPreferences.Editor editor2 = userPref2.edit();
                    editor2.putString("name",user.getString("name"));
                    editor2.putString("address",user.getString("address"));
                    editor2.putString("contactno",user.getString("contactno"));
                    //editor2.putString("email",user.getString("email"));
                    // editor2.putString("background",user.getString("background"));
                    editor2.apply();
                    editor2.commit();
                    if(user.get("contactno").toString().equals("null")|| user.get("address").toString().equals("null")|| user.get("Specialization").toString().equals("null")|| user.get("gender").toString().equals("null"))
                    {
                        txtcontactno.setVisibility(View.GONE);
                        txtaddress.setVisibility(View.GONE);
                        txtspecialization.setVisibility(View.GONE);
                        txtgender.setVisibility(View.GONE);
                    }
                    if(user.get("resume").toString().equals("null"))
                    {
                        txtresume.setVisibility(View.GONE);
                        txtviewresume.setVisibility(View.GONE);
                    }
                    else {
                        txtcontactno.setVisibility(View.VISIBLE);
                        txtaddress.setVisibility(View.VISIBLE);
                        txtspecialization.setVisibility(View.VISIBLE);
                        txtgender.setVisibility(View.VISIBLE);
                        txtresume.setVisibility(View.VISIBLE);
                        txtviewresume.setVisibility(View.VISIBLE);
                    }

                }
                else
                {
                    Toast.makeText(ViewRecommendedApplicantProfileActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                    // progressDialog.cancel();
                }

            }catch(JSONException e)
            {
                //Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                txtcontactno.setVisibility(View.GONE);
                txtaddress.setVisibility(View.GONE);
                txtspecialization.setVisibility(View.GONE);
                txtgender.setVisibility(View.GONE);
                txtresume.setVisibility(View.GONE);
                txtviewresume.setVisibility(View.GONE);
                txtname.setText(name2);
                txtemail.setText(email);
                //  progressDialog.cancel();
            }
        },error ->{
            error.printStackTrace();
            Toast.makeText(ViewRecommendedApplicantProfileActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            txtname.setText(name2);
            txtemail.setText(email);
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

        RequestQueue queue = Volley.newRequestQueue(ViewRecommendedApplicantProfileActivity.this);
        queue.add(request);

    }
    public void onResume() {
        super.onResume();
        StringRequest request = new StringRequest(Request.Method.GET, Constant.MY_POST+"?applicant_id="+applicant_id, response -> {
            try{
                JSONObject object= new JSONObject(response);
                if(object.getBoolean("success")){
                    JSONObject user = object.getJSONObject("user");
                    txtname.setText(user.get("name").toString());
                    txtemail.setText(user.get("email").toString());
                    txtcontactno.setText(user.get("contactno").toString());
                    val_contactno = txtcontactno.getText().toString();
                    txtaddress.setText(user.get("address").toString());
                    val_address = txtaddress.getText().toString();
                    txtspecialization.setText(user.get("Specialization").toString());
                    val_specialization = txtspecialization.getText().toString();
                    txtgender.setText(user.get("gender").toString());
                    val_gender = txtgender.getText().toString();
                    txtresume.setText(user.get("resume").toString());
                    val_resume = txtresume.getText().toString();
                    Picasso.get().load(Constant.URL+"/storage/profiles/"+user.getString("profile_pic")).into(imageview_user);
                    SharedPreferences.Editor editor2 = userPref2.edit();
                    editor2.putString("name",user.getString("name"));
                    editor2.putString("address",user.getString("address"));
                    editor2.putString("contactno",user.getString("contactno"));
                    // editor2.putString("email",user.getString("email"));
                    // editor2.putString("background",user.getString("background"));
                    editor2.apply();
                    editor2.commit();
                    if(user.get("contactno").toString().equals("null")|| user.get("address").toString().equals("null")|| user.get("Specialization").toString().equals("null")|| user.get("gender").toString().equals("null"))
                    {
                        txtcontactno.setVisibility(View.GONE);
                        txtaddress.setVisibility(View.GONE);
                        txtspecialization.setVisibility(View.GONE);
                        txtgender.setVisibility(View.GONE);
                    }
                    if(user.get("resume").toString().equals("null"))
                    {
                        txtresume.setVisibility(View.GONE);
                        txtviewresume.setVisibility(View.GONE);
                    }
                    else {
                        txtcontactno.setVisibility(View.VISIBLE);
                        txtaddress.setVisibility(View.VISIBLE);
                        txtspecialization.setVisibility(View.VISIBLE);
                        txtgender.setVisibility(View.VISIBLE);
                        txtresume.setVisibility(View.VISIBLE);
                        txtviewresume.setVisibility(View.VISIBLE);
                    }

                }
                else
                {
                    Toast.makeText(ViewRecommendedApplicantProfileActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                    // progressDialog.cancel();
                }

            }catch(JSONException e)
            {
                //Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                txtcontactno.setVisibility(View.GONE);
                txtaddress.setVisibility(View.GONE);
                txtspecialization.setVisibility(View.GONE);
                txtgender.setVisibility(View.GONE);
                txtresume.setVisibility(View.GONE);
                txtviewresume.setVisibility(View.GONE);
                txtname.setText(name2);
                txtemail.setText(email);
                //  progressDialog.cancel();
            }
        },error ->{
            error.printStackTrace();
            Toast.makeText(ViewRecommendedApplicantProfileActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
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

        RequestQueue queue = Volley.newRequestQueue(ViewRecommendedApplicantProfileActivity.this);
        queue.add(request);
        arraylist.clear();
        StringRequest request2 = new StringRequest(Request.Method.GET, Constant.geteducation+"?educational_id="+applicant_id, response ->{
            try{
                JSONObject object = new JSONObject(response);
                if(object.getBoolean("success"))
                {
                    JSONArray array = new JSONArray(object.getString("education"));
                    for(int i = 0; i < array.length(); i++)
                    {
                        JSONObject postObject = array.getJSONObject(i);
                        //JSONObject getpostObject = postObject.getJSONObject("jobposts");

                        educationalbackground educationalbackground2 = new educationalbackground();
                        educationalbackground2.setApplicantschool(postObject.getString("school"));
                        educationalbackground2.setApplicantcourse(postObject.getString("course"));
                        educationalbackground2.setApplicantyeargraduated(postObject.getString("year"));
                        educationalbackground2.setId(postObject.getString("id"));



                        arraylist.add(educationalbackground2);

                    }
                    educationalbackgroundadapter = new vieweducationalbackgroundadapter(arraylist,ViewRecommendedApplicantProfileActivity.this);
                    recyclerView.setAdapter(educationalbackgroundadapter);
                    educationalbackgroundadapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(ViewRecommendedApplicantProfileActivity.this,"error",Toast.LENGTH_SHORT).show();
                    recyclerView.setVisibility(View.GONE);
                }
            }catch(JSONException e)
            {
                recyclerView.setVisibility(View.GONE);
                e.printStackTrace();
                Toast.makeText(ViewRecommendedApplicantProfileActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }



        },error -> {
            error.printStackTrace();
            Toast.makeText(ViewRecommendedApplicantProfileActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.GONE);
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue queue2 = Volley.newRequestQueue(ViewRecommendedApplicantProfileActivity.this);
        queue2.add(request2);

        //workexperience
        arraylist2.clear();
        StringRequest request3 = new StringRequest(Request.Method.GET, Constant.getworkexperience+"?workexp_id="+applicant_id, response ->{
            try{
                JSONObject object = new JSONObject(response);
                if(object.getBoolean("success"))
                {
                    JSONArray array = new JSONArray(object.getString("workexperience"));
                    for(int i = 0; i < array.length(); i++)
                    {
                        JSONObject postObject = array.getJSONObject(i);
                        //JSONObject getpostObject = postObject.getJSONObject("jobposts");
                        workexperience workexperience2 = new workexperience();
                        // educationalbackground educationalbackground2 = new educationalbackground();
                        workexperience2.setApplicantworkposition(postObject.getString("position"));
                        workexperience2.setApplicantworkcompanyname(postObject.getString("name"));
                        workexperience2.setApplicantworkdate(postObject.getString("startenddate"));
                        workexperience2.setId(postObject.getString("id"));
                        workexperience2.setApplicantworkspecialization(postObject.getString("specialization"));



                        arraylist2.add(workexperience2);

                    }
                    workexperienceadapter2 = new viewworkexperienceadapter(arraylist2,ViewRecommendedApplicantProfileActivity.this);
                    recyclerView2.setAdapter(workexperienceadapter2);
                    workexperienceadapter2.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(ViewRecommendedApplicantProfileActivity.this,"error",Toast.LENGTH_SHORT).show();
                    recyclerView2.setVisibility(View.GONE);
                }
            }catch(JSONException e)
            {
                recyclerView2.setVisibility(View.GONE);
                e.printStackTrace();
                Toast.makeText(ViewRecommendedApplicantProfileActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }



        },error -> {
            error.printStackTrace();
            Toast.makeText(ViewRecommendedApplicantProfileActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            recyclerView2.setVisibility(View.GONE);
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue queue3 = Volley.newRequestQueue(ViewRecommendedApplicantProfileActivity.this);
        queue3.add(request3);
    }
}