package cm.clear.qmerchant.notificationService;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

public class StartServiceReceiver extends BroadcastReceiver {
    public static final String TAG = StartServiceReceiver.class.getSimpleName();
    private StartServiceReceiver startServiceReceiver;
    private static JobScheduler jobScheduler;


    /**
     * it returns the number of version code
     *
     * @param context
     * @return
     */
    public static long getVersionCode(Context context) {
        PackageInfo pInfo;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            long versionCode = System.currentTimeMillis();  //PackageInfoCompat.getLongVersionCode(pInfo);
            return versionCode;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return 0;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            scheduleJob(context);
        } else {

            registerRestarterReceiver(context);
            ServiceRegister serviceRegister = new ServiceRegister();
            serviceRegister.launchService(context);
            //context.startService(new Intent(context, TransactionUploadService.class));
        }
    }

    public static void scheduleJob(Context context) {
        if (jobScheduler == null) {
            jobScheduler = (JobScheduler) context
                    .getSystemService(JOB_SCHEDULER_SERVICE);
        }
        ComponentName componentName = new ComponentName(context,
                ReceiverJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(1, componentName)
                // setOverrideDeadline runs it immediately - you must have at least one constraint
                // https://stackoverflow.com/questions/51064731/firing-jobservice-without-constraints
                .setOverrideDeadline(0)
                .setPersisted(false).build(); // to restart afet reboot
        jobScheduler.schedule(jobInfo);
    }


    public static void reStartTracker(Context context) {
        // restart the never ending service
        Log.i(TAG, "Restarting tracker");
        Intent broadcastIntent = new Intent(ServiceGlobals.RESTART_INTENT);
        context.sendBroadcast(broadcastIntent);
    }

    private void registerRestarterReceiver(final Context context) {

        // the context can be null if app just installed and this is called from restartsensorservice
        // https://stackoverflow.com/questions/24934260/intentreceiver-components-are-not-allowed-to-register-to-receive-intents-when
        // Final decision: in case it is called from installation of new version (i.e. from manifest, the application is
        // null. So we must use context.registerReceiver. Otherwise this will crash and we try with context.getApplicationContext
        if (startServiceReceiver == null)
            startServiceReceiver = new StartServiceReceiver();
        else try{
            context.unregisterReceiver(startServiceReceiver);
        } catch (Exception e){
            // not registered
        }
        // give the time to run
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // we register the  receiver that will restart the background service if it is killed
                // see onDestroy of Service
                IntentFilter filter = new IntentFilter();
                filter.addAction(ServiceGlobals.RESTART_INTENT);
                try {
                    context.registerReceiver(startServiceReceiver, filter);
                } catch (Exception e) {
                    try {
                        context.getApplicationContext().registerReceiver(startServiceReceiver, filter);
                    } catch (Exception ex) {

                    }
                }
            }
        }, 1000);

    }
}
