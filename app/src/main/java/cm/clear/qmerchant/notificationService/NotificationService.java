package cm.clear.qmerchant.notificationService;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.Schedulers.BookingPollScheduler;
import cm.clear.qmerchant.Schedulers.OrderPollScheduler;
import cm.clear.qmerchant.Schedulers.PollObserver;
import cm.clear.qmerchant.Schedulers.PollingScheduler;
import cm.clear.qmerchant.common.toggleOptions.ScheduleManager;
import cm.clear.qmerchant.common.toggleOptions.ToggleScheduleManager;
import cm.clear.qmerchant.notificationService.NotificationObservers.BookingObserver;
import cm.clear.qmerchant.notificationService.NotificationObservers.NotificationEmitter;
import cm.clear.qmerchant.notificationService.NotificationObservers.OrderObserver;

public class NotificationService extends Service {

    private final IBinder binder = new NotificationBinder();

    private static final String TAG = "NotificationService";
    //private static final String TAG = NotificationService.class.getName();

    protected static final int NOTIFICATION_ID = 1337;
    @NonNull
    protected Map<Integer, PollingScheduler> schedulers = new HashMap<>();
    NotificationManager notificationManager  = new NotificationManager(this);
    @NonNull
    protected Map<Integer, NotificationEmitter> notificationObservers = new HashMap<Integer, NotificationEmitter>(){{
       put(ServiceGlobals.BOOKING_SERVICE, new BookingObserver(notificationManager));
       put(ServiceGlobals.ORDER_SERVICE, new OrderObserver(notificationManager));
    }};

    @Nullable
    protected ScheduleManager scheduleManager = new ToggleScheduleManager("Notification", 15000);

    @Nullable
    @Override
    public IBinder onBind(@Nullable Intent intent) {
        Log.d("FAKE-JPurple", NotificationService.class.getName()+ "::onBind() called with: intent = [" + intent + "]");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        startUpProcedure();
        Log.d("FAKE-JPurple", NotificationService.class.getName()+"::onCreate() called");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("FAKE-JPurple", NotificationService.class.getName()+"::onStartCommand() called with: intent = [" + intent + "], flags = [" + flags + "], startId = [" + startId + "]");
        if (intent == null) {
            ServiceRegister serviceRegister = new ServiceRegister();
            serviceRegister.launchService(this);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            restartForeground();
        }

        //registerReceiver(new MyReceiver(), new IntentFilter(SMS_RECEIVED));
        startUpProcedure();
        return START_STICKY;
    }

    private void startUpProcedure(){
        initializePollSchedulers();
        startPollSchedulers();
    }

    /**
     * it starts the process in foreground. Normally this is done when screen goes off
     * THIS IS REQUIRED IN ANDROID 8 :
     * "The system allows apps to call Context.startForegroundService()
     * even while the app is in the background.
     * However, the app must call that service's startForeground() method within five seconds
     * after the service is created."
     */
    public void restartForeground() {
        Log.d("FAKE-JPurple", NotificationService.class.getName()+"-->restartForeground() called");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Log.i(TAG, "restarting foreground");
            try {
                Notification notification = new QNotification().setNotification(this, ""+getResources().getString(R.string.service_running), "", R.drawable.ic_baseline_calendar_today_24);
                startForeground(NOTIFICATION_ID, notification);
                //Log.i(TAG, "restarting foreground successful");
                //registerReceiver(new MyReceiver(), new IntentFilter(SMS_RECEIVED));
                //startUploadProcedure();
            } catch (Exception e) {
                Log.e(TAG, "Error in notification " + e.getMessage());
            }
        }
    }

    private void initializePollSchedulers(){
        //scheduleManager = new ToggleScheduleManager("Notification", 15000);
        scheduleManager.start();
        Log.d("FAKE-JPurple", NotificationService.class.getName()+ "::initializePollSchedulers() called");
        if (!schedulers.containsKey(ServiceGlobals.BOOKING_SERVICE)){
            BookingPollScheduler bookingPollScheduler = new BookingPollScheduler(scheduleManager);
            bookingPollScheduler.addPollObserver((PollObserver) notificationObservers.get(ServiceGlobals.BOOKING_SERVICE));
            schedulers.put(ServiceGlobals.BOOKING_SERVICE, bookingPollScheduler);
        }
        if (!schedulers.containsKey(ServiceGlobals.ORDER_SERVICE)){
            OrderPollScheduler orderPollScheduler = new OrderPollScheduler(scheduleManager);
            orderPollScheduler.addPollObserver((PollObserver) notificationObservers.get(ServiceGlobals.ORDER_SERVICE));
            schedulers.put(ServiceGlobals.ORDER_SERVICE, orderPollScheduler);
        }

    }

    private void startPollSchedulers(){
        Log.d("FAKE-JPurple", NotificationService.class.getName()+ "::startPollSchedulers() called");
        for (PollingScheduler scheduler : schedulers.values()) {
            Log.d("FAKE-JPurple", NotificationService.class.getName()+ "::startPollSchedulers() called "+scheduler.isStarted());
            if (!scheduler.isStarted()){
                scheduler.startPolling();
            }
        }
    }

    @Override
    public void onDestroy() {
        Log.d("FAKE-JPurple", NotificationService.class.getName()+ "::onDestroy() called");
        Intent intent = new Intent(ServiceGlobals.RESTART_INTENT);
        sendBroadcast(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d("FAKE-JPurple", NotificationService.class.getName()+ "::onTaskRemoved() called with: rootIntent = [" + rootIntent + "]");
        super.onTaskRemoved(rootIntent);
        Intent intent = new Intent(ServiceGlobals.RESTART_INTENT);
        sendBroadcast(intent);
    }

    public class NotificationBinder extends Binder {
        public int subscribe(int serviceId, @NonNull PollObserver pollObserver ) {
            Log.d("FAKE-JPurple", NotificationService.class.getName()+ "::subscribe() called with: serviceId = [" + serviceId + "], pollObserver = [" + pollObserver + "]");
            if (!NotificationService.this.schedulers.containsKey(serviceId)
                    || !NotificationService.this.notificationObservers.containsKey(serviceId)) return 1;
            if (NotificationService.this.schedulers.get(serviceId)==null
                    || NotificationService.this.notificationObservers.get(serviceId)==null) return -1;
            Objects.requireNonNull(NotificationService.this.schedulers.get(serviceId)).addPollObserver(pollObserver);
            //Objects.requireNonNull(NotificationService.this.notificationObservers.get(serviceId)).setActive(false);
            return 0;
        }

        public int unSubscribe(int serviceId, @NonNull PollObserver pollObserver) {
            Log.d("FAKE-JPurple", NotificationService.class.getName()+ "::unSubscribe() called with: serviceId = [" + serviceId + "], pollObserver = [" + pollObserver + "]");
            if (!NotificationService.this.schedulers.containsKey(serviceId)
                    || !NotificationService.this.notificationObservers.containsKey(serviceId)) return 1;
            if (NotificationService.this.schedulers.get(serviceId)==null
                    || NotificationService.this.notificationObservers.get(serviceId)==null) return -1;
            Objects.requireNonNull(NotificationService.this.schedulers.get(serviceId)).removePollObserver(pollObserver);
            //Objects.requireNonNull(NotificationService.this.notificationObservers.get(serviceId)).setActive(true);
            return 0;
        }
    }
}
