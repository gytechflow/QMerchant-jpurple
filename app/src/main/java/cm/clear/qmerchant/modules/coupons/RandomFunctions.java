package cm.clear.qmerchant.modules.coupons;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.models.coupon.Coupon;

public class RandomFunctions {
    @NonNull
    public static String getCouponDate(@Nullable Coupon coupon) {
        if (coupon == null)
            return TmsConverter.todayDateOnly();
        else if (coupon.getExpiryDateTms() == null || coupon.getExpiryDateTms() == 0)
            return TmsConverter.todayDateOnly();
        return TmsConverter.getDate(coupon.getExpiryDateTms(), TmsConverter.FROM_SQL_JAVA);
    }
}
