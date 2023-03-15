package cm.clear.qmerchant.modules.tables;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import cm.clear.qmerchant.models.booking.BookingActions;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class InstantOccupation {
    private static Booking getOccupationBooking(long start_time, long end_time){
        Booking booking = new Booking();
        booking.setNames("Instant Occupation");
        booking.setEmail("instant@quessadilla.com");
        booking.setPhone("0000000000");
        booking.setType_id("5");
        booking.setUserownerid("1");
        booking.setDatep(start_time / 1000L);
        booking.setDatep2(end_time / 1000L);
        return booking;
    }


    @NonNull
    public static LiveData<Integer> createInstantOccupation(@NonNull Context context, @NonNull Integer tableId, long start_time, long end_time){
        return createInstantBooking(context,getOccupationBooking(start_time, end_time), tableId);
    }

    private static LiveData<Integer> createInstantBooking(Context context, Booking occupationBooking, Integer tableId) {
        Log.d("J-Purple", "createInstantBooking() called with: context = [" + context + "], occupationBooking = [" + occupationBooking + "], tableId = [" + tableId + "]");
        MutableLiveData<Integer> result = new MutableLiveData<>();
        ApiUtil.getBookingService().createBooking(occupationBooking).enqueue(new QCallback<Integer>() {
            @Override
            public void responseSuccessful(Call<Integer> call, Response<Integer> response) {
                Log.d("J-Purple", "responseSuccessful() called with: call = [" + call + "], response = [" + response + "]");
                BookingActions.addResource(response.body().toString(), tableId.toString()).observe((LifecycleOwner) context, resourceResultState1 -> {
                    if (resourceResultState1 != null) {
                        if (resourceResultState1.isSuccessful()) {
                            Log.d("J-Purple", "onClick() called with: v = [ success two ]");
                            result.setValue(response.body());
                        } else {
                            result.setValue(-1);
                        }
                    }
                });
            }

            @Override
            public void requestFailure(Call<Integer> call, Throwable t) {
                Log.e("J-Purple", "requestFailure: ",t );
                result.setValue(-1);
            }

            @Override
            public void responseUnsuccessful(Call<Integer> call, Response<Integer> response) {
                Log.e("J-Purple", "responseUnsuccessful: "+response.errorBody().toString() );
                result.setValue(-1);
            }
        });
        return result;
    }

}
