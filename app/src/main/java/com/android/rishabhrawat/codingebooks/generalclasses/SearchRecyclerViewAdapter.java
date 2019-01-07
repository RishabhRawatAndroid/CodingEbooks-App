package com.android.rishabhrawat.codingebooks.generalclasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rishabhrawat.codingebooks.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<String> search_list;
    private List<String> filter_list;

    public SearchRecyclerViewAdapter(Context context, List<String> search_list) {
        this.context = context;
        this.search_list = search_list;
        filter_list = new ArrayList<>(search_list);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.search_item_layout, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.textView.setText(search_list.get(i));
    }

    @Override
    public int getItemCount() {
        return search_list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.search_img);
            textView = itemView.findViewById(R.id.search_text);
        }
    }


    @Override
    public Filter getFilter() {
        return search_item_filter;
    }

    private Filter search_item_filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredlist = new ArrayList<>(search_list);
            if (constraint == null || constraint.length() == 0) {
                filteredlist.clear();
                filteredlist.addAll(filter_list);
            } else {
                String pattern = constraint.toString().toLowerCase().trim();
                filteredlist.clear();
                for (String item : filter_list) {
                    if (item.toLowerCase().contains(pattern)) {
                        filteredlist.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            search_list.clear();
            search_list.addAll((Collection<? extends String>) results.values);
            notifyDataSetChanged();
        }
    };
}
