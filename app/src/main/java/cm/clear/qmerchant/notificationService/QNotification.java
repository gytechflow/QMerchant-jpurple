package cm.clear.qmerchant.notificationService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.dashboard.DashboardActivity;

public class QNotification {
    private static final String TAG = QNotification.class.getName();
    private PendingIntent notificationPendingIntent;

    /**
     * This is the method  called to create the Notification
     */
    public Notification setNotification(Context context, String title, String text, int icon) {
//        Log.d("JPurple", TAG+"::setNotification() called with: context");
        if (notificationPendingIntent == null) {
            Intent notificationIntent = new Intent(context, DashboardActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                Log.d("JPurple", TAG+"::setNotification() Build.VERSION.SDK_INT >= Build.VERSION_CODES.M");
                notificationPendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
            } else
                notificationPendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        }

        Notification notification;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // OREO
//        Log.d("JPurple", TAG+"::setNotification() "+Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Log.d("JPurple", TAG+"::setNotification() Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP");
            notification = new NotificationCompat.Builder(context, "channel")
                    // to be defined in the MainActivity of the app
                    .setSmallIcon(icon)
                    .setContentTitle(title)
//                    .setColor(mContext.getResources().getColor(R.color.colorAccent))
                    .setContentText(text)
                    .setPriority(Notification.PRIORITY_MIN)
                    .setContentIntent(notificationPendingIntent).build();
        } else {
//            Log.d("JPurple", TAG+"::setNotification() Build.VERSION.SDK_INT !>= Build.VERSION_CODES.LOLLIPOP");
            notification = new NotificationCompat.Builder(context, "channel")
                    // to be defined in the MainActivity of the app
                    .setSmallIcon(icon)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setPriority(Notification.PRIORITY_MIN)
                    .setContentIntent(notificationPendingIntent).build();
            return notification;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            Log.d("JPurple", TAG+"::setNotification() Build.VERSION.SDK_INT >= Build.VERSION_CODES.O");
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            CharSequence name = "Permanent Notification Mod";
            //mContext.getString(R.string.channel_name);
            int importance = NotificationManager.IMPORTANCE_NONE;

            String CHANNEL_ID = "uk.ac.shelf.oak.channel";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            //String description = mContext.getString(R.string.notifications_description);
            String description = "I would like to receive travel alerts and notifications for:";
            channel.setDescription(description);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
            notification = notificationBuilder
                    //the log is PNG file format with a transparent background
                    .setSmallIcon(icon)
                    .setColor(ContextCompat.getColor(context, R.color.primaryLightColor))
                    .setContentTitle(title)
                    .setContentText(text)
                    .setContentIntent(notificationPendingIntent)
                    .build();

        }

        return notification;
    }

}