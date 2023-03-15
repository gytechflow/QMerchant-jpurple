package cm.clear.qmerchant.modules.coupons.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.common.CustomCallback;
import cm.clear.qmerchant.models.coupon.Coupon;
import cm.clear.qmerchant.models.coupon.CouponActions;
import cm.clear.qmerchant.models.coupon.CouponState;

public class ClickHandler {
    private static final String TAG = "ClickHandler";
    final CouponsAdapter owner;

    public ClickHandler(@NonNull CouponsAdapter owner) {
        this.owner = owner;
    }

    public void itemClicked(@NonNull View view, @NonNull Coupon coupon) {
        Log.d(TAG, "itemClicked() called with: view = [" + view + "], coupon = [" + coupon.getId() + "]");
        Bundle couponDetails = new Bundle();
        couponDetails.putSerializable("coupon", coupon.getId());
        owner.navigate("", couponDetails, R.id.action_show_coupon_details_fragment);
    }

    public void discardCoupon(@NonNull View view, @NonNull Coupon coupon) {
        if (coupon.getState_code() != CouponState.CANCELED && coupon.getState_code() != CouponState.USED)
            CouponActions.Cancel(coupon, getCallBack());
    }

    public void useCoupon(@NonNull View view, @NonNull Coupon coupon) {
        if (coupon.getState_code() != CouponState.CANCELED && coupon.getState_code() != CouponState.USED)
            CouponActions.Use(coupon, getCallBack());
    }

    private CustomCallback getCallBack() {
        return new CustomCallback() {
            @Override
            public void positive(@NonNull String message) {
                owner.reload();
            }

            @Override
            public void negative(@NonNull String message) {
            }
        };
    }
}
