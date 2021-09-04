package com.example.onlinejobfinder.employer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinejobfinder.CheckInternet;
import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.adapter.appliedapplicantsadapter;
import com.example.onlinejobfinder.adapter.recommendedapplicantsadapter;
import com.example.onlinejobfinder.applicant.ApplicantJobDescriptionActivity;
import com.example.onlinejobfinder.model.appliedapplicants;
import com.example.onlinejobfinder.model.job;
import com.example.onlinejobfinder.model.recommendedapplicants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployerHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployerHomeFragment extends Fragment {

    TextView tv_networkerrorrefresh;
    LinearLayout ln_networkrecommendedapperror;
    View ln_norecommendedlayout;
    RecyclerView recyclerView;
    View ln_delay;
    LinearLayout main;
    CountDownTimer CDT;
    SharedPreferences userPref2;
    recommendedapplicantsadapter.RecyclerViewClickListener listener;
    int position =0;
    int position2 =0;
    boolean[] selectedspecialization, selectedlocation;
    ArrayList<Integer> Specialization = new ArrayList<>();
    TextView btnfilter,tvsearchspecialization,tvsearchlocation;
    ArrayList<recommendedapplicants> arraylist;
    ArrayList<recommendedapplicants> arraylist2;
    ArrayList<String> category,location;
    ArrayList<recommendedapplicants> w = new ArrayList<>();
    JSONArray result,result2;
    SwipeRefreshLayout refreshLayout;
    recommendedapplicantsadapter recommendedapplicantsadapter2;
    // Spinner spinnercategory, spinnerlocation;
    String catergoryString,yearString, approved,id;
    String [] specializationarray, locationarray;
    String name2, user_id,token,email;
    String val_contactno = "";
    String val_specialization = "";
    String intentcompanyname, intentcompanyoverview, intentaddress, intentemail, intentcompanylogo,intentcompanylogo2 ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EmployerHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmployerHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmployerHomeFragment newInstance(String param1, String param2) {
        EmployerHomeFragment fragment = new EmployerHomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_employer_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_recommendedapplicants);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout = view.findViewById(R.id.recommendedswipe);
//        spinnercategory = view.findViewById(R.id.spinner_category);
        //       spinnerlocation = view.findViewById(R.id.spinner_location);
        ln_norecommendedlayout = view.findViewById(R.id.ln_norecommendedlayout);
        arraylist = new ArrayList<>();
        arraylist2 = new ArrayList<>();
        category = new ArrayList<String>();
        location = new ArrayList<String>();
        userPref2 = getContext().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        name2 = userPref2.getString("name","name");
        email = userPref2.getString("email","email");
        user_id = userPref2.getString("id","id");
        token = userPref2.getString("token","token");
        tv_networkerrorrefresh = view.findViewById(R.id.tv_networkrecommendedapperrorrefresh);
        ln_networkrecommendedapperror = view.findViewById(R.id.networkerecommendedapperrorlayout);
        ln_networkrecommendedapperror.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        ln_delay = view.findViewById(R.id.ln_delayloadinglayout);
        main = view.findViewById(R.id.bruh);
        main.setVisibility(View.GONE);
        delay();
        tv_networkerrorrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshLayout.setRefreshing(true);
                getEmployer();
            }
        });
     //   val_specialization = userPref2.getString("Specialization","Specialization");
 //       id = getIntent().getExtras().getString("intentid");
  //      approved = "Approved";

//        category.add("Category");
//        category.add("Accountant");
//        category.add("Programmer");
        //  location.add("Region");
        //  location.add("Region 4-A");
        //  location.add("Region 3");
        //  spinnerlocation.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, location));
        refreshLayout.setRefreshing(true);
        setOnClickListener();
        //getCategory();
        // getLocation();
