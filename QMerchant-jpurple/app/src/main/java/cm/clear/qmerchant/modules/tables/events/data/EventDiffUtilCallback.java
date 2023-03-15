package cm.clear.qmerchant.modules.tables.events.data;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import cm.clear.qmerchant.models.booking.Booking;

public class EventDiffUtilCallback extends DiffUtil.ItemCallback<Booking> {
    @Override
    public boolean areItemsTheSame(@NonNull Booking oldItem, @NonNull Booking newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Booking oldItem, @NonNull Booking newItem) {
        return oldItem.getPercentage().equalsIgnoreCase(newItem.getPercentage());
    }
}
