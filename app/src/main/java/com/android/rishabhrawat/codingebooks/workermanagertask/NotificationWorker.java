package com.android.rishabhrawat.codingebooks.workermanagertask;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.rishabhrawat.codingebooks.R;
import com.android.rishabhrawat.codingebooks.generalclasses.MySharedPreference;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.concurrent.TimeUnit;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWorker extends Worker {

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            final Document document = Jsoup.connect("http://www.allitebooks.com/page/3/").get();
            Elements mElementDataSize = document.select("div[class=main-content-inner clearfix]");
            int mElementSize = mElementDataSize.size();
            for (int i = 0; i <= mElementSize - 1; i++) {
                Elements elements = mElementDataSize.select("article");
                for (int a = 0; a <= elements.size() - 1; a++) {
                    String image = elements.select("div[class=entry-thumbnail hover-thumb]").select("img").eq(a).attr("src");
                    Elements distext = elements.select("div[class=entry-body]").select("div[class=entry-summary] > p").eq(a);
                    Elements nametext = elements.select("div[class=entry-body]").select("header[class=entry-header]").select("h2[class=entry-title] > a").eq(a);
                  //  String hreftext = elements.select("div[class=entry-body]").select("header[class=entry-header]").select("h2[class=entry-title]").select("a").eq(a).attr("href");

                    MySharedPreference preference=new MySharedPreference(getApplicationContext());
                    Log.d("RishabhNotification","BBBBBBB "+image);
                    Log.d("RishabhNotification","BBBBBBBAAAAAAAAA "+preference.getFirstBookUrl());

                    if(!preference.getFirstBookUrl().equals(image))
                    {
                        Log.d("RishabhNotification","New Books come please check $$$$$$$$$$$$$$$$$$$$$$$$$");
                        ShowNotification("New Book Come",nametext.text(),distext.text());

                    }
                    else {
                        Log.d("RishabhNotification","New Books not come ############################### ");
                    }
                    break;
                }
                break;
            }
            StartNewRequest();
            return Result.success();

        } catch (Exception e) {
            e.printStackTrace();
            StartNewRequest();
            Log.d("RishabhNotification","ERERERERERERERERERERERERERERERERERERERERERERERERERERERE");
            return Result.failure();
        }
    }

    private void ShowNotification(String Message, String name,String Information)
    {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Coding Ebooks", NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationChannel.setSound(null,null );
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID);

        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setAutoCancel(false)
                .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE|Notification.DEFAULT_LIGHTS)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(uri)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle(Message)
                .setContentText(name)
                .setContentInfo(Information);

        notificationManager.notify(/*notification id*/1, notificationBuilder.build());
    }

    private void StartNewRequest()
    {
        OneTimeWorkRequest compressionWork = new OneTimeWorkRequest.Builder(NotificationWorker.class).setInitialDelay(15,TimeUnit.MINUTES).addTag("Notification_Book").build();
        WorkManager.getInstance().enqueue(compressionWork);
    }
}
