package cm.clear.qmerchant.modules.coupons.couponDetails;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import cm.clear.qmerchant.dashboard.MainViewModel;
import cm.clear.qmerchant.databinding.FragmentCouponDetailsBinding;
import cm.clear.qmerchant.models.coupon.Coupon;
import cm.clear.qmerchant.modules.coupons.TestInterface;

public class CouponDetails extends Fragment implements TestInterface {
    private FragmentCouponDetailsBinding binding;
    private MainViewModel mainViewModel;
    private String couponId;
    private InfoHolder viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            couponId = getArguments().getString("coupon");
        }
        if (TextUtils.isEmpty(couponId)){
            Toast.makeText(requireContext(), "Coupon Not Passed!", Toast.LENGTH_SHORT).show();
        }

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel = new InfoHolder();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCouponDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel.setFabVisible(false);
        viewModel.getCouponById(couponId, this);

    }

    @Override
    public void onComplete(@Nullable Coupon coupon) {
        if (binding!=null)
            binding.setCoupon(coupon);
    }
}
