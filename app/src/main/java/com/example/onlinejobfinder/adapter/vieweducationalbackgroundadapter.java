package com.example.onlinejobfinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.model.educationalbackground;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class vieweducationalbackgroundadapter extends RecyclerView.Adapter<vieweducationalbackgroundadapter.Viewholder> {


    public vieweducationalbackgroundadapter(ArrayList<educationalbackground> educationalbackgroundArrayList, Context context) {
        this.educationalbackgroundArrayList = educationalbackgroundArrayList;
        this.context = context;

    }

    private ArrayList<educationalbackground> educationalbackgroundArrayList;
    private Context context;



    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_view_vieweducationalbackground,parent,false);
        return new Viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        educationalbackground educationalbackground2 = educationalbackgroundArrayList.get(position);
        holder.txtview_school.setText(educationalbackground2.getApplicantschool());
        holder.txtview_course.setText(educationalbackground2.getApplicantcourse());
        holder.txtview_year.setText(educationalbackground2.getApplicantyeargraduated());
        educationalbackground2.getId();
    }

    @Override
    public int getItemCount() {
        return educationalbackgroundArrayList.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder{

        TextView txtview_school,txtview_course,txtview_year;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtview_school = itemView.findViewById(R.id.textView_applicantschool);
            txtview_course = itemView.findViewById(R.id.textView_applicantcourse);
            txtview_year = itemView.findViewById(R.id.textView_applicantyeargraduated);




        }
    }


}
