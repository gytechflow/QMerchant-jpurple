package cm.clear.qmerchant.modules.orders.data;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import cm.clear.qmerchant.models.order.Order;

public class OrderDiffUtilCallback extends DiffUtil.ItemCallback<Order> {
    @Override
    public boolean areItemsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Order oldItem, @NonNull Order newItem) {
        return oldItem.getStatus().equalsIgnoreCase(newItem.getStatus());
    }
}
