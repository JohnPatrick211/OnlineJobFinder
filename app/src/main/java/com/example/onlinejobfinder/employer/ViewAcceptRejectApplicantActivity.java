package com.example.onlinejobfinder.employer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinejobfinder.ApplicantActivity;
import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.MainActivity;
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

public class ViewAcceptRejectApplicantActivity extends AppCompatActivity {
    LinearLayout main, main2, main3, main4;
    RelativeLayout footer;
    View ln_delay;
    View ln_noviewworkexperiencelayout;
    CountDownTimer CDT;
    TextView txtmanageaccount, txtname, txtemail, txtcontactno, txtaddress, txtgender, txtspecialization, txtresume,txtintentresume, txtviewresume, txtaddeduc,txtaddwork;
    ImageView editprofile;
    com.example.onlinejobfinder.adapter.vieweducationalbackgroundadapter educationalbackgroundadapter;
    viewworkexperienceadapter workexperienceadapter2;
    String profile_pic;
    SharedPreferences userPref,userPref2;
    //  TextView textView_name;
    // TextView textView_manage;
    //jobadapter.RecyclerViewClickListener listener;
    ImageView imageview_user;
    String name2, user_id,token,permaid, address, email, contactno, background,id,job_id,saved_id,applicant_id;
    String val_contactno = "";
    String val_address = "";
    String val_gender = "";
    String val_specialization = "";
    String val_resume = "";
    String val_name ="";
    String val_id = "";
    ArrayList<educationalbackground> arraylist;
    ArrayList<workexperience> arraylist2;
    RecyclerView recyclerView, recyclerView2;
    JSONArray result;
    Button btnhire, btndecline;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_accept_reject_applicant);
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
        recyclerView = findViewById(R.id.recyclerview_appliededucationalbackground);
        recyclerView2 = findViewById(R.id.recyclerview_workexperience);
        recyclerView2.setLayoutManager(new LinearLayoutManager(ViewAcceptRejectApplicantActivity.this));
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewAcceptRejectApplicantActivity.this));
        arraylist = new ArrayList<>();
        arraylist2 = new ArrayList<>();
        btndecline = findViewById(R.id.btn_appliedapplicantreject);
        btnhire = findViewById(R.id.btn_appliedapplicantaccept);
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
//        job_id = getIntent().getExtras().getString("intentjob_id");
//        id = getIntent().getExtras().getString("intentid");
//        saved_id = getIntent().getExtras().getString("intentsavedid");
        applicant_id = getIntent().getExtras().getString("intentapplicant_id");
        val_id = getIntent().getExtras().getString("intentid");
        ln_noviewworkexperiencelayout = findViewById(R.id.ln_noviewworkexperiencelayout);
        //check ID debugging//
