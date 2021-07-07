package com.example.onlinejobfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinejobfinder.employer.EmployerActivity;
import com.example.onlinejobfinder.guest.HomeFragment;
import com.example.onlinejobfinder.tabregister.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView txtview_signup,txtview_guest;
    Button btnlogin;
    EditText edt_loginemail,edt_loginpassword;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    PhoneAuthCredential phoneAuthCredential;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    PhoneAuthProvider.ForceResendingToken token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtview_signup = findViewById(R.id.txtview_signup);
        txtview_guest = findViewById(R.id.txtview_guest);
        edt_loginemail = findViewById(R.id.edittext_email);
        edt_loginpassword = findViewById(R.id.edittext_password);
        btnlogin = findViewById(R.id.btnlogin);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user2 = mAuth.getCurrentUser();

        txtview_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        txtview_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, GuestActivity.class);
                startActivity(i);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user2.reload();
                progressDialog.setMessage("Logging In");
                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, Constant.login, response -> {
                    try{
                       user2.reload();
                        if(user2.isEmailVerified())
                        {
                            progressDialog.cancel();
                            JSONObject object= new JSONObject(response);
                            if(object.getBoolean("success")){
                                JSONObject user = object.getJSONObject("user");
                                SharedPreferences userPref = getSharedPreferences("user",MainActivity.this.MODE_PRIVATE);
                                SharedPreferences.Editor editor = userPref.edit();
                                editor.putString("id",user.getString("id"));
                                //editor.putString("specialization",user.getString("specialization"));
                                editor.putString("token",object.getString("token"));
                                editor.putString("email",user.getString("email"));
                                editor.putString("name",user.getString("name"));
                                editor.apply();
                                editor.commit();
                                String role = user.getString("role");
                                String status = user.getString("status");
                                if(role.equals("employer"))
                                {
                                    if(status.equals("pending"))
                                    {
                                        Toast.makeText(MainActivity.this,"Your Account is not approved",Toast.LENGTH_SHORT).show();
                                        progressDialog.cancel();
                                    }
                                    else
                                    {
                                        Intent ia = new Intent(MainActivity.this, EmployerActivity.class);
                                        startActivity(ia);
                                        finish();
                                    }

                                }
                                else
                                {
                                    Intent i = new Intent(MainActivity.this, GuestActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }
                            else{
                                Toast.makeText(MainActivity.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        }
                        else if(!user2.isEmailVerified())
                        {
                            Toast.makeText(MainActivity.this,"Your email is not verified",Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                        progressDialog.cancel();

                    }catch(JSONException e)
                    {
                        Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                },error ->{
                    error.printStackTrace();
                    progressDialog.cancel();
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> map = new HashMap<>();
                        map.put("email",edt_loginemail.getText().toString().trim());
                        map.put("password",edt_loginpassword.getText().toString());
                        return map;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(request);

            }
        });

    }
}