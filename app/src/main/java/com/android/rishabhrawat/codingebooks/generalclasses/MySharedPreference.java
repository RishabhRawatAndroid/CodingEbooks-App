package com.android.rishabhrawat.codingebooks.generalclasses;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreference {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "AppCache";


    public MySharedPreference(Context context) {
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean("first_time", isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean("first_time", true);
    }

    public void setFirstBookUrl(String url) {
        editor.putString("URL_BOOK", url);
        editor.commit();
    }

    public String getFirstBookUrl() {
        return pref.getString("URL_BOOK", null);
    }


    public void setSwitchState(boolean value)
    {
        editor.putBoolean("SWITCH", value);
        editor.commit();
    }

    public boolean getSwitchState()
    {
        return pref.getBoolean("SWITCH",false);
    }
}
