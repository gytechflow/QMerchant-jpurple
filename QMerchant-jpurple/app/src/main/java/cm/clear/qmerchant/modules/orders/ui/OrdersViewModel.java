package cm.clear.qmerchant.modules.orders.ui;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.BR;
import cm.clear.qmerchant.R;
import cm.clear.qmerchant.common.interfaces.FilterChangeObserver;
import cm.clear.qmerchant.common.interfaces.QFilterManager;
import cm.clear.qmerchant.common.listprovider.ListObject;
import cm.clear.qmerchant.common.listprovider.ListObserver;
import cm.clear.qmerchant.common.listviewmanagement.ListChangeObserver;
import cm.clear.qmerchant.common.toggleOptions.OrderToggleObject;
import cm.clear.qmerchant.common.toggleOptions.ScheduleManager;
import cm.clear.qmerchant.common.toggleOptions.ToggleAdapter;
import cm.clear.qmerchant.common.toggleOptions.ToggleObject;
import cm.clear.qmerchant.common.toggleOptions.ToggleScheduleManager;
import cm.clear.qmerchant.models.order.Order;
import cm.clear.qmerchant.models.order.OrderActionsObserver;
import cm.clear.qmerchant.models.order.OrdersActions;
import cm.clear.qmerchant.modules.orders.data.OrderDiffUtilCallback;
import cm.clear.qmerchant.modules.orders.data.OrderListFilter;
import cm.clear.qmerchant.modules.orders.data.OrderListProvider;
import cm.clear.qmerchant.modules.orders.data.OrderStates.OrderState;
import cm.clear.qmerchant.modules.orders.data.OrdersMessenger;
import cm.clear.qmerchant.remote.ApiUtil;

public class OrdersViewModel extends BaseObservable implements FilterChangeObserver, ListChangeObserver, ListObserver, QFilterManager, OrderActionsObserver {
    private static final String TAG = OrdersViewModel.class.getName();
    public static final String DEFAULT_VALUE = String.valueOf(OrderState.DEFAULT_VALUE);
    @NonNull
    public Integer progressBarVisibility = View.VISIBLE;
    private  boolean loading;
    protected final OrderListProvider ordersRepo;
    private final ListAdapter<Order, OrderAdapter.ViewHolder> adapter;
    //private OrderListFilter orderListFilter;
    @NonNull
    protected final ScheduleManager scheduleManager = new ToggleScheduleManager("Orders", 5000);
    protected final List<ToggleObject> toggleObjects = new ArrayList<ToggleObject>() {{
        //add(new BookingToggleObject(R.string.all_text, "-1", scheduleManager));
        add(new OrderToggleObject(R.string.new_text, "0", scheduleManager));
        add(new OrderToggleObject(R.string.confirmed_text, "1", scheduleManager));
        add(new OrderToggleObject(R.string.in_process, "2", scheduleManager));
        add(new OrderToggleObject(R.string.delivered, "3", scheduleManager));
        add(new OrderToggleObject(R.string.canceled, "-1", scheduleManager));
    }};


    public OrdersViewModel(@NonNull Context context) {
        //Log.d("JPurple-LifeCycle", TAG+"::OrdersViewModel() called with: context = [" + context + "]");
        //OrdersActions.getINSTANCE().subscribe(this);
        OrdersMessenger.getSharedInstance().setFilterChangeObserver(this);
        OrdersMessenger.getSharedInstance().setListChangeObserver(this);
        //orderListFilter = new OrderListFilter();
        adapter = new OrderAdapter(new OrderDiffUtilCallback(), this);
        ordersRepo = new OrderListProvider(context, ApiUtil.getOrdersService(), this);
    }

    @NonNull
    public ListAdapter<Order, OrderAdapter.ViewHolder> getAdapter() {
        return adapter;
    }

    @Bindable
    @NonNull
    public Integer getProgressBarVisibility() {
        return progressBarVisibility;
    }