//        Toast.makeText(ViewAcceptRejectApplicantActivity.this,  val_id, Toast.LENGTH_SHORT).show();
        //Toast.makeText(ApplicantFinalCheckProfileActivity.this, email, Toast.LENGTH_SHORT).show();
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
        getSupportActionBar().setCustomView(R.layout.customactionbarapplicantprofile);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        delay();
       btnhire.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder builder = new AlertDialog.Builder(ViewAcceptRejectApplicantActivity.this);
               ViewGroup viewGroup = findViewById(R.id.content);
               View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_hiring,viewGroup,false);
               builder.setView(dialogView);
               builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       progressDialog.setMessage("Sending");
                       progressDialog.show();
                       StringRequest request = new StringRequest(Request.Method.POST, Constant.applicanthired, response -> {
                           try{
                               JSONObject object= new JSONObject(response);
                               if(object.getBoolean("success")){
                                   onBackPressed();
                                   Toast.makeText(ViewAcceptRejectApplicantActivity.this,"Applicant Hired Successfully",Toast.LENGTH_SHORT).show();
                                   progressDialog.cancel();
                               }
                               else
                               {
                                   Toast.makeText(ViewAcceptRejectApplicantActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                                   progressDialog.cancel();
                               }

                           }catch(JSONException e)
                           {
                               Toast.makeText(ViewAcceptRejectApplicantActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                               progressDialog.cancel();
                           }
                       },error ->{
                           error.printStackTrace();
                           progressDialog.cancel();
                       })
                       {
                           @Override
                           protected Map<String, String> getParams() throws AuthFailureError {
                               HashMap<String,String> map = new HashMap<>();
                               map.put("id",val_id);
                               map.put("applicant_id",applicant_id);
                               return map;
                           }
                       };
                       RequestQueue queue = Volley.newRequestQueue(ViewAcceptRejectApplicantActivity.this);
                       queue.add(request);

                   }
               });
               builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {

                   }
               });
               builder.show();
           }
       });
       btndecline.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder builder = new AlertDialog.Builder(ViewAcceptRejectApplicantActivity.this);
               ViewGroup viewGroup = findViewById(R.id.content);
               View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_declining,viewGroup,false);
               builder.setView(dialogView);
               builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       progressDialog.setMessage("Declining");
                       progressDialog.show();
                       StringRequest request = new StringRequest(Request.Method.POST, Constant.deleteapplicantapply, response -> {
                           try{
                               JSONObject object= new JSONObject(response);
                               if(object.getBoolean("success")){
                                   //JSONObject user = object.getJSONObject("update");
                                   //SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                                   // SharedPreferences.Editor editor = userPref.edit();
                                   //editor.putString("id",user.getString("educational_id"));
                                   progressDialog.cancel();

                                   //editor.apply();
                                   //  editor.commit();
                                   Toast.makeText(ViewAcceptRejectApplicantActivity.this,"Decline Successfully",Toast.LENGTH_SHORT).show();
                                   onBackPressed();


                               }
                               else
                               {
                                   Toast.makeText(ViewAcceptRejectApplicantActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                                   progressDialog.cancel();
                               }

                           }catch(JSONException e)
                           {
                               Toast.makeText(ViewAcceptRejectApplicantActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                               progressDialog.cancel();
                           }
                       },error ->{
                           error.printStackTrace();
                           Toast.makeText(ViewAcceptRejectApplicantActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                           progressDialog.cancel();
                       })
                       {

                           @Override
                           protected Map<String, String> getParams() throws AuthFailureError {
                               HashMap<String,String> map = new HashMap<>();
                               //map.put("id",user_id);
                               map.put("id",val_id);
                               return map;
                           }
                       };

                       RequestQueue queue = Volley.newRequestQueue(ViewAcceptRejectApplicantActivity.this);
                       queue.add(request);

                   }
               });
               builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {

                   }
               });
               builder.show();
           }
       });
        txtviewresume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://peso-ojfss.000webhostapp.com/storage/resume/"+txtresume.getText().toString()));
                startActivity(browserIntent);
            }
        });
       //get Data
        StringRequest request = new StringRequest(Request.Method.GET, Constant.MY_POST+"?applicant_id="+applicant_id, response -> {
            try{
                JSONObject object= new JSONObject(response);
                if(object.getBoolean("success")){
                    JSONObject user = object.getJSONObject("user");
                    txtname.setText(user.get("name").toString());
                    val_name = user.get("name").toString();
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
                    Toast.makeText(ViewAcceptRejectApplicantActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(ViewAcceptRejectApplicantActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
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

        RequestQueue queue = Volley.newRequestQueue(ViewAcceptRejectApplicantActivity.this);
        queue.add(request);
    }

    public void onResume() {
        super.onResume();
        delay();
        main.setVisibility(View.GONE);
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
                    Toast.makeText(ViewAcceptRejectApplicantActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(ViewAcceptRejectApplicantActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
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

        RequestQueue queue = Volley.newRequestQueue(ViewAcceptRejectApplicantActivity.this);
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
                    educationalbackgroundadapter = new vieweducationalbackgroundadapter(arraylist,ViewAcceptRejectApplicantActivity.this);
                    recyclerView.setAdapter(educationalbackgroundadapter);
                    educationalbackgroundadapter.notifyDataSetChanged();

                }
                else {
                    Toast.makeText(ViewAcceptRejectApplicantActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                    recyclerView.setVisibility(View.GONE);
                }
            }catch(JSONException e)
            {
                recyclerView.setVisibility(View.GONE);
                e.printStackTrace();
                Toast.makeText(ViewAcceptRejectApplicantActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
            }



        },error -> {
            error.printStackTrace();
            Toast.makeText(ViewAcceptRejectApplicantActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.GONE);
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue queue2 = Volley.newRequestQueue(ViewAcceptRejectApplicantActivity.this);
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
                    workexperienceadapter2 = new viewworkexperienceadapter(arraylist2,ViewAcceptRejectApplicantActivity.this);
                    recyclerView2.setAdapter(workexperienceadapter2);
                    workexperienceadapter2.notifyDataSetChanged();
                    if(arraylist2.isEmpty())
                    {
                        ln_noviewworkexperiencelayout.setVisibility(View.VISIBLE);
                        recyclerView2.setVisibility(View.GONE);
                    }
                    else
                    {
                        recyclerView2.setVisibility(View.VISIBLE);
                        ln_noviewworkexperiencelayout.setVisibility(View.GONE);
                    }
                }
                else {
                    Toast.makeText(ViewAcceptRejectApplicantActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                    recyclerView2.setVisibility(View.GONE);
                }
            }catch(JSONException e)
            {
                recyclerView2.setVisibility(View.GONE);
                e.printStackTrace();
                Toast.makeText(ViewAcceptRejectApplicantActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
            }



        },error -> {
            error.printStackTrace();
            Toast.makeText(ViewAcceptRejectApplicantActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
            recyclerView2.setVisibility(View.GONE);
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue queue3 = Volley.newRequestQueue(ViewAcceptRejectApplicantActivity.this);
        queue3.add(request3);
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
                main.setVisibility(View.VISIBLE);
                main2.setVisibility(View.VISIBLE);
                main3.setVisibility(View.VISIBLE);
                main4.setVisibility(View.VISIBLE);
                footer.setVisibility(View.VISIBLE);

            }
        }.start();

    }
}