package cm.clear.qmerchant.modules.orders.data;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.common.listviewmanagement.IItemState;
import cm.clear.qmerchant.common.listviewmanagement.IItemStateHolder;
import cm.clear.qmerchant.modules.orders.data.OrderStates.OrderState;

public class OrderStateHolder implements IItemStateHolder {
    private OrderState currentState;
    private int position = -1;

    public OrderStateHolder(OrderState orderState) {
        this.currentState = orderState;
    }


    @NonNull
    @Override
    public OrderState getState() {
        return currentState;
    }

    @Override
    public boolean isValueUpdated(@NonNull IItemState itemState) {
        currentState = (OrderState)itemState;
        return true;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void onReloadState() {
        currentState = null;
    }
}
