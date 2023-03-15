package cm.clear.qmerchant.models.order;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.common.actionablebuttons.ResourceResult;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class OrdersActions {
    private static OrdersActions INSTANCE;
    private List<OrderActionsObserver> observers = new ArrayList<>();

    public static OrdersActions getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new OrdersActions();
        return INSTANCE;
    }

    public void subscribe(@NonNull OrderActionsObserver observer){
        if (!observers.contains(observer))
            observers.add(observer);
    }

    public void unSubscribe(@NonNull OrderActionsObserver observer){
        observers.remove(observer);
    }

    /*
    public LiveData<ResourceResultState> removeResource(String bookingId, String resourceId) {
        MutableLiveData<ResourceResultState> result = new MutableLiveData<>();
        Call<String> call = ApiUtil.getUserService().removeResource(bookingId, resourceId);
        QCallback qCallback = new QCallback() {
            @Override
            public void responseSuccessful(Call call, Response response) {
                if (response.body().toString().contains("200"))
                    result.setValue(new ResourceResultState(resourceId, response.body().toString(), true));
            }

            @Override
            public void requestFailure(Call call, Throwable t) {
                result.setValue(new ResourceResultState(resourceId, t.getLocalizedMessage(), true));
            }

            @Override
            public void responseUnsuccessful(Call call, Response response) {
                result.setValue(new ResourceResultState(resourceId, response.raw().message(), true));
            }
        };
        CallAndCallback<String> callAndCallback = new CallAndCallback<>(call, qCallback);
        RequestManager.getInstance().addTempCall(callAndCallback);

        return result;
    }

    public LiveData<ResourceResultState> removeAllResources(String bookingId){
        MutableLiveData<ResourceResultState> result = new MutableLiveData<>();
        Call<Object> call = ApiUtil.getUserService().removeAllResources(bookingId);
        QCallback qCallback = new QCallback() {
            @Override
            public void responseSuccessful(Call call, Response response) {
                result.setValue(new ResourceResultState(bookingId, response.body().toString(), true));
            }

            @Override
            public void requestFailure(Call call, Throwable t) {
                result.setValue(new ResourceResultState(bookingId, t.getLocalizedMessage(), true));
            }

            @Override
            public void responseUnsuccessful(Call call, Response response) {
                result.setValue(new ResourceResultState(bookingId, response.raw().message(), true));
            }
        };
        CallAndCallback<String> callAndCallback = new CallAndCallback<>(call, qCallback);
        RequestManager.getInstance().addTempCall(callAndCallback);

        return result;
    }

    public LiveData<ResourceResultState> endEvent(String bookingId) {
        MutableLiveData<ResourceResultState> result = new MutableLiveData<>();
        Call<Object> call = ApiUtil.getUserService().endBooking(bookingId);
        QCallback qCallback = new QCallback() {
            @Override
            public void responseSuccessful(Call call, Response response) {
                result.setValue(new ResourceResultState(bookingId, response.body().toString(), true));
            }

            @Override
            public void requestFailure(Call call, Throwable t) {
                result.setValue(new ResourceResultState(bookingId, t.getLocalizedMessage(), false));
            }

            @Override
            public void responseUnsuccessful(Call call, Response response) {
                result.setValue(new ResourceResultState(bookingId, response.raw().message(), false));
            }
        };
        CallAndCallback<Object> callAndCallback = new CallAndCallback<>(call, qCallback);
        RequestManager.getInstance().addTempCall(callAndCallback);
        return result;
    }

    public LiveData<ResourceResultState> addResource(String bookingId, String resourceId) {
        MutableLiveData<ResourceResultState> result = new MutableLiveData<>();
        AffectTable affectTable = new AffectTable(resourceId, bookingId);
        Call<Object> call = ApiUtil.getUserService().assignResource(affectTable);
        QCallback qCallback = new QCallback() {
            @Override
            public void responseSuccessful(Call call, Response response) {
                Log.d("J-Purple", "responseSuccessful() called with:  response = [" + response.body().toString() + "]");
                result.setValue(new ResourceResultState(resourceId, response.body().toString(), true));
            }

            @Override
            public void requestFailure(Call call, Throwable t) {
                result.setValue(new ResourceResultState(resourceId, t.getLocalizedMessage(), false));
            }

            @Override
            public void responseUnsuccessful(Call call, Response response) {
                result.setValue(new ResourceResultState(resourceId, response.raw().message(), false));
            }
        };
        CallAndCallback<String> callAndCallback = new CallAndCallback<>(call, qCallback);
        RequestManager.getInstance().addTempCall(callAndCallback);
        return result;
    }
    */

    /**
     *
     * @param orderId
     * @return
     */
    @NonNull
    public LiveData<ResourceResult> confirmOrder(String orderId){
        for (OrderActionsObserver observer : observers) {
            observer.onConfirm(true);
        }
        MutableLiveData<ResourceResult> result = new MutableLiveData<>();
        Call<Object> call = ApiUtil.getOrdersService().confirmOrder(orderId);
        QCallback qCallback = new QCallback() {
            @Override
            public void responseSuccessful(Call call, Response response) {
                //Log.d("J-Purple", "responseSuccessful() called with:  response = [" + response.body().toString() + "]");
                result.setValue(new ResourceResult(orderId, response.body().toString(), true));
                for (OrderActionsObserver observer : observers) {
                    observer.onConfirm(false);
                }
            }
            @Override
            public void requestFailure(Call call, Throwable t) {
                result.setValue(new ResourceResult(orderId, t.getLocalizedMessage(), false));
            }
            @Override
            public void responseUnsuccessful(Call call, Response response) {
                result.setValue(new ResourceResult(orderId, response.raw().message(), false));
            }
        };
        CallAndCallback<String> callAndCallback = new CallAndCallback<>(call, qCallback);
        RequestManager.getInstance().addTempCall(callAndCallback);
        return result;
    }

    /**
     *
     * @param orderId
     * @return
     */
    @NonNull
    public LiveData<ResourceResult> cancelOrder(String orderId){
        for (OrderActionsObserver observer : observers) {
            observer.onCancel(true);
        }
        MutableLiveData<ResourceResult> result = new MutableLiveData<>();
        Call<Object> call = ApiUtil.getOrdersService().cancelOrder(orderId);
        QCallback qCallback = new QCallback() {
            @Override
            public void responseSuccessful(Call call, Response response) {
                for (OrderActionsObserver observer : observers) {
                    observer.onCancel(false);
                }
                result.setValue(new ResourceResult(orderId, response.body().toString(), true));
            }
            @Override
            public void requestFailure(Call call, Throwable t) {
                result.setValue(new ResourceResult(orderId, t.getLocalizedMessage(), false));
            }
            @Override
            public void responseUnsuccessful(Call call, Response response) {
                result.setValue(new ResourceResult(orderId, response.raw().message(), false));
            }
        };
        CallAndCallback<String> callAndCallback = new CallAndCallback<>(call, qCallback);
        RequestManager.getInstance().addTempCall(callAndCallback);
        return result;
    }

    /**
     *
     * @param orderId
     * @return
     */
    @NonNull
    public LiveData<ResourceResult> closeOrder(String orderId){
        for (OrderActionsObserver observer : observers) {
            observer.onClose(true);
        }
        MutableLiveData<ResourceResult> result = new MutableLiveData<>();
        Call<Object> call = ApiUtil.getOrdersService().closeOrder(orderId);
        QCallback qCallback = new QCallback() {
            @Override
            public void responseSuccessful(Call call, Response response) {
                for (OrderActionsObserver observer : observers) {
                    observer.onClose(false);
                }
                result.setValue(new ResourceResult(orderId, response.body().toString(), true));
            }
            @Override
            public void requestFailure(Call call, Throwable t) {
                result.setValue(new ResourceResult(orderId, t.getLocalizedMessage(), false));
            }
            @Override
            public void responseUnsuccessful(Call call, Response response) {
                result.setValue(new ResourceResult(orderId, response.raw().message(), false));
            }
        };
        CallAndCallback<String> callAndCallback = new CallAndCallback<>(call, qCallback);
        RequestManager.getInstance().addTempCall(callAndCallback);
        return result;
    }

    /**
     *
     * @param orderId
     * @return
     */
    @NonNull
    public LiveData<ResourceResult> prepareOrder(@NonNull String orderId){
        for (OrderActionsObserver observer : observers) {
            observer.onPrepare(true);
        }
        MutableLiveData<ResourceResult> result = new MutableLiveData<>();
        Call<Object> call = ApiUtil.getOrdersService().orderTokitchen(orderId);
        QCallback qCallback = new QCallback() {
            @Override
            public void responseSuccessful(Call call, Response response) {
                for (OrderActionsObserver observer : observers) {
                    observer.onPrepare(false);
                }
                result.setValue(new ResourceResult(orderId, response.body().toString(), true));
            }
            @Override
            public void requestFailure(Call call, Throwable t) {
                result.setValue(new ResourceResult(orderId, t.getLocalizedMessage(), false));
            }
            @Override
            public void responseUnsuccessful(Call call, Response response) {
                result.setValue(new ResourceResult(orderId, response.raw().message(), false));
            }
        };
        CallAndCallback<String> callAndCallback = new CallAndCallback<>(call, qCallback);
        RequestManager.getInstance().addTempCall(callAndCallback);
        return result;
    }



}
