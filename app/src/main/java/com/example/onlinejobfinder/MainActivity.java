package com.example.onlinejobfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.onlinejobfinder.guest.GuestActivity;
import com.example.onlinejobfinder.tabregister.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "101";
    TextView txtview_signup,txtview_guest;
    Button btnlogin;
    TextInputEditText edt_loginemail,edt_loginpassword;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    SessionManager sessionManager;
    private TextInputLayout layoutEmail,layoutPassword;
    PhoneAuthCredential phoneAuthCredential;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    PhoneAuthProvider.ForceResendingToken token;
    private static final String TAG ="JobApprovedOrRejectNotification";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtview_signup = findViewById(R.id.txtview_signup);
        txtview_guest = findViewById(R.id.txtview_guest);
        layoutPassword = findViewById(R.id.txtLayoutPasswordSignIn);
        layoutEmail = findViewById(R.id.txtLayoutEmailSignIn);
        edt_loginemail = findViewById(R.id.edittext_email);
        edt_loginpassword = findViewById(R.id.edittext_password);
        btnlogin = findViewById(R.id.btnlogin);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user2 = mAuth.getCurrentUser();
        getSupportActionBar().hide();
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.customactionbar);
//        getSupportActionBar().setTitle("Login");
        sessionManager = new SessionManager(getApplicationContext());
        createNotificationChannel();
        getTokenId();
        FirebaseMessaging.getInstance().unsubscribeFromTopic("Employer");
        FirebaseMessaging.getInstance().unsubscribeFromTopic("Applicant");


        edt_loginemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edt_loginemail.getText().toString().isEmpty()){
                    layoutEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_loginpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edt_loginpassword.getText().toString().length()>7){
                    layoutPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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
                if(validate())
                {
                    progressDialog.setMessage("Logging In");
                    progressDialog.show();
                    StringRequest request = new StringRequest(Request.Method.POST, Constant.login, response -> {
                        try{
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
                                            sessionManager.setEmployerLogin(true);
                                            FirebaseMessaging.getInstance().subscribeToTopic("Employer");
                                            FirebaseMessaging.getInstance().unsubscribeFromTopic("Applicant");
                                            Intent ia = new Intent(MainActivity.this, EmployerActivity.class);
                                            startActivity(ia);
                                            finish();
                                        }

                                    }
                                    else
                                    {
                                        sessionManager.setApplicantLogin(true);
                                        FirebaseMessaging.getInstance().subscribeToTopic("Applicant");
                                        FirebaseMessaging.getInstance().unsubscribeFromTopic("Employer");
                                        Intent i = new Intent(MainActivity.this, ApplicantActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                                else{
                                    Toast.makeText(MainActivity.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                }
                            progressDialog.cancel();

                        }catch(JSONException e)
                        {
                            Toast.makeText(MainActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
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
            }
        });
        if(sessionManager.getApplicantLogin())
        {
            FirebaseMessaging.getInstance().subscribeToTopic("Applicant");
            FirebaseMessaging.getInstance().unsubscribeFromTopic("Employer");
            Intent i = new Intent(MainActivity.this, ApplicantActivity.class);
            startActivity(i);
        }
        else if(sessionManager.getEmployerLogin())
        {
            FirebaseMessaging.getInstance().subscribeToTopic("Employer");
            FirebaseMessaging.getInstance().unsubscribeFromTopic("Applicant");
            Intent ia = new Intent(MainActivity.this, EmployerActivity.class);
            startActivity(ia);
        }
    }

    private boolean validate(){
        if (edt_loginemail.getText().toString().isEmpty()){
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Email is Required");
            return false;
        }
        if (edt_loginpassword.getText().toString().isEmpty()){
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("Password is Required");
            return false;
        }
        if (edt_loginpassword.getText().toString().length()<8){
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("Required at least 8 characters");
            return false;
        }
        return true;
    }

    private void getTokenId()
    {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful())
                {
                    Log.d(TAG,"onComplete: Failed to get token");
                }

                //token
                String token = task.getResult();
                Log.d(TAG,"onComplete: "+token);
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "firebasenotifchannel";
            String description = "Receive Firebase Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}