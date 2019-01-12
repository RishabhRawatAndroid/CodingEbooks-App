package com.android.rishabhrawat.codingebooks.activities;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.android.rishabhrawat.codingebooks.R;
import com.android.rishabhrawat.codingebooks.fragments.AllBooksFragment;
import com.android.rishabhrawat.codingebooks.fragments.BookCategoriesFragment;
import com.android.rishabhrawat.codingebooks.fragments.BookSearchResultFragment;
import com.android.rishabhrawat.codingebooks.fragments.BooksBookMarkFragment;
import com.android.rishabhrawat.codingebooks.fragments.SearchBookFragment;
import com.android.rishabhrawat.codingebooks.fragments.SettingsFragment;
import com.android.rishabhrawat.codingebooks.generalclasses.BottomNavigationBehaviour;
import com.android.rishabhrawat.codingebooks.interfaces.ActivityListener;
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BottomNavigationActivity extends AppCompatActivity  {


    private Toolbar toolbar;
    private static BottomNavigationView bottomNavigationView;
  //  private InternetChecker checker;
    private CoordinatorLayout coordinatorLayout;
    public static Snackbar snackbar;
    private static View view;
    private static Context context;
    private static Fragment myfragment;
    public TextView toolbar_text;

    public ActivityListener activityListener;
    int state;
    int save_state = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        toolbar = findViewById(R.id.toolbar);
        toolbar_text = findViewById(R.id.toolbar_title);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.tabitem1));

        coordinatorLayout = findViewById(R.id.cord_nav);
        snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE);
        view = snackbar.getView();
        view.setBackgroundColor(getResources().getColor(R.color.dracula));
        TextView tv = view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(getResources().getColor(R.color.tabitem2));
        internet_status();


        context = BottomNavigationActivity.this;

//        checker = new InternetChecker(BottomNavigationActivity.this);
//        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(checker, filter);

        //setting up the toolbar setting
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setLogo(R.drawable.toolbaricon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        if (savedInstanceState != null) {
            save_state = savedInstanceState.getInt("save_state");
            Log.d("RishabhRawat", "Save instance state not null");
        }

        if (save_state == 0) {
            setFragment(new AllBooksFragment());
            bottomNavigationView.setSelectedItemId(R.id.all_books_menu);
            Log.d("RishabhRawat", "Save state 0");
        } else if (save_state == 1) {
            bottomNavigationView.setSelectedItemId(R.id.all_books_menu);
            Log.d("RishabhRawat", "Save state 1");
            bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.tabitem1));
        } else if (save_state == 2) {
            setFragment(new BookCategoriesFragment());
            bottomNavigationView.setSelectedItemId(R.id.book_categories);
            Log.d("RishabhRawat", "Save state 2");
            bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.tabitem2));
        } else if (save_state == 3) {
            setFragment(new BooksBookMarkFragment());
            Log.d("RishabhRawat", "Save state 3");
            bottomNavigationView.setSelectedItemId(R.id.book_bookmark);
            bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.tabitem3));
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.all_books_menu:
                        state = 1;
                        myfragment = new AllBooksFragment();
                        setFragment(myfragment);
                        bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.tabitem1));
                        return true;
                    case R.id.book_categories:
                        state = 2;
                        myfragment = new BookCategoriesFragment();
                        setFragment(myfragment);
                        bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.tabitem2));
                        return true;
                    case R.id.book_bookmark:
                        state = 3;
                        myfragment = new BooksBookMarkFragment();
                        setFragment(myfragment);
                        bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.tabitem3));
                        return true;
                }
                updateNavigationBarState(menuItem.getItemId());
                return false;
            }
        });

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehaviour());

    }

    private void updateNavigationBarState(int actionId) {
        Menu menu = bottomNavigationView.getMenu();

        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            item.setChecked(item.getItemId() == actionId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_navigation_toolbar_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_up, R.animator.slide_down, R.animator.slide_up, R.animator.slide_down);
        switch (item.getItemId()) {
            case R.id.search_btn:
                fragment = new SearchBookFragment();
                transaction.replace(android.R.id.content, fragment);
                transaction.addToBackStack("search");
                transaction.commit();
                return true;

            case R.id.setting_btn:
                fragment = new SettingsFragment();
                transaction.replace(android.R.id.content, fragment);
                transaction.addToBackStack("setting");
                transaction.commit();

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        // transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void showsnackbar() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setBackgroundColor(context.getResources().getColor(R.color.dracula));
            }
        }, 300);
        view.setBackgroundColor(context.getResources().getColor(R.color.dracula1));

    }

    public void open_search_result_fragment(String URL, String NAME) {
        //Now open the all book fragment and hide the action bar
        Fragment searchFragment = new BookSearchResultFragment().newInstance(URL, NAME);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //  Toast.makeText(context, "DATA IS THE "+URL, Toast.LENGTH_SHORT).show();
        transaction.replace(R.id.frame_container, searchFragment);
        transaction.addToBackStack("search_result");
        transaction.commit();

    }


    //https://stackoverflow.com/questions/17458261/activity-and-fragment-interaction
    public void setActivityListener(ActivityListener activityListener) {
        this.activityListener = activityListener;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  unregisterReceiver(checker);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("save_state", state);
        super.onSaveInstanceState(outState);
    }


    private void internet_status() {
        ReactiveNetwork
                .observeNetworkConnectivity(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Connectivity>() {
                    @Override
                    public void accept(Connectivity connectivity) throws Exception {
                        if (connectivity.state() == NetworkInfo.State.CONNECTED) {
                            Log.d("RishabhRX", "INTERNET IS CONNECTED");
                            if (activityListener != null) {
                                activityListener.network_status(true);
                            }

                            if (snackbar.isShown()) {
                                AllBooksFragment.isonline = true;
                                snackbar.dismiss();
                            }

                            if (AllBooksFragment.swipeRefreshLayout != null) {
                                if (AllBooksFragment.swipeRefreshLayout.isRefreshing())
                                    AllBooksFragment.swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                        if (connectivity.state() == NetworkInfo.State.DISCONNECTED || connectivity.state() == NetworkInfo.State.DISCONNECTING) {
                            Log.d("RishabhRX", "INTERNET IS DISSCONNECTED");
                            snackbar.show();
                            AllBooksFragment.isonline = false;
                        }
                    }
                });
    }
}
