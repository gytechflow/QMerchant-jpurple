package cm.clear.qmerchant.notificationService;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

@SuppressLint("SpecifyJobSchedulerIdRange")
public class ReceiverJobService extends JobService {
    private static String TAG = "SMSJobService";
    private static StartServiceReceiver startServiceReceiver;
    private static JobService instance;
    private static JobParameters jobParameters;
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        ServiceRegister serviceRegister = new ServiceRegister();
        serviceRegister.launchService(this);
        registerRestarterReceiver();
        instance = this;
        ReceiverJobService.jobParameters = jobParameters;
        return false;
    }

    private void registerRestarterReceiver() {

        // the context can be null if app just installed and this is called from restartsensorservice
        // https://stackoverflow.com/questions/24934260/intentreceiver-components-are-not-allowed-to-register-to-receive-intents-when
        // Final decision: in case it is called from installation of new version (i.e. from manifest, the application is
        // null. So we must use context.registerReceiver. Otherwise this will crash and we try with context.getApplicationContext
        if (startServiceReceiver == null)
            startServiceReceiver = new StartServiceReceiver();
        else try{
            unregisterReceiver(startServiceReceiver);
        } catch (Exception e){
            // not registered
        }
        //give the time to run
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                IntentFilter filter = new IntentFilter();
                filter.addAction(ServiceGlobals.RESTART_INTENT);
                try {
                    registerReceiver(startServiceReceiver, filter);
                } catch (Exception e) {
                    try {
                        getApplicationContext().registerReceiver(startServiceReceiver, filter);
                    } catch (Exception ex) {

                    }
                }
            }
        }, 1000);
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Intent broadcastIntent = new Intent(ServiceGlobals.RESTART_INTENT);
        sendBroadcast(broadcastIntent);
        // give the time to run
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                unregisterReceiver(startServiceReceiver);
            }
        }, 1000);

        return false;
    }

    /**
     * called when the tracker is stopped for whatever reason
     * @param context
     */
    public static void stopJob(Context context) {
        if (instance!=null && jobParameters!=null) {
            try{
                instance.unregisterReceiver(startServiceReceiver);
            } catch (Exception e){
                // not registered
            }
            instance.jobFinished(jobParameters, true);
        }
    }
}
