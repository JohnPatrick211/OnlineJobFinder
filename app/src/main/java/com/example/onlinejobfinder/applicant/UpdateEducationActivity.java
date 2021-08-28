package com.example.onlinejobfinder.applicant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
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
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UpdateEducationActivity extends AppCompatActivity {
    EditText update_school, update_course;
    TextView tv_year;
    Button btnupdateeduc,btndeleteeduc;
    ProgressDialog progressDialog;
    String intentid,intentschool,intentcourse,intentyear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_education);
        update_school = findViewById(R.id.edittext_updateschool);
        update_course = findViewById(R.id.edittext_updatecourse);
        tv_year = findViewById(R.id.tv_updateyeargraduation);
        btnupdateeduc = findViewById(R.id.btnupdateeducation);
        btndeleteeduc = findViewById(R.id.btndeleteeducation);
        intentid = getIntent().getExtras().getString("educ_id");
        intentschool = getIntent().getExtras().getString("intentschool");
        intentcourse = getIntent().getExtras().getString("intentcourse");
        intentyear = getIntent().getExtras().getString("intentyear");
        update_school.setText(intentschool);
        update_course.setText(intentcourse);
        tv_year.setText(intentyear);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        getSupportActionBar().setCustomView(R.layout.customactionbarupdateeducation);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //Toast.makeText(UpdateEducationActivity.this, intentid, Toast.LENGTH_SHORT).show();
        update_school.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                update_school.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                update_school.setError(null);
            }
        });
        update_course.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                update_course.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                update_course.setError(null);
            }
        });
        tv_year.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_year.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_year.setError(null);
            }
        });
        tv_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnYear(view);
            }
        });

        btndeleteeduc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Deleting");
                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, Constant.deleteeducation, response -> {
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
                            Toast.makeText(UpdateEducationActivity.this,"Delete Successfully",Toast.LENGTH_SHORT).show();
                            onBackPressed();


                        }
                        else
                        {
                            Toast.makeText(UpdateEducationActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }

                    }catch(JSONException e)
                    {
                        Toast.makeText(UpdateEducationActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                },error ->{
                    error.printStackTrace();
                    Toast.makeText(UpdateEducationActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
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

                RequestQueue queue = Volley.newRequestQueue(UpdateEducationActivity.this);
                queue.add(request);
            }
        });

        btnupdateeduc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                    progressDialog.setMessage("Saving");
                    progressDialog.show();
                    StringRequest request = new StringRequest(Request.Method.POST, Constant.updateeducation, response -> {
                        try{
                            JSONObject object= new JSONObject(response);
                            if(object.getBoolean("success")){
                                JSONObject user = object.getJSONObject("update");
                                //SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                                // SharedPreferences.Editor editor = userPref.edit();
                                //editor.putString("id",user.getString("educational_id"));
                                progressDialog.cancel();

                                //editor.apply();
                                //  editor.commit();
                                Toast.makeText(UpdateEducationActivity.this,"Saved Successfully",Toast.LENGTH_SHORT).show();
                                onBackPressed();


                            }
                            else
                            {
                                Toast.makeText(UpdateEducationActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }

                        }catch(JSONException e)
                        {
                            Toast.makeText(UpdateEducationActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    },error ->{
                        error.printStackTrace();
                        Toast.makeText(UpdateEducationActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    })
                    {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> map = new HashMap<>();
                            //map.put("id",user_id);
                            map.put("id",intentid);
                            map.put("school",update_school.getText().toString().trim());
                            map.put("course",update_course.getText().toString().trim());
                            map.put("year",tv_year.getText().toString().trim());
                            return map;
                        }
                    };

                    RequestQueue queue = Volley.newRequestQueue(UpdateEducationActivity.this);
                    queue.add(request);
                }
            }
        });
    }
    public void btnYear(View view)
    {
        final Calendar today = Calendar.getInstance();
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(UpdateEducationActivity.this, new MonthPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int selectedMonth, int selectedYear) {
                tv_year.setText(String.valueOf(selectedYear));
            }

        },today.get(Calendar.YEAR), today.get(Calendar.MONTH));

        builder.setActivatedMonth(Calendar.JULY)
                .setMinYear(1990)
                .setActivatedYear(today.get(Calendar.YEAR))
                .setMaxYear(2099)
                .setTitle("Select Year")
                .showYearOnly()
                .build().show();
    }
    private boolean validate(){
        if (update_school.getText().toString().isEmpty()){
            update_school.setError("School/Institute is required");
            update_school.requestFocus();
            return false;
        }
        if (update_course.getText().toString().isEmpty()) {
            update_course.setError("Course is required");
            update_course.requestFocus();
            return false;
        }
        if (tv_year.getText().toString().equals("Year of Graduation") || tv_year.getText().toString().isEmpty()){
            tv_year.setError("Year of Graduation is required");
            tv_year.requestFocus();
            return false;
        }
        return true;
}
}