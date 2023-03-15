package cm.clear.qmerchant.models.coupon;

import android.util.Log;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.common.CustomCallback;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.modules.coupons.CouponsSourceTest;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class CouponActions {
    private static final String TAG = "CouponActions";


    public static void Use(@NonNull Coupon coupon, @NonNull CustomCallback callback){
        Coupon updatedCoupon = new Coupon(coupon);
        updatedCoupon.setState_code(CouponState.USED);
        Call<Coupon> couponCall = ApiUtil.getCouponService().updateCoupon(coupon.getId(), updatedCoupon);
        QCallback<Coupon> onCouponUpdated = new QCallback<Coupon>() {
            @Override
            public void responseSuccessful(Call<Coupon> call, Response<Coupon> response) {
                if (response.body() != null) {
                    callback.positive(response.body().getId());
                }
            }

            @Override
            public void requestFailure(Call<Coupon> call, Throwable t) {
                Log.e(TAG, "requestFailure: ", t);

            }

            @Override
            public void responseUnsuccessful(Call<Coupon> call, Response<Coupon> response) {
                Log.e(TAG, "responseUnsuccessful: "+ response.raw().toString());
            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback<Coupon>(couponCall, onCouponUpdated));
        //post coupon id to api
        //on result update #result
    }


    public static void Cancel(@NonNull Coupon coupon, @NonNull CustomCallback callback){
        //post coupon id to api
        //on result update #result
        Coupon updatedCoupon = new Coupon(coupon);
        updatedCoupon.setState_code(CouponState.CANCELED);
        ApiUtil.getCouponService().updateCoupon(coupon.getId(), updatedCoupon);
        Call<Coupon> couponCall = ApiUtil.getCouponService().updateCoupon(coupon.getId(), updatedCoupon);
        QCallback<Coupon> onCouponUpdated = new QCallback<Coupon>() {
            @Override
            public void responseSuccessful(Call<Coupon> call, Response<Coupon> response) {
                if (response.body() != null) {
                    callback.positive(response.body().getId());
                }
            }

            @Override
            public void requestFailure(Call<Coupon> call, Throwable t) {
                Log.e(TAG, "requestFailure: ", t);

            }

            @Override
            public void responseUnsuccessful(Call<Coupon> call, Response<Coupon> response) {
                Log.e(TAG, "responseUnsuccessful: "+ response.raw().toString());
            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback<Coupon>(couponCall, onCouponUpdated));

    }

    public static void create(@NonNull Coupon coupon, @NonNull CustomCallback callback){
        //post coupon id to api
        //on result update #result
        Call<String> createCouponCall = ApiUtil.getCouponService().createCoupon(coupon);
        QCallback<String> onCouponCreated = new QCallback<String>() {
            @Override
            public void responseSuccessful(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    callback.positive(response.body());
                } else {
                    Log.d(TAG, "responseSuccessful: not actually successfull: "+response.raw().toString());
                }
            }

            @Override
            public void requestFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "requestFailure: ", t);

            }

            @Override
            public void responseUnsuccessful(Call<String> call, Response<String> response) {
                Log.e(TAG, "responseUnsuccessful: "+ response.raw().toString());
            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback<>(createCouponCall, onCouponCreated));
    }

    private static void testUse(String couponId, CustomCallback result) {
        CouponsSourceTest.useCoupon(couponId, result);
    }

    private static void testCancel(String couponId, CustomCallback result) {
        CouponsSourceTest.cancelCoupon(couponId, result);
    }

    private static void testCreate(Coupon coupon, CustomCallback result) {
        CouponsSourceTest.addCoupon(coupon, result);
    }
}
