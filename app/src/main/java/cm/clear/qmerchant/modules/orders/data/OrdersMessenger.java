package cm.clear.qmerchant.modules.orders.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cm.clear.qmerchant.common.interfaces.AdapterManager;
import cm.clear.qmerchant.common.interfaces.FilterChangeObserver;
import cm.clear.qmerchant.common.interfaces.NavigationListener;
import cm.clear.qmerchant.common.interfaces.QFilterManager;
import cm.clear.qmerchant.common.listviewmanagement.ListChangeObserver;

public class OrdersMessenger implements QFilterManager, AdapterManager {
    private FilterChangeObserver filterChangeObserver;
    private NavigationListener navigationListener;
    @Nullable
    public static OrdersMessenger INSTANCE;
    private ListChangeObserver listChangeObserver;


    @NonNull
    public static OrdersMessenger getSharedInstance() {
        if (INSTANCE==null)
            INSTANCE = new OrdersMessenger();
        return INSTANCE;
    }

    public void setFilterChangeObserver(@NonNull FilterChangeObserver filterChangeObserver) {
        this.filterChangeObserver = filterChangeObserver;
    }

    public void setListChangeObserver(@NonNull ListChangeObserver listChangeObserver) {
        this.listChangeObserver = listChangeObserver;
    }

    public void setNavigationListener(@NonNull NavigationListener navigationListener) {
        this.navigationListener = navigationListener;
    }

    @NonNull
    @Override
    public FilterChangeObserver getFilterChangeObserver() {
        return filterChangeObserver;
    }

    @NonNull
    @Override
    public NavigationListener getNavigationListener() {
        return navigationListener;
    }

    @NonNull
    @Override
    public ListChangeObserver getListChangeObserver() {
        return listChangeObserver;
    }
}
