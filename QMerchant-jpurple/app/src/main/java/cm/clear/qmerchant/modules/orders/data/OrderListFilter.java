package cm.clear.qmerchant.modules.orders.data;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cm.clear.qmerchant.common.listprovider.IFilterable;
import cm.clear.qmerchant.common.listprovider.ListObject;
import cm.clear.qmerchant.common.listprovider.ListObserver;
import cm.clear.qmerchant.models.order.Order;
import cm.clear.qmerchant.modules.orders.ui.OrderAdapter;

public class OrderListFilter implements IFilterable, ListObserver {

    protected ListAdapter<Order, OrderAdapter.ViewHolder> adapter;
    private List<Order> unfilteredOrders = new ArrayList<>();
    private String order_type = "";
    private Long order_date_tms = 0L;
    private String order_date = "";

    public OrderListFilter() {
        //this.adapter = new OrderAdapter(new OrderDiffUtilCallback(), this);
    }

    @Override
    public void onDateChanged(@NonNull String date) {
        this.order_date = date;
    }

    @Override
    public void onDateChanged(@NonNull Long date_tms) {
        this.order_date_tms = date_tms;
    }

    @Override
    public void onTypeChanged(@NonNull String type) {
        this.order_type = type;
    }

    @Override
    public void onListUpdate(@NonNull List<ListObject> items) {
        List<Order> oldOrders = adapter.getCurrentList();
        //List<Order> orders = getFilteredOrders(toOrderType(items));
        List<Order> orders = toOrderType(items);
        oldOrders.addAll(orders);
        adapter.submitList(oldOrders);
    }

    @Override
    public void onReloadList(@NonNull List<ListObject> items) {
        unfilteredOrders = toOrderType(items);
        //List<Order> orders = getFilteredOrders(unfilteredOrders);
        adapter.submitList(unfilteredOrders);
    }

    @Override
    public void onReloadList() {
        adapter.submitList(null);
    }

    @NonNull
    public static List<Order> toOrderType(@NonNull List<ListObject> items){
        List<Order> ordersTemp = new ArrayList<>();
        for (ListObject item : items) {
            ordersTemp.add((Order)item);
        }

        return ordersTemp;
    }

    List<Order> getFilteredOrders(List<Order> orders){
        return  filterByDate(filterByType(orders));
    }

    List<Order> filterByDate(List<Order> orders){
        Comparator<Order> comparator = (order_1, order_2) -> Collator.getInstance().compare(order_1.getDateCommande(),order_2.getDateCommande());
        Collections.sort(orders,comparator);
        return orders;
    }

    List<Order> filterByType(List<Order> orders){
        Comparator<Order> comparator = (order_1, order_2) -> Collator.getInstance().compare(order_1.getStatus(),order_2.getStatus());
        Collections.sort(orders,comparator);
        return orders;
    }
}
