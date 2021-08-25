package com.example.onlinejobfinder.applicant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UpdateWorkExperienceActivity extends AppCompatActivity {

    Switch Switch_work;
    int position =0;
    JSONArray result;
    TextView tvworkspecialization,workstartdate,workenddate;
    boolean[] selectedspecialization;
    ArrayList<Integer> Specialization = new ArrayList<>();
    ArrayList<String> category;
    String [] specializationarray;
    String [] specializationarray2 = {"pp","pp"};
    EditText workposition,workcompanyname;
    //    Spinner workspecialization;
    Button btn_savework,btndelete;
    ProgressDialog progressDialog;
    String name2,user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_work_experience);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        category = new ArrayList<String>();
        Switch_work = findViewById(R.id.updateswitch_currentlyworking);
        String intentid = getIntent().getExtras().getString("work_id");
        String intentworkposition = getIntent().getExtras().getString("intentworkposition");
        String intentworkcompanyname = getIntent().getExtras().getString("intentworkcompanyname");
        String intentspecialization = getIntent().getExtras().getString("intentspecialization");
        String intentworkdate = getIntent().getExtras().getString("intentworkdate");
        String intentenddate = getIntent().getExtras().getString("intentenddate");
        tvworkspecialization = findViewById(R.id.updatetv_workspecialization);
        tvworkspecialization.setText(intentspecialization);
        workposition = findViewById(R.id.updateedittext_addworkposition);
        workposition.setText(intentworkposition);
        workcompanyname = findViewById(R.id.updateedittext_addworkcompanyname);
        workcompanyname.setText(intentworkcompanyname);
        workstartdate = findViewById(R.id.updateedittext_workstartdate);
        workstartdate.setText(intentworkdate);
        workenddate = findViewById(R.id.updateedittext_workenddate);
        workenddate.setText(intentenddate);
        getSupportActionBar().setCustomView(R.layout.customactionbarupdateworkexperience);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        workspecialization = findViewById(R.id.spinner_workspecialization);
