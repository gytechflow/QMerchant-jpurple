package cm.clear.qmerchant.models.booking.comparator;

import androidx.annotation.NonNull;

import java.util.Comparator;

import cm.clear.qmerchant.models.booking.Booking;

public class EventOwnerComparator implements Comparator<Booking> {
    @Override
    public int compare(@NonNull Booking booking, @NonNull Booking booking2) {
        return booking.getNames().compareTo(booking2.getNames());
    }
}
