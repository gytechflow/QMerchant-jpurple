package cm.clear.qmerchant.modules.bookings.data;

import androidx.annotation.NonNull;

import java.util.List;

import cm.clear.qmerchant.models.booking.bookingStates.BookingState;
import cm.clear.qmerchant.common.listviewmanagement.stateStorage.StateSafe;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.models.qtable.QTable;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class BookingStateSafe extends StateSafe {
    private static BookingStateSafe INSTANCE;

    @NonNull
    public static BookingStateSafe getInstance(){
        if (INSTANCE == null)
            INSTANCE = new BookingStateSafe();
        return INSTANCE;
    }

    @Override
    protected void checkAndReload() {
        Booking booking = (Booking) selectedObject.getValue();
        if (state.getValue().getClient() == null){
            getClient(booking.getSocid());
        }

        BookingState bookingState = (BookingState) state.getValue();
        if (bookingState.hasTables()){
            if (!bookingState.haveTablesLoaded()){
                getTables(booking.getId());
            }
        }

    }

    @Override
    protected void reloadExtras() {
        getTables(((Booking) selectedObject.getValue()).getId());
    }

    @Override
    protected void reloadObject() {
        Booking booking = (Booking) selectedObject.getValue();
        Call<Booking> call = ApiUtil.getBookingService().getBooking(booking.getId());
        QCallback<Booking> qCallback = new QCallback<Booking>() {
            @Override
            public void responseSuccessful(Call<Booking> call, Response<Booking> response) {
                assert response.body()!=null;
                selectedObject.setValue(response.body());
                setState(BookingState.getState(response.body().getPercentage(), Integer.parseInt(response.body().getNbplace())));
            }

            @Override
            public void requestFailure(Call<Booking> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<Booking> call, Response<Booking> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback<>(call, qCallback));

    }

    private void getTables(String id) {
        Call<List<QTable>> call = ApiUtil.getBookingService().getAssignedResources(Integer.parseInt(id));
        QCallback<List<QTable>> qCallback = new QCallback<List<QTable>>() {

            @Override
            public void responseSuccessful(Call<List<QTable>> call, Response<List<QTable>> response) {
//                Log.d("J-Purple", "BookingsViewModel::responseSuccessful: ");
//                liveData.setValue(response.body());
                assert response.body() != null;
                BookingState bookingState = (BookingState) state.getValue();
                bookingState.setTablesUpdated(true);
                bookingState.setAssignedTables(response.body());
                state.setValue(bookingState);
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

