package com.android.rishabhrawat.codingebooks.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.android.rishabhrawat.codingebooks.R;
import com.android.rishabhrawat.codingebooks.activities.BottomNavigationActivity;
import com.android.rishabhrawat.codingebooks.generalclasses.BooksAdapter;
import com.android.rishabhrawat.codingebooks.interfaces.ActivityListener;
import com.android.rishabhrawat.codingebooks.modelclasses.Books;
import com.android.rishabhrawat.codingebooks.modelclasses.BooksList;
import com.android.rishabhrawat.codingebooks.room_database.BookEntity;
import com.android.rishabhrawat.codingebooks.room_database.BookViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class AllBooksFragment extends Fragment implements ActivityListener {

    private static final String ARG_PARAM1 = "param1_url";
    private static final String ARG_PARAM2 = "param2_page";
    private String mParam1_url;
    private int mParam2_page;

    static BookViewModel bookViewModel;


    private static ShimmerFrameLayout shimmerFrameLayout;
    private RecyclerView recyclerView;
    private BooksAdapter booksAdapter;
    private ArrayList<Books> booksArrayList;
    private Books books;
    private LinearLayoutManager llm;
    private static int page = 1;
    private boolean scrolled = false;
    private int currentItem, scrolledItem, totalItem;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static boolean isonline = true;
    public static boolean isrefreshing = false;
    private static boolean showshimmer = false;
    private static boolean firsttime = false;
    private static boolean scrollprogress = false;

    // private OnFragmentInteractionListener mListener;

    public AllBooksFragment() {
        // Required empty public constructor
    }

//    public static AllBooksFragment newInstance(String url, int page) {
//        AllBooksFragment fragment = new AllBooksFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, url);
//        args.putInt(ARG_PARAM2, page);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1_url = getArguments().getString(ARG_PARAM1);
//            mParam2_page = getArguments().getInt(ARG_PARAM2);
//        }
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BottomNavigationActivity) getActivity()).setActivityListener(AllBooksFragment.this);
        bookViewModel = new BookViewModel(getActivity().getApplication());

    }

    //this OncreateView method call after the Oncreate
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_books, container, false);

        // booksArrayList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.my_recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        shimmerFrameLayout = view.findViewById(R.id.shimmerLayout);


        if (!isOnline() && BooksList.getBooksArrayList() == null) {
            firsttime = true;
            shimmerFrameLayout.startShimmer();
        } else if (isOnline() && BooksList.getBooksArrayList() == null) {
            showshimmer = true;
            booksArrayList = new ArrayList<>();
            MyAsynckTask task = new MyAsynckTask(getActivity());
            task.execute("http://www.allitebooks.com/page/" + page + "/");
            setAdapter();
        } else if (isOnline() && BooksList.getBooksArrayList() != null) {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            showshimmer = false;
            booksArrayList = BooksList.getBooksArrayList();
            setAdapter();
        } else if (!isOnline() && BooksList.getBooksArrayList() != null) {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            showshimmer = false;
            booksArrayList = BooksList.getBooksArrayList();
            setAdapter();
        }


        //suppose Booklist has data and internet is gone and user switch to different fragment then I take the previous position where user left the recyclerview position
        if (BooksList.getBooksArrayList() != null) {
            final RecyclerView myrecycler = recyclerView;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    myrecycler.scrollToPosition(BooksList.getPosition());
                }
            }, 0);
        }

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
                    BooksList.setPosition(scrolledItem);

                    if (scrolled && (currentItem + scrolledItem >= totalItem)) {
                        scrolled = false;
                        page = page + 1;
                        Log.d("Rishabh", "Pages is the " + page);
                        scrollprogress = true;
                        new MyAsynckTask(getActivity()).execute("http://www.allitebooks.com/page/" + page + "/");
                    }
                }

            }
        });


        ///////////////////////////////////////////////swipe to refresh layout
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.tabitem3), getResources().getColor(R.color.tabitem2), getResources().getColor(R.color.tabitem1), getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //suppose user is online then we refresh the layout and make the bookarray list empty
                if (isOnline()) {
                    booksArrayList = new ArrayList<>();
                    showshimmer = true;
                    shimmerFrameLayout.setVisibility(View.VISIBLE);
                    Log.d("RishabhRawat", "refresh call isonline");
                    MyAsynckTask task = new MyAsynckTask(getActivity());
                    page=1;
                    task.execute("http://www.allitebooks.com/page/1/");
                    setAdapter();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    //if user is not online then i not make a request for refresh the layout
                    //  BottomNavigationActivity bottomNavigationActivity=new BottomNavigationActivity();
                    //  bottomNavigationActivity.showsnackbar();
                    BottomNavigationActivity.showsnackbar();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });


        return view;
    }


    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
            return true;
        else
            return false;
    }


    private void setAdapter() {
        //bookArrayList takes the data from async task (in OnCreate method) if internet is available and if internet is not availabe then bookarraylist get the data from the BookList class
        booksAdapter = new BooksAdapter(booksArrayList, getActivity(), AllBooksFragment.this);
        recyclerView.setAdapter(booksAdapter);
        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

    }

    @Override
    public void network_status(boolean status) {
        if (status && firsttime) {
            Log.d("RishabhRawat", "Internet up");
            booksArrayList = new ArrayList<>();
            showshimmer = true;
            MyAsynckTask task = new MyAsynckTask(getActivity());
            task.execute("http://www.allitebooks.com/page/1/");
            setAdapter();
            firsttime = false;
        }
    }

    public static void insert(BookEntity bookEntity)
    {
        bookViewModel.insert(bookEntity);

    }


    class MyAsynckTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (showshimmer) {
                shimmerFrameLayout.startShimmer();
            }
            if (scrollprogress) {
                swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.dracula));
                swipeRefreshLayout.setRefreshing(true);
                // progressBar.setVisibility(View.VISIBLE);
            }
        }

        public MyAsynckTask(Context context) {
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                final Document document = Jsoup.connect(strings[0]).get();
                Elements mElementDataSize = document.select("div[class=main-content-inner clearfix]");
                int mElementSize = mElementDataSize.size();
                for (int i = 0; i <= mElementSize - 1; i++) {
                    Elements elements = mElementDataSize.select("article");
                    for (int a = 0; a <= elements.size() - 1; a++) {
                        books = new Books();
                        String image = elements.select("div[class=entry-thumbnail hover-thumb]").select("img").eq(a).attr("src");
                        Elements distext = elements.select("div[class=entry-body]").select("div[class=entry-summary] > p").eq(a);
                        Elements nametext = elements.select("div[class=entry-body]").select("header[class=entry-header]").select("h2[class=entry-title] > a").eq(a);
                        String hreftext = elements.select("div[class=entry-body]").select("header[class=entry-header]").select("h2[class=entry-title]").select("a").eq(a).attr("href");
                        books.setBook_image(image);
                        books.setBook_name(nametext.text());
                        books.setBook_info(distext.text());
                        books.setBook_href(hreftext);
                        booksArrayList.add(books);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //
            if (booksAdapter != null) {
                booksAdapter.notifyDataSetChanged();
                BooksList.setBooksArrayList(booksArrayList);
            }
            //this means refresh the data again from the isonline check in onrefresh method in refresh listener
//            if (isrefreshing) {
//                Log.d("RishabhRawat", "is refreshing async");
//                booksAdapter = new BooksAdapter(booksArrayList, getActivity());
//                recyclerView.setAdapter(booksAdapter);
//                booksAdapter.notifyDataSetChanged();
//                BooksList.setBooksArrayList(booksArrayList);
//                swipeRefreshLayout.setRefreshing(false);
//                isrefreshing = false;
//            }


            // progressBar.setVisibility(View.INVISIBLE);
            if (showshimmer) {
                Log.d("RishabhRawat", "show simmer async");
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                showshimmer = false;
            }

            //suppose the server allitebook website is down in any causes which means bookArrayList is empty( on background in async task the IO exception call which means the bookArrayList will be null or empty)
            if (booksArrayList.isEmpty()) {
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                shimmerFrameLayout.startShimmer();
            }

            if (scrollprogress) {
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white));
                scrollprogress = false;
            }


        }



    }

}