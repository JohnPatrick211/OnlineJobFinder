package com.example.onlinejobfinder.employer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.onlinejobfinder.EmailActivity;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.SendMail;
import com.example.onlinejobfinder.SendRequestMail;

public class RequestMaintenanceActivity extends AppCompatActivity {
    Button btn_sendrequest;
    ProgressDialog progressDialog;
    EditText edittext_subject, edittext_message,edittext_email;
    String name2, user_id,token,permaid, address, email, contactno, background;
    String val_contactno = "";
    String val_specialization = "";
    SharedPreferences userPref2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_maintenance);
        btn_sendrequest = findViewById(R.id.btnsendrequestmaintenance);
        edittext_message = findViewById(R.id.edittext_requestmaintenance_message);
        edittext_subject = findViewById(R.id.edittext_requestmaintenance_subject);
        edittext_email = findViewById(R.id.edittext_requestmaintenance_sender);
        userPref2 = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPref2.edit();
        name2 = userPref2.getString("name","name");
        email = userPref2.getString("email","email");
        user_id = userPref2.getString("id","id");
        token = userPref2.getString("token","token");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        edittext_email.setText(email);

        btn_sendrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendRequestMail sm = new SendRequestMail(RequestMaintenanceActivity.this, "pesojob@gmail.com", edittext_subject.getText().toString(), edittext_message.getText().toString(),edittext_email.getText().toString());
                sm.execute();
            }
        });
    }
}