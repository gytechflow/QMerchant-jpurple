package cm.clear.qmerchant.common.listviewmanagement;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.common.listviewmanagement.IItemState;

public interface IItemStateHolder {
    @NonNull
    IItemState getState();

    boolean isValueUpdated(@NonNull IItemState itemState);

    void setPosition(int position);

    int getPosition();

    void onReloadState();
}
