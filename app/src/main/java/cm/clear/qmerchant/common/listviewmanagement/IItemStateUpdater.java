package cm.clear.qmerchant.common.listviewmanagement;

import androidx.annotation.NonNull;

public interface IItemStateUpdater {
    void register(@NonNull Object o);
    void addToMap(@NonNull Object o);
    void loadExtras(@NonNull Object o);
    void update(int position);

    @NonNull
    IItemState getObjectState(@NonNull Object o, int position);

    void reloadState(@NonNull Object o);
}
