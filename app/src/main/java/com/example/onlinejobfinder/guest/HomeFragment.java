package com.example.onlinejobfinder.guest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
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
import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    TextView txtview_name, errortext;
    String name2,specialization,user_id;
    LinearLayout errorlayout;


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
        txtview_name = view.findViewById(R.id.textView_Name);
        SharedPreferences prefs = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        errorlayout = view.findViewById(R.id.allerrorlayout);
        errortext = view.findViewById(R.id.allerrortext);
        errorlayout.setVisibility(View.GONE);
        name2 = prefs.getString("name","name");
        user_id = prefs.getString("id","id");
        //specialization = prefs.getString("specialization","specialization");
//        Bundle bundle = getActivity().getIntent().getExtras();
////        if (bundle != null) {
////            name2 = bundle.getString("name");
////
          txtview_name.setText(name2);
////        }

        StringRequest request = new StringRequest(Request.Method.POST, Constant.checkspecialization, response -> {
            try{
                JSONObject object= new JSONObject(response);
                if(object.getBoolean("success")){
                    errorlayout.setVisibility(View.GONE);
                }
                else
                {
                    errorlayout.setVisibility(View.VISIBLE);
                    errortext.setText("Please set your Specialization in your Profile");
                }

            }catch(JSONException e)
            {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        },error ->{
            error.printStackTrace();
            Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

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

       return view;
    }

    public void onResume() {
        super.onResume();
        StringRequest request = new StringRequest(Request.Method.POST, Constant.checkspecialization, response -> {
            try{
                JSONObject object= new JSONObject(response);
                if(object.getBoolean("success")){
                    errorlayout.setVisibility(View.GONE);
                    //errortext.setText("Please set your Specialization in your Profile");
                }
                else
                {
                    errorlayout.setVisibility(View.VISIBLE);
                    errortext.setText("Please set your Specialization in your Profile");
                }

            }catch(JSONException e)
            {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        },error ->{
            error.printStackTrace();
            Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

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
}