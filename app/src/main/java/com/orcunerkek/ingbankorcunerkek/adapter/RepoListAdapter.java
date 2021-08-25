package com.orcunerkek.ingbankorcunerkek.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orcunerkek.ingbankorcunerkek.R;
import com.orcunerkek.ingbankorcunerkek.model.GithubModel;

import java.util.ArrayList;
import java.util.List;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoListAdapterJavaViewHolder> implements Filterable {

public OnItemClickListener mListener;
private List<GithubModel> lists;
private List<GithubModel> listFull;

public interface OnItemClickListener {
    public void onItemClick(int position);

}

public class RepoListAdapterJavaViewHolder extends RecyclerView.ViewHolder {

    TextView textView_repo_name;
    ImageView imageView_star;


    public RepoListAdapterJavaViewHolder(View view, final OnItemClickListener listener) {
        super(view);
        textView_repo_name = itemView.findViewById(R.id.textView_repo_name);
        imageView_star = itemView.findViewById(R.id.imageView_star);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!= null) {
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION) {
                        listener.onItemClick(pos); // products.get(pos).getId()
                    }
                }
            }
        });

    }
}

    public void setOnItemClickListener(OnItemClickListener listener ) {
        mListener = listener;
    }

    public RepoListAdapter(List<GithubModel> repoList){
        this.lists=repoList;
        listFull = new ArrayList<>(repoList);
    }

    @NonNull
    @Override
    public RepoListAdapterJavaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo,parent,false);
        RepoListAdapterJavaViewHolder repoListAdapterJavaViewHolder= new RepoListAdapterJavaViewHolder(view, mListener);
        return repoListAdapterJavaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RepoListAdapter.RepoListAdapterJavaViewHolder holder, int position) {
        holder.textView_repo_name.setText(lists.get(position).getName());
        if(lists.get(position).isStarFlag()){
            holder.imageView_star.setVisibility(View.VISIBLE);
        }
        else {
            holder.imageView_star.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<GithubModel> filteredList = new ArrayList<>();

            if(constraint==null || constraint.length()==0) {
                filteredList.addAll(listFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(GithubModel list : listFull){
                    /*if(list.getId().toString().contains(filterPattern)) {
                        filteredList.add(list);
                    }*/

                }
            }
            FilterResults results=new FilterResults();
            results.values = filteredList;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            lists.clear();
            lists.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

}
