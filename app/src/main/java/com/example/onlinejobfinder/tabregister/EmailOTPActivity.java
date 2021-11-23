package com.example.onlinejobfinder.tabregister;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.EmailActivity;
import com.example.onlinejobfinder.MainActivity;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.SendOTPMail;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EmailOTPActivity extends AppCompatActivity {
    EditText code1, code2, code3, code4, code5, code6;
    Button btnEmailVerify;
    TextView displayemail,tv_resend;
    ProgressDialog progressDialog;
    int i = 30;
    int e = 2;
    CountDownTimer CDT;
    int randomnumber;
    String intentOTPcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_o_t_p);
        displayemail = findViewById(R.id.textemail);
        code1 = findViewById(R.id.inputCode1);
        code2 = findViewById(R.id.inputCode2);
        code3 = findViewById(R.id.inputCode3);
        code4 = findViewById(R.id.inputCode4);
        code5 = findViewById(R.id.inputCode5);
        code6 = findViewById(R.id.inputCode6);
        tv_resend = findViewById(R.id.textResendOTP);
        btnEmailVerify = findViewById(R.id.btnVerifyEmailOTP);
        progressDialog = new ProgressDialog(EmailOTPActivity.this);
        progressDialog.setCancelable(false);

        String intentname = getIntent().getExtras().getString("name");
        String intentemail = getIntent().getExtras().getString("email");
        String intentpassword = getIntent().getExtras().getString("password");
        String intentrole = getIntent().getExtras().getString("role");
        intentOTPcode = getIntent().getExtras().getString("OTPcode");
//        Toast.makeText(EmailOTPActivity.this,intentOTPcode,Toast.LENGTH_SHORT).show();
        displayemail.setText(intentemail);

        Random random = new Random();
        randomnumber = random.nextInt(999999-111111) + 111111;

        setupOTPInputs();
        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendOTPMail sm = new SendOTPMail(EmailOTPActivity.this, intentemail, "Email Verification", "Your Code is " +randomnumber,"peso-notification@pesobalayan-ojfs.online");
                intentOTPcode = Integer.toString(randomnumber);
                sm.execute();
                CDT = new CountDownTimer(30000, 1000) {
                    @Override
                    public void onTick(long l) {
                        tv_resend.setText("Resend In " + i + " sec");
                        tv_resend.setEnabled(false);
                        i--;
                    }

                    @Override
                    public void onFinish() {
                        tv_resend.setText("Resend Here");
                        tv_resend.setEnabled(true);
                        i=30;
                    }
                }.start();
            }
        });

        btnEmailVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                    String code = code1.getText().toString() +
                            code2.getText().toString() +
                            code3.getText().toString() +
                            code4.getText().toString() +
                            code5.getText().toString() +
                            code6.getText().toString();
                    if(code.equals(intentOTPcode))
                    {
                        if(intentrole.equals("applicant"))
                        {
                            progressDialog.setMessage("Registering");
                            progressDialog.show();
                            StringRequest request = new StringRequest(Request.Method.POST, Constant.register, response -> {
                                try{
                                    JSONObject object= new JSONObject(response);
                                    if(object.getBoolean("success")){
//                                mAuth.createUserWithEmailAndPassword(edt_email.getText().toString().trim(),edt_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                        if(task.isSuccessful())
//                                        {
//                                            sendVerificationEmail();
//                                        }
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                                        Log.e("MYAPP", "exception", e);
//                                    }
//                                });
                                        Intent i = new Intent(EmailOTPActivity.this, MainActivity.class);
                                        startActivity(i);
                                        Toast.makeText(EmailOTPActivity.this,"Register Successfully",Toast.LENGTH_SHORT).show();
                                        JSONObject user = object.getJSONObject("user");
                                        SharedPreferences userPref = getApplicationContext().getSharedPreferences("user",MODE_PRIVATE);
                                        SharedPreferences.Editor editor = userPref.edit();
                                        editor.putString("token",object.getString("token"));
                                        editor.putString("email",user.getString("email"));
                                        editor.putString("name",user.getString("name"));
                                        editor.putString("role",user.getString("role"));
                                        editor.apply();
                                        finish();
//                            Toast.makeText(getContext(),"Register Successfully",Toast.LENGTH_SHORT).show();
                                        progressDialog.cancel();
                                    }
                                    else if(object.getString("Status").equals("201"))
                                    {
                                        Toast.makeText(EmailOTPActivity.this,"Email Already Exists, Please Try Again",Toast.LENGTH_SHORT).show();
                                        progressDialog.cancel();
                                    }
                                    else if(object.getString("Status").equals("202"))
                                    {
                                        Toast.makeText(EmailOTPActivity.this,"Name Already Exists, Please Try Again",Toast.LENGTH_SHORT).show();
                                        progressDialog.cancel();
                                    }
                                    else
                                    {
                                        Toast.makeText(EmailOTPActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                                        progressDialog.cancel();
                                    }

                                }catch(JSONException e)
                                {
                                    Toast.makeText(EmailOTPActivity.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                }
                            },error ->{
                                error.printStackTrace();
                            })
                            {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String,String> map = new HashMap<>();
                                    map.put("name",intentname);
                                    map.put("email",intentemail);
                                    map.put("password",intentpassword);
                                    map.put("role",intentrole);
                                    return map;
                                }
                            };
                            RequestQueue queue = Volley.newRequestQueue(EmailOTPActivity.this);
                            queue.add(request);
                        }
                        else
                        {
                            CDT = new CountDownTimer(2000, 1000) {
                                @Override
                                public void onTick(long l) {
                                    progressDialog.setMessage("Verifying");
                                    progressDialog.show();
                                    e--;
                                }

                                @Override
                                public void onFinish() {
                                    e=3;
                                    progressDialog.cancel();
                                    Intent i = new Intent(EmailOTPActivity.this, EmployerRegistrationActivity.class);
                                    i.putExtra("employeremail", intentemail);
                                    startActivity(i);
                                    finish();
                                }
                            }.start();

                        }
                    }
                    else
                    {
                        Toast.makeText(EmailOTPActivity.this,"Wrong Code, Please Try Again",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void setupOTPInputs()
    {
        code1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    code2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        code2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    code3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        code3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    code4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        code4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    code5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        code5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    code6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    private boolean validate()
    {
        if(code1.getText().toString().trim().isEmpty() || code2.getText().toString().trim().isEmpty() || code3.getText().toString().trim().isEmpty() || code4.getText().toString().trim().isEmpty() || code5.getText().toString().trim().isEmpty() || code6.getText().toString().trim().isEmpty())
        {
            Toast.makeText(EmailOTPActivity.this,"Please Enter Valid Code",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}