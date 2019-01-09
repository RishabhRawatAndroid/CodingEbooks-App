package com.android.rishabhrawat.codingebooks.generalclasses;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.rishabhrawat.codingebooks.R;
import com.android.rishabhrawat.codingebooks.modelclasses.CategoriesBooks;
import com.android.rishabhrawat.codingebooks.modelclasses.CategoriesTypeBooks;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

public class BooksCategoriesAdapter extends RecyclerView.Adapter<BooksCategoriesAdapter.BookViewHolder> {

    private Context context;
    private ArrayList<CategoriesTypeBooks> categoriesTypeBooks;

    public BooksCategoriesAdapter(Context context, ArrayList<CategoriesTypeBooks> categoriesTypeBooks) {
        this.context = context;
        this.categoriesTypeBooks = categoriesTypeBooks;
    }

    @NonNull
    @Override
    public BooksCategoriesAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_horizontal, viewGroup, false);

        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int i) {

        bookViewHolder.cate_text.setText(categoriesTypeBooks.get(i).getBookcategoriesname());
        ArrayList<CategoriesBooks> categoriesBooks = categoriesTypeBooks.get(i).getCategoriesBooksArrayList();
//        BooksCategoriesItemAdapter adapter=new BooksCategoriesItemAdapter(categoriesBooks);
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
//        bookViewHolder.cate_recyclerview.setLayoutManager(mLayoutManager);
//        bookViewHolder.cate_recyclerview.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
//        bookViewHolder.cate_recyclerview.setItemAnimator(new DefaultItemAnimator());
//        bookViewHolder.cate_recyclerview.setAdapter(adapter);
          new MyAsyncTask(bookViewHolder.cate_recyclerview).execute(categoriesBooks);
          bookViewHolder.textShimmer.startShimmer();


    }


    @Override
    public int getItemCount() {
        return categoriesTypeBooks.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        TextView cate_text;
        RecyclerView cate_recyclerview;
        ShimmerFrameLayout textShimmer;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            cate_text = itemView.findViewById(R.id.categories_name);
            cate_recyclerview = itemView.findViewById(R.id.horizontal_recyclerview);
            textShimmer=itemView.findViewById(R.id.text_shimmer);
        }
    }


    public class MyAsyncTask extends AsyncTask<ArrayList<CategoriesBooks>, Void, Void> {
        RecyclerView recyclerView;
        BooksCategoriesItemAdapter adapter;

        MyAsyncTask(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override
        protected Void doInBackground(ArrayList<CategoriesBooks>... arrayLists) {
            adapter = new BooksCategoriesItemAdapter(arrayLists[0],context);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3);
            recyclerView.setLayoutManager(mLayoutManager);
            //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);

        }
    }

}
