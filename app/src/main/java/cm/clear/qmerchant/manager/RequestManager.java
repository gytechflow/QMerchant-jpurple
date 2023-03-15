package cm.clear.qmerchant.manager;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cm.clear.qmerchant.interfaces.ResponseListener;
import cm.clear.qmerchant.requestsHandler.NetworkStateObserver;

public class RequestManager<T> implements NetworkStateObserver, RequestTimerObserver<T> {
    public static int SUCCESSFUL_EXECUTION = 0;
    public static int ERROR_NO_CONNECTION = 99;
    public static int ERROR_CALL_NOT_FOUND = 1;
    public static int ERROR_LISTENER_NOT_FOUND = -1;
    public static final String STANDARD_PAGE_LIMIT = "50";
    public static final String START_PAGE = "0";
    private List<CallAndCallback<T>> list = new ArrayList<>();
    private Map<ResponseListener, List<CallAndCallback<T>>> listenerListMap = new HashMap<>();
    private static RequestManager INSTANCE;
    public static boolean REFRESH = true;

    @NonNull
    public static RequestManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RequestManager<>();
            NetworkStateHandler.getInstance().addObserver(INSTANCE);
        }

        return INSTANCE;
    }

    public void addCall(@NonNull ResponseListener responseListener, @NonNull CallAndCallback<T> callAndCallback) {
        list.add(callAndCallback);
        registerRequestAndListener(responseListener, callAndCallback);
//        checkAndExecute(false);
    }

    public void addNewCall(@NonNull ResponseListener responseListener, @NonNull CallAndCallback<T> callAndCallback) {
        registerRequestAndListener(responseListener, callAndCallback);
//        checkAndExecute(false);
    }

    public void addTimerCall(@NonNull ResponseListener responseListener, @NonNull CallAndCallback<T> callAndCallback) {
        registerRequestAndListener(responseListener, callAndCallback);
        RequestTimerManager.getInstance().addTimerRequest(responseListener, callAndCallback);
    }

    public void addTimerCall(@NonNull ResponseListener responseListener, @NonNull CallAndCallback<T> callAndCallback, int customTimerPeriod) {
        registerRequestAndListener(responseListener, callAndCallback);
        RequestTimerManager.getInstance().addTimerRequest(responseListener, callAndCallback, customTimerPeriod);
    }

    /**
     * @param callAndCallback for one time requests only
     */
    public void addTempCall(@NonNull CallAndCallback<T> callAndCallback) {
        requestExecution(callAndCallback, !REFRESH);
    }

    public int removeCall(@NonNull CallAndCallback<T> callAndCallback) {
        if (list.contains(callAndCallback)) {
            if (list.remove(callAndCallback)) {
                callAndCallback.getCall().cancel();
                return SUCCESSFUL_EXECUTION;
            } else return 1;
        } else return -1;
    }


    private int requestExecution(@NonNull CallAndCallback<T> callAndCallback, boolean shouldBeRefreshed) {
        if (NetworkStateHandler.getInstance().isConnected()){
            if (shouldBeRefreshed)
                callAndCallback.getCall().clone().enqueue(callAndCallback.getCallback());
            else if (!callAndCallback.getCall().isExecuted())
                callAndCallback.getCall().enqueue(callAndCallback.getCallback());
            return SUCCESSFUL_EXECUTION;
        } else {
            return ERROR_NO_CONNECTION;
        }
    }


    private int requestVerification(@NonNull ResponseListener responseListener, @NonNull CallAndCallback<T> callAndCallback, boolean shouldBeRefreshed) {
        if (listenerListMap.containsKey(responseListener)) {
            if (listenerListMap.get(responseListener).contains(callAndCallback)) {
                return requestExecution(callAndCallback, shouldBeRefreshed);
            } else {
                return ERROR_CALL_NOT_FOUND;
            }
        } else {
            return ERROR_LISTENER_NOT_FOUND;
        }
    }


    private void registerRequestAndListener(@NonNull ResponseListener responseListener, @NonNull CallAndCallback<T> callAndCallback) {
        if (!listenerListMap.containsKey(responseListener)) {
            listenerListMap.put(responseListener, new ArrayList<>());
        }
        listenerListMap.get(responseListener).add(callAndCallback);
//        list.add(callAndCallback);

        requestVerification(responseListener, callAndCallback, !REFRESH);
    }

    private void reloadRequests(){
        List<CallAndCallback<T>> tempList = new ArrayList<>();
        for (ResponseListener listener : listenerListMap.keySet()) {
            tempList.addAll(listenerListMap.get(listener));
        }
        for (CallAndCallback<T> callAndCallback : tempList) {
            requestExecution(callAndCallback, REFRESH);
        }
    }

    @Override
    public void onStatusChanged(boolean status) {
//        Log.e("J-Purple", "RequestManager::onStatusChanged: "+status );
        if (status) {
            reloadRequests();
        }
    }

    /**
     * @param listener
     */
    public void clear(@NonNull ResponseListener listener) {
        if (listenerListMap.containsKey(listener)) {
            for (CallAndCallback<T> tCallAndCallback : listenerListMap.get(listener)) {
                tCallAndCallback.getCall().cancel();
            }
            listenerListMap.get(listener).clear();
        }
    }

    @Override
    public int makeRequest(@NonNull CallAndCallback<T> callAndCallback, @NonNull ResponseListener responseListener) {
//        Log.d("J-Purple", "makeRequest() called with: url = [" + callAndCallback.getCall().request().url() + "]");
        return requestVerification(responseListener, callAndCallback, true);
    }

    public void remove(ResponseListener responseListener) {
        if (listenerListMap.containsKey(responseListener)) {
            listenerListMap.remove(responseListener);
        }
    }

    public void reset() {
        List<ResponseListener> listeners = new ArrayList<>(listenerListMap.keySet());
        listenerListMap.clear();
        for (ResponseListener responseListener : listeners) {
            responseListener.reloadRequest();
        }
    }
}
