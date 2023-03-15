package cm.clear.qmerchant.manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;

public class CallAndCallback<T> {
    private Call<T> call;
    private QCallback<T> callback;

    public CallAndCallback(Call<T> call, QCallback<T> callback) {
        this.call = call;
        this.callback = callback;
    }

    public Call<T> getCall() {
        return call;
    }

    public QCallback<T> getCallback() {
        return callback;
    }

    public void setCallback(@NonNull QCallback<T> callback) {
        this.callback = callback;
    }

    @Nullable
    public String getCallId(){
        return call.request().toString();
    }
}
