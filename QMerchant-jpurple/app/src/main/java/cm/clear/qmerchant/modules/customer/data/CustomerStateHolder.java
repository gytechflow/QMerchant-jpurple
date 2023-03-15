package cm.clear.qmerchant.modules.customer.data;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.common.listviewmanagement.IItemState;
import cm.clear.qmerchant.common.listviewmanagement.IItemStateHolder;

public class CustomerStateHolder implements IItemStateHolder {
    private CustomerState currentState = new CustomerState();
    private int position = -1;

    @NonNull
    @Override
    public CustomerState getState() {
        return currentState;
    }

    @Override
    public boolean isValueUpdated(@NonNull IItemState itemState) {
        return checkUpdate((CustomerState) itemState);
    }

    private boolean checkUpdate(CustomerState customerState) {
        CustomerState oldState = currentState;
        currentState = customerState;
        boolean b1 = oldState.getOrderCount() != currentState.getOrderCount();
        boolean b2 = oldState.getBookingCount() != currentState.getBookingCount();
        return b1 || b2;
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

    @NonNull
    @Override
    public String toString() {
        return "CustomerStateHolder{" +
                "currentState=" + currentState.toString() +
                ", position=" + position +
                '}';
    }
}
