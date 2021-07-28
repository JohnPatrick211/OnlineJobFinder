package com.example.onlinejobfinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.model.workexperience;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class viewworkexperienceadapter extends RecyclerView.Adapter<viewworkexperienceadapter.Viewholder> {


    public viewworkexperienceadapter(ArrayList<workexperience> workexperienceArrayList, Context context) {
        this.workexperienceArrayList = workexperienceArrayList;
        this.context = context;
    }

    private ArrayList<workexperience> workexperienceArrayList;
    private Context context;


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_view_viewworkexperience,parent,false);
        return new Viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        workexperience workexperience2 = workexperienceArrayList.get(position);
        //educationalbackground educationalbackground2 = educationalbackgroundArrayList.get(position);
        holder.txtview_position.setText(workexperience2.getApplicantworkposition());
        holder.txtview_companyname.setText(workexperience2.getApplicantworkcompanyname());
        holder.txtview_date.setText(workexperience2.getApplicantworkdate());
        workexperience2.getApplicantworkspecialization();
        workexperience2.getId();
    }

    @Override
    public int getItemCount() {
        return workexperienceArrayList.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder{

        TextView txtview_position,txtview_companyname,txtview_date;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtview_position = itemView.findViewById(R.id.textView_applicantworkposition);
            txtview_companyname = itemView.findViewById(R.id.textView_applicantworkcompanyname);
            txtview_date = itemView.findViewById(R.id.textView_applicantworkdate);



        }
    }


}
