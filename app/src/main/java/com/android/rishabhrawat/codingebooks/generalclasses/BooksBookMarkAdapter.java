package com.android.rishabhrawat.codingebooks.generalclasses;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rishabhrawat.codingebooks.R;
import com.android.rishabhrawat.codingebooks.activities.BottomNavigationActivity;
import com.android.rishabhrawat.codingebooks.fragments.BookDiscriptionFragment;
import com.android.rishabhrawat.codingebooks.fragments.BooksBookMarkFragment;
import com.android.rishabhrawat.codingebooks.room_database.BookEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BooksBookMarkAdapter extends RecyclerView.Adapter<BooksBookMarkAdapter.MyBookHolder> {

    private List<BookEntity> mBooks;
    private Context context;
    public static BookEntity bookEntitytemp;

    public BooksBookMarkAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyBookHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bookmark_book_item, viewGroup, false);
        return new BooksBookMarkAdapter.MyBookHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBookHolder myBookHolder, int i) {
        if (mBooks != null) {
            BookEntity current = mBooks.get(i);
            Picasso.get().load(current.getBook_url()).error(R.drawable.loaderror).into(myBookHolder.imageView);
            myBookHolder.title.setText(current.getBook_name());
            myBookHolder.description.setText(current.getBook_description());
        } else {
            // Covers the case of data not being ready yet.

        }
    }

    @Override
    public int getItemCount() {
        if (mBooks != null)
            return mBooks.size();
        else return 0;
    }

    public void setWords(List<BookEntity> books) {
        mBooks = books;
        notifyDataSetChanged();
    }

    public void swiperemove(int position)
    {
        bookEntitytemp=mBooks.remove(position);
        notifyItemRemoved(position);
    }

    public void undooperation(int position)
    {
        mBooks.add(position,bookEntitytemp);
        notifyItemInserted(position);
    }

    class MyBookHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title;
        TextView description;
        ImageView deleteicon;
        CardView cardView;

        public MyBookHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.book_image_bookmark);
            title = itemView.findViewById(R.id.book_name_bookmark);
            description = itemView.findViewById(R.id.book_disc_textview_bookmark);
            deleteicon = itemView.findViewById(R.id.delete_icon);
            cardView=itemView.findViewById(R.id.book_card_bookmark);

            deleteicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    final BookEntity entity = mBooks.remove(pos);
                    notifyItemRemoved(pos);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            BooksBookMarkFragment.deletebook(entity);
                        }
                    }, 1000);

                    Toast.makeText(context, context.getResources().getString(R.string.book_delete), Toast.LENGTH_SHORT).show();
                }
            });

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(isNetworkConnected()) {
                        Fragment fragment = new BookDiscriptionFragment().newInstance(
                                mBooks.get(position).getBook_href(),
                                mBooks.get(position).getBook_name(),
                                mBooks.get(position).getBook_url(),
                                mBooks.get(position).getBook_description(),
                                false);

                        FragmentTransaction transaction = ((BottomNavigationActivity) context).getSupportFragmentManager().beginTransaction().addSharedElement(imageView, "book_image");
                        transaction.replace(android.R.id.content, fragment);
                        transaction.addToBackStack("book_description");
                        transaction.commit();
                    }else
                    {
                        BottomNavigationActivity.showsnackbar();
                    }
                }
            });
        }
    }
    protected boolean isNetworkConnected() {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            return (mNetworkInfo == null) ? false : true;

        }catch (NullPointerException e){
            return false;

        }
    }
}
