package cm.clear.qmerchant.remote;

import androidx.annotation.NonNull;

import java.util.List;

import cm.clear.qmerchant.models.coupon.Coupon;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CouponRequestInterface {
    public static final String SORT_FIELD = "t.rowid";
    public static final String SORT_ORDER = "ASC";
    public static final String LIMIT = "10";
    @NonNull
    @GET("couponsapi/coupons")
    Call<List<Coupon>> getCoupons(
            @Query(value = "sortfield") @NonNull String label,
            @Query(value = "sortorder") @NonNull String sortorder,
            @Query(value = "limit") @NonNull String limit,
            @Query(value = "page") @NonNull Integer page,
            @Query(value = "sqlfilters") @NonNull String sqlfilters
    );
    @NonNull
    @GET("couponsapi/coupons")
    Call<List<Coupon>> getCoupons(
            @Query(value = "sortfield") @NonNull String label,
            @Query(value = "sortorder") @NonNull String sortorder,
            @Query(value = "limit") @NonNull String limit,
            @Query(value = "page") @NonNull Integer page
    );

    @NonNull
    @GET("couponsapi/coupons")
    Call<List<Coupon>> getCoupons(
            @Query(value = "sortfield") @NonNull String label,
            @Query(value = "sortorder") @NonNull String sortorder,
            @Query(value = "limit") @NonNull String limit
    );

    @NonNull
    @GET("couponsapi/coupons/{id}")
    Call<Coupon> getCouponById(
            @Path("id") @NonNull String couponId
    );

    @NonNull
    @PUT("couponsapi/coupons/{id}")
    Call<Coupon> updateCoupon(
            @Path("id") @NonNull String couponId,
            @Body @NonNull Coupon updatedCoupon
    );

    @NonNull
    @POST("couponsapi/coupons")
    Call<String> createCoupon(
            @Body @NonNull Coupon updatedCoupon
    );

    @GET("couponsapi/count")
    Call<String> getCouponsCount(
            @Query(value = "sqlfilters") String sqlFilter
    );
}
