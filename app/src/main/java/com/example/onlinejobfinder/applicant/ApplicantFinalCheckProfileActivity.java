package com.example.onlinejobfinder.applicant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.onlinejobfinder.adapter.educationalbackgroundadapter;
import com.example.onlinejobfinder.adapter.workexperienceadapter;
import com.example.onlinejobfinder.model.educationalbackground;
import com.example.onlinejobfinder.model.workexperience;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

public class ApplicantFinalCheckProfileActivity extends AppCompatActivity {

    TextView txtmanageaccount, txtname, txtemail, txtcontactno, txtaddress, txtgender, txtspecialization, txtresume, txtintentresume, txtviewresume, txtaddeduc, txtaddwork;
    ImageView editprofile;
    View ln_noworkexperiencelayout, ln_noeducationlayout, ln_noresumelayout;
    com.example.onlinejobfinder.adapter.educationalbackgroundadapter educationalbackgroundadapter;
    workexperienceadapter workexperienceadapter2;
    workexperienceadapter.RecyclerViewClickListener worklistener;
    educationalbackgroundadapter.RecyclerViewClickListener listener;
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
    SharedPreferences userPref, userPref2;
    //  TextView textView_name;
    // TextView textView_manage;
    //jobadapter.RecyclerViewClickListener listener;
    ImageView imageview_user;
    String name2, user_id, token, permaid, address, email, contactno, background, id, job_id, saved_id;
    String val_contactno = "";
    String val_address = "";
    String val_gender = "";
    String val_specialization = "";
    String val_resume = "";
    String val_saved_id,val_id,val_job_id;
    ArrayList<educationalbackground> arraylist;
    ArrayList<workexperience> arraylist2;
    RecyclerView recyclerView, recyclerView2;
    JSONArray result;
    Button btnapply;
    ProgressDialog progressDialog;
    RelativeLayout footer;
    View ln_delay;
    String imgUrl = "";
    LinearLayout main, main2, main3, main4;
    CountDownTimer CDT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_final_check_profile);
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
        recyclerView2.setLayoutManager(new LinearLayoutManager(ApplicantFinalCheckProfileActivity.this));
        recyclerView.setLayoutManager(new LinearLayoutManager(ApplicantFinalCheckProfileActivity.this));
        arraylist = new ArrayList<>();
        arraylist2 = new ArrayList<>();
        btnapply = findViewById(R.id.btn_applynow);
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
        name2 = userPref2.getString("name", "name");
        email = userPref2.getString("email", "email");
        user_id = userPref2.getString("id", "id");
        token = userPref2.getString("token", "token");
        permaid = user_id;
        job_id = getIntent().getExtras().getString("intentjob_id");
        id = getIntent().getExtras().getString("intentid");
        saved_id = getIntent().getExtras().getString("intentsavedid");
        userPref = getSharedPreferences("apply_id", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = userPref.edit();
        editor2.putString("id",id);
        editor2.putString("saved_id", saved_id);
        editor2.putString("job_id",job_id);
        editor2.apply();
        editor2.commit();
        val_id = userPref.getString("id","id");
        val_saved_id = userPref.getString("saved_id","saved_id");
        val_job_id = userPref.getString("job_id","job_id");
//        Toast.makeText(getApplicationContext(),val_id,Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(),val_saved_id,Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(),val_job_id,Toast.LENGTH_SHORT).show();
        ln_delay = findViewById(R.id.ln_delayloadinglayout);
        main = findViewById(R.id.bruh);
        main2 = findViewById(R.id.bruh2);
        main3 = findViewById(R.id.bruh3);
        main4 = findViewById(R.id.bruh4);
        footer = findViewById(R.id.footer);
        main.setVisibility(View.GONE);
        main2.setVisibility(View.GONE);
        main3.setVisibility(View.GONE);
        main4.setVisibility(View.GONE);
        footer.setVisibility(View.GONE);
        getSupportActionBar().setCustomView(R.layout.customactionbarcheckprofile);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ln_noworkexperiencelayout = findViewById(R.id.ln_noworkexperiencelayout);
        ln_noeducationlayout = findViewById(R.id.ln_noeducationlayout);
        ln_noresumelayout = findViewById(R.id.ln_noresumelayout);
        delay();
        try {
            if (val_saved_id.equals("saved_id")) {
                saved_id = "";
            }
        } catch (Exception e) {
            saved_id = "";
        }
        //check ID debugging//
        //Toast.makeText(getContext(), user_id, Toast.LENGTH_SHORT).show();
        //Toast.makeText(ApplicantFinalCheckProfileActivity.this, email, Toast.LENGTH_SHORT).show();
        setOnClickListener();
        setOnClickListener2();
        btnapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                    if (saved_id.isEmpty()) {
                        progressDialog.setMessage("Saving");
                        progressDialog.show();
                        StringRequest request = new StringRequest(Request.Method.POST, Constant.applyjob, response -> {
//                StringRequest request = new StringRequest(Request.Method.POST, Constant.SAVE_USER_PROFILE, response -> {
                            try {
                                JSONObject object = new JSONObject(response);
                                if (object.getBoolean("success")) {
                                    JSONObject user = object.getJSONObject("update");
//                                SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = userPref.edit();
//                                editor.putString("id",user.getString("applicant_id"));
                                    //editor.putString("file_path",user.getString("file_path"));
                                    //                           Intent i = new Intent(UploadProfileRegister.this, MainActivity.class);
                                    //                           startActivity(i);
                                    progressDialog.cancel();
//                                    Toast.makeText(ApplicantFinalCheckProfileActivity.this, "From Display Job", Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(ApplicantFinalCheckProfileActivity.this, job_id, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(ApplicantFinalCheckProfileActivity.this, id, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(ApplicantFinalCheckProfileActivity.this, user_id, Toast.LENGTH_SHORT).show();

//                                editor.apply();
//                                editor.commit();
                                    Toast.makeText(ApplicantFinalCheckProfileActivity.this, "Apply Successfully", Toast.LENGTH_SHORT).show();
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


                                } else if (object.getString("Status").equals("202")) {
                                    Toast.makeText(ApplicantFinalCheckProfileActivity.this, "You Already Applied On this Job", Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                } else {
                                    Toast.makeText(ApplicantFinalCheckProfileActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                }

                            } catch (JSONException e) {
                                Toast.makeText(ApplicantFinalCheckProfileActivity.this, "Network Error, Please Try Again", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        }, error -> {
                            error.printStackTrace();
                            Toast.makeText(ApplicantFinalCheckProfileActivity.this, "Network Error, Please Try Again", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }) {
                            //  public Map<String, String> getHeaders() throws AuthFailureError {
                            //     HashMap<String,String> map = new HashMap<>();
                            //    String token = userPref.getString("token","");
                            //    map.put("Authorization","Bearer"+token);
                            //     return map;
                            //}

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> map = new HashMap<>();
                                //map.put("id",user_id);
                                map.put("applicant_id", user_id);
                                map.put("job_id", val_job_id);
                                map.put("applyjob_id", val_id);
                                map.put("name", txtname.getText().toString().trim());
                                map.put("email", txtemail.getText().toString().trim());
                                map.put("contactno", txtcontactno.getText().toString().trim());
                                map.put("address", txtaddress.getText().toString().trim());
                                map.put("gender", txtgender.getText().toString().trim());
                                map.put("status", "pending");
                                map.put("profile_pic", profile_pic);
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

                        RequestQueue queue = Volley.newRequestQueue(ApplicantFinalCheckProfileActivity.this);
                        queue.add(request);
                    } else {
                        progressDialog.setMessage("Saving");
                        progressDialog.show();
                        StringRequest request = new StringRequest(Request.Method.POST, Constant.applyjob, response -> {
//                StringRequest request = new StringRequest(Request.Method.POST, Constant.SAVE_USER_PROFILE, response -> {
                            try {
                                JSONObject object = new JSONObject(response);
                                if (object.getBoolean("success")) {
                                    JSONObject user = object.getJSONObject("update");
//                                SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
//                                SharedPreferences.Editor editor = userPref.edit();
//                                editor.putString("id",user.getString("applicant_id"));
                                    //editor.putString("file_path",user.getString("file_path"));
                                    //                           Intent i = new Intent(UploadProfileRegister.this, MainActivity.class);
                                    //                           startActivity(i);
                                    progressDialog.cancel();
//                                    Toast.makeText(ApplicantFinalCheckProfileActivity.this, "From Display Saved Job", Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(ApplicantFinalCheckProfileActivity.this, job_id, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(ApplicantFinalCheckProfileActivity.this, saved_id, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(ApplicantFinalCheckProfileActivity.this, user_id, Toast.LENGTH_SHORT).show();

//                                editor.apply();
//                                editor.commit();
                                    Toast.makeText(ApplicantFinalCheckProfileActivity.this, "Apply Successfully", Toast.LENGTH_SHORT).show();
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


                                } else if (object.getString("Status").equals("202")) {
                                    Toast.makeText(ApplicantFinalCheckProfileActivity.this, "You Already Applied On this Job", Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                } else {
                                    Toast.makeText(ApplicantFinalCheckProfileActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                }

                            } catch (JSONException e) {
                                Toast.makeText(ApplicantFinalCheckProfileActivity.this, "Network Error, Please Try Again", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        }, error -> {
                            error.printStackTrace();
                            Toast.makeText(ApplicantFinalCheckProfileActivity.this, "Network Error, Please Try Again", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }) {
                            //  public Map<String, String> getHeaders() throws AuthFailureError {
                            //     HashMap<String,String> map = new HashMap<>();
                            //    String token = userPref.getString("token","");
                            //    map.put("Authorization","Bearer"+token);
                            //     return map;
                            //}

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> map = new HashMap<>();
                                //map.put("id",user_id);
                                map.put("applicant_id", user_id);
                                map.put("job_id", val_job_id);
                                map.put("applyjob_id", val_saved_id);
                                map.put("name", txtname.getText().toString().trim());
                                map.put("email", txtemail.getText().toString().trim());
                                map.put("contactno", txtcontactno.getText().toString().trim());
                                map.put("address", txtaddress.getText().toString().trim());
                                map.put("gender", txtgender.getText().toString().trim());
                                map.put("status", "pending");
                                map.put("profile_pic", profile_pic);
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

                        RequestQueue queue = Volley.newRequestQueue(ApplicantFinalCheckProfileActivity.this);
                        queue.add(request);
                    }
                }
            }
        });
        txtaddwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ApplicantFinalCheckProfileActivity.this, AddWorkExperience.class);
                startActivity(i);
            }
        });
        txtaddeduc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ApplicantFinalCheckProfileActivity.this, AddEducationActivity.class);
                startActivity(i);
            }
        });
        txtintentresume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ApplicantFinalCheckProfileActivity.this, UploadResumeActivity.class);
                i.putExtra("applicant_id", user_id);
                startActivity(i);
            }
        });
        txtviewresume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://peso-ojfss.000webhostapp.com/storage/resume/" + txtresume.getText().toString()));
                startActivity(browserIntent);
            }
        });
        StringRequest request = new StringRequest(Request.Method.GET, Constant.MY_POST + "?applicant_id=" + user_id, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")) {
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
                    Picasso.get().load(Constant.URL + "/storage/profiles/" + user.getString("profile_pic")).into(imageview_user);
                    imgUrl = Constant.URL + "/storage/profiles/" + user.getString("profile_pic");
                    profile_pic = user.getString("profile_pic");
                    if(user.get("contactno").toString().equals("null")|| user.get("address").toString().equals("null")|| user.get("Specialization").toString().equals("null")|| user.get("gender").toString().equals("null"))
                    {
                        txtcontactno.setVisibility(View.GONE);
                        txtaddress.setVisibility(View.GONE);
                        txtspecialization.setVisibility(View.GONE);
                        txtgender.setVisibility(View.GONE);
                    }
                    else {
                        txtcontactno.setVisibility(View.VISIBLE);
                        txtaddress.setVisibility(View.VISIBLE);
                        txtspecialization.setVisibility(View.VISIBLE);
                        txtgender.setVisibility(View.VISIBLE);
                        if(user.get("resume").toString().equals("null"))
                        {
                            txtresume.setVisibility(View.GONE);
                            txtviewresume.setVisibility(View.GONE);
                            ln_noresumelayout.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            txtresume.setVisibility(View.VISIBLE);
                            txtviewresume.setVisibility(View.VISIBLE);
                            ln_noresumelayout.setVisibility(View.GONE);
                        }
                    }

                } else {
                    Toast.makeText(ApplicantFinalCheckProfileActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                    // progressDialog.cancel();
                }

            } catch (JSONException e) {
                //Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                txtcontactno.setVisibility(View.GONE);
                txtaddress.setVisibility(View.GONE);
                txtspecialization.setVisibility(View.GONE);
                imageview_user.setBackgroundResource(R.drawable.img);
                txtgender.setVisibility(View.GONE);
                txtresume.setVisibility(View.GONE);
                txtviewresume.setVisibility(View.GONE);
                txtname.setText(name2);
                txtemail.setText(email);
                //  progressDialog.cancel();
            }
        }, error -> {
            error.printStackTrace();
            Toast.makeText(ApplicantFinalCheckProfileActivity.this, "Network Error, Please Try Again", Toast.LENGTH_SHORT).show();
            txtname.setText(name2);
            imageview_user.setBackgroundResource(R.drawable.img);
            txtemail.setText(email);
            // progressDialog.cancel();
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                //String token = userPref.getString("token","token");
                map.put("Authorization", "Bearer " + token);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ApplicantFinalCheckProfileActivity.this);
        queue.add(request);

