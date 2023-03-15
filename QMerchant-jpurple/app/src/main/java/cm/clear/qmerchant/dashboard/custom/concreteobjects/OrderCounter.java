package cm.clear.qmerchant.dashboard.custom.concreteobjects;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.common.core.CommonFilters;
import cm.clear.qmerchant.dashboard.custom.abstractclasses.CompositeDashBoardCounter;
import cm.clear.qmerchant.dashboard.custom.interfaces.CompositeCountListener;
import cm.clear.qmerchant.models.order.Order;
import cm.clear.qmerchant.remote.ApiUtil;
import retrofit2.Call;

/**
 * created on: 17/01/22
 * by : J-Purple
 *
 * concrete object class that will implement methods required by {@link CompositeDashBoardCounter} according to orders
 */
public class OrderCounter extends CompositeDashBoardCounter {
    public static final String DATE_ATTRIBUTE = "date_commande";
    private final String NEW_ORDER_STATUS = "0";

    public OrderCounter(@NonNull CompositeCountListener compositeCountListener) {
        super(compositeCountListener);
    }


    @Override
    public Call<String> getCall(){
//        String filter = "t.date_commande >= FROM_UNIXTIME(" + Calendar.getInstance().getTime().getTime() / 1000L + ")"  + getStateFilter();
        String dateFilter = CommonFilters.fromToday(DATE_ATTRIBUTE);
        String filter = dateFilter + getStateFilter();
//        Log.e("J-Purple", "getCall::OrderCounter: "+filter );
        return ApiUtil.getOrdersService().getOrdersCount("t.rowid", "AsC", "100", filter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Call<String> getTotalCall() {
        //        LocalDate.now();
//        String filter = "t.date_commande >= DATE '" + formatter.format(calendar.getTime())+"'";
        String filter = CommonFilters.fromToday(DATE_ATTRIBUTE);
//        Log.e("J-Purple", "getTotalCall::OrderCounter: "+filter );
        return ApiUtil.getOrdersService().getOrdersCount("t.rowid", "AsC", "100", filter);
    }

    private void filterNew(List<Order> orderList){
        List<Order> orders = new ArrayList<>();
        for (Order order : orderList) {
            if (!NEW_ORDER_STATUS.equalsIgnoreCase("-1")) {
//                Log.d("J-Purple", "updatePercentage() called with: selectedPercentage = [" + FILTER_VALUE + "] bookingPercentage = ["+order.getStatus()+"] bookingRef = ["+order.getRef()+"]");
                if (order.getStatus().equalsIgnoreCase(NEW_ORDER_STATUS))
                    orders.add(order);
            } else {
                orders = new ArrayList<>(orderList);
                break;
            }
        }
        setPreviousCounter(getNewCounter());
        setNewCounter(orders.size());
        if (isCounterDifferent()){
//            Log.d("J-Purple", "filterNew() called with different sizes");
            getCompositeCountListener().onCompositeCountChange(OrderCounter.this);
        }
    }

    private String getStateFilter(){
        return " AND t.fk_statut = "+ NEW_ORDER_STATUS;
    }
}
