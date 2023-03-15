package cm.clear.qmerchant.notificationService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.UUID;

import cm.clear.qmerchant.dashboard.DashboardActivity;
import cm.clear.qmerchant.notificationService.NotificationObservers.NotificationObserver;

public class NotificationManager implements NotificationObserver {

    private static final String CHANNEL_ID = "uk.ac.new_shelf.ouk.channel";
    private static final String TAG = NotificationManager.class.getName();
    private final Context context;
    private PendingIntent notificationPendingIntent;
    public NotificationManager(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public void createNotification(@Nullable String notificationIconUrl, @Nullable String notificationMessage) {

    }

    @Override
    public void createNotification(int notificationIconResource, int notificationMessageResource) {
        createNotification(notificationIconResource, context.getResources().getString(notificationMessageResource));
    }

    @Override
    public void createNotification(int notificationIconResource, @Nullable String notificationMessage) {
//        Log.d("JPurple", TAG+ "::createNotification() called with: notificationIconResource = [" + notificationIconResource + "], notificationMessage = [" + notificationMessage + "]");

        //Intent intent = new Intent(this, AlertDetails.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);


        if (notificationPendingIntent == null) {
            Intent notificationIntent = new Intent(context, DashboardActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                notificationPendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
            } else
                notificationPendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(notificationIconResource)
                .setContentTitle(notificationMessage)
                //.setContentText(textContent)
                //.setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        //String CHANNEL_ID = "uk.ac.new_shelf.ouk.channel";
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID, "new_name_one", android.app.NotificationManager.IMPORTANCE_HIGH);
            //String description = mContext.getString(R.string.notifications_description);
            String description = "I would like to receive travel alerts and notifications for:";
            channel.setDescription(description);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
            notificationManager.createNotificationChannel(channel);
        }
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(UUID.randomUUID().hashCode(), builder.build());


    }


}
