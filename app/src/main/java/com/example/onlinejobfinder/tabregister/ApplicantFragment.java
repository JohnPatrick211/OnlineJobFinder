package com.example.onlinejobfinder.tabregister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.onlinejobfinder.MainActivity;
import com.example.onlinejobfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApplicantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplicantFragment extends Fragment {
    TextView signin;
    Button btnregister;
     TextInputLayout layoutEmail,layoutPassword,layoutConfirm,layoutName;
    TextInputEditText edt_email,edt_name,edt_password,edt_confirmpassword;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser user;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ApplicantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApplicantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApplicantFragment newInstance(String param1, String param2) {
        ApplicantFragment fragment = new ApplicantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_applicant, container, false);
        btnregister = view.findViewById(R.id.btn_register);
        edt_email = view.findViewById(R.id.edittext_email);
        edt_name = view.findViewById(R.id.edittext_name);
        edt_password = view.findViewById(R.id.edittext_password);
        edt_confirmpassword = view.findViewById(R.id.edittext_confirmpassword);
        layoutName = view.findViewById(R.id.txtLayoutNameSignUp);
        layoutPassword = view.findViewById(R.id.txtLayoutPasswordSignUp);
        layoutEmail = view.findViewById(R.id.txtLayoutEmailSignUp);
        layoutConfirm = view.findViewById(R.id.txtLayoutConfirmPasswordSignUp);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        mAuth = FirebaseAuth.getInstance();
        String role_applicant = "applicant";
        user = mAuth.getCurrentUser();
        signin = view.findViewById(R.id.txtview_signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
            }
        });

        edt_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edt_name.getText().toString().isEmpty()){
                    layoutName.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edt_email.getText().toString().isEmpty()){
                    layoutEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ( edt_password.getText().toString().length()>7){
                    layoutPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edt_confirmpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edt_confirmpassword.getText().toString().equals(edt_password.getText().toString())){
                    layoutConfirm.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                    //                Toast.makeText(getContext(),"login success",Toast.LENGTH_SHORT).show();
                    progressDialog.setMessage("Registering");
                    progressDialog.show();
                    StringRequest request = new StringRequest(Request.Method.POST, Constant.register, response -> {
                        try{
                            JSONObject object= new JSONObject(response);
                            if(object.getBoolean("success")){
                                mAuth.createUserWithEmailAndPassword(edt_email.getText().toString().trim(),edt_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful())
                                        {
                                            sendVerificationEmail();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                        Log.e("MYAPP", "exception", e);
                                    }
                                });
                                JSONObject user = object.getJSONObject("user");
                                SharedPreferences userPref = getActivity().getApplicationContext().getSharedPreferences("user",getContext().MODE_PRIVATE);
                                SharedPreferences.Editor editor = userPref.edit();
                                editor.putString("token",object.getString("token"));
                                editor.putString("email",user.getString("email"));
                                editor.putString("name",user.getString("name"));
                                editor.putString("role",user.getString("role"));
                                editor.apply();
//                            Toast.makeText(getContext(),"Register Successfully",Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                            else if(object.getString("Status").equals("201"))
                            {
                                Toast.makeText(getContext(),"Email Already Exists, Please Try Again",Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                            else if(object.getString("Status").equals("202"))
                            {
                                Toast.makeText(getContext(),"Name Already Exists, Please Try Again",Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                            else
                            {
                                Toast.makeText(getContext(), "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }

                        }catch(JSONException e)
                        {
                            Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    },error ->{
                        error.printStackTrace();
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> map = new HashMap<>();
                            map.put("name",edt_name.getText().toString().trim());
                            map.put("email",edt_email.getText().toString().trim());
                            map.put("password",edt_password.getText().toString());
                            map.put("role",role_applicant);
                            return map;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    queue.add(request);
                }
            }
        });
        return view;
    }

    private void sendVerificationEmail() {
        if(mAuth.getCurrentUser()!=null)
        {
            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getContext(),"Email has been send to your email address to verify, if you verified, proceed to login by clicking login below",Toast.LENGTH_SHORT).show();
//                        user.reload();
                    }
                    else
                    {
                        Toast.makeText(getContext(),"failed to send verification email",Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private boolean validate (){
        if (edt_name.getText().toString().isEmpty()){
            layoutName.setErrorEnabled(true);
            layoutName.setError("Name is Required");
            return false;
        }
        if (edt_email.getText().toString().isEmpty()){
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Email is Required");
            return false;
        }
        if (edt_password.getText().toString().isEmpty()){
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("Password is Required");
            return false;
        }
        if (edt_password.getText().toString().length()<8){
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("Required at least 8 characters");
            return false;
        }
        if (!edt_confirmpassword.getText().toString().equals(edt_password.getText().toString())){
            layoutConfirm.setErrorEnabled(true);
            layoutConfirm.setError("Password does not match");
            return false;
        }


        return true;
    }
}