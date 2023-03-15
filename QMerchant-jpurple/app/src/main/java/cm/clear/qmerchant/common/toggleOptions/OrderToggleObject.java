package cm.clear.qmerchant.common.toggleOptions;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.common.core.CommonFilters;
import cm.clear.qmerchant.dashboard.custom.concreteobjects.OrderCounter;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.modules.orders.data.OrderStates.OrderState;
import cm.clear.qmerchant.modules.orders.ui.OrdersViewModel;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class OrderToggleObject extends ToggleObject{
    private static final String TAG =OrderToggleObject.class.getName() ;

    public OrderToggleObject(int optionText, @NonNull String optionValue, @NonNull ScheduleManager scheduleManager) {
        super(optionText, optionValue, scheduleManager);
        if (optionValue.equalsIgnoreCase(OrdersViewModel.DEFAULT_VALUE))
            this.setClicked(true);
    }

    @Override
    public void onDataChange() {

    }

    @Override
    public void makeRequest() {
        getLatestValue();
    }

    private void getLatestValue() {
        String filter = CommonFilters.fromToday(OrderCounter.DATE_ATTRIBUTE) + " AND " + getStateFilter();
//        Log.e("JPurple",TAG+ "::getLatestValue() called for: "+filter);
        Call<String> countRequest = ApiUtil.getOrdersService().getOrdersCount(ApiUtil.DEFAULT_SORT_FIELD, ApiUtil.DEFAULT_SORT_ORDER, ApiUtil.DEFAULT_LIMIT, filter);
        QCallback<String> onCountRequestMade = new QCallback<String>() {
            @Override
            public void responseSuccessful(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    updateCapacity(response.body());
//                    Log.d("JPurple",TAG+ "::responseSuccessful: "+response.raw().toString());
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
