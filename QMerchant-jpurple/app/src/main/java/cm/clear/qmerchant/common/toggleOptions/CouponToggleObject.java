package cm.clear.qmerchant.common.toggleOptions;

import android.util.Log;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.modules.orders.data.OrderStates.OrderState;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class CouponToggleObject extends ToggleObject{


    private static final String TAG =CouponToggleObject.class.getName() ;

    public CouponToggleObject(int optionText, @NonNull String optionValue, @NonNull ScheduleManager scheduleManager) {
        super(optionText, optionValue, scheduleManager);
    }

    @Override
    public void onDataChange() {

    }

    @Override
    public void makeRequest() {
        getLatestValue();
    }

    private void getLatestValue() {
        String filter = "t.coupon_state = "+optionValue;
        Log.e("JPurple",TAG+ "::getLatestValue() called for: "+filter);
        Call<String> countRequest = ApiUtil.getCouponService().getCouponsCount(filter);
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

    private String getStateFilter() {
        if (optionValue.equalsIgnoreCase(String.valueOf(OrderState.ALL_ORDERS)))
            return ApiUtil.ALWAYS_TRUE_FILTER;
        if (optionValue.equalsIgnoreCase(String.valueOf(OrderState.ORDER_IN_PREPARATION)))
            return "t.fk_statut = " + OrderState.ORDER_CONFIRMED + " AND facture = 1";
        if (optionValue.equalsIgnoreCase(String.valueOf(OrderState.ORDER_CONFIRMED)))
            return "t.fk_statut = " + OrderState.ORDER_CONFIRMED + " AND facture = 0";
        return "t.fk_statut = " + optionValue;
    }
}
