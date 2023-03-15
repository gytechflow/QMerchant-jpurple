package cm.clear.qmerchant.modules.coupons;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cm.clear.qmerchant.common.IsPagedList;
import cm.clear.qmerchant.models.coupon.Coupon;

public class CouponsViewModel extends ViewModel {
    private static final String TAG = CouponsViewModel.class.getName();
    private CouponsSource dataSource;
    private MutableLiveData<IsPagedList<Coupon>> coupons = new MutableLiveData<>();
    private MutableLiveData<Integer> isProgressBarVisible = new MutableLiveData<>(View.INVISIBLE);

    public CouponsViewModel() {
        this.dataSource = new CouponsSource(this);
    }

    @NonNull
    public LiveData<IsPagedList<Coupon>> getCoupons(){
        if (coupons.getValue()==null){
            dataSource.getCoupons();
        }
        dataSource.reload();
        return coupons;
    }

    public void reloadCoupons(){
        dataSource.reload();
    }

    @NonNull
    public CouponsSource getDataSource() {
        return dataSource;
    }

    public void updateCoupons(@NonNull List<Coupon> filteredCoupons) {
        coupons.postValue(new IsPagedList<Coupon>() {
            @NonNull
            @Override
            public List<Coupon> getList() {
                return filteredCoupons;
            }
        });
        loaded();
    }

    public void listBottomReached() {
        dataSource.loadNextPage();
    }

    @NonNull
    public LiveData<Integer> isProgressBarVisible() {
        return isProgressBarVisible;
    }

    public void loading() {
        isProgressBarVisible.postValue(View.VISIBLE);
    }

    public void loaded() {
        isProgressBarVisible.postValue(View.INVISIBLE);
    }

    public void addCoupons(@NonNull List<Coupon> tempCoupons) {
        coupons.postValue(new IsPagedList<Coupon>() {
            @NonNull
            @Override
            public List<Coupon> getList() {
                return tempCoupons;
            }

            @Override
            public boolean isNewPage() {
                return true;
            }
        });
        loaded();
    }

    public void reset() {
        dataSource.reset();
    }
}
