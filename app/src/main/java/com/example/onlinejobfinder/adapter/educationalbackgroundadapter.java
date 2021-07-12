package com.example.onlinejobfinder.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.applicant.AddEducationActivity;
import com.example.onlinejobfinder.applicant.UpdateEducationActivity;
import com.example.onlinejobfinder.model.educationalbackground;
import com.example.onlinejobfinder.model.job;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class educationalbackgroundadapter extends RecyclerView.Adapter<educationalbackgroundadapter.Viewholder> {


    public educationalbackgroundadapter(ArrayList<educationalbackground> educationalbackgroundArrayList, Context context, RecyclerViewClickListener listener) {
        this.educationalbackgroundArrayList = educationalbackgroundArrayList;
        this.context = context;
        this.listener = listener;
    }

    private ArrayList<educationalbackground> educationalbackgroundArrayList;
    private Context context;
    private RecyclerViewClickListener listener;


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_view_educationalbackground,parent,false);
        return new Viewholder(view);
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
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


    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtview_school,txtview_course,txtview_year;
        ImageView editeduc;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtview_school = itemView.findViewById(R.id.textView_applicantschool);
            txtview_course = itemView.findViewById(R.id.textView_applicantcourse);
            txtview_year = itemView.findViewById(R.id.textView_applicantyeargraduated);
            editeduc = itemView.findViewById(R.id.image_editeducation);

            editeduc.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }


}
