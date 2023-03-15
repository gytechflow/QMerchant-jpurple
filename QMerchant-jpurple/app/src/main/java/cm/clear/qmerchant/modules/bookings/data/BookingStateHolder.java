package cm.clear.qmerchant.modules.bookings.data;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.models.booking.bookingStates.BookingState;
import cm.clear.qmerchant.common.listviewmanagement.IItemState;
import cm.clear.qmerchant.common.listviewmanagement.IItemStateHolder;

public class BookingStateHolder implements IItemStateHolder {
    private BookingState currentState;
    private int position = -1;

    public BookingStateHolder(@NonNull BookingState currentState) {
        this.currentState = currentState;
    }

    @NonNull
    @Override
    public BookingState getState() {
        return currentState;
    }

    @Override
    public boolean isValueUpdated(@NonNull IItemState itemState) {
        return checkUpdate((BookingState) itemState);
    }

    private boolean checkUpdate(BookingState itemState) {
        BookingState oldState = currentState;
        currentState = itemState;
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
