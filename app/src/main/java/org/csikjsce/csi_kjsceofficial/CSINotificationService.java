package org.csikjsce.csi_kjsceofficial;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class CSINotificationService extends FirebaseMessagingService {
    public static final String TAG = CSINotificationService.class.getSimpleName();
    public static final int REQUEST_CODE = 172;
    Intent intent;

    public CSINotificationService() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        intent = new Intent(getBaseContext(), NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent
                .getActivity(getBaseContext(),REQUEST_CODE, intent,PendingIntent.FLAG_ONE_SHOT);
        Uri soundUri = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this,"csi_channel");
        notifBuilder
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifManager.notify(0,notifBuilder.build());
    }
}
