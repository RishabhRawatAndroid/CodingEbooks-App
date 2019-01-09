package com.android.rishabhrawat.codingebooks.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rishabhrawat.codingebooks.R;
import com.android.rishabhrawat.codingebooks.activities.BottomNavigationActivity;
import com.android.rishabhrawat.codingebooks.generalclasses.BooksAdapter;
import com.android.rishabhrawat.codingebooks.modelclasses.BookDiscriptionModel;
import com.android.rishabhrawat.codingebooks.modelclasses.Books;
import com.android.rishabhrawat.codingebooks.modelclasses.BooksList;
import com.android.rishabhrawat.codingebooks.room_database.BookViewModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BookSearchResultFragment extends Fragment {
    private static final String ARG_PARAM1 = "searchURL";
    private static final String ARG_PARAM2 = "searchNAME";

    private String search_url;
    private String search_name;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static BooksAdapter booksAdapter;
    static BookViewModel bookViewModel;
    private ArrayList<Books> booksArrayList = new ArrayList<>();
    ;
    private LinearLayoutManager llm;
    private Books books;
    private ImageView imageView;

    private ImageView not_found;
    private RelativeLayout relativeLayout;
    private TextView not_found_text;

    private TextView textView;
    private int currentItem, scrolledItem, totalItem;
    private boolean scrolled = false;
    static String localurl;

    private Disposable disposable;

    public static BookSearchResultFragment newInstance(String param1, String param2) {
        BookSearchResultFragment fragment = new BookSearchResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            search_url = getArguments().getString(ARG_PARAM1);
            search_name = getArguments().getString(ARG_PARAM2);
        }
        bookViewModel = new BookViewModel(getActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_search_result, container, false);
        ((BottomNavigationActivity) getActivity()).toolbar_text.setText("  " + search_name);

        recyclerView = view.findViewById(R.id.search_result_recyclerview);
        swipeRefreshLayout = view.findViewById(R.id.search_result_swipelayout);
        imageView = view.findViewById(R.id.lost_connection_search_result);
        not_found = view.findViewById(R.id.not_found_search_result);
        textView = view.findViewById(R.id.error_text_search_result);
        not_found_text = view.findViewById(R.id.not_fount_text);
        relativeLayout = view.findViewById(R.id.search_result_layout);

        imageView.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        not_found.setVisibility(View.GONE);
        not_found_text.setVisibility(View.GONE);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.tabitem3), getResources().getColor(R.color.tabitem2), getResources().getColor(R.color.tabitem1), getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.dracula));

        if (booksArrayList.isEmpty()) {
            localurl=search_url;
            RUN_ASYNK_TASK(search_url);
        }

        //booksArrayList = new ArrayList<>();
        booksAdapter = new BooksAdapter(booksArrayList, getActivity(), BookSearchResultFragment.this);
        recyclerView.setAdapter(booksAdapter);
        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                booksArrayList.clear();
                localurl=search_url;
                RUN_ASYNK_TASK(search_url);
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    scrolled = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if (dy > 0) {
                    currentItem = llm.getChildCount();
                    totalItem = llm.getItemCount();
                    scrolledItem = llm.findFirstVisibleItemPosition();
                    //scrolled item represents the current postion of the recyclerview

                    if (scrolled && (currentItem + scrolledItem >= totalItem)) {
                        scrolled = false;
                        localurl=convertURL(localurl);
                        Log.d("RishabhRX",localurl);
                        RUN_ASYNK_TASK(convertURL(localurl));

                    }
                }

            }
        });


        return view;
    }

    private io.reactivex.Observable<ArrayList<Books>> createObservable(final String url) {
        return io.reactivex.Observable.defer(new Callable<ObservableSource<? extends ArrayList<Books>>>() {
            @Override
            public io.reactivex.ObservableSource<? extends ArrayList<Books>> call() throws Exception {
                return io.reactivex.Observable.just(runasyntask(url));
            }
        });
    }

    private ArrayList<Books> runasyntask(String url) throws IOException {
        Books mybooks;
        ArrayList<Books> mybookarraylist = new ArrayList<>();
        final Document document = Jsoup.connect(url).get();
        Elements mElementDataSize = document.select("div[class=main-content-inner clearfix]");
        int mElementSize = mElementDataSize.size();
        for (int i = 0; i <= mElementSize - 1; i++) {
            Elements elements = mElementDataSize.select("article");
            for (int a = 0; a <= elements.size() - 1; a++) {
                mybooks = new Books();
                String image = elements.select("div[class=entry-thumbnail hover-thumb]").select("img").eq(a).attr("src");
                Elements distext = elements.select("div[class=entry-body]").select("div[class=entry-summary] > p").eq(a);
                Elements nametext = elements.select("div[class=entry-body]").select("header[class=entry-header]").select("h2[class=entry-title] > a").eq(a);
                String hreftext = elements.select("div[class=entry-body]").select("header[class=entry-header]").select("h2[class=entry-title]").select("a").eq(a).attr("href");
                mybooks.setBook_image(image);
                mybooks.setBook_name(nametext.text());
                mybooks.setBook_info(distext.text());
                mybooks.setBook_href(hreftext);
                mybookarraylist.add(mybooks);

            }

        }
        // Log.d("RISHABHRX",mybookarraylist.get(0).getBook_name());
        return mybookarraylist;
    }

    private void RUN_ASYNK_TASK(String search_book) {

        createObservable(search_book).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                }).subscribe(new Observer<ArrayList<Books>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(ArrayList<Books> books) {
                //which means data successfully Arrive
                if (!books.isEmpty()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    not_found_text.setVisibility(View.GONE);
                    not_found.setVisibility(View.GONE);
                    booksArrayList.addAll(books);
                    booksAdapter.notifyDataSetChanged();
                }
                //if books not found then its show
                if (books.size() == 0 && booksArrayList.isEmpty()) {
                    not_found.setVisibility(View.VISIBLE);
                    not_found_text.setVisibility(View.VISIBLE);
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.not_found_color));
                }
                else if(books.size()==0)
                {
                 swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                swipeRefreshLayout.setRefreshing(false);
                if (booksArrayList.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    relativeLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    swipeRefreshLayout.setRefreshing(false);
                }


            }

            @Override
            public void onComplete() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private String convertURL(String string) {
        int value = Integer.parseInt(string.replaceAll("[^0-9]", ""));
        int page = value + 1;

        char last = String.valueOf(value).charAt(String.valueOf(value).length() - 1);
        int startindex = string.indexOf(String.valueOf(value));
        int lastindex = string.lastIndexOf(String.valueOf(last));

         return string.substring(0, startindex) + page + string.substring(lastindex + 1, string.length());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        booksArrayList = null;
        disposable.dispose();
    }
}
