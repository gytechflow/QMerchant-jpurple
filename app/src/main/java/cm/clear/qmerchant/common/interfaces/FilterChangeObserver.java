package cm.clear.qmerchant.common.interfaces;

import cm.clear.qmerchant.common.listviewmanagement.ListChangeObserver;

public interface FilterChangeObserver {
    void onFilterChange(String value);
    void onClearCalls();
    void refresh();
}
