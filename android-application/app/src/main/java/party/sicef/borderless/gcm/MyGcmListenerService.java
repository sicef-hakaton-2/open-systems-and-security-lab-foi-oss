package party.sicef.borderless.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;

import party.sicef.borderless.R;
import party.sicef.borderless.ui.activity.NewsReaderActivity;
import party.sicef.borderless.util.AppConstants;
import party.sicef.borderless.util.PreferencesManager;

/**
 * Created by Andro on 11/15/2015.
 */
public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("title");
        String url = data.getString("url");

        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);
        Log.d(TAG, "URL: " + url);
        Log.d(TAG, data.toString());

        PreferencesManager.saveGcmUrl(getApplicationContext(), url);

        ArrayList<String> category = new ArrayList<>();
        try {
            JSONArray temp = new JSONArray(data.getString("tags"));
            int length = temp.length();

            boolean send = false;

            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    category.add(String.valueOf(i));
                    Log.d("Tag", " " + i);
                    if (PreferencesManager.isCategorySet(getApplicationContext(), AppConstants.KEY_CATEGORY + i)) {
                        send = true;
                    }
                }
            }

            if (send) {
                sendNotification(message, category);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message, ArrayList<String> category) {
        Intent intent = new Intent(this, NewsReaderActivity.class);

        intent.putStringArrayListExtra("category", category);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Borderless")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}