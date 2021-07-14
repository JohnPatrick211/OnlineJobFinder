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

public class VerifyOTP extends AppCompatActivity {

    EditText edit_OTPcode;
    Button btn_verifycode;
    ProgressDialog progressDialog;
    TextView txt_contactnum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p);
        edit_OTPcode = findViewById(R.id.edittext_OTPcode);
        btn_verifycode = findViewById(R.id.btnverifyOTP);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        String intentname = getIntent().getExtras().getString("name");
        String intentemail = getIntent().getExtras().getString("email");
        String intentaddress = getIntent().getExtras().getString("address");
        String intentcontactnum = getIntent().getExtras().getString("contactnum");
        String intentOTPcode = getIntent().getExtras().getString("OTPcode");
        txt_contactnum = findViewById(R.id.OTPcontactnum);
        txt_contactnum.setText(intentcontactnum);
        //checkCode
        Toast.makeText(VerifyOTP.this,intentOTPcode,Toast.LENGTH_SHORT).show();
        btn_verifycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Verifying");
                progressDialog.show();
                if(edit_OTPcode.getText().toString().equals(intentOTPcode))
                {
                    Intent i = new Intent(VerifyOTP.this, EditProfileActivity.class);
                    i.putExtra("name",intentname);
                    i.putExtra("email",intentemail);
                    i.putExtra("address",intentaddress);
                    i.putExtra("contactno",txt_contactnum.getText().toString());
                    startActivity(i);
                    finish();
                    progressDialog.cancel();

                }
                else
                {
                    Toast.makeText(VerifyOTP.this,"Wrong OTP, please send again",Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                }
            }
        });
    }
}