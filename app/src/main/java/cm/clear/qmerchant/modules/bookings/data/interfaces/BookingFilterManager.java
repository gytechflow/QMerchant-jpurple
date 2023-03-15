package cm.clear.qmerchant.modules.bookings.data.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface BookingFilterManager{
    @Nullable
    BookingsChangeObserver getBookingsChangeObserver();

    default void changeDate(@NonNull Long tms){
        if (getBookingsChangeObserver()==null){
            throw new NullPointerException("BookingsChangeObserver is null");
        }

        getBookingsChangeObserver().onDateChange(tms);
    };


}
