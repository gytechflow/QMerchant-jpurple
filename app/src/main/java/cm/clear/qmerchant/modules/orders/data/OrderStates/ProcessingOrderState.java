package cm.clear.qmerchant.modules.orders.data.OrderStates;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import cm.clear.qmerchant.common.actionablebuttons.ResourceResult;
import cm.clear.qmerchant.models.order.OrdersActions;

public class ProcessingOrderState extends OrderState {


    public ProcessingOrderState(int status) {
        super(status);
    }

    @Override
    public int getConfirmButtonVisibility() {
        return View.VISIBLE;
    }

    @Nullable
    @Override
    public LiveData<ResourceResult> getConfirmResult(@NonNull String id) {
        return OrdersActions.getINSTANCE().closeOrder( id );
    }

}
