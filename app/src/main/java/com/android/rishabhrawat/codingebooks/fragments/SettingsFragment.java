package com.android.rishabhrawat.codingebooks.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.android.rishabhrawat.codingebooks.R;
import com.android.rishabhrawat.codingebooks.generalclasses.MySharedPreference;
import com.android.rishabhrawat.codingebooks.workermanagertask.NotificationWorker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import java.util.concurrent.TimeUnit;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;


public class SettingsFragment extends Fragment {

    ImageView close_btn;
    ConstraintLayout about_app;
    ConstraintLayout share_it;
    ConstraintLayout rate_it;
    ConstraintLayout privacy_policy;
    ConstraintLayout pro_version;
    private AdView mAdView;
    Switch notify_switch;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        create_view(view);
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        share_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey Check out the Coding EBooks app for free reading ebooks online: https://play.google.com/store/apps/details?id=" + getActivity().getPackageName());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });


        rate_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("RishabhSetting","Rate it button click");
                final String appPackageName = getActivity().getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });


        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PrivacyPolicyFragment().newInstance("https://privacypolicies.com/privacy/view/27d0ffccb16751240f1659bf4f03a27d");

                FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_left,R.anim.slide_right);
                transaction.add(android.R.id.content, fragment);
                transaction.addToBackStack("webview");
                transaction.commit();

            }
        });


        pro_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(getResources().getString(R.string.app_info))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        about_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AboutAppFragment();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_left,R.anim.slide_right);
                transaction.add(android.R.id.content, fragment);
                transaction.addToBackStack("app_info");
                transaction.commit();
            }
        });

        notify_switch.setSoundEffectsEnabled(true);
        final MySharedPreference preference=new MySharedPreference(getContext());
        notify_switch.setChecked(preference.getSwitchState());
        notify_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    //preference.setFirstBookUrl("http://www.allitebooks.com/wp-content/uploads/2019/01/iPad-Application-Sketch-Book.jpg");
                    preference.setSwitchState(true);
//                    PeriodicWorkRequest builder= new PeriodicWorkRequest.Builder(NotificationWorker.class,15, TimeUnit.MINUTES).addTag("Notification_Book").build();
//                    WorkManager.getInstance().enqueueUniquePeriodicWork("Notification_Book", ExistingPeriodicWorkPolicy.KEEP, builder);
//                    WorkManager.getInstance().getWorkInfosByTagLiveData("").observe(SettingsFragment.this, new Observer<List<WorkInfo>>() {
//                        @Override
//                        public void onChanged(@Nullable List<WorkInfo> workInfos) {
//                            if(workInfos!=null)
//                            {
//                                workInfos.get(0).getState();
//                            }
//                        }
//                    });

                    OneTimeWorkRequest compressionWork = new OneTimeWorkRequest.Builder(NotificationWorker.class).setInitialDelay(15,TimeUnit.MINUTES).addTag("Notification_Book").build();
                    WorkManager.getInstance().enqueue(compressionWork);


                }
                else {
                    preference.setSwitchState(false);
                    WorkManager.getInstance().cancelAllWorkByTag("Notification_Book");
                }
            }
        });

        return view;
    }

    private void create_view(View view) {
        close_btn = view.findViewById(R.id.close_img);
        about_app = view.findViewById(R.id.about_card_layout);
        pro_version = view.findViewById(R.id.pro_version_card_layout);
        privacy_policy = view.findViewById(R.id.privacy_card_layout);
        rate_it = view.findViewById(R.id.rate_card_layout);
        share_it = view.findViewById(R.id.share_card_layout);
        notify_switch=view.findViewById(R.id.switch1);

    }

}
