package cm.clear.qmerchant.modules.coupons;

import android.view.View;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.models.coupon.CouponState;

public class ClickHandler {
    private final CouponsSource source;
    public ClickHandler(@NonNull CouponsSource source) {
        this.source = source;
    }

    public void allCouponsClicked(@NonNull View view){
//        Toast.makeText(view.getContext(), "allCouponsClicked", Toast.LENGTH_SHORT).show();
        source.setState(CouponState.ALL);
    }
    public void usedCouponsClicked(@NonNull View view){
//        Toast.makeText(view.getContext(), "usedCouponsClicked", Toast.LENGTH_SHORT).show();
        source.setState(CouponState.USED);
    }
    public void unUsedCouponsClicked(@NonNull View view){
//        Toast.makeText(view.getContext(), "unUsedCouponsClicked", Toast.LENGTH_SHORT).show();
        source.setState(CouponState.UNUSED);
    }
    public void canceledCouponsClicked(@NonNull View view){
//        Toast.makeText(view.getContext(), "canceledCouponsClicked", Toast.LENGTH_SHORT).show();
        source.setState(CouponState.CANCELED);
    }
}
