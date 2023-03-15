package cm.clear.qmerchant.Schedulers;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.common.toggleOptions.ScheduleManager;
import cm.clear.qmerchant.interfaces.ResponseListener;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.notificationService.ServiceGlobals;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class BookingPollScheduler extends AbstractPollScheduler implements ResponseListener {
    private static final String TAG = BookingPollScheduler.class.getName();
    @NonNull
    private int latestBookingId = -1;
    @NonNull
    protected int latestCall = 0 ;
    @NonNull
    protected List<Integer> receivedBookingIds = new ArrayList<>();

    public BookingPollScheduler(@NonNull ScheduleManager scheduleManager) {
        super(scheduleManager);
    }


    @NonNull
    @Override
    public String getId() {
        return ServiceGlobals.BOOKING_SERVICE+"";
    }

    @Override
    public void startPolling() {
//        Log.d("JPurple:Notifications", TAG+ "::startPolling() called");
        setStarted(true);
        notifyManager();
    }

    protected void getLatestBooking(){
//        Log.d("JPurple",TAG+ "getLatestBooking() called : "+latestCall++);
        Call<List<Booking>> latestBooking = ApiUtil.getBookingService().getBookings("t.id", "DESC", "1", "" + 0, "");
        QCallback<List<Booking>> onBookingRequested = new QCallback<List<Booking>>() {
            @Override
            public void responseSuccessful(Call<List<Booking>> call, Response<List<Booking>> response) {
//                Log.d("JPurple:Notifications", TAG+ "::responseSuccessful()");
                    if (response.body() != null) {
                        if (!response.body().isEmpty()){
                            int returned_value = Integer.parseInt(response.body().get(0).getId());
                            updateValue(returned_value);
                        }
                    }
            }

            @Override
            public void requestFailure(Call<List<Booking>> call, Throwable t) {
                t.printStackTrace();
                //getLatestBooking();
                notifyManager();
            }

            @Override
            public void responseUnsuccessful(Call<List<Booking>> call, Response<List<Booking>> response) {
//                Log.e(TAG, "responseUnsuccessful: "+ response.raw());
                //getLatestBooking();
                notifyManager();
            }
        };
        RequestManager.getInstance().addTempCall( new CallAndCallback<List<Booking>>(latestBooking, onBookingRequested));
    }

    protected void updateValue(int returned_value) {
//        Log.d("JPurple",TAG+ "updateValue() called with: returned_value = [" + returned_value + "] latestBookingId = ["+latestBookingId+"]");
        if (latestBookingId>0){
            if (returned_value != latestBookingId){
                Log.d("JPurple:Notifications", TAG+"::booking polling -- "+returned_value+" : "+latestBookingId+"-->");
                notifyPollObservers();
            }
        }
//        latestBookingId.setValue(returned_value);
        latestBookingId = returned_value;
//        Log.d("JPurple:Notifications", TAG+"::latestBookingId : "+latestBookingId);
        //getLatestBooking();
        notifyManager();
    }

    @Override
    public void reloadRequest() {
        //getLatestBooking();
    }

    @Override
    public void makeRequest() {
        getLatestBooking();
    }

    protected static class BookingValue {
        private int value = -1;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
