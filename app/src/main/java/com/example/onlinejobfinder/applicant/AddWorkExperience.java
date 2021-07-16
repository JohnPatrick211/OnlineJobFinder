package com.example.onlinejobfinder.applicant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddWorkExperience extends AppCompatActivity {

    Switch Switch_work;
    int position =0;
    JSONArray result;
    TextView tvworkspecialization;
    boolean[] selectedspecialization;
    ArrayList<Integer> Specialization = new ArrayList<>();
    ArrayList<String> category;
    String [] specializationarray;
    String [] specializationarray2 = {"pp","pp"};
    EditText workposition,workcompanyname,workstartdate,workenddate;
//    Spinner workspecialization;
    Button btn_savework;
    ProgressDialog progressDialog;
    String name2,user_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_experience);
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        category = new ArrayList<String>();
        Switch_work = findViewById(R.id.switch_currentlyworking);
        tvworkspecialization = findViewById(R.id.tv_workspecialization);
        workposition = findViewById(R.id.edittext_addworkposition);
        workcompanyname = findViewById(R.id.edittext_addworkcompanyname);
        workstartdate = findViewById(R.id.edittext_workstartdate);
        workenddate = findViewById(R.id.edittext_workenddate);
//        workspecialization = findViewById(R.id.spinner_workspecialization);
//        Specialization = new ArrayList<String>();
//        Specialization.add("Specialization");
//        Specialization.add("Accountant");
//        Specialization.add("Programmer");
//        workspecialization.setAdapter(new ArrayAdapter<String>(AddWorkExperience.this, android.R.layout.simple_spinner_dropdown_item, Specialization));
        btn_savework = findViewById(R.id.btnsaveworkexperience);
        user_id = prefs.getString("id","id");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        //getCategory();
        tvworkspecialization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        AddWorkExperience.this
                );
                tvworkspecialization.setText(specializationarray[position]);
                builder.setTitle("Select Specialization");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(specializationarray, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        position = i;
                        tvworkspecialization.setText(specializationarray[i]);
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
                        tvworkspecialization.setText("");
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j=0; j<selectedspecialization.length; j++)
                        {
                            selectedspecialization[j] = false;
                           // Specialization.clear();
                            tvworkspecialization.setText("");
                        }
                    }
                });
                builder.show();
            }
        });
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
                        map.put("specialization",tvworkspecialization.getText().toString());
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
                Toast.makeText(AddWorkExperience.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            //refreshLayout.setRefreshing(false);

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

        RequestQueue queue = Volley.newRequestQueue(AddWorkExperience.this);
        queue.add(request);
    }

    private void getSubCategory(JSONArray j) {
        for(int ai=0;ai<j.length();ai++)
        {
            try{
                JSONObject json = j.getJSONObject(ai);
               category.add(json.getString("category"));
               //Toast.makeText(AddWorkExperience.this,category.get(ai),Toast.LENGTH_SHORT).show();
            }catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        //for(int ai=0;ai<j.length();ai++) {
            specializationarray = category.toArray(new String[0]);
            //Toast.makeText(AddWorkExperience.this, specializationarray[ai], Toast.LENGTH_SHORT).show();
            selectedspecialization = new boolean[specializationarray.length];
        //}
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCategory();
    }
}