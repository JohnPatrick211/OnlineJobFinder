package com.example.onlinejobfinder.guest;

import android.animation.LayoutTransition;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.adapter.jobadapter;
import com.example.onlinejobfinder.applicant.ApplicantJobDescriptionActivity;
import com.example.onlinejobfinder.model.job;

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
 * Use the {@link GuestSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuestSearchFragment extends Fragment {

    RelativeLayout searchlayout;
    ImageView filterbutton;
    boolean expand = false;
    RecyclerView recyclerView;
    EditText edt_search;
    LinearLayout main;
    CountDownTimer CDT;
    View ln_delay;
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
    String catergoryString,yearString, approved;
    String [] specializationarray, locationarray;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GuestSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuestSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuestSearchFragment newInstance(String param1, String param2) {
        GuestSearchFragment fragment = new GuestSearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_guest_search, container, false);

        filterbutton = view.findViewById(R.id.filterbutton);
        searchlayout = view.findViewById(R.id.searchlayout);
        btnfilter = view.findViewById(R.id.btn_filter);
        tvsearchspecialization = view.findViewById(R.id.tv_searchspecialization);
        tvsearchlocation  = view.findViewById(R.id.tv_searchlocation);
        edt_search = view.findViewById(R.id.search);
        recyclerView = view.findViewById(R.id.recyclerview_jobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout = view.findViewById(R.id.swipe);
        arraylist = new ArrayList<>();
        arraylist2 = new ArrayList<>();
        category = new ArrayList<String>();
        location = new ArrayList<String>();
        approved = "Approved";
        refreshLayout.setRefreshing(true);
        ln_delay = view.findViewById(R.id.ln_delayloadinglayout);
        main = view.findViewById(R.id.bruh);
        main.setVisibility(View.GONE);
        setOnClickListener();
        delay();

        filterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!expand)
                {
                    ValueAnimator va = ValueAnimator.ofInt(100,250);
                    va.setDuration(400);
                    va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            Integer value = (Integer) valueAnimator.getAnimatedValue();
                            searchlayout.getLayoutParams().height = value.intValue();
                            searchlayout.requestLayout();
                        }
                    });
                    va.start();
                    expand = true;
                }
                else
                {
                    ValueAnimator va = ValueAnimator.ofInt(250,100);
                    va.setDuration(400);
                    va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            Integer value = (Integer) valueAnimator.getAnimatedValue();
                            searchlayout.getLayoutParams().height = value.intValue();
                            searchlayout.requestLayout();
                        }
                    });
                    va.start();
                    expand = false;
                }
            }
        });
        //getCategory();
        // getLocation();
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                jobadapter2.getFilter().filter(charSequence.toString().toLowerCase());
                tvsearchspecialization.setText("Specialization");
                tvsearchlocation.setText("Region");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        tvsearchspecialization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        getContext()
                );
                tvsearchspecialization.setText(specializationarray[position]);
                builder.setTitle("Select Specialization");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(specializationarray, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        position = i;
                        tvsearchspecialization.setText(specializationarray[i]);
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        tvsearchspecialization.setText("Specialization");
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j=0; j<selectedspecialization.length; j++)
                        {
                            selectedspecialization[j] = false;
                            tvsearchspecialization.setText("Specialization");
                        }
                    }
                });
                builder.show();
            }
        });
        tvsearchlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        getContext()
                );
                tvsearchlocation.setText(locationarray[position2]);
                builder.setTitle("Select Region");
                builder.setCancelable(false);
                builder.setSingleChoiceItems(locationarray, position2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        position2 = i;
                        tvsearchlocation.setText(locationarray[i]);
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        tvsearchlocation.setText("Region");
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j=0; j<selectedlocation.length; j++)
                        {
                            selectedlocation[j] = false;
                            // Specialization.clear();
                            tvsearchlocation.setText("Region");
                        }
                    }
                });
                builder.show();
            }
        });
        btnfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                catergoryString = tvsearchspecialization.getText().toString();
                yearString = tvsearchlocation.getText().toString();
                ArrayList<job> w = new ArrayList<>();
               List<String> al = new ArrayList<String>();
                if(catergoryString.equals("Specialization") && yearString.equals("Region"))
                {

                    w.addAll(arraylist);
                }
                else
                {
                    for(job details : arraylist)
                    {
                        if(catergoryString.equals("Specialization") && !TextUtils.isEmpty(yearString))
                        {
                            if(details.getJoblocation().contains(yearString))
                            {
                                w.add(details);
                            }
                        }
                        else if(yearString.equals("Region") && !TextUtils.isEmpty(catergoryString))
                        {
                            if(details.getJobcategory().contains(catergoryString))
                            {
                                w.add(details);
                            }
                        }
                        else
                        {
                            if(details.getJobcategory().contains(catergoryString) && details.getJoblocation().contains(yearString))
                            {
                                w.add(details);
                            }
                        }

                    }
                }
                jobadapter2.setWinnerDetails(w);
            }
        });
        getPost();
        SharedPreferences sharedPreferences = getContext().getApplicationContext().getSharedPreferences("jobpost", Context.MODE_PRIVATE);
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
                getPost();
            }
        });

        return view;
    }

    private void getCategory() {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.categoryfilter, response ->{
            JSONObject j = null;
            try{
                j = new JSONObject(response);
                result = j.getJSONArray("categories");
                getSubCategory(result);


            }catch(JSONException e)
            {
                e.printStackTrace();
                Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
            }

            refreshLayout.setRefreshing(false);

        },error -> {
            error.printStackTrace();
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
    }

    private void getSubCategory(JSONArray j) {
        for(int ai=0;ai<j.length();ai++)
        {
            try{
                JSONObject json = j.getJSONObject(ai);
                category.add(json.getString("category"));
            }catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        specializationarray = category.toArray(new String[0]);
        //Toast.makeText(AddWorkExperience.this, specializationarray[ai], Toast.LENGTH_SHORT).show();
        selectedspecialization = new boolean[specializationarray.length];
//        spinnercategory.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, category));
    }

    private void getLocation() {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.locationfilter, response ->{
            JSONObject j = null;
            try{
                j = new JSONObject(response);
                result2 = j.getJSONArray("locations");
                getSubLocation(result2);


            }catch(JSONException e)
            {
                e.printStackTrace();
                Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
            }

            refreshLayout.setRefreshing(false);
            recyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });

        },error -> {
            error.printStackTrace();
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
    }

    private void getSubLocation(JSONArray j) {
        for(int ai=0;ai<j.length();ai++)
        {
            try{
                JSONObject json = j.getJSONObject(ai);
                location.add(json.getString("region"));
            }catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        locationarray = location.toArray(new String[0]);
        //Toast.makeText(AddWorkExperience.this, specializationarray[ai], Toast.LENGTH_SHORT).show();
        selectedlocation = new boolean[locationarray.length];
//        spinnercategory.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, category));
    }

    private void getPost() {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.jobposts+"?jobstatus="+approved, response ->{
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
                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    safefilter();
                }
                else {
                    Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
                }
            }catch(JSONException e)
            {
                e.printStackTrace();
                Toast.makeText(getContext(),"Network Error, Please Try Again",Toast.LENGTH_SHORT).show();
            }

            refreshLayout.setRefreshing(false);

        },error -> {
            error.printStackTrace();
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
    }
    public void onResume() {
        super.onResume();
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        delay();
        getCategory();
        getLocation();
//        getPost();
//        arraylist.clear();
        category.clear();
        location.clear();
    }

    private void setOnClickListener() {
        listener = new jobadapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getActivity(), GuestJobDescriptionActivity.class);
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
    private void safefilter()
    {
        catergoryString = tvsearchspecialization.getText().toString();
        yearString = tvsearchlocation.getText().toString();
        ArrayList<job> w = new ArrayList<>();
        if(catergoryString.equals("Specialization") && yearString.equals("Region"))
        {

            w.addAll(arraylist);
        }
        else
        {
            for(job details : arraylist)
            {
                if(catergoryString.equals("Specialization") && !TextUtils.isEmpty(yearString))
                {
                    if(details.getJoblocation().contains(yearString))
                    {
                        w.add(details);
                    }
                }
                else if(yearString.equals("Region") && !TextUtils.isEmpty(catergoryString))
                {
                    if(details.getJobcategory().contains(catergoryString))
                    {
                        w.add(details);
                    }
                }
                else
                {
                    if(details.getJobcategory().contains(catergoryString) && details.getJoblocation().contains(yearString))
                    {
                        w.add(details);
                    }
                }

            }
        }
        jobadapter2.setWinnerDetails(w);
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
