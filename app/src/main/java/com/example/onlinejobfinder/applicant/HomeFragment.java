package com.example.onlinejobfinder.applicant;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.example.onlinejobfinder.adapter.jobadapter;
import com.example.onlinejobfinder.model.job;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    TextView errortext,tv_networkerrorrefresh;
    String name2,specialization,user_id;
    LinearLayout errorlayout,ln_networkrecommendedjoberror;
    RecyclerView recyclerView;
    jobadapter.RecyclerViewClickListener listener;
    int position =0;
    int position2 =0;
    boolean[] selectedspecialization, selectedlocation;
    ArrayList<Integer> Specialization = new ArrayList<>();
    TextView btnfilter,tvsearchspecialization,tvsearchlocation;
    ArrayList<job> arraylist;
    ArrayList<job> arraylist2;
    ArrayList<String> category,location;
    JSONArray result,result2;
    SwipeRefreshLayout refreshLayout;
    jobadapter jobadapter2;
    // Spinner spinnercategory, spinnerlocation;
    String catergoryString,yearString, approved;
    String [] specializationarray, locationarray;
    String val_specialization = "";
    View ln_delay;
    LinearLayout main;
    CountDownTimer CDT;
//    ProgressBar loader;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
       View view = inflater.inflate(R.layout.fragment_home, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        errorlayout = view.findViewById(R.id.allerrorlayout);
        errortext = view.findViewById(R.id.allerrortext);
        errorlayout.setVisibility(View.GONE);
        name2 = prefs.getString("name","name");
        user_id = prefs.getString("id","id");
        recyclerView = view.findViewById(R.id.recyclerview_recommendedjobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout = view.findViewById(R.id.recommendedjobswipe);
//        spinnercategory = view.findViewById(R.id.spinner_category);
        //       spinnerlocation = view.findViewById(R.id.spinner_location);
        arraylist = new ArrayList<>();
        arraylist2 = new ArrayList<>();
        tv_networkerrorrefresh = view.findViewById(R.id.tv_networkrecommendedjoberrorrefresh);
        ln_networkrecommendedjoberror = view.findViewById(R.id.networkerecommendedjoberrorlayout);
        ln_networkrecommendedjoberror.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        ln_delay = view.findViewById(R.id.ln_delayloadinglayout);
        main = view.findViewById(R.id.bruh);
        main.setVisibility(View.GONE);
        delay();
        tv_networkerrorrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckSpecialization();
            }
        });
        refreshLayout.setRefreshing(true);
        setOnClickListener();
        //specialization = prefs.getString("specialization","specialization");
       //CheckSpecialization();

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
                CheckSpecialization();
            }
        });


       return view;
    }

    private void getPost() {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.recommendedjobpost+"?category="+val_specialization, response ->{
            try{
                JSONObject object = new JSONObject(response);
                if(object.getBoolean("success"))
                {
                    JSONArray array = new JSONArray(object.getString("jobpost"));
                    for(int i = 0; i < array.length(); i++)
                    {
                        JSONObject postObject = array.getJSONObject(i);
                        //JSONObject getpostObject = postObject.getJSONObject("jobposts");

                        job job2 = new job();
                        job2.setJoblogo(postObject.getString("logo"));
                        job2.setJobtitle(postObject.getString("jobtitle"));
                        job2.setJobcompany(postObject.getString("companyname"));
                        job2.setJoblocation(postObject.getString("location"));
                        job2.setJobsalary(postObject.getString("salary"));
                        job2.setJobdateposted(postObject.getString("created_at"));
                        job2.setJobaddress(postObject.getString("address"));
                        job2.setCompanyoverview(postObject.getString("companyoverview"));
                        job2.setJobcategory(postObject.getString("category"));
                        job2.setJobid(postObject.getString("job_id"));
                        job2.setJobdescription(postObject.getString("jobdescription"));
                        job2.setJobuniqueid(postObject.getString("id"));
                        job2.setJobstatus(postObject.getString("jobstatus"));

                        arraylist.add(job2);
                        arraylist2.add(job2);
                    }
                    jobadapter2 = new jobadapter(arraylist,getContext(),listener);
                    recyclerView.setAdapter(jobadapter2);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                }
                else {
                    Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
                    recyclerView.setVisibility(View.GONE);
                    ln_networkrecommendedjoberror.setVisibility(View.VISIBLE);
                }
            }catch(JSONException e)
            {
                e.printStackTrace();
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.GONE);
                ln_networkrecommendedjoberror.setVisibility(View.VISIBLE);
            }

            refreshLayout.setRefreshing(false);

        },error -> {
            error.printStackTrace();
            refreshLayout.setRefreshing(false);
            recyclerView.setVisibility(View.GONE);
            ln_networkrecommendedjoberror.setVisibility(View.VISIBLE);
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }


    public void onResume() {
        super.onResume();
        delay();
        refreshLayout.setRefreshing(true);
        arraylist.clear();
        CheckSpecialization();

    }


    private void CheckSpecialization() {
        ln_networkrecommendedjoberror.setVisibility(View.GONE);
        refreshLayout.setRefreshing(true);
        if(new CheckInternet().checkInternet(getContext()))
        {
            StringRequest request = new StringRequest(Request.Method.POST, Constant.checkspecialization, response -> {
                try{
                    JSONObject object= new JSONObject(response);
                    if(object.getBoolean("success")){
                        errorlayout.setVisibility(View.GONE);
                        JSONObject user = object.getJSONObject("Specialization");
                        val_specialization =  user.get("Specialization").toString();
                        getPost();
                    }
                    else
                    {
                        errorlayout.setVisibility(View.VISIBLE);
                        errortext.setText("Please set your Specialization in your Profile");
                        refreshLayout.setRefreshing(false);
                    }

                }catch(JSONException e)
                {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    refreshLayout.setRefreshing(false);
                }
            },error ->{
                error.printStackTrace();
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);

            })
            {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> map = new HashMap<>();
                    map.put("applicant_id",user_id);
                    return map;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(request);
        }
        else
        {
            recyclerView.setVisibility(View.GONE);
            ln_networkrecommendedjoberror.setVisibility(View.VISIBLE);
            refreshLayout.setRefreshing(false);
        }
    }

    private void setOnClickListener() {
        listener = new jobadapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getActivity(), ApplicantJobDescriptionActivity.class);
                intent.putExtra("intentjob_id",arraylist.get(position).getJobid());
                intent.putExtra("intentid",arraylist.get(position).getJobuniqueid());
                intent.putExtra("intentjoblogo",Constant.URL+"/storage/profiles/"+arraylist.get(position).getJoblogo());
                intent.putExtra("intentjobtitle",arraylist.get(position).getJobtitle());
                intent.putExtra("intentjobcompany",arraylist.get(position).getJobcompany());
                intent.putExtra("intentjobdescription",arraylist.get(position).getJobdescription());
                intent.putExtra("intentjobcompanyoverview",arraylist.get(position).getCompanyoverview());
                intent.putExtra("intentjoblocation",arraylist.get(position).getJoblocation());
                intent.putExtra("intentjobaddress",arraylist.get(position).getJobaddress());
                intent.putExtra("intentjobspecialization",arraylist.get(position).getJobcategory());
                intent.putExtra("intentjobsalary",arraylist.get(position).getJobsalary());
                intent.putExtra("intentjobposted",arraylist.get(position).getJobdateposted());
                intent.putExtra("intentjobstatus",arraylist.get(position).getJobstatus());
                intent.putExtra("jobcompanylogo2",arraylist.get(position).getJoblogo());
                startActivity(intent);
            }
        };
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

}