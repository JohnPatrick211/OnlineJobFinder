package com.example.onlinejobfinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.model.appliedapplicants;
import com.example.onlinejobfinder.model.job;
import com.example.onlinejobfinder.model.recommendedapplicants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recommendedapplicantsadapter extends RecyclerView.Adapter<recommendedapplicantsadapter.Viewholder> implements Filterable {


    public recommendedapplicantsadapter(ArrayList<recommendedapplicants> recommendedapplicantsArrayList, Context context, RecyclerViewClickListener listener) {
        this.recommendedapplicantsArrayList = recommendedapplicantsArrayList;
        this.context = context;
        this.listener = listener;
        categorysearch = new ArrayList<>(recommendedapplicantsArrayList);
    }

    private ArrayList<recommendedapplicants> recommendedapplicantsArrayList;
    private Context context;
    private RecyclerViewClickListener listener;
    private List<recommendedapplicants> categorysearch;


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_view_recommendedapplicants,parent,false);
        return new Viewholder(view);
    }

    @Override
    public Filter getFilter() {
        return categoryfilter;
    }

    public Filter categoryfilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<recommendedapplicants> filterList = new ArrayList<>();
            if (charSequence == null || charSequence.length() ==0){
                filterList.addAll(categorysearch);
            }
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (recommendedapplicants item : categorysearch){
                    if (item.getAddress().toLowerCase().contains(filterPattern)){
                        filterList.add(item);
                    }
                    else if(item.getContactno().toLowerCase().contains(filterPattern))
                    {
                        filterList.add(item);
                    }
                    else if(item.getAddress().toLowerCase().contains(filterPattern) && item.getContactno().toLowerCase().contains(filterPattern))
                    {
                        filterList.add(item);
                    }
                    else if(item.getAddress().toLowerCase().contains(filterPattern) || item.getContactno().toLowerCase().contains(filterPattern))
                    {
                        filterList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            recommendedapplicantsArrayList.clear();
            recommendedapplicantsArrayList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        recommendedapplicants recommendedapplicants2 = recommendedapplicantsArrayList.get(position);
        //educationalbackground educationalbackground2 = educationalbackgroundArrayList.get(position);
        holder.txtview_name.setText(recommendedapplicants2.getName());
        holder.txtview_email.setText(recommendedapplicants2.getEmail());
        holder.txtview_address.setText(recommendedapplicants2.getAddress());
        holder.txtview_contactno.setText(recommendedapplicants2.getContactno());
        holder.txtview_gender.setText(recommendedapplicants2.getGender());
        holder.txtview_specialization.setText(recommendedapplicants2.getSpecialization());
        recommendedapplicants2.getApplicant_id();
        recommendedapplicants2.getWorkexp_id();
        recommendedapplicants2.getEducational_id();
        Picasso.get().load(Constant.URL+"/storage/profiles/"+recommendedapplicants2.getProfile_pic()).into(holder.profile_pic);
    }

    @Override
    public int getItemCount() {
        return recommendedapplicantsArrayList.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtview_name,txtview_email,txtview_address,txtview_contactno,txtview_gender,txtview_specialization;
        ImageView profile_pic;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtview_name = itemView.findViewById(R.id.textView_applicantname);
            txtview_address = itemView.findViewById(R.id.textView_applicantaddress);
            txtview_contactno = itemView.findViewById(R.id.textView_applicantcontactno);
            txtview_gender = itemView.findViewById(R.id.textView_applicantgender);
            txtview_email = itemView.findViewById(R.id.textView_applicantemail);
            txtview_specialization = itemView.findViewById(R.id.textView_applicantspecialization);
            profile_pic = itemView.findViewById(R.id.imageview_applicantprofilepic);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    public void setWinnerDetails(ArrayList<recommendedapplicants> recommendedapplicantsArrayList)
    {
        this.recommendedapplicantsArrayList= recommendedapplicantsArrayList;
        notifyDataSetChanged();
    }


}
