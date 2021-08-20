package com.example.onlinejobfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EditContactNumberActivity extends AppCompatActivity {

    EditText edt_editcontactnum;
    Button btnsendOTP;
    int randomnumber;
    ProgressDialog progressDialog;
    String intentcontactnum, intentOTPcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact_number);

        edt_editcontactnum = findViewById(R.id.edittext_editcontactnumber);
        btnsendOTP = findViewById(R.id.btnsendOTP);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        String intentname = getIntent().getExtras().getString("name");
        String intentemail = getIntent().getExtras().getString("email");
        String intentaddress = getIntent().getExtras().getString("address");
        String intentcompanyoverview = getIntent().getExtras().getString("companyoverview");
        getSupportActionBar().setCustomView(R.layout.customactionbareditcontactno);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        edt_editcontactnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edt_editcontactnum.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                edt_editcontactnum.setError(null);
            }
        });

        btnsendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( validate())
                {
                    progressDialog.setMessage("Sending OTP");
                    progressDialog.show();
//                Random random = new Random();
//                randomnumber = random.nextInt(9999);
//                VonageClient client = VonageClient.builder().apiKey("b08f100e").apiSecret("S6OypDlJB1AvjLyc").build();
//
//                TextMessage message = new TextMessage("PESO-OJF",
//                        edt_editcontactnum.getText().toString(),
//                        "Your OTP Code is" + randomnumber
//                );
//
//                SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);
//
//                if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
//                    Toast.makeText(EditContactNumberActivity.this,"OTP send Successfully",Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(EditContactNumberActivity.this, VerifyOTP.class);
//                    i.putExtra("name",intentname);
//                    i.putExtra("email",intentemail);
//                    i.putExtra("address",intentaddress);
//                    i.putExtra("OTPcode",randomnumber);
//                    i.putExtra("contactnum",edt_editcontactnum.getText().toString());
//                    startActivity(i);
//
//                    progressDialog.cancel();
//                } else
//                    {
//                    Toast.makeText(EditContactNumberActivity.this,response.getMessages().get(0).getErrorText(),Toast.LENGTH_SHORT).show();
//                    progressDialog.cancel();
//                }
                    StringRequest request = new StringRequest(Request.Method.GET, Constant.sendOTP+"?contactnum="+edt_editcontactnum.getText().toString(), response -> {
                        try{
                            JSONObject object= new JSONObject(response);
                            if(object.getBoolean("success")){
                                //JSONObject user = object.getJSONObject("contactnumber");
                                // JSONObject user2 = object.getJSONObject("OTPcode");
                                intentcontactnum = object.get("contactnumber").toString();
                                intentOTPcode = object.get("OTPcode").toString();
                                Toast.makeText(EditContactNumberActivity.this,"OTP send Successfully",Toast.LENGTH_SHORT).show();
                                //     Toast.makeText(EditContactNumberActivity.this,intentOTPcode,Toast.LENGTH_SHORT).show();
                                //    Toast.makeText(EditContactNumberActivity.this,intentcontactnum,Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(EditContactNumberActivity.this, VerifyOTP.class);
                                i.putExtra("name",intentname);
                                i.putExtra("email",intentemail);
                                i.putExtra("address",intentaddress);
                                i.putExtra("OTPcode", intentOTPcode);
                                i.putExtra("gender",getIntent().getExtras().getString("gender"));
                                i.putExtra("profile_pic", getIntent().getStringExtra("profile_pic"));
                                i.putExtra("contactnum","0" + intentcontactnum);
                                i.putExtra("companyoverview", intentcompanyoverview);
                                i.putExtra("specialization",getIntent().getExtras().getString("specialization"));
                                startActivity(i);
                                finish();

                                progressDialog.cancel();

                            }
                            else
                            {
                                Toast.makeText(EditContactNumberActivity.this, "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                                // progressDialog.cancel();
                            }

                        }catch(JSONException e)
                        {
                            Toast.makeText(EditContactNumberActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                            progressDialog.cancel();
                        }
                    },error ->{
                        error.printStackTrace();
                        Toast.makeText(EditContactNumberActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
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

                    RequestQueue queue = Volley.newRequestQueue(EditContactNumberActivity.this);
                    queue.add(request);
                }
            }
        });
    }
    private boolean validate(){
        if (edt_editcontactnum.getText().toString().isEmpty()) {
            edt_editcontactnum.setError("Contact Number is required");
            edt_editcontactnum.requestFocus();
            return false;
        }
        if (edt_editcontactnum.getText().toString().length()<10){
            edt_editcontactnum.setError("Contact Number must have 10 digits");
            edt_editcontactnum.requestFocus();
            return false;
        }
        return true;
    }
}