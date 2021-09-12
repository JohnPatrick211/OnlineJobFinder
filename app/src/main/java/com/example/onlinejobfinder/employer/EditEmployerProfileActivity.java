package com.example.onlinejobfinder.employer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.EditContactNumberActivity;
import com.example.onlinejobfinder.EditEmployerContactNumberActivity;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.applicant.AddWorkExperience;
import com.example.onlinejobfinder.applicant.EditProfileActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditEmployerProfileActivity extends AppCompatActivity {


    String name2,user_id;
    JSONArray result;
    SharedPreferences userPref2;
    String val_contactno = "";
    String val_specialization = "";
    TextView employer_email, employer_contactnum,employer_selectphoto, employer_specialization;
    EditText employer_companyname, employer_address, employer_companyoverview;
    //Spinner employer_specialization;
    ImageView employer_profilepic;
    Button btn_saveemployer;
    Bitmap bitmap = null;
    ProgressDialog progressDialog;
    private static final int GALLERY_ADD_PROFILE = 1;
    boolean[] selectedspecialization;
    ArrayList<Integer> Specialization = new ArrayList<>();
    ArrayList<String> category;
    String [] specializationarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employer);
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        user_id = prefs.getString("id","id");
        employer_selectphoto = findViewById(R.id.txt_editemployerselectphoto);
        employer_email = findViewById(R.id.editemployerprofile_email);
        employer_contactnum = findViewById(R.id.editemployerprofile_contactnum);
        employer_companyname = findViewById(R.id.editemployerprofile_name);
        employer_address = findViewById(R.id.editemployerprofile_address);
        employer_companyoverview = findViewById(R.id.editemployerprofile_companyoverview);
        employer_profilepic = findViewById(R.id.editemployer_imguser);
        //employer_specialization = findViewById(R.id.spinner_editemployerspecialization);
        employer_specialization = findViewById(R.id.editemployerprofile_specialization);
        String intentspecialization = getIntent().getExtras().getString("specialization");
        employer_specialization.setText(intentspecialization);
        btn_saveemployer = findViewById(R.id.btnemployersaveimage);
        String intentemployername = getIntent().getExtras().getString("name");
        employer_companyname.setText(intentemployername);
        String intentemployeremail = getIntent().getExtras().getString("email");
        employer_email.setText(intentemployeremail);
        String intentemployercontactno = getIntent().getExtras().getString("contactno");
        employer_contactnum.setText(intentemployercontactno);
        String intentemployeraddress = getIntent().getExtras().getString("address");
        employer_address.setText(intentemployeraddress);
        String intentemployercompanyoverview = getIntent().getExtras().getString("companyoverview");
        employer_companyoverview.setText(intentemployercompanyoverview);
        userPref2 = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        category = new ArrayList<String>();
        getSupportActionBar().setCustomView(R.layout.customactionbareditprofile);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
       // Specialization = new ArrayList<String>();
       // Specialization.add("Specialization");
       // Specialization.add("Accountant");
       // Specialization.add("Programmer");
        //employer_specialization.setAdapter(new ArrayAdapter<String>(EditEmployerProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, Specialization));
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        employer_companyname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                employer_companyname.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                employer_companyname.setError(null);
            }
        });
        employer_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                employer_address.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                employer_address.setError(null);
            }
        });
        employer_contactnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                employer_contactnum.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                employer_contactnum.setError(null);
            }
        });
        employer_companyoverview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                employer_companyoverview.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                employer_companyoverview.setError(null);
            }
        });
        employer_specialization.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                employer_specialization.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                employer_specialization.setError(null);
            }
        });
        try {
            String checknull = getIntent().getExtras().getString("profile_pic");
            if (checknull.equals("null")) {
                employer_profilepic.setImageResource(R.drawable.img);
                bitmap = ((BitmapDrawable) employer_profilepic.getDrawable()).getBitmap();
            } else {
                Picasso.get().load(getIntent().getStringExtra("profile_pic")).into( employer_profilepic);
                bitmap = ((BitmapDrawable) employer_profilepic.getDrawable()).getBitmap();
//                if(bitmap == null)
//                {
//                    Toast.makeText(EditEmployerProfileActivity.this,"failed",Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(EditEmployerProfileActivity.this,bitmap.toString(),Toast.LENGTH_SHORT).show();
//                }

            }
        } catch (Exception e) {
            employer_profilepic.setImageResource(R.drawable.img);
            bitmap = ((BitmapDrawable) employer_profilepic.getDrawable()).getBitmap();
        }
        getCategory();

        employer_specialization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        EditEmployerProfileActivity.this
                );

                builder.setTitle("Select Specialization");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(specializationarray, selectedspecialization, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if(b)
                        {
                            Specialization.add(i);
                        }
                        else
                        {
                            for(int aq=0; aq<= Specialization.size(); aq++)
                            {
                                if(Specialization.get(aq) == i)
                                {
                                    Specialization.remove(aq);
                                    break;
                                }
                            }
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for(int j=0; j<Specialization.size(); j++)
                        {
                            stringBuilder.append(specializationarray[Specialization.get(j)]);
                            if(j != Specialization.size()-1)
                            {
                                stringBuilder.append(", ");
                            }
                        }
                        if(Specialization.isEmpty())
                        {
                            employer_specialization.setText("Specialization");
                        }
                        else {
                            employer_specialization.setText(stringBuilder.toString());
                        }

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j=0; j<selectedspecialization.length; j++)
                        {
                            selectedspecialization[j] = false;
                            //Specialization.clear();
                            employer_specialization.setText("Specialization");
                        }
                    }
                });
                builder.show();
            }
        });

        employer_contactnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditEmployerProfileActivity.this, EditEmployerContactNumberActivity.class);
                i.putExtra("name", employer_companyname.getText().toString());
                i.putExtra("address", employer_address.getText().toString());
                i.putExtra("email",employer_email.getText().toString());
                i.putExtra("profile_pic", getIntent().getStringExtra("profile_pic"));
                i.putExtra("companyoverview",employer_companyoverview.getText().toString());
                i.putExtra("specialization", employer_specialization.getText().toString().trim());
                startActivity(i);
                finish();
            }
        });

        employer_selectphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,GALLERY_ADD_PROFILE);
            }
        });

        btn_saveemployer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                    progressDialog.setMessage("Saving");
                    progressDialog.show();
                    StringRequest request = new StringRequest(Request.Method.POST, Constant.updateemployer, response -> {
//                StringRequest request = new StringRequest(Request.Method.POST, Constant.SAVE_USER_PROFILE, response -> {
                        try{
                            JSONObject object= new JSONObject(response);

                            if(object.getBoolean("success")){
                                JSONObject user = object.getJSONObject("update");
                                SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = userPref.edit();
                                editor.putString("id",user.getString("employer_id"));
                                SharedPreferences.Editor editor2 = userPref2.edit();
                                editor2.putString("name",user.getString("name"));
                                editor2.putString("address",user.getString("address"));
                                editor2.putString("contactno",user.getString("contactno"));
                                editor2.putString("Specialization",user.getString("Specialization"));
                                //editor.putString("file_path",user.getString("file_path"));
                                //                           Intent i = new Intent(UploadProfileRegister.this, MainActivity.class);
                                //                           startActivity(i);
                                progressDialog.cancel();

                                editor.apply();
                                editor.commit();
                                Toast.makeText(EditEmployerProfileActivity.this,"Saved Successfully",Toast.LENGTH_SHORT).show();
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


                            }
                            else
                            {
                                Toast.makeText(EditEmployerProfileActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(EditEmployerProfileActivity.this, response, Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }

                        }catch(JSONException e)
                        {
                            Toast.makeText(EditEmployerProfileActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                            // Toast.makeText(EditEmployerProfileActivity.this, response, Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    },error ->{
                        error.printStackTrace();
                        Toast.makeText(EditEmployerProfileActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    })
                    {
                        //  public Map<String, String> getHeaders() throws AuthFailureError {
                        //     HashMap<String,String> map = new HashMap<>();
                        //    String token = userPref.getString("token","");
                        //    map.put("Authorization","Bearer"+token);
                        //     return map;
                        //}

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> map = new HashMap<>();
                            //map.put("id",user_id);
                            map.put("employer_id",user_id);
                            map.put("name",employer_companyname.getText().toString().trim());
                            map.put("email",employer_email.getText().toString().trim());
                            map.put("contactno",employer_contactnum.getText().toString().trim());
                            map.put("address",employer_address.getText().toString().trim());
                            map.put("companyoverview",employer_companyoverview.getText().toString().trim());
                            map.put("Specialization",employer_specialization.getText().toString().trim());
                            map.put("profile_pic",bitmapToString(bitmap));
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

                    RequestQueue queue = Volley.newRequestQueue(EditEmployerProfileActivity.this);
                    queue.add(request);
                }
            }
        });
    }

    private String bitmapToString(Bitmap bitmap) {
        if(bitmap!=null)
        {
            ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte[] array = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(array, Base64.DEFAULT);


        }
        else
        {
            Toast.makeText(EditEmployerProfileActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_ADD_PROFILE && resultCode==RESULT_OK)
        {

            Uri imgUri = data.getData();
            employer_profilepic.setImageURI(imgUri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {

            //Toast.makeText(EditProfileActivity.this,"error2",Toast.LENGTH_SHORT).show();
        }
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
                Toast.makeText(EditEmployerProfileActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
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

        RequestQueue queue = Volley.newRequestQueue(EditEmployerProfileActivity.this);
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
    }
    private boolean validate(){
        if (employer_companyname.getText().toString().isEmpty()){
            employer_companyname.setError("Company Name is required");
            employer_companyname.requestFocus();
            return false;
        }
        if (employer_address.getText().toString().isEmpty()) {
            employer_address.setError("Address is required");
            employer_address.requestFocus();
            return false;
        }
        if (employer_contactnum.getText().toString().equals("Contact Number")){
            employer_contactnum.setError("Contact Number is required");
            employer_contactnum.requestFocus();
            return false;
        }
        if (employer_companyoverview.getText().toString().isEmpty()){
            employer_companyoverview.setError("Company Overview is required");
            employer_companyoverview.requestFocus();
            return false;
        }
        if (employer_specialization.getText().toString().equals("Specialization")) {
            employer_specialization.setError("Specialization is required");
            employer_specialization.requestFocus();
            return false;
        }

        return true;
    }
}