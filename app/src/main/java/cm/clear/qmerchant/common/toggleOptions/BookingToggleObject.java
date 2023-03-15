package cm.clear.qmerchant.common.toggleOptions;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.common.core.CommonFilters;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.modules.bookings.ui.BookingsViewModel;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class BookingToggleObject extends ToggleObject {

    private BookingsViewModel bookingsViewModel;

    public static final String DATE_ATTRIBUTE = "datep";
    private static final String TAG = BookingToggleObject.class.getName();

    public BookingToggleObject(@NonNull int optionText, @NonNull String optionValue, @NonNull ScheduleManager scheduleManager, int nots) {
        super(optionText, optionValue, scheduleManager);
        //notifyManager();
    }

    public BookingToggleObject(BookingsViewModel bookingsViewModel, @NonNull int optionText, @NonNull String optionValue, @NonNull ScheduleManager scheduleManager, @NonNull boolean clicked) {
        super(optionText, optionValue, scheduleManager);

        this.bookingsViewModel = bookingsViewModel;
        this.setClicked(clicked);
    }

    @Override
    public void onDataChange() {
        notifyManager();
    }

    /**
     *
     */
    protected void getLatestValue(String selectedDate) {

        String filter = CommonFilters.allForToday(DATE_ATTRIBUTE, selectedDate) + " AND " + getStateFilter();
        Call<String> countRequest = ApiUtil.getBookingService().getBookingsCount(ApiUtil.DEFAULT_SORT_FIELD_ALT, ApiUtil.DEFAULT_SORT_ORDER, ApiUtil.DEFAULT_LIMIT, filter);
        QCallback<String> onCountRequestMade = new QCallback<String>() {
            @Override
            public void responseSuccessful(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    updateCapacity(response.body());
                }
                notifyManager();
            }

            @Override
            public void requestFailure(Call<String> call, Throwable t) {
                notifyManager();
            }

            @Override
            public void responseUnsuccessful(Call<String> call, Response<String> response) {
                updateCapacity("0");
                notifyManager();
            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(countRequest, onCountRequestMade));
    }

    /**
     *
     * @return
     */
    private String getStateFilter(){
        return "t.percent = "+ optionValue;
    }

    @Override
    public void makeRequest() {
        String selectedDate = this.bookingsViewModel.getSelectedDate();
        Log.d("NEW-JPurple",TAG+ "::makeRequest() called "+optionValue + " and date : "+selectedDate);
        getLatestValue(selectedDate);
    }


}
