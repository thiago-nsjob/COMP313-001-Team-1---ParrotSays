package com.jvinix.iy4s.Utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.jvinix.iy4s.Program;
import com.jvinix.iy4s.R;
import com.jvinix.iy4s.Views.MapsActivity;

public class MessageingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("LOGTAG", s);

        //sendRegistrationToServer("1", s);
    }

    private void passMessageToActivity(String message)
    {
        Intent intent = new Intent().setAction("INTENT_ACTION_SEND_MESSAGE").putExtra("message", message);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("LOGTAG", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("LOGTAG", "Message data payload: " + remoteMessage.getData());
            notify2(remoteMessage.getData().get("default"));
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("LOGTAG", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            notify2(remoteMessage.getNotification().getBody());
        }
    }

    public void notify2(String message)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri notitySound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this)
                //Notification notifyBuilder = new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Parrot Service Notification")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setSound(notitySound)
                .setContentIntent(pendingIntent);
        //.build();
//
        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            NotificationManagerCompat notifyManager = NotificationManagerCompat.from(getApplicationContext());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Channel_1";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Parrot Says Channel 1",
                    NotificationManager.IMPORTANCE_HIGH);
            notifyManager.createNotificationChannel(channel);
            notifyBuilder.setChannelId(channelId);
        }
        notifyManager.notify(1, notifyBuilder.build());
    }
}
