package cm.clear.qmerchant.modules.orders.ui;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.common.core.TextHelper;
import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.common.listprovider.IFilterable;
import cm.clear.qmerchant.common.listviewmanagement.IAdapterItemChangeListener;
import cm.clear.qmerchant.databinding.ItemOrderListBinding;
import cm.clear.qmerchant.models.order.Order;
import cm.clear.qmerchant.models.order.OrderActionsObserver;
import cm.clear.qmerchant.models.order.OrdersActions;
import cm.clear.qmerchant.modules.bookings.ui.BookingsFragment;
import cm.clear.qmerchant.modules.orders.data.OrderItemUpdater;
import cm.clear.qmerchant.modules.orders.data.OrderStateSafe;
import cm.clear.qmerchant.modules.orders.data.OrderStates.OrderState;
import cm.clear.qmerchant.modules.orders.data.OrdersMessenger;

public class OrderAdapter extends ListAdapter<Order, OrderAdapter.ViewHolder> implements IFilterable, IAdapterItemChangeListener {
    private OrderItemUpdater orderItemUpdater;
    private final OrderActionsObserver actionsObserver;

    protected OrderAdapter(@NonNull AsyncDifferConfig<Order> config, @NonNull OrderActionsObserver actionsObserver) {
        super(config);
        this.actionsObserver = actionsObserver;
    }

    public OrderAdapter(@NonNull DiffUtil.ItemCallback<Order> diffCallback, @NonNull OrderActionsObserver actionsObserver) {
        super(diffCallback);
        orderItemUpdater = new OrderItemUpdater(this);
        this.actionsObserver = actionsObserver;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderListBinding binding = ItemOrderListBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        Order current_order = getItem(position);
        orderItemUpdater.register(current_order);
        OrderState state = orderItemUpdater.getObjectState(current_order, position);
        BindingObject bindingObject = new BindingObject(current_order, state, actionsObserver);
        holder.binding.setViewManager(bindingObject);
    }

    @Override
    public void onItemChanged(int position) {
        notifyItemChanged(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemOrderListBinding binding;
        public ViewHolder(@NonNull ItemOrderListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public void submitList(@Nullable List<Order> list) {
        super.submitList(list);
    }

    @Override
    public void submitList(@Nullable List<Order> list, @Nullable Runnable commitCallback) {
        super.submitList(list, commitCallback);
    }

    @NonNull
    @Override
    protected Order getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @NonNull
    @Override
    public List<Order> getCurrentList() {
        return super.getCurrentList();
    }

    @Override
    public void onCurrentListChanged(@NonNull List<Order> previousList, @NonNull List<Order> currentList) {
        super.onCurrentListChanged(previousList, currentList);
    }



    public static class BindingObject {
        private final Order order;
        private final OrderState state;
        private final OrderActionsObserver actionsObserver;
        private String order_ref;
        private String order_type;
        private String order_name;
        private String order_phone;
        private String order_date;
        private String order_start_time;
        private String order_end_time;
        private String order_assign_btn_tint;
        private Integer order_confirm_btn_tint;
        private Integer order_remove_btn_tint;


        public BindingObject(@NonNull Order order, @NonNull OrderState state, @NonNull OrderActionsObserver actionsObserver) {
            this.order = order;
            this.state = state;
            this.actionsObserver = actionsObserver;
        }

        @NonNull
        public Order getOrder() {
            return order;
        }

        @NonNull
        public OrderState getState() {
            return state;
        }

        @NonNull
        public String getDate(){
            return TmsConverter.getDate(Long.parseLong(order.getDateCreation()),
                    TmsConverter.FROM_SQL_JAVA);
        }

        @NonNull
        public String getTime(){
            return TmsConverter.getTime(Long.parseLong(order.getDateCreation()),
                    TmsConverter.FROM_SQL_JAVA);
        }

        @NonNull
        public String getAmount(){
            Double totalTtc = Double.parseDouble(order.getTotalTtc());
            return TextHelper.priceWithDecimal(totalTtc)+" "+order.getMulticurrencyCode();
        }

        @NonNull
        public String getName(){
            return (state.getClient() != null ? state.getClient().getName() : " ** Not yet");
        }

        @NonNull
        public String getPhone(){
            return (state.getClient() != null ? state.getClient().getPhone() : " ** Not yet");
        }

        @NonNull
        public String getId(){
            return (state.getClient() != null ? state.getClient().getId() : " ** Not yet");
        }

        public void onAssignedClicked(@NonNull View view){
            Bundle orderDetails = new Bundle();
            OrderStateSafe.getInstance().setStateObject(order);
            OrderStateSafe.getInstance().setState(state);
//                listener.onNavigate(BookingsFragment.BOOKING_AND_STATE_KEY, orderDetails, R.id.show_resources_fragment);
            OrdersMessenger.getSharedInstance().navigate(BookingsFragment.BOOKING_AND_STATE_KEY, orderDetails, R.id.show_orderDetails_fragment);
        }

        public void onConfirmedClicked(@NonNull View view){
            actionsObserver.onConfirm(true);
            Objects.requireNonNull(state.getConfirmResult(order.getId())).observe((LifecycleOwner) view.getContext(), resourceResult -> {
                actionsObserver.onConfirm(false);
                if (resourceResult!=null){
                    if (resourceResult.isSuccessful()){
                        OrdersMessenger.getSharedInstance().listItemChanged();
                    }
                }
            });
        }

        public void onRemoveClicked(@NonNull View view){
            actionsObserver.onCancel(true);
            OrdersActions.getINSTANCE().cancelOrder(order.getId()).observe((LifecycleOwner) view.getContext(), resourceResultState -> {
                actionsObserver.onCancel(false);
                if (resourceResultState!=null){
                    if (resourceResultState.isSuccessful()){
                        OrdersMessenger.getSharedInstance().listItemChanged();
                    }
                }
            });
        }

        public void onRefClicked(@NonNull View view){
            Bundle orderDetails = new Bundle();
            OrderStateSafe.getInstance().setStateObject(order);
            OrderStateSafe.getInstance().setState(state);
            OrdersMessenger.getSharedInstance().navigate(BookingsFragment.BOOKING_AND_STATE_KEY, orderDetails, R.id.show_orderDetails_fragment);
        }
    }


}
