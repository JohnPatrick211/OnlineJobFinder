package com.example.onlinejobfinder.applicant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddWorkExperience extends AppCompatActivity {

    Switch Switch_work;
    ArrayList<String> Specialization, Gender;
    EditText workposition,workcompanyname,workstartdate,workenddate;
    Spinner workspecialization;
    Button btn_savework;
    ProgressDialog progressDialog;
    String name2,user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_experience);
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Switch_work = findViewById(R.id.switch_currentlyworking);
        workposition = findViewById(R.id.edittext_addworkposition);
        workcompanyname = findViewById(R.id.edittext_addworkcompanyname);
        workstartdate = findViewById(R.id.edittext_workstartdate);
        workenddate = findViewById(R.id.edittext_workenddate);
        workspecialization = findViewById(R.id.spinner_workspecialization);
        Specialization = new ArrayList<String>();
        Specialization.add("Specialization");
        Specialization.add("Accountant");
        Specialization.add("Programmer");
        workspecialization.setAdapter(new ArrayAdapter<String>(AddWorkExperience.this, android.R.layout.simple_spinner_dropdown_item, Specialization));
        btn_savework = findViewById(R.id.btnsaveworkexperience);
        user_id = prefs.getString("id","id");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        Switch_work.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    workenddate.setVisibility(View.GONE);
                    workenddate.setText("Present");
                }
                else
                {
                    workenddate.setVisibility(View.VISIBLE);
                    workenddate.setText("");
                }
            }
        });

        btn_savework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Saving");
                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, Constant.addworkexperience, response -> {
                    try{
                        JSONObject object= new JSONObject(response);
                        if(object.getBoolean("success")){
                            JSONObject user = object.getJSONObject("update");
                            SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = userPref.edit();
                            editor.putString("id",user.getString("workexp_id"));
                            progressDialog.cancel();

                            editor.apply();
                            editor.commit();
                            Toast.makeText(AddWorkExperience.this,"Saved Successfully",Toast.LENGTH_SHORT).show();
                            onBackPressed();


                        }
                        else
                        {
                            Toast.makeText(AddWorkExperience.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }

                    }catch(JSONException e)
                    {
                        Toast.makeText(AddWorkExperience.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                },error ->{
                    error.printStackTrace();
                    Toast.makeText(AddWorkExperience.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                })
                {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> map = new HashMap<>();
                        //map.put("id",user_id);
                        map.put("workexp_id",user_id);
                        map.put("name",workcompanyname.getText().toString().trim());
                        map.put("position",workposition.getText().toString().trim());
                        map.put("specialization",workspecialization.getSelectedItem().toString().trim());
                        map.put("startenddate",workstartdate.getText().toString().trim() + " - " + workenddate.getText().toString().trim());
                        return map;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(AddWorkExperience.this);
                queue.add(request);
            }
        });
    }
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}