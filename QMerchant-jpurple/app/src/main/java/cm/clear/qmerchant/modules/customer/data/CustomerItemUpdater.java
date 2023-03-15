package cm.clear.qmerchant.modules.customer.data;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.List;

import cm.clear.qmerchant.common.listviewmanagement.IAdapterItemChangeListener;
import cm.clear.qmerchant.common.listviewmanagement.ItemStateUpdater;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.models.order.Order;
import cm.clear.qmerchant.models.Thirdparty;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class CustomerItemUpdater extends ItemStateUpdater {
    public CustomerItemUpdater(@NonNull List objects, @NonNull IAdapterItemChangeListener adapter) {
        super(objects, adapter);
    }

    @Override
    public void addToMap(@NonNull Object o) {
        getStateHolderMap().put(o, new CustomerStateHolder());
    }

    @Override
    public void loadExtras(@NonNull Object o) {
        requestOrders((Thirdparty)o);
        requestBookings((Thirdparty)o);
    }

    @NonNull
    @Override
    public CustomerState getObjectState(@NonNull Object o, int position) {
        return (CustomerState) super.getObjectState(o, position);
    }

    private void requestBookings(Thirdparty thirdparty) {
        Call<List<Booking>> call = ApiUtil.getBookingService().getBookings("t.id", "AsC", "100", "t.fk_soc ="+thirdparty.getId());
        QCallback<List<Booking>> QCallback = new QCallback<List<Booking>>() {
            @SuppressLint("SyntheticAccessor")
            @Override
            public void responseSuccessful(Call<List<Booking>> call, Response<List<Booking>> response) {
//                bookingsList.setValue(response.body());
                assert response.body() != null;
                CustomerState tempState = new CustomerState();
                tempState.setBookingCount(response.body().size());
                CustomerStateHolder holder = (CustomerStateHolder)getStateHolderMap().get(thirdparty);
                assert holder != null;
                tempState.setOrderCount(holder.getState().getOrderCount());

                if (holder.isValueUpdated(tempState)){
//                    Log.i("J-Purple", "responseSuccessful: thirdparty=["+thirdparty.getId()+"] holder =["+holder.toString()+"]");
//                    itemChangeListener.onItemChanged(customerBookingsMap.get(thirdparty)[2]);
                    update(holder.getPosition());
                }
            }

            @Override
            public void requestFailure(Call<List<Booking>> call, Throwable t) {

            }

            @SuppressLint("SyntheticAccessor")
            @Override
            public void responseUnsuccessful(Call<List<Booking>> call, Response<List<Booking>> response) {
                if (response.raw().toString().contains("Not Found")){
//                    reload(new ArrayList<>());
                }

            }
        };
        CallAndCallback<List<Booking>> main_call = new CallAndCallback<List<Booking>>(call, QCallback);
        RequestManager.getInstance().clear(this);
        RequestManager.getInstance().addTempCall(main_call);
    }

    private void requestOrders(Thirdparty thirdparty) {
        long today = Calendar.getInstance().getTime().getTime() / 1000L;
        String filter = "t.date_commande <= FROM_UNIXTIME(" + today + ") AND t.fk_soc="+thirdparty.getId();
        Call<List<Order>> call = ApiUtil.getOrdersService().getOrders("t.rowid", "AsC", "100", filter);
        QCallback<List<Order>> QCallback = new QCallback<List<Order>>() {
            @Override
            public void responseSuccessful(Call<List<Order>> call, Response<List<Order>> response) {
                assert response.body() != null;
                CustomerState tempState = new CustomerState();
                tempState.setOrderCount(response.body().size());
                CustomerStateHolder holder = (CustomerStateHolder)getStateHolderMap().get(thirdparty);
                assert holder != null;
                tempState.setBookingCount(holder.getState().getBookingCount());
                if (holder.isValueUpdated(tempState)){
//                    Log.i("J-Purple", "responseSuccessful: thirdparty=["+thirdparty.getId()+"] name = ["+thirdparty.getName()+"] holder =["+holder.toString()+"]");
//                    itemChangeListener.onItemChanged(customerBookingsMap.get(thirdparty)[2]);
                    update(holder.getPosition());
                }

            }

            @Override
            public void requestFailure(Call<List<Order>> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<List<Order>> call, Response<List<Order>> response) {

            }
        };
        CallAndCallback main_call = new CallAndCallback<List<Order>>(call, QCallback);
        RequestManager.getInstance().clear(this);
        RequestManager.getInstance().addTempCall(main_call);
    }
}
