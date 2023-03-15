package cm.clear.qmerchant.notificationService;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

public class ServiceRegister {
    public static final String TAG = ServiceRegister.class.getSimpleName();
    private static Intent serviceIntent = null;
    private List<Intent> servicesToStart = new ArrayList<>();

    public ServiceRegister() {
    }


    private void setServiceIntent(Context context) {
        if (serviceIntent == null) {
            serviceIntent = new Intent(context, NotificationService.class);
        }
    }

    /**
     * launching the service
     */
    public void launchService(Context context) {
        if (context == null) {
            return;
        }
        setServiceIntent(context);
        // depending on the version of Android we eitehr launch the simple service (version<O)
        // or we start a foreground service
        if (!isServiceRunning(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent);
            } else {
                context.startService(serviceIntent);
            }
        }
//        Log.d(TAG, "ProcessMainClass: start service go!!!!");
    }

    public static boolean isServiceRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (NotificationService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
