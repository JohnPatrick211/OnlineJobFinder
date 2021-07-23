package com.example.onlinejobfinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.model.appliedapplicants;
import com.example.onlinejobfinder.model.workexperience;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class appliedapplicantsadapter extends RecyclerView.Adapter<appliedapplicantsadapter.Viewholder> {


    public appliedapplicantsadapter(ArrayList<appliedapplicants> appliedapplicantsArrayList, Context context, RecyclerViewClickListener listener) {
        this.appliedapplicantsArrayList = appliedapplicantsArrayList;
        this.context = context;
        this.listener = listener;
    }

    private ArrayList<appliedapplicants> appliedapplicantsArrayList;
    private Context context;
    private RecyclerViewClickListener listener;


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_view_appliedapplicants,parent,false);
        return new Viewholder(view);
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        appliedapplicants appliedapplicants2 = appliedapplicantsArrayList.get(position);
        //educationalbackground educationalbackground2 = educationalbackgroundArrayList.get(position);
        holder.txtview_name.setText(appliedapplicants2.getName());
        holder.txtview_email.setText(appliedapplicants2.getEmail());
        holder.txtview_address.setText(appliedapplicants2.getAddress());
        holder.txtview_contactno.setText(appliedapplicants2.getContactno());
        holder.txtview_gender.setText(appliedapplicants2.getGender());
        holder.txtview_status.setText(appliedapplicants2.getStatus());
        appliedapplicants2.getId();
        appliedapplicants2.getJobapplyid();
        appliedapplicants2.getJobid();
        Picasso.get().load(Constant.URL+"/storage/profiles/"+appliedapplicants2.getProfilepic()).into(holder.profile_pic);
    }

    @Override
    public int getItemCount() {
        return appliedapplicantsArrayList.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtview_name,txtview_email,txtview_address,txtview_contactno,txtview_gender,txtview_status;
        ImageView profile_pic;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtview_name = itemView.findViewById(R.id.textView_applicantname);
            txtview_address = itemView.findViewById(R.id.textView_applicantaddress);
            txtview_contactno = itemView.findViewById(R.id.textView_applicantcontactno);
            txtview_gender = itemView.findViewById(R.id.textView_applicantgender);
            txtview_email = itemView.findViewById(R.id.textView_applicantemail);
            txtview_status = itemView.findViewById(R.id.textView_applicantstatus);
            profile_pic = itemView.findViewById(R.id.imageview_applicantprofilepic);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }


}
