package cm.clear.qmerchant.manager;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.interfaces.ResponseListener;

public interface RequestTimerObserver<T> {
    /**
     *
     * @param responseListener
     * @param callAndCallback
     * @return {@linkplain RequestManager#requestVerification(ResponseListener, CallAndCallback, boolean)}
     */
    int makeRequest(@NonNull CallAndCallback<T> callAndCallback, @NonNull ResponseListener responseListener);
}
