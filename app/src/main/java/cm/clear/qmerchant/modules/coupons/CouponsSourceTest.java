package cm.clear.qmerchant.modules.coupons;

import androidx.annotation.NonNull;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.common.CustomCallback;
import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.models.coupon.Coupon;
import cm.clear.qmerchant.models.coupon.CouponState;

public class CouponsSourceTest {
    private static final String TAG = "CouponSource";
    private int state = CouponState.ALL;
    private final CouponsViewModel couponsViewModel;
    static List<Coupon> coupons = new ArrayList<>();

    public CouponsSourceTest(@NonNull CouponsViewModel couponsViewModel) {
        this.couponsViewModel = couponsViewModel;
    }

    public void getCoupons() {
        getTestCoupons();
    }

    private void filterCoupons() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Coupon> tempCoupons = new ArrayList<>();
                for (Coupon coupon : coupons) {
                    if (coupon.getState_code() == state || state == CouponState.ALL)
                        tempCoupons.add(coupon);
                }
                update(tempCoupons);
            }
        });
        thread.start();
    }

    void update(List<Coupon> tempCoupons) {
        couponsViewModel.updateCoupons(tempCoupons);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        filterCoupons();
    }

    private void getTestCoupons() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    Coupon coupon = new Coupon();
                    coupon.setId(String.valueOf(i));
                    coupon.setCode(RandomStringUtils.randomAlphabetic(5).toUpperCase());
                    coupon.setValue(Integer.valueOf(RandomStringUtils.random(3, false, true)));
                    coupon.setDescription(RandomStringUtils.random(5) + " " + RandomStringUtils.random(5) + " " + RandomStringUtils.random(5));
                    coupon.setState_code(Integer.valueOf(RandomStringUtils.random(1, "012")));
                    coupon.setEmail(RandomStringUtils.random(6, true, false) + "@" + RandomStringUtils.random(5, true, false) + "." + RandomStringUtils.random(3, true, false));
//                    Long tms = TmsConverter.todayFullTms();
//                    char first = String.valueOf(tms).toCharArray()[0];
//                    char last = String.valueOf(tms).toCharArray()[tms.toString().length()-1];
//                    Log.d("TAG", "getTestCoupons: "+tms+" "+first+" "+last);
//                    coupon.setExpiryDateTms(Long.valueOf(first+""+RandomStringUtils.random(tms.toString().length()-2,false,true)+""+last)*TmsConverter.FROM_JAVA_SQL);
                    coupon.setExpiryDateTms(TmsConverter.getFromJavaToSql(TmsConverter.todayPlusFullTms(i)));
                    coupons.add(coupon);
                }
                loadedCoupons();
            }
        });
        thread.start();

    }

    private void loadedCoupons() {
        filterCoupons();
    }

    public void reload() {
        filterCoupons();
    }

    public static void getCouponById(@NonNull String couponId, @NonNull TestInterface testInterface) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (Integer.parseInt(couponId) < coupons.size())
                    testInterface.onComplete(coupons.get(Integer.parseInt(couponId)));
                else
                    testInterface.onComplete(coupons.get(0));
            }
        });
        thread.start();
    }

    public static void addCoupon(@NonNull Coupon couponToAdd, @NonNull CustomCallback result) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int size = coupons.size();
                for (int i = 0; i < size; i++) {
                    if (couponToAdd.getCode().equalsIgnoreCase(coupons.get(i).getCode())) {
                        result.negative("Bad Token");
                        break;
                    }
                }
                couponToAdd.setId(String.valueOf(coupons.size()));
                couponToAdd.setState_code(CouponState.UNUSED);
                coupons.add(couponToAdd);
                result.positive("success");
            }
        });
        thread.start();
    }

    public static void useCoupon(@NonNull String couponId, @NonNull CustomCallback result) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int size = coupons.size();
                for (int i = 0; i < size; i++) {
                    if (coupons.get(i).getId().equalsIgnoreCase(couponId)) {
                        Coupon coupon = new Coupon(coupons.get(i));
                        coupon.setState_code(CouponState.USED);
                        coupons.remove(i);
                        coupons.add(i, coupon);
                        result.positive("ok");
                        break;
                    }
                }
                result.negative("not okay");
            }
        });
        thread.start();
    }

    public static void cancelCoupon(@NonNull String couponId, @NonNull CustomCallback result) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int size = coupons.size();
                for (int i = 0; i < size; i++) {
                    if (coupons.get(i).getId().equalsIgnoreCase(couponId)) {
                        Coupon coupon = new Coupon(coupons.get(i));
                        coupon.setState_code(CouponState.CANCELED);
                        coupons.remove(i);
                        coupons.add(i, coupon);
                        result.positive("ok");
                        break;
                    }
                }
                result.negative("not okay");
            }
        });
        thread.start();
    }


    public void loadNextPage() {

    }
}
