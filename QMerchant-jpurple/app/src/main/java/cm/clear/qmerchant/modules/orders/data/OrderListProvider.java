package cm.clear.qmerchant.modules.orders.data;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.common.core.CommonFilters;
import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.common.listprovider.IFilterable;
import cm.clear.qmerchant.common.listprovider.ListObserver;
import cm.clear.qmerchant.common.listprovider.ListRepository;
import cm.clear.qmerchant.dashboard.custom.concreteobjects.OrderCounter;
import cm.clear.qmerchant.models.order.Order;
import cm.clear.qmerchant.modules.orders.data.OrderStates.OrderState;
import cm.clear.qmerchant.modules.orders.ui.OrdersViewModel;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.remote.OrdersRequestInterface;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class OrderListProvider implements ListRepository, IFilterable {
    private static final String TAG = OrderListProvider.class.getName();
    protected final Context context;
    protected final ListObserver listObserver;
    private Boolean loading;
    protected int page = 0;
    private String order = "DESC";
    private String filter = "";
    private String order_type = OrdersViewModel.DEFAULT_VALUE;
    private String order_date = "";
    public static final int PAGE_SIZE = 5;
    private final OrdersRequestInterface requestInterface;
    protected int call_id;
    private Call<List<Order>> callForOrders;

    public OrderListProvider(@NonNull Context context, @NonNull OrdersRequestInterface requestInterface, @NonNull ListObserver listObserver) {
        this.context = context;
        this.listObserver = listObserver;
        this.requestInterface = requestInterface;
        loadItems();
    }

    private void loadItems() {
        //Log.d("JPurple-NewArch", TAG+"::loadItems() called");
        if (callForOrders!=null)
            callForOrders.cancel();
        loading = true;
        filter = getFilter();
        callForOrders = requestInterface.getOrders("t.rowid", ""+order, ""+PAGE_SIZE, "" + page, filter);
        QCallback<List<Order>> onOrdersLoaded = new QCallback<List<Order>>() {
            @Override
            public void responseSuccessful(Call<List<Order>> call, Response<List<Order>> response) {
                //Log.d("JPurple-NewArch", TAG+"::responseSuccessful() called with size =["+response.body().size()+"]");
                loading = false;
                if (page<1)
                    listObserver.onReloadList(new ArrayList<>(response.body()));
                else listObserver.onListUpdate(new ArrayList<>(response.body()));

                if (response.body().isEmpty())
                    page--;
            }

            @Override
            public void requestFailure(Call<List<Order>> call, Throwable t) {
                //Log.d("JPurple-NewArch", TAG+"::requestFailure() called ");
                Toast.makeText(context, "Request for orders failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void responseUnsuccessful(Call<List<Order>> call, Response<List<Order>> response) {
                //Log.d("JPurple-NewArch", TAG+"::responseUnsuccessful() called ");
                listObserver.onListUpdate(new ArrayList<>());
                Toast.makeText(context, "Request for orders failed", Toast.LENGTH_SHORT).show();
            }
        };
        callForOrders.enqueue(onOrdersLoaded);
    }

    private void resetCallId() {
        long time = System.currentTimeMillis();
        call_id = (int) (time & 0xfffffff);
    }

    private String getFilter() {
        return getDateFilter() + " AND " + getTypeFilter();
    }

    private String getTypeFilter() {
        if (order_type.equalsIgnoreCase(String.valueOf(OrderState.ALL_ORDERS)))
            return ApiUtil.ALWAYS_TRUE_FILTER;
        if (order_type.equalsIgnoreCase(String.valueOf(OrderState.ORDER_IN_PREPARATION)))
            return "t.fk_statut = " + OrderState.ORDER_CONFIRMED + " AND facture = 1";
        if (order_type.equalsIgnoreCase(String.valueOf(OrderState.ORDER_CONFIRMED)))
            return "t.fk_statut = " + OrderState.ORDER_CONFIRMED + " AND facture = 0";
        return "t.fk_statut = " + order_type;
    }

    private String getDateFilter() {
        if (TextUtils.isEmpty(order_date))
            return CommonFilters.fromToday(OrderCounter.DATE_ATTRIBUTE);
        return order_date;
    }

    @Override
    public void listBottomReached() {
        page++;
        loadItems();
    }

    @Override
    public void reload() {
        page = 0;
        loadItems();
    }

    @Override
    public boolean isLoading() {
        return loading;
    }

    @Override
    public void onTypeChanged(@NonNull String type) {
        this.order_type = type;
        reload();
    }

    @Override
    public void onDateChanged(@NonNull Long date_tms) {
        this.order_date = TmsConverter.getDateForQuery(date_tms, TmsConverter.FROM_SQL_JAVA);
        reload();
    }
}
