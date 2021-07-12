package com.example.onlinejobfinder.tabregister;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.MainActivity;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.SendMail;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EmployerRegistrationActivity extends AppCompatActivity {
    EditText txtemployercompanyname, txtemployerpassword, txtemployerconfirmpassword, txtemployeraddress, txtemployercompanyoverview;
    TextView txtemployeremail,txtselectBIR;
    ImageView imagemployerBIR;
    Button btnsendapproval;
    Bitmap bitmap = null;
    ProgressDialog progressDialog;
    private static final int GALLERY_ADD_PROFILE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_registration);

        txtemployercompanyname = findViewById(R.id.edittext_employer_name);
        txtemployerpassword = findViewById(R.id.edittext_employer_password);
        txtemployeremail = findViewById(R.id.txt_employer_email);
        txtemployeraddress = findViewById(R.id.txt_employer_address);
        txtemployercompanyoverview = findViewById(R.id.edittext_employer_companyoverview);
        imagemployerBIR = findViewById(R.id.img_register_BIR);
        imagemployerBIR.setVisibility(View.GONE);
        txtselectBIR = findViewById(R.id.txt_selectBIR);
        btnsendapproval = findViewById(R.id.btnsendtoapproval);
        String intentemail = getIntent().getExtras().getString("employeremail");
        String role_employer = "employer";
        String status_employer = "pending";
        txtemployeremail.setText(intentemail);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        txtselectBIR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,GALLERY_ADD_PROFILE);
            }
        });
        btnsendapproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Sending");
                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, Constant.registeremployer, response -> {
                    try{
                        JSONObject object= new JSONObject(response);
                        if(object.getBoolean("success")){
               //             JSONObject user = object.getJSONObject("employer");
//                            SharedPreferences userPref = getApplicationContext().getSharedPreferences("user",getApplicationContext().MODE_PRIVATE);
//                            SharedPreferences.Editor editor = userPref.edit();
//                            editor.putString("token",object.getString("token"));
//                            editor.putString("email",user.getString("email"));
//                            editor.putString("name",user.getString("name"));
//                            editor.putString("role",user.getString("role"));
//                            editor.apply();

                                Toast.makeText(getApplicationContext(),"Register Successfully, Please Wait for the email confirmation",Toast.LENGTH_SHORT).show();
                            SendMail sm = new SendMail(EmployerRegistrationActivity.this, "pesojob@gmail.com", "New Employer Approval", "Please Check the Pending Employer");
                            sm.execute();

                                Intent i = new Intent(EmployerRegistrationActivity.this, MainActivity.class);
                               startActivity(i);
                                progressDialog.cancel();


                        }
                        else if(object.getString("Status").equals("201"))
                        {
                            Toast.makeText(getApplicationContext(),"Email Already Exists",Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
//                        else if(object.getString("Status").equals("202"))
//                        {
//                            Toast.makeText(getApplicationContext(),"Name Already Exists, Please Try Again",Toast.LENGTH_SHORT).show();
//                            progressDialog.cancel();
//                        }
                        else
                        {
                            if(!object.getBoolean("success")){
                                JSONObject user = object.getJSONObject("employer");
                                String status = user.getString("status");
                                if(status.equals("pending"))
                                {
                                    Toast.makeText(getApplicationContext(),"Approval Already sent, Please Wait for the email confirmation",Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                }
                                if(status.equals("Approved"))
                                {
                                    Toast.makeText(getApplicationContext(),"This account is already approved",Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Email Already Exists",Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }

                        }

                    }catch(JSONException e)
                    {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                },error ->{
                    error.printStackTrace();
                    Toast.makeText(getApplicationContext(),"network error, please try again",Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> map = new HashMap<>();
                        map.put("name",txtemployercompanyname.getText().toString().trim());
                        map.put("email",txtemployeremail.getText().toString().trim());
                        map.put("password",txtemployerpassword.getText().toString());
                        map.put("BIR_file",bitmapToString(bitmap));
                        map.put("address",txtemployeraddress.getText().toString().trim());
                        map.put("companyoverview",txtemployercompanyoverview.getText().toString());
                        map.put("status",status_employer);
                        map.put("role",role_employer);
                        return map;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
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
            Toast.makeText(EmployerRegistrationActivity.this,"error uploading, please try again",Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_ADD_PROFILE && resultCode==RESULT_OK)
        {
            Uri imgUri = data.getData();
            imagemployerBIR.setImageURI(imgUri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
                imagemployerBIR.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            //Toast.makeText(EmployerRegistrationActivity.this,"error2",Toast.LENGTH_SHORT).show();
        }
    }
}