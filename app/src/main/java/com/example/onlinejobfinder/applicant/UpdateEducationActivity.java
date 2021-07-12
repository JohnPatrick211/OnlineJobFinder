package com.example.onlinejobfinder.applicant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateEducationActivity extends AppCompatActivity {
    EditText update_school, update_course, update_year;
    Button btnupdateeduc,btndeleteeduc;
    ProgressDialog progressDialog;
    String intentid,intentschool,intentcourse,intentyear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_education);
        update_school = findViewById(R.id.edittext_updateschool);
        update_course = findViewById(R.id.edittext_updatecourse);
        update_year = findViewById(R.id.edittext_updateyeargraduation);
        btnupdateeduc = findViewById(R.id.btnupdateeducation);
        intentid = getIntent().getExtras().getString("educ_id");
        intentschool = getIntent().getExtras().getString("intentschool");
        intentcourse = getIntent().getExtras().getString("intentcourse");
        intentyear = getIntent().getExtras().getString("intentyear");
        update_school.setText(intentschool);
        update_course.setText(intentcourse);
        update_year.setText(intentyear);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        //Toast.makeText(UpdateEducationActivity.this, intentid, Toast.LENGTH_SHORT).show();

        btnupdateeduc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        Toast.makeText(UpdateEducationActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                },error ->{
                    error.printStackTrace();
                    Toast.makeText(UpdateEducationActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
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
                        map.put("year",update_year.getText().toString().trim());
                        return map;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(UpdateEducationActivity.this);
                queue.add(request);
            }
        });
    }
}