package com.example.onlinejobfinder.guest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import de.hdodenhof.circleimageview.CircleImageView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.onlinejobfinder.adapter.jobadapter;
import com.example.onlinejobfinder.model.job;
import com.squareup.picasso.Picasso;

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


    TextView txtmanageaccount, txtname;
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
       txtmanageaccount = root.findViewById(R.id.txt_manageaccountprofile);
        txtname = root.findViewById(R.id.textView_NameProfile);
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
        user_id = userPref2.getString("id","id");
        token = userPref2.getString("token","token");
        permaid = user_id;
        Toast.makeText(getContext(), user_id, Toast.LENGTH_SHORT).show();
        StringRequest request = new StringRequest(Request.Method.GET, Constant.MY_POST+"?id="+userPref2.getString("id","id"), response -> {
            try{
                JSONObject object= new JSONObject(response);
                if(object.getBoolean("success")){
                    JSONObject user = object.getJSONObject("user");
                    txtname.setText(user.get("name").toString());
                    Picasso.get().load(Constant.URL+"/storage/profiles/"+user.getString("profile_pic")).into(imageview_user);
                    SharedPreferences.Editor editor2 = userPref2.edit();
                    editor2.putString("name",user.getString("name"));
                    editor2.putString("address",user.getString("address"));
                    editor2.putString("contactno",user.getString("contactno"));
                    editor2.putString("email",user.getString("email"));
                   // editor2.putString("background",user.getString("background"));
                    editor2.apply();
                    editor2.commit();

                }
                else
                {
                    Toast.makeText(getContext(), "Error Occurred, Please try again", Toast.LENGTH_SHORT).show();
                    // progressDialog.cancel();
                }

            }catch(JSONException e)
            {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                //  progressDialog.cancel();
            }
        },error ->{
            error.printStackTrace();
            Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
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
        // Toast.makeText(getContext(), user_id, Toast.LENGTH_SHORT).show();
//        editor.putString("name",name2);
     //   editor.putString("id1",user_id);
     //   editor.apply();
     //   editor.commit();
       // getData();
      //  setOnClickListener();
       // refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
       //     @Override
      //      public void onRefresh() {
               // arraylist.clear();
     //           getData();
     //       }
     //   });

       txtmanageaccount.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(getContext(), EditProfileActivity.class);
               startActivity(i);
           }
       });
       return root;
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
}