//        Specialization = new ArrayList<String>();
//        Specialization.add("Specialization");
//        Specialization.add("Accountant");
//        Specialization.add("Programmer");
//        workspecialization.setAdapter(new ArrayAdapter<String>(AddWorkExperience.this, android.R.layout.simple_spinner_dropdown_item, Specialization));
        btn_savework = findViewById(R.id.updatebtnsaveworkexperience);
        btndelete = findViewById(R.id.deletebtnworkexperience);
        user_id = prefs.getString("id","id");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        //getCategory();
        workposition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                workposition.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                workposition.setError(null);
            }
        });
        workcompanyname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                workcompanyname.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                workcompanyname.setError(null);
            }
        });
        tvworkspecialization.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvworkspecialization.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                tvworkspecialization.setError(null);
            }
        });
        workstartdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                workstartdate.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                workstartdate.setError(null);
            }
        });
        workenddate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                workenddate.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                workenddate.setError(null);
            }
        });
        if(workenddate.getText().toString().equals("Present"))
        {
            Switch_work.setChecked(true);
            workenddate.setVisibility(View.GONE);
        }
        else
        {
            workenddate.setVisibility(View.VISIBLE);
            Switch_work.setChecked(false);
        }
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

        workstartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnStartMonthYear(view);
            }
        });
        workenddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEndMonthYear(view);
            }
        });

        tvworkspecialization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        UpdateWorkExperienceActivity.this
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

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Deleting");
                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, Constant.deleteworkexperience, response -> {
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
                            Toast.makeText(UpdateWorkExperienceActivity.this,"Delete Successfully",Toast.LENGTH_SHORT).show();
                            onBackPressed();


                        }
                        else
                        {
                            Toast.makeText(UpdateWorkExperienceActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }

                    }catch(JSONException e)
                    {
                        Toast.makeText(UpdateWorkExperienceActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                },error ->{
                    error.printStackTrace();
                    Toast.makeText(UpdateWorkExperienceActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
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

                RequestQueue queue = Volley.newRequestQueue(UpdateWorkExperienceActivity.this);
                queue.add(request);
            }
        });

        btn_savework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( validate())
                {
                    progressDialog.setMessage("Saving");
                    progressDialog.show();
                    StringRequest request = new StringRequest(Request.Method.POST, Constant.updateworkexperience, response -> {
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
                                Toast.makeText(UpdateWorkExperienceActivity.this,"Saved Successfully",Toast.LENGTH_SHORT).show();
                                onBackPressed();


                            }
                            else
                            {
                                Toast.makeText(UpdateWorkExperienceActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }

                        }catch(JSONException e)
                        {
                            Toast.makeText(UpdateWorkExperienceActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    },error ->{
                        error.printStackTrace();
                        Toast.makeText(UpdateWorkExperienceActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    })
                    {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> map = new HashMap<>();
                            //map.put("id",user_id);
                            map.put("id",intentid);
                            map.put("workexp_id",user_id);
                            map.put("name",workcompanyname.getText().toString().trim());
                            map.put("position",workposition.getText().toString().trim());
                            map.put("specialization",tvworkspecialization.getText().toString());
                            map.put("startenddate",workstartdate.getText().toString().trim() + " - " + workenddate.getText().toString().trim());
                            return map;
                        }
                    };

                    RequestQueue queue = Volley.newRequestQueue(UpdateWorkExperienceActivity.this);
                    queue.add(request);
                }
            }
        });
    }

    public void btnStartMonthYear(View view)
    {
        final Calendar today = Calendar.getInstance();
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(UpdateWorkExperienceActivity.this, new MonthPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int selectedMonth, int selectedYear) {
                String monthName;
                switch (selectedMonth){
                    case Calendar.JANUARY:
                        monthName = "January";
                        workstartdate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.FEBRUARY:
                        monthName = "February";
                        workstartdate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.MARCH:
                        monthName = "March";
                        workstartdate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.APRIL:
                        monthName = "April";
                        workstartdate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.MAY:
                        monthName = "May";
                        workstartdate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.JUNE:
                        monthName = "June";
                        workstartdate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.JULY:
                        monthName = "July";
                        workstartdate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.AUGUST:
                        monthName = "August";
                        workstartdate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.SEPTEMBER:
                        monthName = "September";
                        workstartdate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.OCTOBER:
                        monthName = "October";
                        workstartdate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.NOVEMBER:
                        monthName = "November";
                        workstartdate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.DECEMBER:
                        monthName = "December";
                        workstartdate.setText(monthName + " " + selectedYear);
                        break;
                }

            }

        },today.get(Calendar.YEAR), today.get(Calendar.MONTH));

        builder.setActivatedMonth(Calendar.JULY)
                .setMinYear(1990)
                .setActivatedYear(today.get(Calendar.YEAR))
                .setMaxYear(2099)
                .setTitle("Select Start Date")
                .build().show();
    }
    public void btnEndMonthYear(View view)
    {
        final Calendar today = Calendar.getInstance();
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(UpdateWorkExperienceActivity.this, new MonthPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int selectedMonth, int selectedYear) {
                String monthName;
                switch (selectedMonth){
                    case Calendar.JANUARY:
                        monthName = "January";
                        workenddate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.FEBRUARY:
                        monthName = "February";
                        workenddate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.MARCH:
                        monthName = "March";
                        workenddate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.APRIL:
                        monthName = "April";
                        workenddate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.MAY:
                        monthName = "May";
                        workenddate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.JUNE:
                        monthName = "June";
                        workenddate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.JULY:
                        monthName = "July";
                        workenddate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.AUGUST:
                        monthName = "August";
                        workenddate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.SEPTEMBER:
                        monthName = "September";
                        workenddate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.OCTOBER:
                        monthName = "October";
                        workenddate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.NOVEMBER:
                        monthName = "November";
                        workenddate.setText(monthName + " " + selectedYear);
                        break;
                    case Calendar.DECEMBER:
                        monthName = "December";
                        workenddate.setText(monthName + " " + selectedYear);
                        break;
                }
            }

        },today.get(Calendar.YEAR), today.get(Calendar.MONTH));

        builder.setActivatedMonth(Calendar.JULY)
                .setMinYear(1990)
                .setActivatedYear(today.get(Calendar.YEAR))
                .setMaxYear(2099)
                .setTitle("Select Start Date")
                .build().show();
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
                Toast.makeText(UpdateWorkExperienceActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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

        RequestQueue queue = Volley.newRequestQueue(UpdateWorkExperienceActivity.this);
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
    private boolean validate(){
        if (workposition.getText().toString().isEmpty()){
            workposition.setError("Position is required");
            workposition.requestFocus();
            return false;
        }
        if (workcompanyname.getText().toString().isEmpty()) {
            workcompanyname.setError("Company Name is required");
            workcompanyname.requestFocus();
            return false;
        }
        if (tvworkspecialization.getText().toString().equals("Specialization") || tvworkspecialization.getText().toString().isEmpty()){
            tvworkspecialization.setError("Specialization is required");
            tvworkspecialization.requestFocus();
            return false;
        }
        if (workstartdate.getText().toString().equals("Start Date") || workstartdate.getText().toString().isEmpty()){
            workstartdate.setError("Start Date is required");
            workstartdate.requestFocus();
            return false;
        }
        if (workenddate.getText().toString().equals("End Date") || workenddate.getText().toString().isEmpty()){
            workenddate.setError("End Date is required");
            workenddate.requestFocus();
            return false;
        }



        return true;
    }
}