//        StringRequest request2 = new StringRequest(Request.Method.GET, Constant.geteducation+"?educational_id="+user_id, response ->{
//            try{
//                JSONObject object = new JSONObject(response);
//                if(object.getBoolean("success"))
//                {
//                    JSONArray array = new JSONArray(object.getString("education"));
//                    for(int i = 0; i < array.length(); i++)
//                    {
//                        JSONObject postObject = array.getJSONObject(i);
//                        //JSONObject getpostObject = postObject.getJSONObject("jobposts");
//
//                        educationalbackground educationalbackground2 = new educationalbackground();
//                        educationalbackground2.setApplicantschool(postObject.getString("school"));
//                        educationalbackground2.setApplicantcourse(postObject.getString("course"));
//                        educationalbackground2.setApplicantyeargraduated(postObject.getString("year"));
//
//
//                        arraylist.add(educationalbackground2);
//                        arraylist2.add(educationalbackground2);
//                    }
//                    educationalbackgroundadapter = new educationalbackgroundadapter(arraylist,getContext());
//                    recyclerView.setAdapter(educationalbackgroundadapter);
//                }
//                else {
//                    recyclerView.setVisibility(View.GONE);
//                    Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
//                }
//            }catch(JSONException e)
//            {
//                e.printStackTrace();
//                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                recyclerView.setVisibility(View.GONE);
//            }
//
//
//
//        },error -> {
//            error.printStackTrace();
//            Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
//            recyclerView.setVisibility(View.GONE);
//        }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String,String> map = new HashMap<>();
//                return map;
//            }
//        };
//
//        RequestQueue queue2 = Volley.newRequestQueue(getContext());
//        queue2.add(request2);
        // Toast.makeText(getContext(), user_id, Toast.LENGTH_SHORT).show();
