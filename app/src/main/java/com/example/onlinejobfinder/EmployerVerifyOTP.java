package com.example.onlinejobfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinejobfinder.applicant.EditProfileActivity;
import com.example.onlinejobfinder.employer.EditEmployerProfileActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployerVerifyOTP extends AppCompatActivity {

    EditText code1, code2, code3, code4;
    Button btn_verifycode;
    ProgressDialog progressDialog;
    TextView txt_contactnum,tv_resend;
    int i = 30;
    int e = 2;
    CountDownTimer CDT;
    int randomnumber;
    String intentOTPcode,resendnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_verify_o_t_p);
        code1 = findViewById(R.id.inputCode1);
        code2 = findViewById(R.id.inputCode2);
        code3 = findViewById(R.id.inputCode3);
        code4 = findViewById(R.id.inputCode4);
        tv_resend = findViewById(R.id.textResendOTP);
        btn_verifycode = findViewById(R.id.employer_btnverifyOTP);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        String intentname = getIntent().getExtras().getString("name");
        String intentemail = getIntent().getExtras().getString("email");
        String intentaddress = getIntent().getExtras().getString("address");
        String intentcontactnum = getIntent().getExtras().getString("contactnum");
        intentOTPcode = getIntent().getExtras().getString("OTPcode");
        String intentcompanyoverview = getIntent().getExtras().getString("companyoverview");
        txt_contactnum = findViewById(R.id.employer_OTPcontactnum);
        txt_contactnum.setText(intentcontactnum);
        resendnum = intentcontactnum.substring(1);

        getSupportActionBar().setCustomView(R.layout.customactionbarverifyotp);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setupOTPInputs();
        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Sending OTP code");
                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.GET, Constant.sendOTP+"?contactnum="+resendnum, response -> {
                    try{
                        JSONObject object= new JSONObject(response);
                        if(object.getBoolean("success")){
                            //JSONObject user = object.getJSONObject("contactnumber");
                            // JSONObject user2 = object.getJSONObject("OTPcode");
                            intentOTPcode = object.get("OTPcode").toString();
                            Toast.makeText(EmployerVerifyOTP.this,"OTP send Successfully",Toast.LENGTH_SHORT).show();
                            //     Toast.makeText(EditContactNumberActivity.this,intentOTPcode,Toast.LENGTH_SHORT).show();
                            //    Toast.makeText(EditContactNumberActivity.this,intentcontactnum,Toast.LENGTH_SHORT).show();

                            progressDialog.cancel();

                        }
                        else
                        {
                            Toast.makeText(EmployerVerifyOTP.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                            // progressDialog.cancel();
                        }

                    }catch(JSONException e)
                    {
                        Toast.makeText(EmployerVerifyOTP.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }
                },error ->{
                    error.printStackTrace();
                    Toast.makeText(EmployerVerifyOTP.this,"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                })
                {

//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        HashMap<String,String> map = new HashMap<>();
//                        //String token = userPref.getString("token","token");
//                        map.put("Authorization","Bearer "+token);
//                        return map;
//                    }
                };

                RequestQueue queue = Volley.newRequestQueue(EmployerVerifyOTP.this);
                queue.add(request);
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

        btn_verifycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                    String code = code1.getText().toString() +
                            code2.getText().toString() +
                            code3.getText().toString() +
                            code4.getText().toString();
                    if(code.equals(intentOTPcode))
                    {
                        Intent i = new Intent(EmployerVerifyOTP.this, EditEmployerProfileActivity.class);
                        i.putExtra("name",intentname);
                        i.putExtra("email",intentemail);
                        i.putExtra("address",intentaddress);
                        i.putExtra("contactno",txt_contactnum.getText().toString());
                        i.putExtra("companyoverview",intentcompanyoverview);
                        i.putExtra("profile_pic", getIntent().getStringExtra("profile_pic"));
                        i.putExtra("specialization",getIntent().getExtras().getString("specialization"));
                        startActivity(i);
                        finish();
                        progressDialog.cancel();
                    }
                    else
                    {
                        Toast.makeText(EmployerVerifyOTP.this,"Wrong Code, Please Try Again",Toast.LENGTH_SHORT).show();
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
    }
    private boolean validate()
    {
        if(code1.getText().toString().trim().isEmpty() || code2.getText().toString().trim().isEmpty() || code3.getText().toString().trim().isEmpty() || code4.getText().toString().trim().isEmpty())
        {
            Toast.makeText(EmployerVerifyOTP.this,"Please Enter Valid Code",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}