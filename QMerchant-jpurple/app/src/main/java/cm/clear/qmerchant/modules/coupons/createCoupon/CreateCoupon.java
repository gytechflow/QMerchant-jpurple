package cm.clear.qmerchant.modules.coupons.createCoupon;

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
import cm.clear.qmerchant.databinding.FragmentCreateCouponBinding;
import cm.clear.qmerchant.models.coupon.Coupon;
import cm.clear.qmerchant.modules.coupons.TestInterface;

public class CreateCoupon extends Fragment implements TestInterface {
    private FragmentCreateCouponBinding binding;
    private MainViewModel mainViewModel;
    private CreateViewModel viewModel;
    private String couponId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        couponId = getArguments().getString("coupon");
        if (TextUtils.isEmpty(couponId))
            Toast.makeText(requireContext(), "Coupon Not Passed!", Toast.LENGTH_SHORT).show();
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel = new CreateViewModel(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateCouponBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel.setFabVisible(false);
        binding.setClickHandler(new ClickHandler(viewModel));
        if (!TextUtils.isEmpty(couponId))
            viewModel.getCouponById(couponId, this);

        viewModel.getShowCreationLoader().observe(getViewLifecycleOwner(), visibility -> {
            binding.creationLoaderLayout.setVisibility(visibility);
        });

        viewModel.getShowAmountError().observe(getViewLifecycleOwner(), visibility -> {
            binding.amountErrorLayout.setVisibility(visibility);
        });

        viewModel.getShowEmailError().observe(getViewLifecycleOwner(), visibility -> {
            binding.emailErrorLayout.setVisibility(visibility);
        });

        viewModel.getShowExpiryDateError().observe(getViewLifecycleOwner(), visibility -> {
            binding.expiryDateErrorLayout.setVisibility(visibility);
        });

        viewModel.getShowExpiryDate().observe(getViewLifecycleOwner(), visibility -> {
            if(!TextUtils.isEmpty(visibility)){
                binding.dateButton.setText(visibility);
            }
        });

    }

    @Override
    public void onComplete(@Nullable Coupon coupon) {
        if (binding!=null)
            binding.setCoupon(coupon);
    }

    public void checkCouponInfo() {
        String amount  = String.valueOf(binding.couponAmount.getText());
        String email  = String.valueOf(binding.couponEmail.getText());
        String description  = String.valueOf(binding.couponDescription.getText());
        if (TextUtils.isEmpty(amount)){
            viewModel.showAmountError();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            viewModel.showEmailError();
            return;
        }
//        if (TextUtils.isEmpty(description))
//            owner.showEmailError();
        assert amount != null;
        assert email != null;
        viewModel.createCoupon(amount, email, description);
    }
}
