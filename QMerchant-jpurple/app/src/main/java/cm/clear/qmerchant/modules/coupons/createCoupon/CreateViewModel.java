package cm.clear.qmerchant.modules.coupons.createCoupon;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import org.apache.commons.lang3.RandomStringUtils;

import cm.clear.qmerchant.common.CustomCallback;
import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.databinding.CopyCouponCodeDialogBinding;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.models.coupon.Coupon;
import cm.clear.qmerchant.models.coupon.CouponActions;
import cm.clear.qmerchant.modules.coupons.TestInterface;
import cm.clear.qmerchant.modules.coupons.couponDetails.InfoHolder;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

class CreateViewModel extends ViewModel {
    private static final String TAG = "CreateViewModel";
    final CreateCoupon owner;
    private MutableLiveData<Integer> showAmountError = new MutableLiveData<>(View.INVISIBLE);
    private MutableLiveData<Integer> showEmailError = new MutableLiveData<>(View.INVISIBLE);
    private MutableLiveData<Integer> showExpiryDateError = new MutableLiveData<>(View.INVISIBLE);
    MutableLiveData<Integer> showCreationLoader = new MutableLiveData<>(View.INVISIBLE);
    private MutableLiveData<String> showExpiryDate = new MutableLiveData<>();
    Coupon coupon;

    CreateViewModel(CreateCoupon owner) {
        this.owner = owner;
    }

    public void createCoupon(@NonNull String amount, @NonNull String email, @Nullable String description) {
        if (coupon == null) {
            showExpiryDateError();
            return;
        }
        showCreationLoader.postValue(View.VISIBLE);
        showAmountError.postValue(View.INVISIBLE);
        showExpiryDateError.postValue(View.INVISIBLE);
        showEmailError.postValue(View.INVISIBLE);
        coupon.setDescription(description);
        coupon.setValue(Integer.parseInt(amount));
        coupon.setEmail(email);
        coupon.setCode(RandomStringUtils.random(5, true, false).toUpperCase());

        Log.d(TAG, "run: "+ coupon.getExpiryDateTms());
        CouponActions.create(coupon, new CustomCallback() {
            @Override
            public void positive(@NonNull String message) {
                showCreationLoader.postValue(View.INVISIBLE);
                owner.requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showCouponCode(coupon.getCode());
                        Log.d(TAG, "run: "+ coupon.getExpiryDateTms());
                    }
                });

            }

            @Override
            public void negative(@NonNull String message) {
                if (message.equalsIgnoreCase("Bad Token")) {
                    createCoupon(amount, email, description);
                }
            }
        });
    }

    void showCouponCode(@NonNull String couponCode) {
        ClipboardManager clipboard = (ClipboardManager) owner.requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("coupon_token", couponCode);
        clipboard.setPrimaryClip(clip);
        CopyCouponCodeDialogBinding dialogBinding = CopyCouponCodeDialogBinding.inflate(owner.getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(owner.getContext());
        builder.setView(dialogBinding.getRoot());
        AlertDialog dialog = builder.create();
        dialogBinding.setCouponCode(couponCode);
        View.OnClickListener copyListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) owner.requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("coupon_token", couponCode);
                clipboard.setPrimaryClip(clip);
            }
        };
        dialogBinding.setClickListener(copyListener);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Navigation.findNavController(owner.requireView()).popBackStack();
            }
        });
        dialog.show();
    }

    private void showExpiryDateError() {
        showExpiryDateError.postValue(View.VISIBLE);
    }

    public void showAmountError() {
        showAmountError.postValue(View.VISIBLE);
    }

    public void showEmailError() {
        showEmailError.postValue(View.VISIBLE);
    }

    public void setCouponExpiryTms(long tms) {
        Log.d(TAG, "setCouponExpiryTms() called with: tms = [" + tms + "]");
        if (coupon == null)
            coupon = new Coupon();
        coupon.setExpiryDateTms(TmsConverter.getFromJavaToSql(tms));
        Log.d(TAG, "setCouponExpiryTms(): "+ coupon.getExpiryDateTms());
        showExpiryDate.postValue(TmsConverter.getDate(tms, TmsConverter.NO_CONVERSION));
    }

    public LiveData<Integer> getShowAmountError() {
        return showAmountError;
    }

    public LiveData<Integer> getShowEmailError() {
        return showEmailError;
    }

    public LiveData<Integer> getShowExpiryDateError() {
        return showExpiryDateError;
    }

    public LiveData<Integer> getShowCreationLoader() {
        return showCreationLoader;
    }

    public LiveData<String> getShowExpiryDate() {
        return showExpiryDate;
    }

    public void checkCouponInfo() {
        owner.checkCouponInfo();
    }

    public void getCouponById(String couponId, TestInterface createCoupon) {
        TestInterface testInterface = new TestInterface() {
            private final String TAG = InfoHolder.class.toString();

            @Override
            public void onComplete(@Nullable Coupon coupon) {
                Log.d(TAG, "onComplete() called with: coupon = [" + coupon + "]");
                createCoupon.onComplete(coupon);
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
                testInterface.onComplete(null);
            }

            @Override
            public void responseUnsuccessful(Call<Coupon> call, Response<Coupon> response) {
                testInterface.onComplete(null);
            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback<Coupon>(couponCall, onCouponReceived));
    }
}
