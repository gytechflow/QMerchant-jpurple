package cm.clear.qmerchant.modules.orders.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cm.clear.qmerchant.common.listviewmanagement.IAdapterItemChangeListener;
import cm.clear.qmerchant.common.listviewmanagement.ItemStateUpdater;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.models.order.Order;
import cm.clear.qmerchant.models.Thirdparty;
import cm.clear.qmerchant.modules.orders.data.OrderStates.OrderState;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class OrderItemUpdater extends ItemStateUpdater {
    public OrderItemUpdater(@NonNull List objects, @NonNull IAdapterItemChangeListener itemChangeListener) {
        super(objects, itemChangeListener);
    }

    public OrderItemUpdater(@NonNull IAdapterItemChangeListener itemChangeListener) {
        super(itemChangeListener);
    }

    @NonNull
    @Override
    public OrderState getObjectState(@NonNull Object o, int position) {
        return (OrderState) super.getObjectState(o, position);
    }

    @Override
    public void addToMap(@NonNull Object o) {
        Order order = (Order)o;
        int status = Integer.parseInt(order.getBilled()) + Integer.parseInt(order.getStatus());
//        Log.e("J-Purple", "responseSuccessful__: "+status );
        if (status != OrderState.ORDER_IN_PREPARATION)
            status =  Integer.parseInt(order.getStatus());
        OrderState bookingState = OrderState.getOrderState(status);
        getStateHolderMap().put(o, new OrderStateHolder(bookingState));
    }

    @Override
    public void loadExtras(@NonNull Object o) {
        loadThirdParty(o);
    }


    /**
     *
     * @param o
     */
    private void loadThirdParty(Object o){
        Order order = (Order)o ;
        MutableLiveData<Thirdparty> liveData = new MutableLiveData<>();
        Call<Thirdparty> call = ApiUtil.getCustomersService().getCustomerById(order.getSocid());
        QCallback<Thirdparty> qCallback = new QCallback<Thirdparty>() {

            @Override
            public void responseSuccessful(Call<Thirdparty> call, Response<Thirdparty> response) {
                OrderStateHolder holder = (OrderStateHolder)getStateHolderMap().get(o);
                assert holder != null;
                assert response.body() != null;
                holder.getState().setClient(response.body());
                if (holder.isValueUpdated(holder.getState())){
                    update(holder.getPosition());
                }
            }
            @Override
            public void requestFailure(Call<Thirdparty> call, Throwable t) {

            }
            @Override
            public void responseUnsuccessful(Call<Thirdparty> call, Response<Thirdparty> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(call, qCallback));
    }
}
