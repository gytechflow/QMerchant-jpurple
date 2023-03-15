package cm.clear.qmerchant.models.booking.bookingStates;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;

import cm.clear.qmerchant.R;

public class FiftyBookingState extends BookingState {
    public FiftyBookingState(int capacity) {
        super(capacity);
    }

    @Override
    public int getAssignButtonVisibility() {
        return View.VISIBLE;
    }

    @Override
    public int getConfirmButtonVisibility() {
        return View.GONE;
    }

    @Override
    public int getCancelButtonVisibility() {
        return View.VISIBLE;
    }

    @NonNull
    @Override
    public String getAssignText(@NonNull Context context) {
        return context.getString(R.string.modify);
    }

    @NonNull
    @Override
    public ColorStateList getRefColor(@NonNull Context context) {
        return AppCompatResources.getColorStateList(context, R.color.booking_table_selected);
    }
}
