package com.android.rishabhrawat.codingebooks.broadcast_reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.rishabhrawat.codingebooks.interfaces.NetworkChangeListener;

public class InternetChecker extends BroadcastReceiver {

    public NetworkChangeListener networkChangeListener;

    public InternetChecker() {

    }

    public InternetChecker(NetworkChangeListener listener) {
        this.networkChangeListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();

        Log.d("Rishabh", "On RECEIVE");
        if (networkChangeListener != null) {
            Log.d("Rishabh", "Networkchnage listener");
            networkChangeListener.change_internet_connection(isConnected);
        }
    }
}