package cm.clear.qmerchant.dashboard.custom.abstractclasses;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.dashboard.custom.concreteobjects.OrderCounter;
import cm.clear.qmerchant.dashboard.custom.interfaces.CompositeCountListener;
import cm.clear.qmerchant.dashboard.custom.interfaces.ITotalCounterAddon;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

/**
 * created on: 17/01/22
 * by : J-Purple
 *
 * an extension on the {@link SimpleDashBoardCounter},
 * it implements getting the total count with {@link ITotalCounterAddon}
 */
public abstract class CompositeDashBoardCounter extends SimpleDashBoardCounter implements ITotalCounterAddon {
    private int previousTotalCount = 0;
    private int newTotalCount = 0;
    private final CompositeCountListener compositeCountListener;

    protected CompositeDashBoardCounter(@NonNull CompositeCountListener compositeCountListener) {
        super(compositeCountListener);
        this.compositeCountListener = compositeCountListener;
        requestTotalCount();
    }

    protected void setPreviousTotalCount(int previousTotalCount) {
        this.previousTotalCount = previousTotalCount;
    }

    protected void setNewTotalCount(int newTotalCount) {
        this.newTotalCount = newTotalCount;
    }

    protected int getNewTotalCount(){
        return newTotalCount;
    }

    public int getTotalCount() {
        return getNewTotalCount();
    }

    @NonNull
    protected CompositeCountListener getCompositeCountListener() {
        return compositeCountListener;
    }

    protected boolean isTotalCounterDifferent(){
        return previousTotalCount!=newTotalCount;
    }

    /**
     * this is where the request to get and count all events is implemented
     */
    private void requestTotalCount(){
        Call<String> call = getTotalCall();
        QCallback<String> qCallback = new QCallback<String>() {
            @Override
            public void responseSuccessful(Call<String> call, Response<String> response) {
                assert response.body() != null;
//                filterNew(response.body());
                setPreviousTotalCount(getNewTotalCount());
                setNewTotalCount(Integer.parseInt(response.body()));
                if (isTotalCounterDifferent()){
                    getCompositeCountListener().onCompositeCountChange(CompositeDashBoardCounter.this);
                }
            }

            @Override
            public void requestFailure(Call<String> call, Throwable t) {
                setPreviousTotalCount(getNewTotalCount());
                setNewTotalCount(0);
                if (isTotalCounterDifferent()){
                    getCompositeCountListener().onCompositeCountChange(CompositeDashBoardCounter.this);
                }
            }

            @Override
            public void responseUnsuccessful(Call<String> call, Response<String> response) {
                if (response.raw().toString().contains("Not Found")){
                    setPreviousTotalCount(getNewTotalCount());
                    setNewTotalCount(0);
                    if (isTotalCounterDifferent()){
                        getCompositeCountListener().onCompositeCountChange(CompositeDashBoardCounter.this);
                    }
                }
            }
        };
        CallAndCallback main_call = new CallAndCallback<String>(call, qCallback);
//        RequestManager.getInstance().clear(this);
        RequestManager.getInstance().addTimerCall(this, main_call);
    }



    protected abstract Call<String> getTotalCall();

    @Override
    public void reloadRequest() {
        super.reloadRequest();
        requestTotalCount();
    }
}