//        filter();
       // getPost();
        //getEmployer();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
                arraylist.clear();
                w.clear();
                getPost();
            }
        });

        return view;
    }

    private void getPost() {
        if(new CheckInternet().checkInternet(getContext()))
        {
//            if(user.get("Specialization").toString().isEmpty())
//            {
//                recyclerView.setVisibility(View.GONE);
////                            ln_networkrecommendedapperror.setVisibility(View.VISIBLE);
//                ln_norecommendedlayout.setVisibility(View.VISIBLE);
//                refreshLayout.setRefreshing(false);
//            }
//            else
//            {
                StringRequest request = new StringRequest(Request.Method.GET, Constant.recommendedapplicants+"?Specialization="+ val_specialization, response ->{
                    try{
                        JSONObject object = new JSONObject(response);
                        if(object.getBoolean("success"))
                        {
                            JSONArray array = new JSONArray(object.getString("jobpost"));
                            for(int i = 0; i < array.length(); i++)
                            {
                                JSONObject postObject = array.getJSONObject(i);
                                //JSONObject getpostObject = postObject.getJSONObject("jobposts");

                                recommendedapplicants recommendedapplicants2 = new recommendedapplicants();
                                recommendedapplicants2.setId(postObject.getString("id"));
                                recommendedapplicants2.setApplicant_id(postObject.getString("applicant_id"));
                                recommendedapplicants2.setProfile_pic(postObject.getString("profile_pic"));
                                recommendedapplicants2.setName(postObject.getString("name"));
                                recommendedapplicants2.setEmail(postObject.getString("email"));
                                recommendedapplicants2.setAddress(postObject.getString("address"));
                                recommendedapplicants2.setContactno(postObject.getString("contactno"));
                                recommendedapplicants2.setSpecialization(postObject.getString("Specialization"));
                                recommendedapplicants2.setGender(postObject.getString("gender"));
                                recommendedapplicants2.setWorkexp_id(postObject.getString("workexp_id"));
                                recommendedapplicants2.setEducational_id(postObject.getString("educational_id"));
                                arraylist.add(recommendedapplicants2);
//                        filter();
                                // ArrayList<recommendedapplicants> w = new ArrayList<>();

//                        recommendedapplicantsadapter2.setWinnerDetails(w);
                            }
                            recommendedapplicantsadapter2 = new recommendedapplicantsadapter(arraylist,getContext(),listener);
                            recyclerView.setAdapter(recommendedapplicantsadapter2);
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    return false;
                                }
                            });
                        }
                        else {
                            Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                            recyclerView.setVisibility(View.GONE);
                            ln_networkrecommendedapperror.setVisibility(View.VISIBLE);
                            refreshLayout.setRefreshing(false);
                        }
                    }catch(JSONException e)
                    {
                        e.printStackTrace();
                        Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                        recyclerView.setVisibility(View.GONE);
                        //ln_networkrecommendedapperror.setVisibility(View.VISIBLE);
                        ln_norecommendedlayout.setVisibility(View.VISIBLE);
                        refreshLayout.setRefreshing(false);
                    }

                    refreshLayout.setRefreshing(false);

                },error -> {
                    error.printStackTrace();
                    refreshLayout.setRefreshing(false);
                    recyclerView.setVisibility(View.GONE);
//                ln_networkrecommendedapperror.setVisibility(View.VISIBLE);
                    ln_norecommendedlayout.setVisibility(View.VISIBLE);
                    refreshLayout.setRefreshing(false);
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String,String> map = new HashMap<>();
                        return map;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(request);
//            }
        }
        else
        {
            refreshLayout.setRefreshing(false);
            recyclerView.setVisibility(View.GONE);
            ln_norecommendedlayout.setVisibility(View.GONE);
            ln_networkrecommendedapperror.setVisibility(View.VISIBLE);
            refreshLayout.setRefreshing(false);
        }
    }
    public void onResume() {
        super.onResume();
        delay();
        getEmployer();
       // val_specialization = userPref2.getString("Specialization","Specialization");

//        filter();
        refreshLayout.setRefreshing(true);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

    }

    private void setOnClickListener() {
        listener = new recommendedapplicantsadapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getContext(), ViewRecommendedApplicantProfileActivity.class);
                intent.putExtra("intentapplicant_id",arraylist.get(position).getApplicant_id());
