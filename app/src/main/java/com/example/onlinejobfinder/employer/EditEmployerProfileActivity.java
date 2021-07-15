package com.example.onlinejobfinder.employer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.onlinejobfinder.applicant.EditProfileActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditEmployerProfileActivity extends AppCompatActivity {


    String name2,user_id;
    TextView employer_email, employer_contactnum,employer_selectphoto;
    EditText employer_companyname, employer_address, employer_companyoverview;
    Spinner employer_specialization;
    CircleImageView employer_profilepic;
    Button btn_saveemployer;
    Bitmap bitmap = null;
    ProgressDialog progressDialog;
    private static final int GALLERY_ADD_PROFILE = 1;
    ArrayList<String> Specialization;

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
        employer_specialization = findViewById(R.id.spinner_editemployerspecialization);
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
        Specialization = new ArrayList<String>();
        Specialization.add("Specialization");
        Specialization.add("Accountant");
        Specialization.add("Programmer");
        employer_specialization.setAdapter(new ArrayAdapter<String>(EditEmployerProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, Specialization));
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        employer_contactnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditEmployerProfileActivity.this, EditEmployerContactNumberActivity.class);
                i.putExtra("name", employer_companyname.getText().toString());
                i.putExtra("address", employer_address.getText().toString());
                i.putExtra("email",employer_email.getText().toString());
                i.putExtra("companyoverview",employer_companyoverview.getText().toString());
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
                        Toast.makeText(EditEmployerProfileActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                       // Toast.makeText(EditEmployerProfileActivity.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                },error ->{
                    error.printStackTrace();
                    Toast.makeText(EditEmployerProfileActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
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
                        map.put("Specialization",employer_specialization.getSelectedItem().toString().trim());
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
            Toast.makeText(EditEmployerProfileActivity.this,"error",Toast.LENGTH_SHORT).show();
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
}