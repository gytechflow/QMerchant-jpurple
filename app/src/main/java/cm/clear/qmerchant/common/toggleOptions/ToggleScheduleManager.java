package cm.clear.qmerchant.common.toggleOptions;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ToggleScheduleManager implements ScheduleManager {

    List<ScheduledRequests> requests = new ArrayList<>();
    private int waitingTime;
    private String origin;
    private int speedLimit;
    private int counter;

    private boolean isBusy = false;
    private boolean isStopped = true;

    @Deprecated
    public ToggleScheduleManager() {
        this.waitingTime = 500;
    }

    public ToggleScheduleManager(String origin, int waitingTime) {
        this.origin = origin;
        this.waitingTime = waitingTime;

        this.counter = 0;
        this.speedLimit = 0;
    }

    public ToggleScheduleManager(String origin, int waitingTime, int speedLimit) {
        this.origin = origin;
        this.waitingTime = waitingTime;

        this.counter = 0;
        this.speedLimit = speedLimit;
    }

    /**
     *
     */
    protected void startLast() {
        Log.d("FAKE-JPurple", ToggleScheduleManager.class.getName()+"::startLast() called from " + origin +
                " with "+requests.size()+ " requests. SpeedLimit = " + speedLimit +" && Counter = " + counter + " && isStopped = "+isStopped);

        if (!isStopped){
            isBusy = true;
            if (requests.size()>0){
                //Log.d("JPurple", "::startLast() here ");
                ScheduledRequests request = requests.get(0);
                request.makeRequest();
                requests.remove(request);
            }
            isBusy = false;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startLast();
                }
            }, (speedLimit==0 || (++counter<speedLimit))?waitingTime:(waitingTime*10));
        }
        //Log.d("JPurple", "::startLast() end "+requests.size());
    }

    @Override
    public boolean scheduleMe(@NonNull ScheduledRequests scheduledRequests) {
        Log.d("FAKE-JPurple", ToggleScheduleManager.class.getName()+"::scheduleMe() called with: scheduledRequests. isBusy = [" + isBusy + "]");
        if (!isBusy){
            if (!requests.contains(scheduledRequests)){
                requests.add(scheduledRequests);


                return true;
            }
        }
        return false;
    }

    @Override
    public void start() {
        isStopped = false;
        startLast();
    }

    @Override
    public void stop() {
        isStopped = true;
    }
}
