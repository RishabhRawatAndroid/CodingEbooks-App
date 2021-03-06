package com.myapps.rishabhrawat.codingebooks.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myapps.rishabhrawat.codingebooks.R;
import com.myapps.rishabhrawat.codingebooks.activities.BottomNavigationActivity;
import com.myapps.rishabhrawat.codingebooks.generalclasses.BooksBookMarkAdapter;
import com.myapps.rishabhrawat.codingebooks.room_database.BookEntity;
import com.myapps.rishabhrawat.codingebooks.room_database.BookViewModel;

import java.util.List;


public class BooksBookMarkFragment extends Fragment {

    private RecyclerView recyclerView;
    private ImageView imageView;
    private LinearLayoutManager llm;
    private BooksBookMarkAdapter adapter;
    private RelativeLayout layout;
    private BookViewModel viewModel;
    private TextView text;
    private static BookViewModel bookViewModel;
    private static boolean internet_snackbar = false;

    public BooksBookMarkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_books_book_mark, container, false);
        ((BottomNavigationActivity) getActivity()).toolbar_text.setText("  " + getResources().getString(R.string.app_name));
        recyclerView = view.findViewById(R.id.bookmark_recyclerview);
        imageView = view.findViewById(R.id.bookmark_image);
        text = view.findViewById(R.id.bookmark_text);
        layout = view.findViewById(R.id.bookmark_layout);


        adapter = new BooksBookMarkAdapter(getActivity());
        recyclerView.setAdapter(adapter);


        llm = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean supportsPredictiveItemAnimations() {
                return true;
            }
        };

        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        text.setVisibility(View.GONE);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        viewModel = ViewModelProviders.of(BooksBookMarkFragment.this).get(BookViewModel.class);
        viewModel.getAllBooks().observe(this, new Observer<List<BookEntity>>() {
            @Override
            public void onChanged(@Nullable List<BookEntity> bookEntities) {
                if (bookEntities.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    text.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                    text.setVisibility(View.GONE);
                    adapter.setWords(bookEntities);
                }
            }
        });

        bookViewModel = viewModel;

        return view;
    }

    public static void deletebook(BookEntity bookEntity) {
        bookViewModel.delete(bookEntity);

    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {

            final boolean[] delete = new boolean[1];
            final int pos = viewHolder.getAdapterPosition();
            adapter.swiperemove(pos);

            if (BottomNavigationActivity.snackbar.isShown()) {
                internet_snackbar = true;
            }
            Snackbar.make(layout, getResources().getString(R.string.delete_ing), Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.undo), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.undooperation(pos);
                    delete[0] = true;
                    if (internet_snackbar) {
                        BottomNavigationActivity.snackbar.show();
                        internet_snackbar = false;
                    }
                }
            }).setActionTextColor(Color.YELLOW).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (delete[0] == false) {
                        deletebook(BooksBookMarkAdapter.bookEntitytemp);

                        if (internet_snackbar) {
                            BottomNavigationActivity.snackbar.show();
                            internet_snackbar = false;
                        }
                    }
                }
            }, 5000);
        }
    };


}
