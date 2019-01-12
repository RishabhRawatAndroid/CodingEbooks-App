package com.android.rishabhrawat.codingebooks.generalclasses;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.android.rishabhrawat.codingebooks.fragments.AllBooksFragment;
import com.android.rishabhrawat.codingebooks.fragments.BookDiscriptionFragment;
import com.android.rishabhrawat.codingebooks.modelclasses.Books;
import com.android.rishabhrawat.codingebooks.room_database.BookEntity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    ArrayList<Books> books;
    Context context;
    private InterstitialAd mInterstitialAd;


    public BooksAdapter(ArrayList<Books> books, Context context, Fragment fragment) {
        this.books = books;
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        this.context = context;

    }


    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView bookname;
        TextView bookinfo;
        CardView bookcard;
        ImageView save_icon;

        public BookViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.book_image);
            bookname = itemView.findViewById(R.id.book_name_textview);
            bookinfo = itemView.findViewById(R.id.book_disc_textview);
            bookcard = itemView.findViewById(R.id.book_card);
            save_icon = itemView.findViewById(R.id.save_icon);
            bookcard.setOnClickListener(this);
            save_icon.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
//            Books singlebook=books.get(getAdapterPosition());
//            PassingData.setURL(singlebook.getBook_href());
//            PassingData.setNAME(singlebook.book_name);
//            PassingData.setIMAGE(singlebook.getBook_image());
//            Log.d("RISHABH",PassingData.getIMAGE() +"  "+PassingData.getNAME()+" "+PassingData.getURL());
//            Intent intent=new Intent(context,BookDiscriptionActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,imageView,"book_image");
//            context.startActivity(intent,options.toBundle());


            int position = getAdapterPosition();
            if (v.getId() == R.id.save_icon) {
                BookEntity bookEntity = new BookEntity(books.get(position).getBook_name(),
                        books.get(position).getBook_image(), books.get(position).getBook_info(),
                        books.get(position).getBook_href());
                AllBooksFragment.insert(bookEntity);
                Toast.makeText(context, context.getResources().getString(R.string.book_added), Toast.LENGTH_SHORT).show();

            } else if (v.getId() == R.id.book_card) {
                if(isNetworkConnected()) {

                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                   }


                    Fragment fragment = new BookDiscriptionFragment().newInstance(books.get(position).getBook_href(),
                            books.get(position).getBook_name(),
                            books.get(position).getBook_image(),books.get(position).getBook_info(),true);

                    FragmentTransaction transaction = ((BottomNavigationActivity) context).getSupportFragmentManager()
                            .beginTransaction().addSharedElement(imageView,"book_image")
                            .setCustomAnimations(R.anim.slide_left,R.anim.slide_right);
                    transaction.add(android.R.id.content, fragment);
                    transaction.addToBackStack("book_description");
                    transaction.commit();

                }else
                {
                    BottomNavigationActivity.showsnackbar();
                }

            }
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
        @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

        holder.bookinfo.setText(books.get(position).getBook_info());
        holder.bookname.setText(books.get(position).getBook_name());
        Picasso.get().load(books.get(position).getBook_image()).error(R.drawable.loaderror).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}

