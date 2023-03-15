package cm.clear.qmerchant.dashboard.custom.concreteobjects;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.common.core.CommonFilters;
import cm.clear.qmerchant.dashboard.custom.abstractclasses.CompositeDashBoardCounter;
import cm.clear.qmerchant.dashboard.custom.interfaces.CompositeCountListener;
import cm.clear.qmerchant.remote.ApiUtil;
import retrofit2.Call;

public class ClientCounter extends CompositeDashBoardCounter {
    private final String NEW_CLIENT_VALUE = "";
    public static final String DATE_ATTRIBUTE = "tms";
    public ClientCounter(@NonNull CompositeCountListener compositeCountListener) {
        super(compositeCountListener);
    }

    @Override
    protected Call<String> getTotalCall(){
        String filter = /*CommonFilters.fromToday(DATE_ATTRIBUTE)*/"";
        return ApiUtil.getCustomersService().getCustomersCount(ApiUtil.DEFAULT_SORT_FIELD, ApiUtil.DEFAULT_SORT_ORDER, ApiUtil.DEFAULT_LIMIT, filter);
    }


    @Override
    protected Call<String> getCall(){
//        String filter = "t.tms >= DATE '" + formatter.format(calendar.getTime())+"'";
        String filter = CommonFilters.fromToday(DATE_ATTRIBUTE);
//        Log.e("J-Purple", "getCall::ClientCounter: "+filter );
        return ApiUtil.getCustomersService().getCustomersCount(ApiUtil.DEFAULT_SORT_FIELD, ApiUtil.DEFAULT_SORT_ORDER, ApiUtil.DEFAULT_LIMIT, filter);
    }
}
