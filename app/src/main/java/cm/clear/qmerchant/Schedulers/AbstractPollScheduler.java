package cm.clear.qmerchant.Schedulers;

import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.common.toggleOptions.ScheduleManager;
import cm.clear.qmerchant.common.toggleOptions.ScheduledRequests;

public abstract class AbstractPollScheduler implements PollingScheduler, ScheduledRequests {
    private static final String TAG = AbstractPollScheduler.class.getName();
    private static final int WAITING_TIME = 1000;
    private String schedulerID;
    private boolean started;
    private List<PollObserver> pollObservers = new ArrayList<>();
    private final ScheduleManager scheduleManager;

    protected AbstractPollScheduler(@NonNull ScheduleManager scheduleManager) {
        this.scheduleManager = scheduleManager;
    }

    @Override
    public int addPollObserver(@Nullable PollObserver pollObserver) {
        if (pollObserver == null) return -1;
        if (pollObservers.contains(pollObserver))
            return 1;
        else {
            pollObservers.add(pollObserver);
        }
        return 0;
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    @Override
    public int removePollObserver(@Nullable PollObserver pollObserver) {
        if (pollObserver == null) return -1;
        if (pollObservers.contains(pollObserver))
            return 1;
        else {
            pollObservers.remove(pollObserver);
        }
        return 0;
    }

    @Override
    public void notifyPollObservers() {
        //Log.i("JPurple",TAG+ "notifyPollObservers: "+pollObservers.size());
        for (PollObserver pollObserver : pollObservers) {
            pollObserver.onDataChange();
        }
    }

    @Override
    public void start() {
        startPolling();
        setStarted(true);
    }

    @Override
    public void stop() {
        setStarted(false);
    }

    void notifyManager() {
//        Log.d("JPurple", TAG + "::notifyManager() called by");
        if (!scheduleManager.scheduleMe(this)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    notifyManager();
                }
            }, WAITING_TIME);
        }
    }
}
