package cm.clear.qmerchant.modules.coupons.couponDetails;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.models.coupon.Coupon;
import cm.clear.qmerchant.modules.coupons.TestInterface;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class InfoHolder {
    private static final String TAG = "InfoHolder";

    public void getCouponById(@NonNull String couponId, @NonNull TestInterface couponDetails) {
        Log.d(TAG, "getCouponById() called with: couponId = [" + couponId + "], couponDetails = [" + couponDetails + "]");
        TestInterface testInterface = new TestInterface() {
            private final String TAG = InfoHolder.class.toString();

            @Override
            public void onComplete(@Nullable Coupon coupon) {
                Log.d(TAG, "onComplete() called with: coupon = [" + coupon + "]");
                couponDetails.onComplete(coupon);
            }
        };
        Call<Coupon> couponCall = ApiUtil.getCouponService().getCouponById(couponId);
        QCallback<Coupon> onCouponReceived = new QCallback<Coupon>() {
            @Override
            public void responseSuccessful(Call<Coupon> call, Response<Coupon> response) {
                testInterface.onComplete(response.body());
            }

            @Override
            public void requestFailure(Call<Coupon> call, Throwable t) {
                Log.e(TAG, "requestFailure: ", t);
                testInterface.onComplete(null);
            }

            @Override
            public void responseUnsuccessful(Call<Coupon> call, Response<Coupon> response) {
                Log.e(TAG, "responseUnsuccessful: "+ response.raw().toString());
                testInterface.onComplete(null);
            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback<Coupon>(couponCall, onCouponReceived));
    }
}
