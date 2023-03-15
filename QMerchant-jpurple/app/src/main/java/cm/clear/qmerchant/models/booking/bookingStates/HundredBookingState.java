package cm.clear.qmerchant.models.booking.bookingStates;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.common.actionablebuttons.ResourceResult;

public class HundredBookingState extends BookingState {
    public HundredBookingState(int capacity) {
        super(capacity);
    }
    @Override
    public int getConfirmButtonVisibility() {
        return View.VISIBLE;
    }

    @NonNull
    @Override
    public String getConfirmText(@NonNull Context context) {
        return context.getString(R.string.details);
    }

    @NonNull
    @Override
    public ColorStateList getRefColor(@NonNull Context context) {
        return AppCompatResources.getColorStateList(context, R.color.scarlet_transparent);
    }

    @Override
    public int getTablesColor() {
        if (getTotalCapacity()==0)
            return R.drawable.disabled_item_table_background;
        return R.drawable.booking_default_background;
    }

    @Nullable
    @Override
    public LiveData<ResourceResult> getConfirmResult(@NonNull String id) {
        ResourceResult result = new ResourceResult("","",false);
        return new MutableLiveData<>(result);
    }
}
