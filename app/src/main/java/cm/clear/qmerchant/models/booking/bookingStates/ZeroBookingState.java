package cm.clear.qmerchant.models.booking.bookingStates;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;

import cm.clear.qmerchant.R;

public class ZeroBookingState extends BookingState {
    public ZeroBookingState(int capacity) {
        super(capacity);
    }

    @Override
    public boolean hasTables() {
        return false;
    }

    @Override
    public int getAssignButtonVisibility() {
        return View.VISIBLE;
    }

    @Override
    public int getConfirmButtonVisibility() {
        return View.VISIBLE;
    }

    @Override
    public int getCancelButtonVisibility() {
        return View.VISIBLE;
    }

    @NonNull
    @Override
    public ColorStateList getRefColor(@NonNull Context context) {
        return AppCompatResources.getColorStateList(context, R.color.blue_transparent);
    }
}
