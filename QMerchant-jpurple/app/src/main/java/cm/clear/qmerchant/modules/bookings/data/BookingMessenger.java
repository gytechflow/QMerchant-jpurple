package cm.clear.qmerchant.modules.bookings.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cm.clear.qmerchant.modules.bookings.data.interfaces.BookingFilterManager;
import cm.clear.qmerchant.common.interfaces.AdapterManager;
import cm.clear.qmerchant.common.interfaces.FilterChangeObserver;
import cm.clear.qmerchant.common.interfaces.NavigationListener;
import cm.clear.qmerchant.modules.bookings.data.interfaces.BookingsChangeObserver;
import cm.clear.qmerchant.common.interfaces.QFilterManager;
import cm.clear.qmerchant.common.listviewmanagement.ListChangeObserver;

/**
 * this class handles communication between the major components
 * of the
 */
public class BookingMessenger implements BookingFilterManager, QFilterManager, AdapterManager {

    private BookingsChangeObserver bookingsChangeObserver;
    private NavigationListener navigationListener;
    private static BookingMessenger INSTANCE;

    public BookingMessenger() { }

    @NonNull
    public static BookingMessenger getSharedInstance() {
        if (INSTANCE == null)
            INSTANCE = new BookingMessenger();
        return INSTANCE;
    }

    public void setAdapterObserver(@NonNull BookingsChangeObserver bookingsChangeObserver) {
        this.bookingsChangeObserver = bookingsChangeObserver;
    }

    public void setNavigationListener(@NonNull NavigationListener navigationListener) {
        this.navigationListener = navigationListener;
    }

    @Nullable
    @Override
    public BookingsChangeObserver getBookingsChangeObserver() {
        return bookingsChangeObserver;
    }

    @NonNull
    @Override
    public ListChangeObserver getListChangeObserver() {
        return bookingsChangeObserver;
    }

    @NonNull
    @Override
    public FilterChangeObserver getFilterChangeObserver() {
        return this.bookingsChangeObserver;
    }

    @NonNull
    @Override
    public NavigationListener getNavigationListener() {
        return this.navigationListener;
    }


}
