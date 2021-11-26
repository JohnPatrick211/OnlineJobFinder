package com.example.onlinejobfinder.applicant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.adapter.educationalbackgroundadapter;
import com.example.onlinejobfinder.adapter.workexperienceadapter;
import com.example.onlinejobfinder.model.educationalbackground;
import com.example.onlinejobfinder.model.workexperience;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {


    TextView txtmanageaccount, txtname, txtemail, txtcontactno, txtaddress, txtgender, txtspecialization, txtresume,txtintentresume, txtviewresume, txtaddeduc,txtaddwork;
    ImageView editprofile;
    View ln_noworkexperiencelayout, ln_noeducationlayout, ln_noresumelayout;
    LinearLayout ln_networkerrorworkexperience, ln_networkerroreducation;
    TextView tvs_networkworkerrorrefresh, tvs_networkeducationerrorrefresh;
    educationalbackgroundadapter educationalbackgroundadapter;
    workexperienceadapter workexperienceadapter2;
    workexperienceadapter.RecyclerViewClickListener worklistener;
    educationalbackgroundadapter.RecyclerViewClickListener listener;
    LinearLayout main, main2, main3, main4;
    View ln_delay;
    CountDownTimer CDT;
   // RecyclerView recyclerview;
  //  jobadapter jobadapter2;
    //ArrayList<job> arraylist;
    //SwipeRefreshLayout refreshLayout;
    //    String[] jobtitle = {"Junior Programmer", "Assistant Manager", "Photographer"};
//    int[] images = {R.drawable.samplelogo, R.drawable.samplelogo, R.drawable.samplelogo};
//    String[] jobcompany = {"Codify Ltd.", "Jollibee Antartica", "JBC Firm"};
//    String[] jobaddress = {"Caloocan, Balayan, Batangas", "Antartica", "Ermita, Balayan, Batangas"};
//    String[] jobsalary = {"PHP 100000-200000", "PHP 250000-500000", "PHP 50000-10000"};
//    String[] jobdateposted = {"1 hours ago", "30 Apr 2021", "29 Apr 2021"};
    SharedPreferences userPref,userPref2;
  //  TextView textView_name;
   // TextView textView_manage;
    //jobadapter.RecyclerViewClickListener listener;
    ImageView imageview_user;
    String name2, user_id,token,permaid, address, email, contactno, background;
    String val_contactno = "";
    String val_address = "";
    String val_gender = "";
    String val_specialization = "";
    String val_resume = "";
    String imgUrl = "";
    ArrayList<educationalbackground> arraylist;
    ArrayList<workexperience> arraylist2;
    RecyclerView recyclerView, recyclerView2;
    JSONArray result;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
       View root = inflater.inflate(R.layout.fragment_profile, container, false);
        txtintentresume = root.findViewById(R.id.textView_IntentResume);
        txtviewresume = root.findViewById(R.id.textView_ViewResume);
       editprofile = root.findViewById(R.id.image_manageaccountprofile);
        txtname = root.findViewById(R.id.textView_NameProfile);
        txtemail = root.findViewById(R.id.textView_EmailProfile);
        txtaddress = root.findViewById(R.id.textView_AddressProfile);
        txtcontactno = root.findViewById(R.id.textView_ContactNoProfile);
        txtgender = root.findViewById(R.id.textView_GenderProfile);
        txtresume = root.findViewById(R.id.textView_ResumeView);
        txtspecialization = root.findViewById(R.id.textView_SpecializationProfile);
        txtaddeduc = root.findViewById(R.id.textView_addeducation);
        txtaddwork = root.findViewById(R.id.textView_addworkexperience);
        recyclerView = root.findViewById(R.id.recyclerview_educationalbackground);
        recyclerView2 = root.findViewById(R.id.recyclerview_workexperience);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        arraylist = new ArrayList<>();
        arraylist2 = new ArrayList<>();
     //   textView_manage = root.findViewById(R.id.textView_Manage);
        imageview_user = root. findViewById(R.id.imguserprofile);
     //   recyclerview = root.findViewById(R.id.recyclerview_jobs_recommended);
     //   recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
      //  refreshLayout = root.findViewById(R.id.swipe2);
     //   arraylist = new ArrayList<>();
      //  refreshLayout.setRefreshing(true);
     //   userPref = getContext().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        userPref2 = getContext().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPref2.edit();
        name2 = userPref2.getString("name","name");
        email = userPref2.getString("email","email");
        user_id = userPref2.getString("id","id");
        token = userPref2.getString("token","token");
        permaid = user_id;
        ln_networkerrorworkexperience= root.findViewById(R.id.networkworkexperienceerrorlayout);
        ln_networkerroreducation = root.findViewById(R.id.networkeducationerrorlayout);
        tvs_networkworkerrorrefresh = root.findViewById(R.id.tv_networkworkexperienceerrorrefresh);
        tvs_networkeducationerrorrefresh = root.findViewById(R.id.tv_networkeducationerrorrefresh);
        ln_networkerrorworkexperience.setVisibility(View.GONE);
        ln_networkerroreducation.setVisibility(View.GONE);
        ln_noworkexperiencelayout = root.findViewById(R.id.ln_noworkexperiencelayout);
        ln_noeducationlayout = root.findViewById(R.id.ln_noeducationlayout);
        ln_noresumelayout = root.findViewById(R.id.ln_noresumelayout);
        //check ID debugging//
        //Toast.makeText(getContext(), user_id, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), email, Toast.LENGTH_SHORT).show();
        setOnClickListener();
        setOnClickListener2();

        ln_delay = root.findViewById(R.id.ln_delayloadinglayout);

        main = root.findViewById(R.id.bruh);
        main2 = root.findViewById(R.id.bruh2);
        main3 = root.findViewById(R.id.bruh3);
        main4 = root.findViewById(R.id.bruh4);
        main.setVisibility(View.GONE);
        main2.setVisibility(View.GONE);
        main3.setVisibility(View.GONE);
        main4.setVisibility(View.GONE);

        delay();
        tvs_networkworkerrorrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshWorkExperience();
            }
        });
        tvs_networkeducationerrorrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshEducation();
            }
        });
        txtaddwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), AddWorkExperience.class);
                startActivity(i);
            }
        });
        txtaddeduc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), AddEducationActivity.class);
                startActivity(i);
            }
        });
        txtintentresume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), UploadResumeActivity.class);
                i.putExtra("applicant_id", user_id);
                startActivity(i);
            }
        });
        txtviewresume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pesobalayan-ojfs.online/storage/resume/"+txtresume.getText().toString()));
                startActivity(browserIntent);
            }
        });
        StringRequest request = new StringRequest(Request.Method.GET, Constant.MY_POST+"?applicant_id="+user_id, response -> {
            try{
                JSONObject object= new JSONObject(response);
                if(object.getBoolean("success")){
                    JSONObject user = object.getJSONObject("user");
                    txtname.setText(user.get("name").toString());
                    txtemail.setText(user.get("email").toString());
                    txtcontactno.setText(user.get("contactno").toString());
                    val_contactno = txtcontactno.getText().toString();
                    txtaddress.setText(user.get("address").toString());
                    val_address = txtaddress.getText().toString();
                    txtspecialization.setText(user.get("Specialization").toString());
                    val_specialization = txtspecialization.getText().toString();
                    txtgender.setText(user.get("gender").toString());
                    val_gender = txtgender.getText().toString();
                    txtresume.setText(user.get("resume").toString());
                    val_resume = txtresume.getText().toString();
                    Picasso.get().load(Constant.URL+"/storage/profiles/"+user.getString("profile_pic")).into(imageview_user);
                    imgUrl = Constant.URL+"/storage/profiles/"+user.getString("profile_pic");
                    SharedPreferences.Editor editor2 = userPref2.edit();
                    editor2.putString("name",user.getString("name"));
                    editor2.putString("address",user.getString("address"));
                    editor2.putString("contactno",user.getString("contactno"));
                    //editor2.putString("email",user.getString("email"));
                   // editor2.putString("background",user.getString("background"));
                    editor2.apply();
                    editor2.commit();
                    if(user.get("contactno").toString().equals("null")|| user.get("address").toString().equals("null")|| user.get("Specialization").toString().equals("null")|| user.get("gender").toString().equals("null"))
                    {
                        txtcontactno.setVisibility(View.GONE);
                        txtaddress.setVisibility(View.GONE);
                        txtspecialization.setVisibility(View.GONE);
                        txtgender.setVisibility(View.GONE);
                    }
                    if(user.getString("profile_pic").equals("null"))
                    {

                        imageview_user.setBackgroundResource(R.drawable.img);

                    }
                    if(user.get("resume").toString().equals("null"))
                    {
                        txtresume.setVisibility(View.GONE);
                        txtviewresume.setVisibility(View.GONE);
                        ln_noresumelayout.setVisibility(View.VISIBLE);
                    }
                    else {
                        txtcontactno.setVisibility(View.VISIBLE);
                        txtaddress.setVisibility(View.VISIBLE);
                        txtspecialization.setVisibility(View.VISIBLE);
                        txtgender.setVisibility(View.VISIBLE);
                        txtresume.setVisibility(View.VISIBLE);
                        txtviewresume.setVisibility(View.VISIBLE);
                        ln_noresumelayout.setVisibility(View.GONE);
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
                txtcontactno.setVisibility(View.GONE);
                txtaddress.setVisibility(View.GONE);
                txtspecialization.setVisibility(View.GONE);
                txtgender.setVisibility(View.GONE);
                txtresume.setVisibility(View.GONE);
                txtviewresume.setVisibility(View.GONE);
                imageview_user.setBackgroundResource(R.drawable.img);
                txtname.setText(name2);
                txtemail.setText(email);
                //  progressDialog.cancel();
            }
        },error ->{
            error.printStackTrace();
            Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
            imageview_user.setBackgroundResource(R.drawable.img);
            txtname.setText(name2);
            txtemail.setText(email);
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

       editprofile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(getContext(), EditProfileActivity.class);
               i.putExtra("name",txtname.getText().toString());
               i.putExtra("email",txtemail.getText().toString());
               if(val_contactno.equals("null")||val_address.equals("null")||val_specialization.equals("null") ||val_gender.equals("null")  )
               {
                   i.putExtra("contactno","");
                   i.putExtra("address","");
                   i.putExtra("specialization","");
                   i.putExtra("gender","");
               }
               if(!val_contactno.equals("null")||!val_address.equals("null")||!val_specialization.equals("null") ||!val_gender.equals("null") )
               {
                   i.putExtra("contactno",txtcontactno.getText().toString());
                   i.putExtra("address",txtaddress.getText().toString());
                   i.putExtra("specialization",txtspecialization.getText().toString());
                   i.putExtra("gender",txtgender.getText().toString());
                   if (imageview_user.getDrawable() == null) {
                       i.putExtra("profile_pic", imgUrl);
                   }
                   else
                   {
                       i.putExtra("profile_pic", imgUrl);
                   }
               }
               startActivity(i);
           }
       });
       return root;
    }
    //reload data//
    @Override
    public void onResume() {
        super.onResume();
        delay();
        StringRequest request = new StringRequest(Request.Method.GET, Constant.MY_POST+"?applicant_id="+userPref2.getString("id","id"), response -> {
            try{
                JSONObject object= new JSONObject(response);
                if(object.getBoolean("success")){
                    JSONObject user = object.getJSONObject("user");
                    txtname.setText(user.get("name").toString());
                    txtemail.setText(user.get("email").toString());
                    txtcontactno.setText(user.get("contactno").toString());
                    val_contactno = txtcontactno.getText().toString();
                    txtaddress.setText(user.get("address").toString());
                    val_address = txtaddress.getText().toString();
                    txtspecialization.setText(user.get("Specialization").toString());
                    val_specialization = txtspecialization.getText().toString();
                    txtgender.setText(user.get("gender").toString());
                    val_gender = txtgender.getText().toString();
                    txtresume.setText(user.get("resume").toString());
                    val_resume = txtresume.getText().toString();
                    Picasso.get().load(Constant.URL+"/storage/profiles/"+user.getString("profile_pic")).into(imageview_user);
                    imgUrl = Constant.URL+"/storage/profiles/"+user.getString("profile_pic");
                    SharedPreferences.Editor editor2 = userPref2.edit();
                    editor2.putString("name",user.getString("name"));
                    editor2.putString("address",user.getString("address"));
                    editor2.putString("contactno",user.getString("contactno"));
                   // editor2.putString("email",user.getString("email"));
                    // editor2.putString("background",user.getString("background"));
                    editor2.apply();
                    editor2.commit();
                    if(user.get("contactno").toString().equals("null")|| user.get("address").toString().equals("null")|| user.get("Specialization").toString().equals("null")|| user.get("gender").toString().equals("null"))
                    {
                        txtcontactno.setVisibility(View.GONE);
                        txtaddress.setVisibility(View.GONE);
                        txtspecialization.setVisibility(View.GONE);
                        txtgender.setVisibility(View.GONE);
                    }
                    else {
                        txtcontactno.setVisibility(View.VISIBLE);
                        txtaddress.setVisibility(View.VISIBLE);
                        txtspecialization.setVisibility(View.VISIBLE);
                        txtgender.setVisibility(View.VISIBLE);
                        if(user.get("resume").toString().equals("null"))
                        {
                            txtresume.setVisibility(View.GONE);
                            txtviewresume.setVisibility(View.GONE);
                            ln_noresumelayout.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            txtresume.setVisibility(View.VISIBLE);
                            txtviewresume.setVisibility(View.VISIBLE);
                            ln_noresumelayout.setVisibility(View.GONE);
                        }
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
                txtcontactno.setVisibility(View.GONE);
                txtaddress.setVisibility(View.GONE);
                txtspecialization.setVisibility(View.GONE);
                txtgender.setVisibility(View.GONE);
                txtresume.setVisibility(View.GONE);
                txtviewresume.setVisibility(View.GONE);
                txtname.setText(name2);
                txtemail.setText(email);
                //  progressDialog.cancel();
            }
        },error ->{
            error.printStackTrace();
            Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
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
        arraylist.clear();
        StringRequest request2 = new StringRequest(Request.Method.GET, Constant.geteducation+"?educational_id="+user_id, response ->{
            try{
                JSONObject object = new JSONObject(response);
                if(object.getBoolean("success"))
                {
                    JSONArray array = new JSONArray(object.getString("education"));
                    for(int i = 0; i < array.length(); i++)
                    {
                        JSONObject postObject = array.getJSONObject(i);
                        //JSONObject getpostObject = postObject.getJSONObject("jobposts");

                        educationalbackground educationalbackground2 = new educationalbackground();
                        educationalbackground2.setApplicantschool(postObject.getString("school"));
                        educationalbackground2.setApplicantcourse(postObject.getString("course"));
                        educationalbackground2.setApplicantyeargraduated(postObject.getString("year"));
                        educationalbackground2.setId(postObject.getString("id"));



                        arraylist.add(educationalbackground2);

                    }
                    educationalbackgroundadapter = new educationalbackgroundadapter(arraylist,getContext(),listener);
                    recyclerView.setAdapter(educationalbackgroundadapter);
                    educationalbackgroundadapter.notifyDataSetChanged();
                    if(arraylist.isEmpty())
                    {
                        ln_noeducationlayout.setVisibility(View.VISIBLE);
                        ln_networkerroreducation.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                    }
                    else
                    {
                        recyclerView.setVisibility(View.VISIBLE);
                        ln_networkerroreducation.setVisibility(View.GONE);
                        ln_noeducationlayout.setVisibility(View.GONE);
                    }

                }
                else {
                    networkeducationerror();
                    Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                    recyclerView.setVisibility(View.GONE);
                }
            }catch(JSONException e)
            {
                networkeducationerror();
                recyclerView.setVisibility(View.GONE);
                e.printStackTrace();
                Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
            }



        },error -> {
            error.printStackTrace();
            networkeducationerror();
            Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.GONE);
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue queue2 = Volley.newRequestQueue(getContext());
        queue2.add(request2);

        //workexperience
        arraylist2.clear();
        StringRequest request3 = new StringRequest(Request.Method.GET, Constant.getworkexperience+"?workexp_id="+user_id, response ->{
            try{
                JSONObject object = new JSONObject(response);
                if(object.getBoolean("success"))
                {
                    JSONArray array = new JSONArray(object.getString("workexperience"));
                    for(int i = 0; i < array.length(); i++)
                    {
                        JSONObject postObject = array.getJSONObject(i);
                        //JSONObject getpostObject = postObject.getJSONObject("jobposts");
                        workexperience workexperience2 = new workexperience();
                       // educationalbackground educationalbackground2 = new educationalbackground();
                        workexperience2.setApplicantworkposition(postObject.getString("position"));
                        workexperience2.setApplicantworkcompanyname(postObject.getString("name"));
                        workexperience2.setApplicantworkdate(postObject.getString("startenddate"));
                        workexperience2.setId(postObject.getString("id"));
                        workexperience2.setApplicantworkspecialization(postObject.getString("specialization"));



                        arraylist2.add(workexperience2);

                    }
                    workexperienceadapter2 = new workexperienceadapter(arraylist2,getContext(),worklistener);
                    recyclerView2.setAdapter(workexperienceadapter2);
                    workexperienceadapter2.notifyDataSetChanged();
                    if(arraylist2.isEmpty())
                    {
                        ln_noworkexperiencelayout.setVisibility(View.VISIBLE);
                        ln_networkerrorworkexperience.setVisibility(View.GONE);
                        recyclerView2.setVisibility(View.GONE);
                    }
                    else
                    {
                        recyclerView2.setVisibility(View.VISIBLE);
                        ln_networkerrorworkexperience.setVisibility(View.GONE);
                        ln_noworkexperiencelayout.setVisibility(View.GONE);
                    }

                }
                else {
                    Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                    recyclerView2.setVisibility(View.GONE);
                    networkworkexperienceerror();
                }
            }catch(JSONException e)
            {
                recyclerView2.setVisibility(View.GONE);
                e.printStackTrace();
                networkworkexperienceerror();
                Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
            }



        },error -> {
            error.printStackTrace();
            Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
            recyclerView2.setVisibility(View.GONE);
            networkworkexperienceerror();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue queue3 = Volley.newRequestQueue(getContext());
        queue3.add(request3);
    }

    private void setOnClickListener() {
        listener = new educationalbackgroundadapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getActivity(), UpdateEducationActivity.class);
                intent.putExtra("educ_id",arraylist.get(position).getId());
                intent.putExtra("intentschool",arraylist.get(position).getApplicantschool());
                intent.putExtra("intentcourse",arraylist.get(position).getApplicantcourse());
                intent.putExtra("intentyear",arraylist.get(position).getApplicantyeargraduated());
                startActivity(intent);
            }
        };
    }

    private void setOnClickListener2() {
        worklistener = new workexperienceadapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getActivity(), UpdateWorkExperienceActivity.class);
                intent.putExtra("work_id",arraylist2.get(position).getId());
                intent.putExtra("intentworkposition",arraylist2.get(position).getApplicantworkposition());
                intent.putExtra("intentworkcompanyname",arraylist2.get(position).getApplicantworkcompanyname());
                intent.putExtra("intentspecialization",arraylist2.get(position).getApplicantworkspecialization());
                String splitworkdate = arraylist2.get(position).getApplicantworkdate();
                String[] parts = splitworkdate.split(" - ");
                String part1 = parts[0];
                String part2 = parts[1];
                intent.putExtra("intentworkdate",part1);
                intent.putExtra("intentenddate",part2);
                startActivity(intent);
            }
        };
    }
    private void refreshEducation()
    {
        ln_networkerroreducation.setVisibility(View.GONE);

        arraylist.clear();
        StringRequest request2 = new StringRequest(Request.Method.GET, Constant.geteducation+"?educational_id="+user_id, response ->{
            try{
                JSONObject object = new JSONObject(response);
                if(object.getBoolean("success"))
                {
                    JSONArray array = new JSONArray(object.getString("education"));
                    for(int i = 0; i < array.length(); i++)
                    {
                        JSONObject postObject = array.getJSONObject(i);
                        //JSONObject getpostObject = postObject.getJSONObject("jobposts");

                        educationalbackground educationalbackground2 = new educationalbackground();
                        educationalbackground2.setApplicantschool(postObject.getString("school"));
                        educationalbackground2.setApplicantcourse(postObject.getString("course"));
                        educationalbackground2.setApplicantyeargraduated(postObject.getString("year"));
                        educationalbackground2.setId(postObject.getString("id"));



                        arraylist.add(educationalbackground2);

                    }
                    educationalbackgroundadapter = new educationalbackgroundadapter(arraylist,getContext(),listener);
                    recyclerView.setAdapter(educationalbackgroundadapter);
                    educationalbackgroundadapter.notifyDataSetChanged();
                    if(arraylist.isEmpty())
                    {
                        ln_noeducationlayout.setVisibility(View.VISIBLE);
                        ln_networkerroreducation.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                    }
                    else
                    {
                        recyclerView.setVisibility(View.VISIBLE);
                        ln_networkerroreducation.setVisibility(View.GONE);
                        ln_noeducationlayout.setVisibility(View.GONE);
                    }
                }
                else {
                    Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                    networkeducationerror();
                    recyclerView.setVisibility(View.GONE);
                }
            }catch(JSONException e)
            {
                networkeducationerror();
                recyclerView.setVisibility(View.GONE);
                e.printStackTrace();
                Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
            }



        },error -> {
            error.printStackTrace();
            Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
            networkeducationerror();
            recyclerView.setVisibility(View.GONE);
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue queue2 = Volley.newRequestQueue(getContext());
        queue2.add(request2);
    }
    private void refreshWorkExperience()
    {
        ln_networkerrorworkexperience.setVisibility(View.GONE);
        arraylist2.clear();
        StringRequest request3 = new StringRequest(Request.Method.GET, Constant.getworkexperience+"?workexp_id="+user_id, response ->{
            try{
                JSONObject object = new JSONObject(response);
                if(object.getBoolean("success"))
                {
                    JSONArray array = new JSONArray(object.getString("workexperience"));
                    for(int i = 0; i < array.length(); i++)
                    {
                        JSONObject postObject = array.getJSONObject(i);
                        //JSONObject getpostObject = postObject.getJSONObject("jobposts");
                        workexperience workexperience2 = new workexperience();
                        // educationalbackground educationalbackground2 = new educationalbackground();
                        workexperience2.setApplicantworkposition(postObject.getString("position"));
                        workexperience2.setApplicantworkcompanyname(postObject.getString("name"));
                        workexperience2.setApplicantworkdate(postObject.getString("startenddate"));
                        workexperience2.setId(postObject.getString("id"));
                        workexperience2.setApplicantworkspecialization(postObject.getString("specialization"));



                        arraylist2.add(workexperience2);

                    }
                    workexperienceadapter2 = new workexperienceadapter(arraylist2,getContext(),worklistener);
                    recyclerView2.setAdapter(workexperienceadapter2);
                    workexperienceadapter2.notifyDataSetChanged();
                    if(arraylist2.isEmpty())
                    {
                        ln_noworkexperiencelayout.setVisibility(View.VISIBLE);
                        ln_networkerrorworkexperience.setVisibility(View.GONE);
                        recyclerView2.setVisibility(View.GONE);
                    }
                    else
                    {
                        recyclerView2.setVisibility(View.VISIBLE);
                        ln_networkerrorworkexperience.setVisibility(View.GONE);
                        ln_noworkexperiencelayout.setVisibility(View.GONE);
                    }
                }
                else {
                    Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                    recyclerView2.setVisibility(View.GONE);
                    networkworkexperienceerror();
                }
            }catch(JSONException e)
            {
                recyclerView2.setVisibility(View.GONE);
                e.printStackTrace();
                Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                networkworkexperienceerror();
            }



        },error -> {
            error.printStackTrace();
            Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
            recyclerView2.setVisibility(View.GONE);
            networkworkexperienceerror();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue queue3 = Volley.newRequestQueue(getContext());
        queue3.add(request3);
    }
    private void networkworkexperienceerror()
    {
        ln_noworkexperiencelayout.setVisibility(View.GONE);
        ln_networkerrorworkexperience.setVisibility(View.VISIBLE);
    }
    private void networkeducationerror()
    {
        ln_noeducationlayout.setVisibility(View.GONE);
        ln_networkerroreducation.setVisibility(View.VISIBLE);
    }

    private void getData() {
//        StringRequest request = new StringRequest(Request.Method.GET, Constant.MY_POST+"?id="+userPref2.getString("id","id"), response -> {
//            try{
//                JSONObject object= new JSONObject(response);
//                if(object.getBoolean("success")){
//                    JSONObject user = object.getJSONObject("user");
//                    txtname.setText(user.get("name").toString());
//                    Picasso.get().load(Constant.URL+"/storage/profiles/"+user.getString("profile_pic")).into(imageview_user);
//                    SharedPreferences.Editor editor2 = userPref2.edit();
//                    editor2.putString("name",user.getString("name"));
//                    editor2.putString("address",user.getString("address"));
//                    editor2.putString("contactno",user.getString("contactno"));
//                    editor2.putString("email",user.getString("email"));
//                    editor2.putString("background",user.getString("background"));
//                    editor2.apply();
//                    editor2.commit();
//
//                }
//                else
//                {
//                    Toast.makeText(getContext(), "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
//                    // progressDialog.cancel();
//                }
//
//            }catch(JSONException e)
//            {
//                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                //  progressDialog.cancel();
//            }
//        },error ->{
//            error.printStackTrace();
//            Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
//            // progressDialog.cancel();
//        })
//        {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String,String> map = new HashMap<>();
//                //String token = userPref.getString("token","token");
//                map.put("Authorization","Bearer "+token);
//                return map;
//            }
//        };
//
//        RequestQueue queue = Volley.newRequestQueue(getContext());
//        queue.add(request);

//
//        StringRequest request2 = new StringRequest(Request.Method.GET, Constant.viewjob, response ->{
//            try{
//                JSONObject object = new JSONObject(response);
//                if(object.getBoolean("success"))
//                {
//                    JSONArray array = new JSONArray(object.getString("job"));
//                    for(int i = 0; i < array.length(); i++)
//                    {
//                        JSONObject postObject = array.getJSONObject(i);
//                        //JSONObject getpostObject = postObject.getJSONObject("jobposts");
//
//                        SharedPreferences.Editor editor = userPref.edit();
//                        editor.putString("logo",postObject.getString("logo"));
//                        editor.putString("jobtitle",postObject.getString("jobtitle"));
//                        // editor.putString("id",postObject.getString("id"));
//                        editor.putString("companyname",postObject.getString("companyname"));
//                        editor.putString("location",postObject.getString("location"));
//                        editor.putString("address",postObject.getString("address"));
//                        editor.putString("salary",postObject.getString("salary"));
//                        editor.putString("jobdescription",postObject.getString("jobdescription"));
//                        editor.apply();
//                        editor.commit();
//
//                        JobData jobData = new JobData();
//                        jobData.setJoblogo(postObject.getString("logo"));
//                        jobData.setJobtitle(postObject.getString("jobtitle"));
//                        jobData.setJobcompany(postObject.getString("companyname"));
//                        jobData.setJoblocation(postObject.getString("location"));
//                        jobData.setJobaddress(postObject.getString("address"));
//                        jobData.setJobsalary(postObject.getString("salary"));
//                        jobData.setJobdateposted(postObject.getString("created_at"));
//                        jobData.setJobdescription(postObject.getString("jobdescription"));
//
//                        arraylist.add(jobData);
//                    }
//
//                    jobadapter2 = new jobadapter(arraylist,getContext(),listener);
//                    recyclerview.setAdapter(jobadapter2);
//                }
//                else {
//                    Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
//                }
//            }catch(JSONException e)
//            {
//                e.printStackTrace();
//                Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//            refreshLayout.setRefreshing(false);
//
//        },error -> {
//            error.printStackTrace();
//            refreshLayout.setRefreshing(false);
//        }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String,String> map = new HashMap<>();
//                return map;
//            }
//        };
//
//        RequestQueue queue2 = Volley.newRequestQueue(getContext());
//        queue2.add(request2);

    }
    public void delay()
    {
        main.setVisibility(View.GONE);
        main2.setVisibility(View.GONE);
        main3.setVisibility(View.GONE);
        main4.setVisibility(View.GONE);
        CDT = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {
                ln_delay.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                ln_delay.setVisibility(View.GONE);
                main.setVisibility(View.VISIBLE);
                main2.setVisibility(View.VISIBLE);
                main3.setVisibility(View.VISIBLE);
                main4.setVisibility(View.VISIBLE);
            }
        }.start();

    }
}