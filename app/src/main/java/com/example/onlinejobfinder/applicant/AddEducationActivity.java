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

public class AddEducationActivity extends AppCompatActivity {

    EditText edit_school, edit_course, edit_year;
    Button btnaddeduc;
    ProgressDialog progressDialog;
    String name2,user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_education);
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        edit_school = findViewById(R.id.edittext_addschool);
        edit_course = findViewById(R.id.edittext_addcourse);
        edit_year = findViewById(R.id.edittext_addyeargraduation);
        btnaddeduc = findViewById(R.id.btnsaveeducation);
        user_id = prefs.getString("id","id");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        btnaddeduc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Saving");
                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, Constant.addeducation, response -> {
                       try{
                        JSONObject object= new JSONObject(response);
                        if(object.getBoolean("success")){
                            JSONObject user = object.getJSONObject("update");
                            SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = userPref.edit();
                            editor.putString("id",user.getString("educational_id"));
                            progressDialog.cancel();

                            editor.apply();
                            editor.commit();
                            Toast.makeText(AddEducationActivity.this,"Saved Successfully",Toast.LENGTH_SHORT).show();
                            onBackPressed();


                        }
                        else
                        {
                            Toast.makeText(AddEducationActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }

                    }catch(JSONException e)
                    {
                        Toast.makeText(AddEducationActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                },error ->{
                    error.printStackTrace();
                    Toast.makeText(AddEducationActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                })
                {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> map = new HashMap<>();
                        //map.put("id",user_id);
                        map.put("educational_id",user_id);
                        map.put("school",edit_school.getText().toString().trim());
                        map.put("course",edit_course.getText().toString().trim());
                        map.put("year",edit_year.getText().toString().trim());
                        return map;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(AddEducationActivity.this);
                queue.add(request);
            }
        });
    }

    public void onBackPressed()
    {
        super.onBackPressed();
    }
}