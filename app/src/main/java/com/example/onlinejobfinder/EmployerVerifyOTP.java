package com.example.onlinejobfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinejobfinder.applicant.EditProfileActivity;
import com.example.onlinejobfinder.employer.EditEmployerProfileActivity;

public class EmployerVerifyOTP extends AppCompatActivity {

    EditText edit_OTPcode;
    Button btn_verifycode;
    ProgressDialog progressDialog;
    TextView txt_contactnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_verify_o_t_p);
        edit_OTPcode = findViewById(R.id.employer_edittext_OTPcode);
        btn_verifycode = findViewById(R.id.employer_btnverifyOTP);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        String intentname = getIntent().getExtras().getString("name");
        String intentemail = getIntent().getExtras().getString("email");
        String intentaddress = getIntent().getExtras().getString("address");
        String intentcontactnum = getIntent().getExtras().getString("contactnum");
        String intentOTPcode = getIntent().getExtras().getString("OTPcode");
        String intentcompanyoverview = getIntent().getExtras().getString("companyoverview");
        txt_contactnum = findViewById(R.id.employer_OTPcontactnum);
        txt_contactnum.setText(intentcontactnum);

        getSupportActionBar().setCustomView(R.layout.customactionbarverifyotp);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        btn_verifycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit_OTPcode.getText().toString().equals(intentOTPcode))
                {
                    Intent i = new Intent(EmployerVerifyOTP.this, EditEmployerProfileActivity.class);
                    i.putExtra("name",intentname);
                    i.putExtra("email",intentemail);
                    i.putExtra("address",intentaddress);
                    i.putExtra("contactno",txt_contactnum.getText().toString());
                    i.putExtra("companyoverview",intentcompanyoverview);
                    startActivity(i);
                    finish();
                    progressDialog.cancel();

                }
                else
                {
                    Toast.makeText(EmployerVerifyOTP.this,"Wrong OTP, please send again",Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                }
            }
        });
    }
}