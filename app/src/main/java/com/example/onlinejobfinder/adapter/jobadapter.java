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
import com.example.onlinejobfinder.model.job;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class jobadapter extends RecyclerView.Adapter<jobadapter.Viewholder> implements Filterable {

    public jobadapter(ArrayList<job> listjob, Context context) {
        this.listjob = listjob;
        this.context = context;
        categorysearch = new ArrayList<>(listjob);
    }

    private ArrayList<job> listjob;
    private List<job> categorysearch;
    private Context context;


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_view_jobs,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        job job2 = listjob.get(position);
        Picasso.get().load(Constant.URL+"/storage/jobposts/"+job2.getJoblogo()).into(holder.imageview_joblogo);
        holder.txtview_jobtitle.setText(job2.getJobtitle());
        holder.txtview_jobcompany.setText(job2.getJobcompany());
        holder.txtview_jobaddress.setText(job2.getJobaddress());
        holder.txtview_jobsalary.setText(job2.getJobsalary());
        holder.txtview_jobdateposted.setText(job2.getJobdateposted());
    }

    @Override
    public int getItemCount() {
        return listjob.size();
    }

    @Override
    public Filter getFilter() {
        return categoryfilter;
    }

    public Filter categoryfilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<job> filterList = new ArrayList<>();
            if (charSequence == null || charSequence.length() ==0){
                filterList.addAll(categorysearch);
            }
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (job item : categorysearch){
                    if (item.getJobtitle().toLowerCase().contains(filterPattern)){
                        filterList.add(item);
                    }
                    else if(item.getJobaddress().toLowerCase().contains(filterPattern))
                    {
                        filterList.add(item);
                    }
                    else if(item.getJobaddress().toLowerCase().contains(filterPattern) && item.getJobtitle().toLowerCase().contains(filterPattern))
                    {
                        filterList.add(item);
                    }
                    else if(item.getJobaddress().toLowerCase().contains(filterPattern) || item.getJobtitle().toLowerCase().contains(filterPattern))
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
            listjob.clear();
            listjob.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class Viewholder extends RecyclerView.ViewHolder{

        TextView txtview_jobtitle,txtview_jobcompany,txtview_jobaddress,txtview_jobsalary,txtview_jobdateposted;
        ImageView imageview_joblogo;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtview_jobtitle = itemView.findViewById(R.id.textView_jobtitle);
            imageview_joblogo = itemView.findViewById(R.id.imageview_joblogo);
            txtview_jobcompany = itemView.findViewById(R.id.textView_jobcompany);
            txtview_jobaddress = itemView.findViewById(R.id.textView_jobaddress);
            txtview_jobsalary = itemView.findViewById(R.id.textView_jobsalary);
            txtview_jobdateposted = itemView.findViewById(R.id.textView_jobdateposted);
        }
    }
    public void setWinnerDetails(ArrayList<job> listjob)
    {
        this.listjob = listjob;
        notifyDataSetChanged();
    }

}