    public void setProgressBarVisibility(@NonNull Integer progressBarVisibility) {
        this.progressBarVisibility = progressBarVisibility;
        notifyPropertyChanged(BR.progressBarVisibility);
    }

    @Bindable
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean isLoading) {
        this.loading = isLoading;
        if (!loading)
            notifyPropertyChanged(BR.loading);
    }

    @NonNull
    public RecyclerView.Adapter<ToggleAdapter.ViewHolder> getToggleAdapter() {
        //Log.d("JPurple-LifeCycle", TAG+"::getToggleAdapter() called ");
        return new ToggleAdapter(toggleObjects);
    }

    @Override
    public void onFilterChange(@NonNull String value) {
        onReloadList();
        ordersRepo.onTypeChanged(value);
        requestStarted();
    }

    @Override
    public void onClearCalls() {

    }

    @Override
    public void refresh() {
        //Log.d("JPurple-NewArch", TAG+"::refresh() called");
        ordersRepo.reload();
        requestStarted();
    }

    @Override
    public void onListItemChange() {
        //Log.d("JPurple-NewArch", TAG+"::onListBottomReached() called");
        ordersRepo.reload();
        requestStarted();
    }

    @Override
    public void onListBottomReached() {
        //Log.d("JPurple-NewArch", TAG+"::onListBottomReached() called");
        ordersRepo.listBottomReached();
        requestStarted();
    }

    @Override
    public void onListUpdate(@NonNull List<ListObject> items) {
        Log.d("JPurple-NewArch", TAG + "::onListUpdate() called with: items = [" + items + "]");
        List<Order> oldOrders = new ArrayList<>(adapter.getCurrentList());
        //List<Order> orders = getFilteredOrders(toOrderType(items));
        List<Order> orders = new ArrayList<>(OrderListFilter.toOrderType(items));
        oldOrders.addAll(orders);
        adapter.submitList(oldOrders);
        requestEnded();
    }

    @Override
    public void onReloadList(@NonNull List<ListObject> items) {
        //Log.d("JPurple-NewArch", TAG+"::onReloadList() called with: items = [" + items + "]");
        List<Order> unfilteredOrders = OrderListFilter.toOrderType(items);
        //List<Order> orders = getFilteredOrders(unfilteredOrders);
        adapter.submitList(unfilteredOrders);
        requestEnded();
    }

    @Override
    public void onReloadList() {
        adapter.submitList(null);
    }

    @NonNull
    public RecyclerView.OnScrollListener getScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                assert layoutManager != null;
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!ordersRepo.isLoading() && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= OrderListProvider.PAGE_SIZE) {
                    // End of the list has been reached, request more items
                    onListBottomReached();
                }
            }
        };
    }

    public void newOrderMade() {

    }

    private void requestStarted() {
        setProgressBarVisibility(View.VISIBLE);
        setLoading(true);
    }

    private void requestEnded() {
        setProgressBarVisibility(View.GONE);
        setLoading(false);
    }

    public void onResume() {
        for (ToggleObject toggleObject : toggleObjects) {
            toggleObject.setFilterManager(this);
            toggleObject.start();
        }
        scheduleManager.start();
        ordersRepo.reload();
    }

    public void onStop() {
        for (ToggleObject toggleObject : toggleObjects) {
            //toggleObject.setFilterManager(BookingMessenger.getSharedInstance());
            toggleObject.stop();
        }
        scheduleManager.stop();
    }

    @Nullable
    @Override
    public FilterChangeObserver getFilterChangeObserver() {
        return null;
    }

    @Override
    public void changeFilter(@NonNull String value) {
        onFilterChange(value);
    }

    @Override
    public void onAction(boolean isActionStart) {
        if (isActionStart)
            requestStarted();
        else
            refresh();
    }

    public void onDestroy() {
        OrdersActions.getINSTANCE().unSubscribe(this);
    }
}

