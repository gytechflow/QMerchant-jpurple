package cm.clear.qmerchant.modules.bookings.data.interfaces;

import cm.clear.qmerchant.common.interfaces.FilterChangeObserver;
import cm.clear.qmerchant.common.listviewmanagement.ListChangeObserver;

public interface BookingsChangeObserver extends FilterChangeObserver, ListChangeObserver {

    void onCounterChange(String counterDisplay);
    void onDateChange(Long tms);
}
