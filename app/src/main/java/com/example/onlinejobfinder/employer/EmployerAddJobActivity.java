package com.example.onlinejobfinder.employer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmployerAddJobActivity extends AppCompatActivity {

    CircleImageView joblogo;
    EditText jobtitle,jobaddress,jobsalary,jobdescription,jobcompanyoverview;
    TextView jobcompanyname, jobregion,jobspecialization,jobemail;
    Button btnsendjobapprove,btndelete;
    ProgressDialog progressDialog;
    SharedPreferences userPref2;
    String pending = "pending";
    String name2, user_id,token,email,joblogo2;
    int position =0;
    int position2 =0;
    JSONArray result,result2;
    boolean[] selectedspecialization, selectedlocation;
    ArrayList<String> category,location;
    String [] specializationarray, locationarray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_add_job);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        joblogo = findViewById(R.id.img_addjobeditemployer_imguser);
        jobtitle = findViewById(R.id.edittext_addjobemployerjobtitle);
        jobemail = findViewById(R.id.tv_addjobemail);
        jobaddress = findViewById(R.id.edittext_addjobemployeraddress);
        jobsalary = findViewById(R.id.edittext_addjobemployersalary);
        jobdescription = findViewById(R.id.edittext_addjobemployerjobdescription);
        jobcompanyoverview = findViewById(R.id.edittext_addjobemployercompanyoverview);
        jobcompanyname = findViewById(R.id.tv_addjobemployercompanyname);
        jobregion = findViewById(R.id.tv_addjobemployerregion);
        jobspecialization = findViewById(R.id.tv_addjobemployerspecialization);
        btnsendjobapprove = findViewById(R.id.btnaddjobsaveimage);
        jobcompanyname.setText(getIntent().getExtras().getString("jobcompanyname"));
        jobemail.setText(getIntent().getExtras().getString("jobemail"));
        jobaddress.setText(getIntent().getExtras().getString("jobaddress"));
        jobcompanyoverview.setText(getIntent().getExtras().getString("jobcompanyoverview"));
        Picasso.get().load(getIntent().getStringExtra("jobcompanylogo")).into(joblogo);
        joblogo2 = getIntent().getStringExtra("jobcompanylogo2");
        userPref2 = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        name2 = userPref2.getString("name","name");
        email = userPref2.getString("email","email");
        user_id = userPref2.getString("id","id");
        token = userPref2.getString("token","token");
        category = new ArrayList<String>();
        location = new ArrayList<String>();
        Toast.makeText(EmployerAddJobActivity.this,joblogo2,Toast.LENGTH_SHORT).show();

        jobspecialization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        EmployerAddJobActivity.this
                );
                jobspecialization.setText(specializationarray[position]);
                builder.setTitle("Select Specialization");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(specializationarray, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        position = i;
                        jobspecialization.setText(specializationarray[i]);
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
                        jobspecialization.setText("Specialization");
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j=0; j<selectedspecialization.length; j++)
                        {
                            selectedspecialization[j] = false;
                            // Specialization.clear();
                            jobspecialization.setText("Specialization");
                        }
                    }
                });
                builder.show();
            }
        });
        jobregion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        EmployerAddJobActivity.this
                );
                jobregion.setText(locationarray[position2]);
                builder.setTitle("Select Region");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(locationarray, position2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        position2 = i;
                        jobregion.setText(locationarray[i]);
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
                        jobregion.setText("Region");
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j=0; j<selectedlocation.length; j++)
                        {
                            selectedlocation[j] = false;
                            // Specialization.clear();
                            jobregion.setText("Region");
                        }
                    }
                });
                builder.show();
            }
        });

        btnsendjobapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Sending");
                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, Constant.addjob, response -> {
                    try{
                        JSONObject object= new JSONObject(response);
                        if(object.getBoolean("success")){
                            //JSONObject user = object.getJSONObject("user");
                            onBackPressed();
                            Toast.makeText(EmployerAddJobActivity.this,"Job Approval Successfully Send",Toast.LENGTH_SHORT).show();
//                            SharedPreferences userPref = getActivity().getApplicationContext().getSharedPreferences("user",getContext().MODE_PRIVATE);
//                            SharedPreferences.Editor editor = userPref.edit();
//                            editor.putString("token",object.getString("token"));
//                            editor.putString("email",user.getString("email"));
//                            editor.putString("name",user.getString("name"));
//                            editor.putString("role",user.getString("role"));
//                            editor.apply();
//                            Toast.makeText(getContext(),"Register Successfully",Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                        else if(object.getString("Status").equals("202"))
                        {
                            Toast.makeText(EmployerAddJobActivity.this,"Job Vacant Already Exists",Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                        else
                        {
                            Toast.makeText(EmployerAddJobActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }

                    }catch(JSONException e)
                    {
                        Toast.makeText(EmployerAddJobActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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
                        map.put("job_id",user_id);
                        map.put("companyname",jobcompanyname.getText().toString().trim());
                        map.put("jobtitle",jobtitle.getText().toString().trim());
                        map.put("email",jobemail.getText().toString().trim());
                        map.put("category",jobspecialization.getText().toString());
                        map.put("companyoverview",jobcompanyoverview.getText().toString().trim());
                        map.put("jobdescription",jobdescription.getText().toString().trim());
                        map.put("location",jobregion.getText().toString());
                        map.put("salary",jobsalary.getText().toString().trim());
                        map.put("address",jobaddress.getText().toString().trim());
                        map.put("logo",joblogo2.trim());
                        map.put("jobstatus",pending);
                        return map;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(EmployerAddJobActivity.this);
                queue.add(request);
            }
        });


    }
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    private void getCategory() {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.categoryfilter, response ->{
            JSONObject j = null;
            try{
                j = new JSONObject(response);
                result = j.getJSONArray("categories");
                getSubCategory(result);


            }catch(JSONException e)
            {
                e.printStackTrace();
                Toast.makeText(EmployerAddJobActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }

           // refreshLayout.setRefreshing(false);

        },error -> {
            error.printStackTrace();
           // refreshLayout.setRefreshing(false);
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(EmployerAddJobActivity.this);
        queue.add(request);
    }

    private void getSubCategory(JSONArray j) {
        for(int ai=0;ai<j.length();ai++)
        {
            try{
                JSONObject json = j.getJSONObject(ai);
                category.add(json.getString("category"));
            }catch (JSONException e)
            {
                e.printStackTrace();
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


            }catch(JSONException e)
            {
                e.printStackTrace();
                Toast.makeText(EmployerAddJobActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }

          //  refreshLayout.setRefreshing(false);

        },error -> {
            error.printStackTrace();
           // refreshLayout.setRefreshing(false);
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(EmployerAddJobActivity.this);
        queue.add(request);
    }

    private void getSubLocation(JSONArray j) {
        for(int ai=0;ai<j.length();ai++)
        {
            try{
                JSONObject json = j.getJSONObject(ai);
                location.add(json.getString("region"));
            }catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        locationarray = location.toArray(new String[0]);
        //Toast.makeText(AddWorkExperience.this, specializationarray[ai], Toast.LENGTH_SHORT).show();
        selectedlocation = new boolean[locationarray.length];
//        spinnercategory.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, category));
    }

    public void onResume() {
        super.onResume();
        getCategory();
        getLocation();
//        getPost();
//        arraylist.clear();
        category.clear();
        location.clear();
    }
}