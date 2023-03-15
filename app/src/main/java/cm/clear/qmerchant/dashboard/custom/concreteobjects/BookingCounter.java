package cm.clear.qmerchant.dashboard.custom.concreteobjects;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.common.core.CommonFilters;
import cm.clear.qmerchant.dashboard.custom.abstractclasses.CompositeDashBoardCounter;
import cm.clear.qmerchant.dashboard.custom.interfaces.CompositeCountListener;
import cm.clear.qmerchant.remote.ApiUtil;
import retrofit2.Call;

public class BookingCounter extends CompositeDashBoardCounter {
    private static final String NEW_BOOKING_STATUS = "0";
    public static final String DATE_ATTRIBUTE = "datep";
    private final String NEW_CLIENT_VALUE = "";
    public BookingCounter(@NonNull CompositeCountListener compositeCountListener) {
        super(compositeCountListener);
    }

    @NonNull
    @Override
    protected Call<String> getTotalCall(){
        String filter = CommonFilters.fromToday(DATE_ATTRIBUTE);
        return ApiUtil.getBookingService().getBookingsCount(ApiUtil.DEFAULT_SORT_FIELD_ALT, ApiUtil.DEFAULT_SORT_ORDER, ApiUtil.DEFAULT_LIMIT, filter);
    }


    @NonNull
    @Override
    protected Call<String> getCall(){
        String filter = CommonFilters.fromToday(DATE_ATTRIBUTE) + " AND " + getStateFilter();
//        Log.e("J-Purple", "getCall::BookingCounter: "+filter);
        return ApiUtil.getBookingService().getBookingsCount(ApiUtil.DEFAULT_SORT_FIELD_ALT, ApiUtil.DEFAULT_SORT_ORDER, ApiUtil.DEFAULT_LIMIT, filter);
    }

    private String getStateFilter(){
        return "t.percent = "+ NEW_BOOKING_STATUS;
    }
}
