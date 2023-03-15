package cm.clear.qmerchant.modules.orders.data;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.common.listviewmanagement.stateStorage.StateSafe;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.models.order.Order;
import cm.clear.qmerchant.modules.orders.data.OrderStates.OrderState;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class OrderStateSafe extends StateSafe {
    private static OrderStateSafe INSTANCE;

    @NonNull
    public static OrderStateSafe getInstance(){
        if (INSTANCE == null)
            INSTANCE = new OrderStateSafe();
        return INSTANCE;
    }

    @Override
    protected void checkAndReload() {
        Order order = (Order) selectedObject.getValue();
        if (state.getValue().getClient() == null){
            getClient(order.getSocid());
        }
    }

    @Override
    protected void reloadExtras() {
        Order order = (Order) selectedObject.getValue();
        getClient(order.getSocid());
    }

    @Override
    protected void reloadObject() {
        Order order = (Order) selectedObject.getValue();
        Call<Order> call = ApiUtil.getOrdersService().getOrderById(order.getId(),"1");
        QCallback<Order> qCallback = new QCallback<Order>() {
            @Override
            public void responseSuccessful(Call<Order> call, Response<Order> response) {
                assert response.body()!=null;
                selectedObject.setValue(response.body());
                Order new_order = response.body();
                int status = Integer.parseInt(new_order.getBilled()) + Integer.parseInt(new_order.getStatus());
//                Log.e("J-Purple", "responseSuccessful: "+status );
                if (status != OrderState.ORDER_IN_PREPARATION)
                    status =  Integer.parseInt(new_order.getStatus());
                setState(OrderState.getOrderState(status));
            }

            @Override
            public void requestFailure(Call<Order> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<Order> call, Response<Order> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback<>(call, qCallback));
    }
}

