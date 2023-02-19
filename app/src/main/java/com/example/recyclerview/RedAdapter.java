package com.example.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class RedAdapter extends RecyclerView.Adapter<RedAdapter.ViewHolde> implements Filterable {

    private List<ApiModel> userList;
    List<ApiModel> backup;

    public RedAdapter(List<ApiModel>userList){
        this.userList = userList;
        backup= new ArrayList<>(userList);
    }



    @NonNull
    @Override
    public RedAdapter.ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_item,parent,false);
        return new ViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RedAdapter.ViewHolde holder, int position) {
        holder.userName.setText(userList.get(position).getId());
        holder.userMessage.setText(userList.get(position).getTitle());
        holder.userMessage.setSelected(true);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        // back process
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ApiModel> filterList = new ArrayList<>();
            if (charSequence.toString().isEmpty()){
                filterList.addAll(backup);
            }
            else{
                for (ApiModel apiModel:backup){
                    if (apiModel.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filterList.add(apiModel);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;

            return filterResults;
        }
         //main work
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                userList.clear();
                userList.addAll((Collection<? extends ApiModel>) filterResults.values);
                notifyDataSetChanged();
        }
    };


    public class ViewHolde extends  RecyclerView.ViewHolder {
        private ImageView image;
        private TextView userName;
        private TextView userMessage;
        private TextView userTime;
        private TextView dLine;


        public ViewHolde(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            userName = itemView.findViewById(R.id.name);
            userMessage = itemView.findViewById(R.id.message);
            userTime = itemView.findViewById(R.id.time);
            dLine = itemView.findViewById(R.id.line);

        }


    }
}
