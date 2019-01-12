package com.android.rishabhrawat.codingebooks;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.rishabhrawat.codingebooks.activities.BottomNavigationActivity;
import com.android.rishabhrawat.codingebooks.activities.WelcomeViewPagerActivity;
import com.android.rishabhrawat.codingebooks.generalclasses.MySharedPreference;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide the action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //hide the status bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        //if user is not online then show the snackbar
        if (!isOnline())
            showsnackbar();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity();
            }
        }, 2000);

    }


    private void startActivity() {
        startActivityconnection();
    }

    private void showsnackbar() {
        ConstraintLayout splash_layout = findViewById(R.id.splash_layout);
        Snackbar snackbar = Snackbar.make(splash_layout, getResources().getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE);
        View view = snackbar.getView();
        view.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.progressbar_color, null));
        snackbar.show();

    }

    private void startActivityconnection() {
        MySharedPreference preference = new MySharedPreference(SplashScreenActivity.this);
        if (preference.isFirstTimeLaunch()) {
            startActivity(new Intent(SplashScreenActivity.this, WelcomeViewPagerActivity.class));
        } else {
            startActivity(new Intent(SplashScreenActivity.this, BottomNavigationActivity.class));
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
            return true;
        else
            return false;

    }
}
