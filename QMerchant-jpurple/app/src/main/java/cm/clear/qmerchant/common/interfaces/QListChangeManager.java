package cm.clear.qmerchant.common.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cm.clear.qmerchant.common.listviewmanagement.ListChangeObserver;

public interface QListChangeManager {
    @Nullable
    ListChangeObserver getListChangeObserver();

    default void listItemChanged() {
        if (getListChangeObserver()==null)
            throw new NullPointerException("ListChangeObserver is null");
        getListChangeObserver().onListItemChange();
    }

    default void listBottomReached(){
        if (getListChangeObserver()==null)
            throw new NullPointerException("ListChangeObserver is null");
        getListChangeObserver().onListBottomReached();
    };
}
