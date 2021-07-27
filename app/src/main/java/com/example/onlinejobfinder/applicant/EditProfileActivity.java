package com.example.onlinejobfinder.applicant;

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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.employer.EditEmployerProfileActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    String name2,user_id;
    JSONArray result;
    int position =0;
    int position2 =0;
    boolean[] selectedspecialization;
    ArrayList<Integer> Specialization = new ArrayList<>();
    ArrayList<String> category;
    String [] specializationarray;
    String [] specializationarray2 = {"Male","Female"};
   // Spinner specialization, gender;
    EditText edit_contactno, edit_address, edit_name;
   // ArrayList<String> Specialization, Gender;
    TextView txtid,txtselectphoto,txt_email,txtspecialization,txtgender;
    Button btn_saveimage;
    CircleImageView circleImageView;
    Bitmap bitmap = null;
    ProgressDialog progressDialog;
    //SharedPreferences userPref;
    //String role;
    private static final int GALLERY_ADD_PROFILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String intentname = getIntent().getExtras().getString("name");
        String intentemail = getIntent().getExtras().getString("email");
        String intentcontactno = getIntent().getExtras().getString("contactno");
        String intentaddress = getIntent().getExtras().getString("address");
        String intentspecialization = getIntent().getExtras().getString("specialization");
        String intentgender = getIntent().getExtras().getString("gender");
        category = new ArrayList<String>();
        edit_address = findViewById(R.id.applicantprofile_address);
        edit_address.setText(intentaddress);
        edit_contactno = findViewById(R.id.applicantprofile_contactno);
        edit_contactno.setText(intentcontactno);
        edit_name = findViewById(R.id.applicantprofile_name);
        edit_name.setText(intentname);
        txt_email = findViewById(R.id.applicantprofile_email);
        txt_email.setText(intentemail);
        txtspecialization = findViewById(R.id.applicantprofile_specialization);
        txtspecialization.setText(intentspecialization);
        txtgender = findViewById(R.id.applicantprofile_gender);
        txtgender.setText(intentgender);
//        specialization = findViewById(R.id.spinner_specialization);
//        gender = findViewById(R.id.spinner_gender);
//        Specialization = new ArrayList<String>();
//        Specialization.add("Specialization");
//        Specialization.add("Accountant");
//        Specialization.add("Programmer");
//        Gender = new ArrayList<String>();
//        Gender.add("Gender");
//        Gender.add("Male");
//        Gender.add("Female");
//       specialization.setAdapter(new ArrayAdapter<String>(EditProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, Specialization));
//       gender.setAdapter(new ArrayAdapter<String>(EditProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, Gender));
        //name2 = prefs.getString("name","name");
        user_id = prefs.getString("id","id");
       // role = prefs.getString("role","role");
       // editor.putString("name",name2);
       // editor.putString("id",user_id);
        // txtid = findViewById(R.id.txtuserid);
        btn_saveimage = findViewById(R.id.btnsaveimage);
        circleImageView = findViewById(R.id.imguser);
        txtselectphoto = findViewById(R.id.txt_selectphoto);
//        txtid.setText(user_id);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        //userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        //check ID debugging//
//        Toast.makeText(EditProfileActivity.this,user_id,Toast.LENGTH_SHORT).show();
        txtspecialization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        EditProfileActivity.this
                );

                builder.setTitle("Select Specialization");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(specializationarray, position2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        position2 = i;
                        txtspecialization.setText(specializationarray[i]);
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
//                        txtspecialization.setText(stringBuilder.toString());
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        txtspecialization.setText("");
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        for(int j=0; j<selectedspecialization.length; j++)
//                        {
//                            selectedspecialization[j] = false;
//                            //Specialization.clear();
                            txtspecialization.setText("");
  //                      }
                    }
                });
                builder.show();
            }
        });
        txtgender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        EditProfileActivity.this
                );
                txtgender.setText(specializationarray2[position]);
                builder.setTitle("Select Specialization");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(specializationarray2, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        position = i;
                        txtgender.setText(specializationarray2[i]);
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
                        txtgender.setText("");
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        for(int j=0; j<selectedspecialization.length; j++)
//                        {
//                            selectedspecialization[j] = false;
                            // Specialization.clear();
                            txtgender.setText("");
//                        }
                    }
                });
                builder.show();
            }
        });
        edit_contactno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditProfileActivity.this, EditContactNumberActivity.class);
                i.putExtra("name", edit_name.getText().toString());
                i.putExtra("address", edit_address.getText().toString());
                i.putExtra("email",txt_email.getText().toString());
                startActivity(i);
                finish();
            }
        });
        txtselectphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,GALLERY_ADD_PROFILE);
            }
        });
        btn_saveimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Saving");
                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, Constant.updateprofile, response -> {
//                StringRequest request = new StringRequest(Request.Method.POST, Constant.SAVE_USER_PROFILE, response -> {
                    try{
                        JSONObject object= new JSONObject(response);
                        if(object.getBoolean("success")){
                            JSONObject user = object.getJSONObject("update");
                            SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = userPref.edit();
                            editor.putString("id",user.getString("applicant_id"));
                            //editor.putString("file_path",user.getString("file_path"));
                            //                           Intent i = new Intent(UploadProfileRegister.this, MainActivity.class);
                            //                           startActivity(i);
                            progressDialog.cancel();

                            editor.apply();
                            editor.commit();
                            Toast.makeText(EditProfileActivity.this,"Saved Successfully",Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(EditProfileActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }

                    }catch(JSONException e)
                    {
                        Toast.makeText(EditProfileActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                },error ->{
                    error.printStackTrace();
                    Toast.makeText(EditProfileActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
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
                        map.put("applicant_id",user_id);
                        map.put("name",edit_name.getText().toString().trim());
                        map.put("email",txt_email.getText().toString().trim());
                        map.put("contactno",edit_contactno.getText().toString().trim());
                        map.put("address",edit_address.getText().toString().trim());
                        map.put("gender",txtgender.getText().toString().trim());
                        map.put("Specialization",txtspecialization.getText().toString().trim());
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

                RequestQueue queue = Volley.newRequestQueue(EditProfileActivity.this);
                queue.add(request);
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
            Toast.makeText(EditProfileActivity.this,"error",Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_ADD_PROFILE && resultCode==RESULT_OK)
        {
            Uri imgUri = data.getData();
            circleImageView.setImageURI(imgUri);
            //Toast.makeText(EditProfileActivity.this,imgUri.toString(),Toast.LENGTH_SHORT).show();
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
                Toast.makeText(EditProfileActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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

        RequestQueue queue = Volley.newRequestQueue(EditProfileActivity.this);
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