//                intent.putExtra("intentid",arraylist.get(position).getJobuniqueid());
//                intent.putExtra("intentjoblogo",Constant.URL+"/storage/profiles/"+arraylist.get(position).getJoblogo());
//                intent.putExtra("intentjobtitle",arraylist.get(position).getJobtitle());
//                intent.putExtra("intentjobcompany",arraylist.get(position).getJobcompany());
//                intent.putExtra("intentjobdescription",arraylist.get(position).getJobdescription());
//                intent.putExtra("intentjobcompanyoverview",arraylist.get(position).getCompanyoverview());
//                intent.putExtra("intentjoblocation",arraylist.get(position).getJoblocation());
//                intent.putExtra("intentjobaddress",arraylist.get(position).getJobaddress());
//                intent.putExtra("intentjobspecialization",arraylist.get(position).getJobcategory());
//                intent.putExtra("intentjobsalary",arraylist.get(position).getJobsalary());
//                intent.putExtra("intentjobposted",arraylist.get(position).getJobdateposted());
//                intent.putExtra("intentjobstatus",arraylist.get(position).getJobstatus());
//                intent.putExtra("jobcompanylogo2",arraylist.get(position).getJoblogo());
                //Toast.makeText(getContext(),"Success",Toast.LENGTH_SHORT).show();
               startActivity(intent);
            }
        };
    }
    private void getEmployer()
    {
        ln_networkrecommendedapperror.setVisibility(View.GONE);
        if(new CheckInternet().checkInternet(getContext()))
        {
            StringRequest request = new StringRequest(Request.Method.GET, Constant.EMPLOYER_POST+"?employer_id="+user_id, response -> {
                try{
                    JSONObject object= new JSONObject(response);
                    if(object.getBoolean("success")){
                        JSONObject user = object.getJSONObject("user");
                        val_contactno = user.get("contactno").toString();
                        val_specialization =  user.get("Specialization").toString();
                        val_specialization = val_specialization.replace("&","%26");
//                        Toast.makeText(getContext(),val_contactno,Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(),val_specialization,Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getContext(),"success",Toast.LENGTH_SHORT).show();
                        intentcompanyname = user.get("name").toString();
                        intentemail = user.get("email").toString();
                        intentaddress = user.get("address").toString();
                        intentcompanyoverview = user.get("companyoverview").toString();
                        intentcompanylogo = Constant.URL+"/storage/profiles/"+user.getString("profile_pic");
                        intentcompanylogo2 = user.getString("profile_pic");
                        SharedPreferences.Editor editor2 = userPref2.edit();
                        editor2.putString("name",user.getString("name"));
                        editor2.putString("address",user.getString("address"));
                        editor2.putString("contactno",user.getString("contactno"));
                        //editor2.putString("email",user.getString("email"));
                        // editor2.putString("background",user.getString("background"));
                        editor2.apply();
                        editor2.commit();
                        recyclerView.setVisibility(View.VISIBLE);
                        arraylist.clear();
                        w.clear();
                        getPost();
                        if(user.get("Specialization").toString().equals("null"))
                        {
                            recyclerView.setVisibility(View.GONE);
//                            ln_networkrecommendedapperror.setVisibility(View.VISIBLE);
                            ln_norecommendedlayout.setVisibility(View.VISIBLE);
                            refreshLayout.setRefreshing(false);

                        }
                        else {
                            ln_norecommendedlayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);

                            // txtemployerspecialization.setVisibility(View.VISIBLE);
                        }
                        refreshLayout.setRefreshing(false);

                    }
                    else
                    {
                        Toast.makeText(getContext(), "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                        // progressDialog.cancel();
                        recyclerView.setVisibility(View.GONE);
                        ln_networkrecommendedapperror.setVisibility(View.VISIBLE);
                        refreshLayout.setRefreshing(false);
                    }

                }catch(JSONException e)
                {
                    //Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
//                txtemployercontactno.setVisibility(View.GONE);
//                txtemployeraddress.setVisibility(View.GONE);
//                txtemployerspecialization.setVisibility(View.GONE);
//                txtemployername.setText(name2);
//                txtemployeremail.setText(email);
                    //  progressDialog.cancel();
                    recyclerView.setVisibility(View.GONE);
                    ln_networkrecommendedapperror.setVisibility(View.VISIBLE);
                    ln_norecommendedlayout.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);
                }
            },error ->{
                error.printStackTrace();
                Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.GONE);
                ln_networkrecommendedapperror.setVisibility(View.VISIBLE);
                refreshLayout.setRefreshing(false);
                ln_norecommendedlayout.setVisibility(View.GONE);
//            txtemployername.setText(name2);
//            txtemployeremail.setText(email);
//            txtemployercompanyoverview.setText("network error in loading of content");
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
        else
        {
            recyclerView.setVisibility(View.GONE);
            ln_networkrecommendedapperror.setVisibility(View.VISIBLE);
            refreshLayout.setRefreshing(false);
            ln_norecommendedlayout.setVisibility(View.GONE);
        }

    }
    public void delay()
    {
        main.setVisibility(View.GONE);
        CDT = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) {
                ln_delay.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                ln_delay.setVisibility(View.GONE);
                main.setVisibility(View.VISIBLE);

            }
        }.start();

    }
//    public void filter(){
//        for(recommendedapplicants details : arraylist) {
//            if(!details.getAddress().contains("null") && !details.getContactno().contains("null") && !details.getWorkexp_id().contains("null") && !details.getEducational_id().contains("null") && !TextUtils.isEmpty(val_specialization))
//            {
//                if(details.getSpecialization().contains(val_specialization)) {
//                    w.add(details);
//                    arraylist.clear();
//                    Toast.makeText(getContext(), "checked1", Toast.LENGTH_SHORT).show();
//                }
//            }
//            else
//            {
//                if(details.getSpecialization().contains(val_specialization))
//                {
//                    w.add(details);
//                    arraylist.clear();
//                    Toast.makeText(getContext(),"checked2",Toast.LENGTH_SHORT).show();
//                }
//            }
//
//
//        }
//    }
}