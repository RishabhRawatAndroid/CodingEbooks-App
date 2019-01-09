package com.android.rishabhrawat.codingebooks.generalclasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rishabhrawat.codingebooks.R;
import com.android.rishabhrawat.codingebooks.activities.BottomNavigationActivity;
import com.android.rishabhrawat.codingebooks.modelclasses.CategoriesBooks;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BooksCategoriesItemAdapter extends RecyclerView.Adapter<BooksCategoriesItemAdapter.MyViewHolder> {

    private ArrayList<CategoriesBooks> categoriesBooks;
    private Context context;

    public BooksCategoriesItemAdapter(ArrayList<CategoriesBooks> categoriesBooks,Context context) {
        this.categoriesBooks = categoriesBooks;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.categories_book_items, viewGroup, false);

        return new BooksCategoriesItemAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(categoriesBooks.get(i).getName());
        Picasso.get().load(categoriesBooks.get(i).getDrawable_image()).into( myViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return categoriesBooks.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;
        CardView book_card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.book_categories_image);
            title = itemView.findViewById(R.id.book_categories_text);
            book_card=itemView.findViewById(R.id.book_categories_card);

            book_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((BottomNavigationActivity) context).open_search_result_fragment(categoriesBooks.get(getAdapterPosition()).getCategories_url(),categoriesBooks.get(getAdapterPosition()).getName());
                }
            });
        }
    }
}
