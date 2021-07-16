package com.example.onlinejobfinder.employer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.applicant.EditProfileActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployerProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployerProfileFragment extends Fragment {

    TextView txtemployername, txtemployeremail, txtemployercontactno, txtemployeraddress, txtemployerspecialization,txtemployercompanyoverview;
    ImageView editemployerprofile,imageview_employer,imageview_BIRemployer;
    String name2, user_id,token,permaid, address, email, contactno, background;
    String val_contactno = "";
    String val_specialization = "";
    SharedPreferences userPref2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EmployerProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmployerProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmployerProfileFragment newInstance(String param1, String param2) {
        EmployerProfileFragment fragment = new EmployerProfileFragment();
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
        View root = inflater.inflate(R.layout.fragment_employer_profile, container, false);
        editemployerprofile = root.findViewById(R.id.image_manageemployeraccountprofile);
        txtemployername = root.findViewById(R.id.textView_EmployerNameProfile);
        txtemployeraddress = root.findViewById(R.id.textView_EmployerAddressProfile);
        txtemployercontactno = root.findViewById(R.id.textView_EmployerContactNoProfile);
        txtemployeremail = root.findViewById(R.id.textView_EmployerEmailProfile);
        txtemployerspecialization = root.findViewById(R.id.textView_EmployerSpecializationProfile);
        imageview_employer = root.findViewById(R.id.imgemployerprofile);
        imageview_BIRemployer = root.findViewById(R.id.img_profile_BIR);
        txtemployercompanyoverview = root.findViewById(R.id.textView_EmployerCompanyOverviewProfile);

        userPref2 = getContext().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPref2.edit();
        name2 = userPref2.getString("name","name");
        email = userPref2.getString("email","email");
        user_id = userPref2.getString("id","id");
        token = userPref2.getString("token","token");



        Toast.makeText(getContext(), email, Toast.LENGTH_SHORT).show();

        StringRequest request = new StringRequest(Request.Method.GET, Constant.EMPLOYER_POST+"?employer_id="+user_id, response -> {
            try{
                JSONObject object= new JSONObject(response);
                if(object.getBoolean("success")){
                    JSONObject user = object.getJSONObject("user");
                    txtemployername.setText(user.get("name").toString());
                    txtemployeremail.setText(user.get("email").toString());
                    txtemployercontactno.setText(user.get("contactno").toString());
                    val_contactno = txtemployercontactno.getText().toString();
                    txtemployeraddress.setText(user.get("address").toString());
                    txtemployerspecialization.setText(user.get("Specialization").toString());
                    val_specialization =  txtemployerspecialization.getText().toString();
                    txtemployercompanyoverview.setText(user.get("companyoverview").toString());
                    Picasso.get().load(Constant.URL+"/storage/profiles/"+user.getString("profile_pic")).into(imageview_employer);
                    Picasso.get().load(Constant.URL+"/storage/BIR/"+user.getString("BIR_file")).into(imageview_BIRemployer);
                    SharedPreferences.Editor editor2 = userPref2.edit();
                    editor2.putString("name",user.getString("name"));
                    editor2.putString("address",user.getString("address"));
                    editor2.putString("contactno",user.getString("contactno"));
                    //editor2.putString("email",user.getString("email"));
                    // editor2.putString("background",user.getString("background"));
                    editor2.apply();
                    editor2.commit();
                    if(user.get("contactno").toString().equals("null")|| user.get("Specialization").toString().equals("null"))
                    {
                        txtemployercontactno.setVisibility(View.GONE);
                        txtemployerspecialization.setVisibility(View.GONE);
                    }
                    else {
                        txtemployercontactno.setVisibility(View.VISIBLE);
                        txtemployerspecialization.setVisibility(View.VISIBLE);
                    }

                }
                else
                {
                    Toast.makeText(getContext(), "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                    // progressDialog.cancel();
                }

            }catch(JSONException e)
            {
                //Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                txtemployercontactno.setVisibility(View.GONE);
                txtemployeraddress.setVisibility(View.GONE);
                txtemployerspecialization.setVisibility(View.GONE);
                txtemployername.setText(name2);
                txtemployeremail.setText(email);
                //  progressDialog.cancel();
            }
        },error ->{
            error.printStackTrace();
            Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            txtemployername.setText(name2);
            txtemployeremail.setText(email);
            txtemployercompanyoverview.setText("network error in loading of content");
            // progressDialog.cancel();
        })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                //String token = userPref.getString("token","token");
                map.put("Authorization","Bearer "+token);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);



        editemployerprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), EditEmployerProfileActivity.class);
                i.putExtra("name",txtemployername.getText().toString());
                i.putExtra("email",txtemployeremail.getText().toString());
                i.putExtra("address",txtemployeraddress.getText().toString());
                i.putExtra("companyoverview",txtemployercompanyoverview.getText().toString());
                if(val_contactno.equals("null")||val_specialization.equals("null"))
                {
                    i.putExtra("contactno","");
                    i.putExtra("specialization","");
                }
                else
                {
                    i.putExtra("contactno",txtemployercontactno.getText().toString());
                    i.putExtra("specialization",txtemployerspecialization.getText().toString());
                }
                startActivity(i);
            }
        });

        return root;
    }

    public void onResume() {
        super.onResume();
        StringRequest request = new StringRequest(Request.Method.GET, Constant.EMPLOYER_POST+"?employer_id="+user_id, response -> {
            try{
                JSONObject object= new JSONObject(response);
                if(object.getBoolean("success")){
                    JSONObject user = object.getJSONObject("user");
                    txtemployername.setText(user.get("name").toString());
                    txtemployeremail.setText(user.get("email").toString());
                    txtemployercontactno.setText(user.get("contactno").toString());
                    val_contactno = txtemployercontactno.getText().toString();
                    txtemployeraddress.setText(user.get("address").toString());
                    txtemployerspecialization.setText(user.get("Specialization").toString());
                    val_specialization =  txtemployerspecialization.getText().toString();
                    txtemployercompanyoverview.setText(user.get("companyoverview").toString());
                    Picasso.get().load(Constant.URL+"/storage/profiles/"+user.getString("profile_pic")).into(imageview_employer);
                    Picasso.get().load(Constant.URL+"/storage/BIR/"+user.getString("BIR_file")).into(imageview_BIRemployer);
                    SharedPreferences.Editor editor2 = userPref2.edit();
                    editor2.putString("name",user.getString("name"));
                    editor2.putString("address",user.getString("address"));
                    editor2.putString("contactno",user.getString("contactno"));
                    //editor2.putString("email",user.getString("email"));
                    // editor2.putString("background",user.getString("background"));
                    editor2.apply();
                    editor2.commit();
                    if(user.get("contactno").toString().equals("null")|| user.get("Specialization").toString().equals("null"))
                    {
                        txtemployercontactno.setVisibility(View.GONE);
                        txtemployerspecialization.setVisibility(View.GONE);
                    }
                    else {
                        txtemployercontactno.setVisibility(View.VISIBLE);
                        txtemployerspecialization.setVisibility(View.VISIBLE);
                    }

                }
                else
                {
                    Toast.makeText(getContext(), "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                    // progressDialog.cancel();
                }

            }catch(JSONException e)
            {
                //Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                txtemployercontactno.setVisibility(View.GONE);
                txtemployeraddress.setVisibility(View.GONE);
                txtemployerspecialization.setVisibility(View.GONE);
                txtemployername.setText(name2);
                txtemployeremail.setText(email);
                //  progressDialog.cancel();
            }
        },error ->{
            error.printStackTrace();
            Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            txtemployername.setText(name2);
            txtemployeremail.setText(email);
            txtemployercompanyoverview.setText("network error in loading of content");
            // progressDialog.cancel();
        })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                //String token = userPref.getString("token","token");
                map.put("Authorization","Bearer "+token);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}