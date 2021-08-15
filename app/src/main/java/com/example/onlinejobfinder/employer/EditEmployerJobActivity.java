package com.example.onlinejobfinder.employer;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.example.onlinejobfinder.applicant.ApplicantJobDescriptionActivity;
import com.example.onlinejobfinder.applicant.UpdateWorkExperienceActivity;
import com.example.onlinejobfinder.model.appliedapplicants;
import com.example.onlinejobfinder.model.job;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditEmployerJobActivity extends AppCompatActivity {

    EditText edit_jobtitle, edit_jobsalary, edit_jobaddress, edit_jobdescription,edit_jobcompanyoverview;
    TextView edit_jobcompanyname, edit_jobregion, edit_jobemail, edit_jobspecialization;
    CircleImageView edit_joblogo;
    Button btn_editsendapprove,btndelete;
    ProgressDialog progressDialog;
    String intentid,intentjobtitle,intentjobsalary,intentjobaddress,
            intentjobdescription, intentjobcompanyoverview, intentjobcompanyname,
            intentjobregion,intentjobemail,intentjobspecialization, intentjoblogo;
    String pending = "pending";
    int position =0;
    int position2 =0;
    JSONArray result,result2;
    boolean[] selectedspecialization, selectedlocation;
    ArrayList<String> category,location;
    String [] specializationarray, locationarray;
    boolean remjob = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employer_jobs);

        intentid = getIntent().getExtras().getString("intentid");
        edit_jobtitle = findViewById(R.id.edittext_editaddjobemployerjobtitle);
        edit_jobtitle.setText(getIntent().getExtras().getString("intentjobtitle"));
        edit_jobsalary = findViewById(R.id.edittext_editaddjobemployersalary);
        edit_jobsalary.setText(getIntent().getExtras().getString("intentjobsalary"));
        edit_jobaddress = findViewById(R.id.edittext_editaddjobemployeraddress);
        edit_jobaddress.setText(getIntent().getExtras().getString("intentjobaddress"));
        edit_jobdescription = findViewById(R.id.edittext_editaddjobemployerjobdescription);
        edit_jobdescription.setText(getIntent().getExtras().getString("intentjobdescription"));
        edit_jobcompanyoverview = findViewById(R.id.edittext_editaddjobemployercompanyoverview);
        edit_jobcompanyoverview.setText(getIntent().getExtras().getString("intentjobcompanyoverview"));
        edit_jobcompanyname = findViewById(R.id.tv_editaddjobemployercompanyname);
        edit_jobcompanyname.setText(getIntent().getExtras().getString("intentjobcompany"));
        edit_jobregion = findViewById(R.id.tv_editaddjobemployerregion);
        edit_jobregion.setText(getIntent().getExtras().getString("intentjoblocation"));
        edit_jobemail = findViewById(R.id.tv_editaddjobemail);
        edit_jobemail.setText(getIntent().getExtras().getString("intentjobemail"));
        edit_jobspecialization = findViewById(R.id.tv_editaddjobemployerspecialization);
        edit_jobspecialization.setText(getIntent().getExtras().getString("intentjobspecialization"));
        edit_joblogo = findViewById(R.id.img_editaddjobeditemployer_imguser);
        Picasso.get().load(getIntent().getStringExtra("intentjoblogo")).into(edit_joblogo);
        intentjoblogo = getIntent().getExtras().getString("jobcompanylogo2");
        btn_editsendapprove = findViewById(R.id.btneditaddjobsaveimage);
        category = new ArrayList<String>();
        location = new ArrayList<String>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        getPost();

        btndelete = findViewById(R.id.btndeleteeditjobsaveimage);

        edit_jobtitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edit_jobtitle.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                edit_jobtitle.setError(null);
            }
        });
        edit_jobsalary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edit_jobsalary.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                edit_jobsalary.setError(null);
            }
        });
        edit_jobaddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edit_jobaddress.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                edit_jobaddress.setError(null);
            }
        });
        edit_jobdescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edit_jobdescription.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                edit_jobdescription.setError(null);
            }
        });
        edit_jobcompanyoverview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    edit_jobcompanyoverview.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                edit_jobcompanyoverview.setError(null);
            }
        });
        edit_jobspecialization.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edit_jobspecialization.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                edit_jobspecialization.setError(null);
            }
        });
        edit_jobregion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edit_jobregion.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                edit_jobregion.setError(null);
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(remjob)
                {
                    Toast.makeText(EditEmployerJobActivity.this,"Cannot Delete, there are pending applicants on this job",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    progressDialog.setMessage("Deleting");
                    progressDialog.show();
                    StringRequest request = new StringRequest(Request.Method.POST, Constant.unsavedjob, response -> {
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
                                Toast.makeText(EditEmployerJobActivity.this,"Delete Successfully",Toast.LENGTH_SHORT).show();
                                onBackPressed();


                            }
                            else
                            {
                                Toast.makeText(EditEmployerJobActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }

                        }catch(JSONException e)
                        {
                            Toast.makeText(EditEmployerJobActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    },error ->{
                        error.printStackTrace();
                        Toast.makeText(EditEmployerJobActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    })
                    {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> map = new HashMap<>();
                            //map.put("id",user_id);
                            map.put("id",intentid);
                            return map;
                        }
                    };

                    RequestQueue queue = Volley.newRequestQueue(EditEmployerJobActivity.this);
                    queue.add(request);
                }

            }
        });
        edit_jobregion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        EditEmployerJobActivity.this
                );
                edit_jobregion.setText(locationarray[position2]);
                builder.setTitle("Select Region");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(locationarray, position2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        position2 = i;
                        edit_jobregion.setText(locationarray[i]);
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
                        edit_jobregion.setText("Region");
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j=0; j<selectedlocation.length; j++)
                        {
                            selectedlocation[j] = false;
                            // Specialization.clear();
                            edit_jobregion.setText("Region");
                        }
                    }
                });
                builder.show();
            }
        });
        edit_jobspecialization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        EditEmployerJobActivity.this
                );
                edit_jobspecialization.setText(specializationarray[position]);
                builder.setTitle("Select Specialization");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(specializationarray, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        position = i;
                        edit_jobspecialization.setText(specializationarray[i]);
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
                        edit_jobspecialization.setText("Specialization");
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j=0; j<selectedspecialization.length; j++)
                        {
                            selectedspecialization[j] = false;
                            // Specialization.clear();
                            edit_jobspecialization.setText("Specialization");
                        }
                    }
                });
                builder.show();
            }
        });
        btn_editsendapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                    progressDialog.setMessage("Sending");
                    progressDialog.show();
                    StringRequest request = new StringRequest(Request.Method.POST, Constant.updatejob, response -> {
                        try{
                            JSONObject object= new JSONObject(response);
                            if(object.getBoolean("success")){
                                //JSONObject user = object.getJSONObject("user");
                                onBackPressed();
                                Toast.makeText(EditEmployerJobActivity.this,"Job Approval Successfully Send",Toast.LENGTH_SHORT).show();
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
//                        else if(object.getString("Status").equals("202"))
//                        {
//                            Toast.makeText(EditEmployerJobActivity.this,"Job Vacant Already Exists",Toast.LENGTH_SHORT).show();
//                            progressDialog.cancel();
//                        }
                            else
                            {
                                Toast.makeText(EditEmployerJobActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }

                        }catch(JSONException e)
                        {
                            Toast.makeText(EditEmployerJobActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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
                            map.put("id",intentid);
                            map.put("companyname",edit_jobcompanyname.getText().toString().trim());
                            map.put("jobtitle",edit_jobtitle.getText().toString().trim());
                            map.put("email",edit_jobemail.getText().toString().trim());
                            map.put("category",edit_jobspecialization.getText().toString());
                            map.put("companyoverview",edit_jobcompanyoverview.getText().toString().trim());
                            map.put("jobdescription",edit_jobdescription.getText().toString().trim());
                            map.put("location",edit_jobregion.getText().toString());
                            map.put("salary",edit_jobsalary.getText().toString().trim());
                            map.put("address",edit_jobaddress.getText().toString().trim());
                            map.put("logo", intentjoblogo.trim());
                            map.put("jobstatus",pending);
                            return map;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(EditEmployerJobActivity.this);
                    queue.add(request);
                }
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
                Toast.makeText(EditEmployerJobActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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

        RequestQueue queue = Volley.newRequestQueue(EditEmployerJobActivity.this);
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
                Toast.makeText(EditEmployerJobActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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

        RequestQueue queue = Volley.newRequestQueue(EditEmployerJobActivity.this);
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
        getPost();
//        arraylist.clear();
        category.clear();
        location.clear();
    }

    private void getPost() {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.validateemployerapplyjobpost + "?applyjob_id=" + intentid, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")) {
                    JSONArray array = new JSONArray(object.getString("jobpost"));
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject postObject = array.getJSONObject(i);
                        //JSONObject getpostObject = postObject.getJSONObject("jobposts");

//                        appliedapplicants appliedapplicants2 = new appliedapplicants();
//                        appliedapplicants2.setId(postObject.getString("id"));
//                        appliedapplicants2.setApplicantid(postObject.getString("applicant_id"));//
//                        appliedapplicants2.setJobapplyid(postObject.getString("applyjob_id"));
//                        appliedapplicants2.setJobid(postObject.getString("job_id"));
//                        appliedapplicants2.setProfilepic(postObject.getString("profile_pic"));
//                        appliedapplicants2.setName(postObject.getString("name"));
//                        appliedapplicants2.setEmail(postObject.getString("email"));
//                        appliedapplicants2.setAddress(postObject.getString("address"));
//                        appliedapplicants2.setContactno(postObject.getString("contactno"));
//                        appliedapplicants2.setStatus(postObject.getString("status"));
//                        appliedapplicants2.setGender(postObject.getString("gender"));
                        if (intentid.equals(postObject.getString("applyjob_id"))) {
                            remjob = true;
                        }
                    }

                } else {
                    Toast.makeText(EditEmployerJobActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(EditEmployerJobActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue queue = Volley.newRequestQueue(EditEmployerJobActivity.this);
        queue.add(request);
    }

    private boolean validate(){
        if (edit_jobtitle.getText().toString().isEmpty()){
            edit_jobtitle.setError("Job Title is required");
            return false;
        }
        if (edit_jobregion.getText().toString().equals("Region")) {
            edit_jobregion.setError("Region is required");
            edit_jobregion.requestFocus();
            return false;
        }
        if ( edit_jobaddress.getText().toString().isEmpty()){
            edit_jobaddress.setError("Address is required");
            return false;
        }
        if ( edit_jobsalary.getText().toString().isEmpty()){
            edit_jobsalary.setError("Salary is required");
            return false;
        }
        if ( edit_jobdescription.getText().toString().isEmpty()){
            edit_jobdescription.setError("Job Description is required");
            return false;
        }
        if ( edit_jobcompanyoverview.getText().toString().isEmpty()){
            edit_jobcompanyoverview.setError("Company Overview is required");
            return false;
        }
        if (edit_jobspecialization.getText().toString().equals("Specialization")) {
            edit_jobspecialization.setError("Specialization is required");
            edit_jobregion.requestFocus();
            return false;
        }

        return true;
    }
}