package com.example.onlinejobfinder.guest;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.GuestActivity;
import com.example.onlinejobfinder.MainActivity;
import com.example.onlinejobfinder.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    String name2,user_id;
    TextView txtid,txtselectphoto;
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
                progressDialog.setMessage("Updating");
                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, Constant.SAVE_USER_PROFILE, response -> {
                    try{
                        JSONObject object= new JSONObject(response);
                        if(object.getBoolean("success")){
                            JSONObject user = object.getJSONObject("user");
//                            SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                            //SharedPreferences.Editor editor = userPref.edit();
                           // editor.putString("id",user.getString("id"));
                           // editor.putString("file_path",user.getString("file_path"));
                            //                           Intent i = new Intent(UploadProfileRegister.this, MainActivity.class);
                            //                           startActivity(i);
                            progressDialog.cancel();

                           // editor.apply();
                           // editor.commit();
                            //Toast.makeText(UploadProfileRegister.this,"Upload Successfully",Toast.LENGTH_SHORT).show();
                           // if(role.equals("employer"))
                           // {
                                Intent ia = new Intent(EditProfileActivity.this, GuestActivity.class);
                                startActivity(ia);
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
                        Toast.makeText(EditProfileActivity.this,"Network Busy, Please Try Again",Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                },error ->{
                    error.printStackTrace();
                    Toast.makeText(EditProfileActivity.this,"Network Busy, Please Try Again",Toast.LENGTH_SHORT).show();
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
                        map.put("id",user_id);
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
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imgUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(EditProfileActivity.this,"error2",Toast.LENGTH_SHORT).show();
        }
    }
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}