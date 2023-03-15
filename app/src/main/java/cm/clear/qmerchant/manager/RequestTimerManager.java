package cm.clear.qmerchant.manager;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cm.clear.qmerchant.interfaces.ResponseListener;

public class RequestTimerManager<T> {
    private final RequestTimerObserver<T> timerObserver;
    private Map<ResponseListener, List<Timer>> timerMap = new HashMap<>();
    private static RequestTimerManager INSTANCE;
    private final int TIMER_PERIOD = 15000;

    public RequestTimerManager() {
        this.timerObserver = RequestManager.getInstance();
    }

    @NonNull
    public static RequestTimerManager getInstance(){
        if (INSTANCE==null){
            INSTANCE = new RequestTimerManager();
        }
        return INSTANCE;
    }

    public void addTimerRequest(@NonNull ResponseListener responseListener, @NonNull CallAndCallback<T> callAndCallback){
//        Log.e("J-Purple", "addTimerRequest: "+callAndCallback.getCall().request().url() );
        Timer timer = new Timer();
        timer.schedule(new RequestTimer(responseListener, callAndCallback),0+TIMER_PERIOD, 0+TIMER_PERIOD);
        if (!timerMap.containsKey(responseListener)) {
            timerMap.put(responseListener, new ArrayList<>());
        }
        timerMap.get(responseListener).add(timer);
    }

    public void addTimerRequest(@NonNull ResponseListener responseListener, @NonNull CallAndCallback<T> callAndCallback, int customTimerPeriod){
//        Log.e("J-Purple", "addTimerRequest: "+callAndCallback.getCall().request().url() );
        Timer timer = new Timer();
        timer.schedule(new RequestTimer(responseListener, callAndCallback),0+customTimerPeriod, 0+customTimerPeriod);
        if (!timerMap.containsKey(responseListener)) {
            timerMap.put(responseListener, new ArrayList<>());
        }
        timerMap.get(responseListener).add(timer);
    }

    private class RequestTimer extends TimerTask{
        private final ResponseListener responseListener;
        private final CallAndCallback<T> callAndCallback;

        private RequestTimer(ResponseListener responseListener, CallAndCallback<T> callAndCallback) {
            this.responseListener = responseListener;
            this.callAndCallback = callAndCallback;
        }

        @Override
        public void run() {
//            Log.d("J-Purple", "run() called with: url = [" + callAndCallback.getCall().request().url() + "]");
            switch (timerObserver.makeRequest(callAndCallback, responseListener)){
                case 0:
                    /*Log.i("J-Purple", "run: 0")*/;break;
                case 99: {
                    /*Log.i("J-Purple", "run: 99")*/;
                    break;
                }
                case 1:{
                    /*Log.i("J-Purple", "run: 1");*/
                    this.cancel();
                    timerMap.get(responseListener).remove(this);
                }break;
                case -1:{
                    /*Log.i("J-Purple", "run: -1")*/;
                    this.cancel();
                    timerMap.remove(responseListener);
                }break;
            }
        }
    }

}
