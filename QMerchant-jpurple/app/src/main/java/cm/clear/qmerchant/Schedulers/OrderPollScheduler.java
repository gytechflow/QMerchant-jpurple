package cm.clear.qmerchant.Schedulers;

import androidx.annotation.NonNull;

import java.util.List;

import cm.clear.qmerchant.common.toggleOptions.ScheduleManager;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.models.order.Order;
import cm.clear.qmerchant.notificationService.ServiceGlobals;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class OrderPollScheduler extends AbstractPollScheduler{
    @NonNull
    private int latestBookingId = -1;

    public OrderPollScheduler(@NonNull ScheduleManager scheduleManager) {
        super(scheduleManager);
    }

    @NonNull
    @Override
    public String getId() {
        return ServiceGlobals.ORDER_SERVICE+"";
    }

    @Override
    public void startPolling() {
        setStarted(true);
        notifyManager();
    }

    @Override
    public void makeRequest() {
        getLatestBooking();
    }

    protected void getLatestBooking(){
        Call<List<Order>> latestBooking = ApiUtil.getOrdersService().getOrders("t.rowid", "DESC", "1", "" + 0, "");
        QCallback<List<Order>> onBookingRequested = new QCallback<List<Order>>() {
            @Override
            public void responseSuccessful(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.body() != null) {
                    if (!response.body().isEmpty()){
                        int returned_value = Integer.parseInt(response.body().get(0).getId());
                        updateValue(returned_value);
                    }
                }
            }

            @Override
            public void requestFailure(Call<List<Order>> call, Throwable t) {
                t.printStackTrace();
                notifyManager();
            }

            @Override
            public void responseUnsuccessful(Call<List<Order>> call, Response<List<Order>> response) {
                notifyManager();
            }
        };
        RequestManager.getInstance().addTempCall( new CallAndCallback<List<Order>>(latestBooking, onBookingRequested));
    }

    protected void updateValue(int returned_value) {
        if (latestBookingId>0){
            if (returned_value != latestBookingId){
                notifyPollObservers();
            }
        }
        latestBookingId = returned_value;
        notifyManager();
    }
}
