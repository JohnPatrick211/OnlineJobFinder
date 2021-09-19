package com.example.onlinejobfinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinejobfinder.Constant;
import com.example.onlinejobfinder.R;
import com.example.onlinejobfinder.model.job;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class jobadapter extends RecyclerView.Adapter<jobadapter.Viewholder> implements Filterable {


    public jobadapter(ArrayList<job> listjob, Context context, RecyclerViewClickListener listener) {
        this.listjob = listjob;
        this.context = context;
        this.listener = listener;
        categorysearch = new ArrayList<>(listjob);
        getfilterjob = new ArrayList<>(listjob);
        getfilterjob2 = new ArrayList<>(listjob);
    }

    private ArrayList<job> listjob;
    private ArrayList<job> getfilterjob;
    private ArrayList<job> getfilterjob2;
    private Context context;
    private List<job> categorysearch;
   private RecyclerViewClickListener listener;




    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_view_jobs,parent,false);
        return new Viewholder(view);
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        job job2 = listjob.get(position);
        Picasso.get().load(Constant.URL+"/storage/profiles/"+job2.getJoblogo()).into(holder.imageview_joblogo);
        holder.txtview_jobtitle.setText(job2.getJobtitle());
        holder.txtview_jobcompany.setText(job2.getJobcompany());
        holder.txtview_joblocation.setText(job2.getJoblocation());
        holder.txtview_jobsalary.setText(job2.getJobsalary());
//        SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        Date date = null;
//        try {
//            date = df.parse(job2.getJobdateposted().trim());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        df.applyPattern("dd/MM/yyyy");
//        String newDate = df.format(date);  //Output: newDate = "13/09/2014"
//        holder.txtview_jobdateposted.setText(newDate);
        holder.txtview_jobdateposted.setText(job2.getJobdateposted().trim());
        job2.getJobaddress();
        job2.getCompanyoverview();
        job2.getJobcategory();
        job2.getJobid();
        job2.getJobdescription();
        job2.getJobuniqueid();
        job2.getJobstatus();
        job2.getSavedid();
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
                for (job item :  getfilterjob){
                    if (item.getJobcategory().toLowerCase().contains(filterPattern)){
                        filterList.add(item);
                    }
                    else if(item.getJoblocation().toLowerCase().contains(filterPattern))
                    {
                        filterList.add(item);
                    }
                    else if(item.getJobcompany().toLowerCase().contains(filterPattern))
                    {
                        filterList.add(item);
                    }
                    else if(item.getJobtitle().toLowerCase().contains(filterPattern))
                    {
                        filterList.add(item);
                    }
//                    else if(item.getJoblocation().toLowerCase().contains(filterPattern) && item.getJobcategory().toLowerCase().contains(filterPattern))
//                    {
//                        filterList.add(item);
//                    }
//                    else if(item.getJoblocation().toLowerCase().contains(filterPattern) || item.getJobcategory().toLowerCase().contains(filterPattern))
//                    {
//                        filterList.add(item);
//                    }
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

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtview_jobtitle,txtview_jobcompany,txtview_joblocation,txtview_jobsalary,txtview_jobdateposted;
        ImageView imageview_joblogo;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtview_jobtitle = itemView.findViewById(R.id.textView_jobtitle);
            imageview_joblogo = itemView.findViewById(R.id.imageview_joblogo);
            txtview_jobcompany = itemView.findViewById(R.id.textView_jobcompany);
            txtview_joblocation = itemView.findViewById(R.id.textView_joblocation);
            txtview_jobsalary = itemView.findViewById(R.id.textView_jobsalary);
            txtview_jobdateposted = itemView.findViewById(R.id.textView_jobdateposted);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
           // listener.onClick(view, getAdapterPosition());
            if(listener !=null)
            {
                int position = getAdapterPosition();
                String jobid = listjob.get(position).getJobuniqueid();
                //Toast.makeText(context,jobid,Toast.LENGTH_SHORT).show();
                for(int i=0; i< categorysearch.size(); i++)
                {
                    if(jobid.equals(categorysearch.get(i).getJobuniqueid()))
                    {
                        position = i;
                        break;
                    }
                }
//                Toast.makeText(context,position,Toast.LENGTH_SHORT).show();
                if(position != RecyclerView.NO_POSITION)
                {
                    listener.onClick(view,position);
                }
            }
        }
    }
    public void setWinnerDetails(ArrayList<job> listjob)
    {
        this.listjob = listjob;
        notifyDataSetChanged();
        System.out.println(listjob.size());
        getfilterjob = listjob;
//        getfilterjob2 = listjob;
        System.out.println("filter =" + getfilterjob.size());
    }

}
