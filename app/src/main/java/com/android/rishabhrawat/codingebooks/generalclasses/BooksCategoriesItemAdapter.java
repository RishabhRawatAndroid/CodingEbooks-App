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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BooksCategoriesItemAdapter extends RecyclerView.Adapter<BooksCategoriesItemAdapter.MyViewHolder> implements RewardedVideoAdListener  {

    private ArrayList<CategoriesBooks> categoriesBooks;
    private Context context;
    private RewardedVideoAd mRewardedVideoAd;

    public BooksCategoriesItemAdapter(ArrayList<CategoriesBooks> categoriesBooks, final Context context) {
        this.categoriesBooks = categoriesBooks;
        this.context = context;

        ((BottomNavigationActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
                mRewardedVideoAd.setRewardedVideoAdListener(BooksCategoriesItemAdapter.this);
            }
        });
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
        Picasso.get().load(categoriesBooks.get(i).getDrawable_image()).into(myViewHolder.imageView);

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
            book_card = itemView.findViewById(R.id.book_categories_card);

            book_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((BottomNavigationActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mRewardedVideoAd.isLoaded()) {
                                mRewardedVideoAd.show();
                            }

                            mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                                    new AdRequest.Builder().build());
                        }
                    });

                    ((BottomNavigationActivity) context).open_search_result_fragment(categoriesBooks.get(getAdapterPosition()).getCategories_url(), categoriesBooks.get(getAdapterPosition()).getName());
                }
            });
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {


        ((BottomNavigationActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                        new AdRequest.Builder().build());
            }
        });
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

}