//        editor.putString("name",name2);
        //   editor.putString("id1",user_id);
        //   editor.apply();
        //   editor.commit();
        // getData();
        //  setOnClickListener();
        // refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        //     @Override
        //      public void onRefresh() {
        // arraylist.clear();
        //           getData();
        //       }
        //   });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ApplicantFinalCheckProfileActivity.this, EditProfileActivity.class);
                i.putExtra("name", txtname.getText().toString());
                i.putExtra("email", txtemail.getText().toString());
                if (val_contactno.equals("null") || val_address.equals("null") || val_specialization.equals("null") || val_gender.equals("null")) {
                    i.putExtra("contactno", "");
                    i.putExtra("address", "");
                    i.putExtra("specialization", "");
                    i.putExtra("gender", "");
//                    Toast.makeText(getApplicationContext(),"error1",Toast.LENGTH_SHORT).show();

                }
                 else {
                    i.putExtra("contactno", txtcontactno.getText().toString());
                    i.putExtra("address", txtaddress.getText().toString());
                    i.putExtra("specialization", txtspecialization.getText().toString());
                    i.putExtra("gender", txtgender.getText().toString());
                    if (imageview_user.getDrawable() == null) {
                        i.putExtra("profile_pic", imgUrl);
                    }
                    else
                    {
                        i.putExtra("profile_pic", imgUrl);
                    }

                }
                startActivity(i);
            }
        });
    }

    public void onResume() {
        super.onResume();
        val_id = userPref.getString("id","id");
        val_saved_id = userPref.getString("saved_id","saved_id");
        val_job_id = userPref.getString("job_id","job_id");
        main.setVisibility(View.GONE);
        main2.setVisibility(View.GONE);
        main3.setVisibility(View.GONE);
        main4.setVisibility(View.GONE);
        footer.setVisibility(View.GONE);
        delay();
        StringRequest request = new StringRequest(Request.Method.GET, Constant.MY_POST + "?applicant_id=" + userPref2.getString("id", "id"), response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")) {
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
                    Picasso.get().load(Constant.URL + "/storage/profiles/" + user.getString("profile_pic")).into(imageview_user);
                    imgUrl = Constant.URL + "/storage/profiles/" + user.getString("profile_pic");
                    profile_pic = user.getString("profile_pic");
                    SharedPreferences.Editor editor2 = userPref2.edit();
                    editor2.putString("name", user.getString("name"));
                    editor2.putString("address", user.getString("address"));
                    editor2.putString("contactno", user.getString("contactno"));
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
                    else {
                        txtcontactno.setVisibility(View.VISIBLE);
                        txtaddress.setVisibility(View.VISIBLE);
                        txtspecialization.setVisibility(View.VISIBLE);
                        txtgender.setVisibility(View.VISIBLE);
                        if(user.get("resume").toString().equals("null"))
                        {
                            txtresume.setVisibility(View.GONE);
                            txtviewresume.setVisibility(View.GONE);
                            ln_noresumelayout.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            txtresume.setVisibility(View.VISIBLE);
                            txtviewresume.setVisibility(View.VISIBLE);
                            ln_noresumelayout.setVisibility(View.GONE);
                        }
                    }

                } else {
                    Toast.makeText(ApplicantFinalCheckProfileActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                    // progressDialog.cancel();
                }

            } catch (JSONException e) {
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
        }, error -> {
            error.printStackTrace();
            Toast.makeText(ApplicantFinalCheckProfileActivity.this, "Network Error, Please Try Again", Toast.LENGTH_SHORT).show();
            // progressDialog.cancel();
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                //String token = userPref.getString("token","token");
                map.put("Authorization", "Bearer " + token);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ApplicantFinalCheckProfileActivity.this);
        queue.add(request);
        arraylist.clear();
        StringRequest request2 = new StringRequest(Request.Method.GET, Constant.geteducation + "?educational_id=" + user_id, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")) {
                    JSONArray array = new JSONArray(object.getString("education"));
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject postObject = array.getJSONObject(i);
                        //JSONObject getpostObject = postObject.getJSONObject("jobposts");

                        educationalbackground educationalbackground2 = new educationalbackground();
                        educationalbackground2.setApplicantschool(postObject.getString("school"));
                        educationalbackground2.setApplicantcourse(postObject.getString("course"));
                        educationalbackground2.setApplicantyeargraduated(postObject.getString("year"));
                        educationalbackground2.setId(postObject.getString("id"));


                        arraylist.add(educationalbackground2);

                    }
                    educationalbackgroundadapter = new educationalbackgroundadapter(arraylist, ApplicantFinalCheckProfileActivity.this, listener);
                    recyclerView.setAdapter(educationalbackgroundadapter);
                    educationalbackgroundadapter.notifyDataSetChanged();
                    if (arraylist.isEmpty()) {
                        ln_noeducationlayout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        ln_noeducationlayout.setVisibility(View.GONE);
                    }
                } else {
//                    Toast.makeText(ApplicantFinalCheckProfileActivity.this, "error", Toast.LENGTH_SHORT).show();
                    recyclerView.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                recyclerView.setVisibility(View.GONE);
                e.printStackTrace();
                Toast.makeText(ApplicantFinalCheckProfileActivity.this, "Network Error, Please Try Again", Toast.LENGTH_SHORT).show();
            }


        }, error -> {
            error.printStackTrace();
            Toast.makeText(ApplicantFinalCheckProfileActivity.this, "Network Error, Please Try Again", Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.GONE);
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue queue2 = Volley.newRequestQueue(ApplicantFinalCheckProfileActivity.this);
        queue2.add(request2);

        //workexperience
        arraylist2.clear();
        StringRequest request3 = new StringRequest(Request.Method.GET, Constant.getworkexperience + "?workexp_id=" + user_id, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")) {
                    JSONArray array = new JSONArray(object.getString("workexperience"));
                    for (int i = 0; i < array.length(); i++) {
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
                    workexperienceadapter2 = new workexperienceadapter(arraylist2, ApplicantFinalCheckProfileActivity.this, worklistener);
                    recyclerView2.setAdapter(workexperienceadapter2);
                    workexperienceadapter2.notifyDataSetChanged();
                    if (arraylist2.isEmpty()) {
                        ln_noworkexperiencelayout.setVisibility(View.VISIBLE);
                        recyclerView2.setVisibility(View.GONE);
                    } else {
                        recyclerView2.setVisibility(View.VISIBLE);
                        ln_noworkexperiencelayout.setVisibility(View.GONE);
                    }
                } else {
//                    Toast.makeText(ApplicantFinalCheckProfileActivity.this, "error", Toast.LENGTH_SHORT).show();
                    recyclerView2.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                recyclerView2.setVisibility(View.GONE);
                e.printStackTrace();
                Toast.makeText(ApplicantFinalCheckProfileActivity.this, "Network Error, Please Try Again", Toast.LENGTH_SHORT).show();
            }


        }, error -> {
            error.printStackTrace();
            Toast.makeText(ApplicantFinalCheckProfileActivity.this, "Network Error, Please Try Again", Toast.LENGTH_SHORT).show();
            recyclerView2.setVisibility(View.GONE);
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue queue3 = Volley.newRequestQueue(ApplicantFinalCheckProfileActivity.this);
        queue3.add(request3);
    }

    private void setOnClickListener() {
        listener = new educationalbackgroundadapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(ApplicantFinalCheckProfileActivity.this, UpdateEducationActivity.class);
                intent.putExtra("educ_id", arraylist.get(position).getId());
                intent.putExtra("intentschool", arraylist.get(position).getApplicantschool());
                intent.putExtra("intentcourse", arraylist.get(position).getApplicantcourse());
                intent.putExtra("intentyear", arraylist.get(position).getApplicantyeargraduated());
                startActivity(intent);
            }
        };
    }

    private void setOnClickListener2() {
        worklistener = new workexperienceadapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(ApplicantFinalCheckProfileActivity.this, UpdateWorkExperienceActivity.class);
                intent.putExtra("work_id", arraylist2.get(position).getId());
                intent.putExtra("intentworkposition", arraylist2.get(position).getApplicantworkposition());
                intent.putExtra("intentworkcompanyname", arraylist2.get(position).getApplicantworkcompanyname());
                intent.putExtra("intentspecialization", arraylist2.get(position).getApplicantworkspecialization());
                String splitworkdate = arraylist2.get(position).getApplicantworkdate();
                String[] parts = splitworkdate.split(" - ");
                String part1 = parts[0];
                String part2 = parts[1];
                intent.putExtra("intentworkdate", part1);
                intent.putExtra("intentenddate", part2);
                startActivity(intent);
            }
        };
    }

    public void delay() {
        main.setVisibility(View.GONE);
        CDT = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {
                ln_delay.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                ln_delay.setVisibility(View.GONE);
                main.setVisibility(View.VISIBLE);
                main2.setVisibility(View.VISIBLE);
                main3.setVisibility(View.VISIBLE);
                main4.setVisibility(View.VISIBLE);
                footer.setVisibility(View.VISIBLE);

            }
        }.start();

    }

    private boolean validate() {
        if (val_address.isEmpty() || val_contactno.isEmpty() || val_specialization.isEmpty() || val_gender.isEmpty()) {
            Toast.makeText(ApplicantFinalCheckProfileActivity.this, "Please Update Your Profile", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(arraylist.isEmpty())
        {
            Toast.makeText(ApplicantFinalCheckProfileActivity.this, "Please Add Your Educational Background", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(val_resume.isEmpty() || val_resume.equals("null"))
        {
            Toast.makeText(ApplicantFinalCheckProfileActivity.this, "Please Add Your Resume", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}