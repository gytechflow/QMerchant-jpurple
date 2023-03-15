package cm.clear.qmerchant.common.listprovider;

import androidx.annotation.NonNull;

import java.util.List;

public interface ListObserver {
    void onListUpdate(@NonNull List<ListObject> items);
    void onReloadList(@NonNull List<ListObject> items);
    default void onReloadList(){};
}
