package cm.clear.qmerchant.modules.coupons;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.common.interfaces.FilterChangeObserver;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.models.coupon.Coupon;
import cm.clear.qmerchant.models.coupon.CouponState;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.remote.CouponRequestInterface;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class CouponsSource implements FilterChangeObserver {
    int page = 0;
    private static final String TAG = "CouponSource";
    private int state = CouponState.UNUSED;
    private final CouponsViewModel couponsViewModel;
    private CallAndCallback<List<Coupon>> main_call;
    private boolean nextPage = true;

    public CouponsSource(@NonNull CouponsViewModel couponsViewModel) {
        this.couponsViewModel = couponsViewModel;
    }

    public void getCoupons() {
        couponsViewModel.loading();
        Call<List<Coupon>> callForCoupons = ApiUtil.getCouponService().getCoupons(
                CouponRequestInterface.SORT_FIELD,
                CouponRequestInterface.SORT_ORDER,
                CouponRequestInterface.LIMIT,
                page,
                "t.coupon_state = "+getState()
                );
        QCallback<List<Coupon>> onCouponsLoaded = new QCallback<List<Coupon>>() {
            @Override
            public void responseSuccessful(Call<List<Coupon>> call, Response<List<Coupon>> response) {
                if (isLatestCall(call)) {
                    update(response.body());
                    if (response.body().size() < Integer.parseInt(CouponRequestInterface.LIMIT)) {
//                        setNextPage(false);
                    }
                }
            }

            @Override
            public void requestFailure(Call<List<Coupon>> call, Throwable t) {
                if (isLatestCall(call))
                    update(new ArrayList<>());
            }

            @Override
            public void responseUnsuccessful(Call<List<Coupon>> call, Response<List<Coupon>> response) {
                if (isLatestCall(call))
                    update(new ArrayList<>());
            }
        };
        main_call = new CallAndCallback<List<Coupon>>(callForCoupons, onCouponsLoaded);
        RequestManager.getInstance().addTempCall(main_call);
//        getTestCoupons();
    }

    boolean isLatestCall(Call<List<Coupon>> call) {
        return main_call.getCall().request().toString().equalsIgnoreCase(call.request().toString());
    }

    boolean hasNextPage(){
        return nextPage;
    }

    private void setNextPage(boolean value){
        this.nextPage = value;
    }
    void update(List<Coupon> tempCoupons) {
        if (page>0){
            if (tempCoupons.isEmpty())
                page--;
            couponsViewModel.addCoupons(tempCoupons);
        }
        else
            couponsViewModel.updateCoupons(tempCoupons);
    }

    @NonNull
    public String getState() {
        if (state == CouponState.ALL) {
            return "t.coupon_state";
        }
        return "" + state;
    }

    public void setState(int state) {
        this.state = state;
        reload();
    }

    String getFilter(){
        if (getState().equalsIgnoreCase(""+CouponState.UNUSED))
            return "t.coupon_state = "+getState()+" AND t.expiry_date_tms";
        return "t.coupon_state = "+getState();
    }

    public void reload() {
        page = 0;
        getCoupons();
    }

    public void loadNextPage() {
        if (hasNextPage()){
            page++;
            getCoupons();
        }
    }

    @Override
    public void onFilterChange(String value) {
        setState(Integer.parseInt(value));
    }

    @Override
    public void onClearCalls() {

    }

    @Override
    public void refresh() {
        reload();
    }

    public void reset() {
        state = CouponState.UNUSED;
        reload();
    }
}
