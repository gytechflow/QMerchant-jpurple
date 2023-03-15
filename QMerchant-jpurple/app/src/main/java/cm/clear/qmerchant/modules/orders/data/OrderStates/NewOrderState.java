package cm.clear.qmerchant.modules.orders.data.OrderStates;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import cm.clear.qmerchant.common.actionablebuttons.ResourceResult;
import cm.clear.qmerchant.models.order.OrdersActions;

public class NewOrderState extends OrderState {


    public NewOrderState(int status) {
        super(status);
    }

    @Override
    public int getConfirmButtonVisibility() {
        return View.VISIBLE;
    }

    @Override
    public int getCancelButtonVisibility() {
        return View.VISIBLE;
    }

    @Nullable
    @Override
    public LiveData<ResourceResult> getConfirmResult(@NonNull String id) {
        return OrdersActions.getINSTANCE().confirmOrder( id );
    }

    @Nullable
    @Override
    public LiveData<ResourceResult> getCancelResult(@NonNull String id) {
        return OrdersActions.getINSTANCE().cancelOrder( id );
    }

}
