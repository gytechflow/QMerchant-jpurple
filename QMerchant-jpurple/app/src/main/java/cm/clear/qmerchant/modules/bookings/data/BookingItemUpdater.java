package cm.clear.qmerchant.modules.bookings.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.common.listviewmanagement.IAdapterItemChangeListener;
import cm.clear.qmerchant.common.listviewmanagement.ItemStateUpdater;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.models.Thirdparty;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.models.booking.bookingStates.BookingState;
import cm.clear.qmerchant.models.category.Category;
import cm.clear.qmerchant.models.qtable.QTable;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class BookingItemUpdater extends ItemStateUpdater {
    private static final String TAG = BookingItemUpdater.class.getName();

    public BookingItemUpdater(@NonNull List objects, @NonNull IAdapterItemChangeListener itemChangeListener) {
        super(objects, itemChangeListener);
    }

    public BookingItemUpdater(@NonNull IAdapterItemChangeListener itemChangeListener) {
        super(itemChangeListener);
    }

    @Override
    public void addToMap(@NonNull Object o) {
        Booking booking = (Booking)o;
        BookingState bookingState = BookingState.getState(booking.getPercentage(), Integer.parseInt(booking.getNbplace()));
        getStateHolderMap().put(o, new BookingStateHolder(bookingState));
    }

    @NonNull
    @Override
    public BookingState getObjectState(@NonNull Object o, int position) {
//        Log.e("J-Purple", "getObjectState() called with: o = [" + o + "], position = [" + position + "] found = ["+getStateHolderMap().containsKey(o)+"]");
        return (BookingState) super.getObjectState(o, position);
    }

    @Override
    public void loadExtras(@NonNull Object o) {
        loadThirdParty(o);
        getCategories((Booking)o);
        BookingState bookingState = (BookingState) getStateHolderMap().get(o).getState();
        if (bookingState.hasTables())
            loadTables(o);
    }

    /**
     *
     * @param o
     */
    private void loadThirdParty(Object o){
        Booking booking = (Booking)o ;
        MutableLiveData<Thirdparty> liveData = new MutableLiveData<>();
        Call<Thirdparty> call = ApiUtil.getCustomersService().getCustomerById(booking.getSocid());
        QCallback<Thirdparty> qCallback = new QCallback<Thirdparty>() {

            @Override
            public void responseSuccessful(Call<Thirdparty> call, Response<Thirdparty> response) {
                BookingStateHolder holder = (BookingStateHolder)getStateHolderMap().get(o);
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

    private void getCategories(@NonNull Booking booking){
        Call<List<Category>> requestForCategories =  ApiUtil.getBookingService().getCategoriesForBookingById(Integer.parseInt(booking.getId()), "actioncomm");
        QCallback<List<Category>> onCategoriesRequested = new QCallback<List<Category>>() {
            @Override
            public void responseSuccessful(Call<List<Category>> call, Response<List<Category>> response) {
                assert response.body() != null;
                BookingStateHolder holder = (BookingStateHolder)getStateHolderMap().get(booking);
                List<Category> requestedCategories = new ArrayList<>(response.body());
                holder.getState().setCategories(requestedCategories);
                //update(holder.getPosition());
            }

            @Override
            public void requestFailure(Call<List<Category>> call, Throwable t) {
                Log.e("JPurple", TAG+"::requestFailure: ", t.getCause());
                BookingStateHolder holder = (BookingStateHolder)getStateHolderMap().get(booking);
                holder.getState().setCategories(new ArrayList<>());
            }

            @Override
            public void responseUnsuccessful(Call<List<Category>> call, Response<List<Category>> response) {
                BookingStateHolder holder = (BookingStateHolder)getStateHolderMap().get(booking);
                holder.getState().setCategories(new ArrayList<>());
                try {
                    Log.e("JPurple", TAG+"::requestFailure: "+ response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        requestForCategories.enqueue(onCategoriesRequested);
    }

    /**
     *
     * @param o
     */
    private void loadTables(Object o) {
        Booking booking = (Booking)o ;
//        MutableLiveData<List<QTable>> liveData = new MutableLiveData<>();
        Call<List<QTable>> call = ApiUtil.getBookingService().getAssignedResources(Integer.parseInt(booking.getId()));
        QCallback<List<QTable>> qCallback = new QCallback<List<QTable>>() {

            @Override
            public void responseSuccessful(Call<List<QTable>> call, Response<List<QTable>> response) {
//                Log.d("J-Purple", "BookingsViewModel::responseSuccessful: ");
//                liveData.setValue(response.body());
                BookingStateHolder holder = (BookingStateHolder)getStateHolderMap().get(o);
                assert holder != null;
                assert response.body() != null;
                holder.getState().setTablesUpdated(true);
                holder.getState().setAssignedTables(response.body());
                if (holder.isValueUpdated(holder.getState())){
                    update(holder.getPosition());
                }
            }

            @Override
            public void requestFailure(Call<List<QTable>> call, Throwable t) {
//                Log.d("J-Purple", "BookingsViewModel::requestFailure: ");
//                liveData.setValue(new ArrayList<>());

            }

            @Override
            public void responseUnsuccessful(Call<List<QTable>> call, Response<List<QTable>> response) {
//                Log.d("J-Purple", "BookingsViewModel::responseUnsuccessful: ");
//                liveData.setValue(new ArrayList<>());
            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback<>(call, qCallback));
    }
}
