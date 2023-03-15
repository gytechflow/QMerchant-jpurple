package cm.clear.qmerchant.dashboard.custom.abstractclasses;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Calendar;

import cm.clear.qmerchant.dashboard.custom.concreteobjects.OrderCounter;
import cm.clear.qmerchant.dashboard.custom.interfaces.ISimpleDashboardCounter;
import cm.clear.qmerchant.dashboard.custom.interfaces.SimpleCountListener;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

/**
 * created on: 17/01/22
 * by : J-Purple
 *
 * abstract class that defines the attributes the Concrete {@link ISimpleDashboardCounter} objects will have
 */
public abstract class SimpleDashBoardCounter implements ISimpleDashboardCounter {
    private int previousCounter = 0;
    private int newCounter = 0;
    private final SimpleCountListener simpleCountListener;

    protected SimpleDashBoardCounter(@NonNull SimpleCountListener simpleCountListener) {
        this.simpleCountListener = simpleCountListener;
        requestCount();
    }


    protected void setPreviousCounter(int previousCounter) {
        this.previousCounter = previousCounter;
    }

    protected void setNewCounter(int newCounter) {
        this.newCounter = newCounter;
    }

    protected int getNewCounter(){
        return newCounter;
    }

    public int getCount() {
        return getNewCounter();
    }

    protected boolean isCounterDifferent(){
        return previousCounter!=newCounter;
    }

    @NonNull
    protected SimpleCountListener getSimpleCountListener() {
        return simpleCountListener;
    }

    /**
     * this is where the request to get and count new events is implemented
     */
    private void requestCount(){
        Call<String> call = getCall();
        QCallback<String> qCallback = new QCallback<String>() {
            @Override
            public void responseSuccessful(Call<String> call, Response<String> response) {
                assert response.body() != null;
                setPreviousCounter(getNewCounter());
                setNewCounter(Integer.parseInt(response.body()));
                if (isCounterDifferent()){
                    getSimpleCountListener().onSimpleCountChange(SimpleDashBoardCounter.this);
                }

            }

            @Override
            public void requestFailure(Call<String> call, Throwable t) {
                setPreviousCounter(getNewCounter());
                setNewCounter(0);
                if (isCounterDifferent()){
                    getSimpleCountListener().onSimpleCountChange(SimpleDashBoardCounter.this);
                }
                Log.e("J-Purple", "SimpleDashBoardCounter::requestFailure: ",t );
            }

            @Override
            public void responseUnsuccessful(Call<String> call, Response<String> response) {
                if (response.raw().toString().contains("Not Found")){
                    setPreviousCounter(getNewCounter());
                    setNewCounter(0);
                    if (isCounterDifferent()){
                        getSimpleCountListener().onSimpleCountChange(SimpleDashBoardCounter.this);
                    }
                } else {
                    Log.d("J-Purple", "SimpleDashBoardCounter::responseUnsuccessful() called with: call = [" + call + "], response = [" + response.raw() + "]");
                }
            }
        };
        CallAndCallback main_call = new CallAndCallback<String>(call, qCallback);
//        RequestManager.getInstance().clear(this);
        RequestManager.getInstance().addTimerCall(this, main_call);
    };

    @NonNull
    protected abstract Call<String> getCall();

    @Override
    public void reloadRequest() {
        requestCount();
    }
}
