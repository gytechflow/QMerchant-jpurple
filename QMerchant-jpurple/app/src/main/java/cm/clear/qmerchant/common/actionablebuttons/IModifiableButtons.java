package cm.clear.qmerchant.common.actionablebuttons;

import android.content.Context;
import android.content.res.ColorStateList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import cm.clear.qmerchant.common.listviewmanagement.IItemState;

public interface IModifiableButtons extends IItemState {
    int getAssignButtonVisibility();
    int getConfirmButtonVisibility();
    int getCancelButtonVisibility();

    @NonNull
    String getAssignText(@NonNull Context context);

    @NonNull
    String getConfirmText(@NonNull Context context);

    @NonNull
    String getCancelText(@NonNull Context context);

    @NonNull
    ColorStateList getRefColor(@NonNull Context context);

    @NonNull
    ColorStateList getAssignButtonColor(@NonNull Context context);

    @NonNull
    ColorStateList getConfirmButtonColor(@NonNull Context context);

    @NonNull
    ColorStateList getCancelButtonColor(@NonNull Context context);

    @Nullable
    LiveData<ResourceResult> getAssignResult();

    @Nullable
    LiveData<ResourceResult> getConfirmResult(@NonNull String id);

    @Nullable
    LiveData<ResourceResult> getCancelResult(@NonNull String id);
}
