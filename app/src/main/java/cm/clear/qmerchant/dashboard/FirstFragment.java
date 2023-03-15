package cm.clear.qmerchant.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.database.QDatabase;
import cm.clear.qmerchant.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private MainViewModel mainViewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.setFabVisible(false);
        mainViewModel.setFabAction(new fabAction() {
            @Override
            public int getNavigation() {
                return 0;
            }
        });
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QDatabase.setContext(requireContext());
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View.OnClickListener ordersListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), OrdersItemDetailHostActivity.class);
//                getActivity().startActivity(intent);

                Navigation.findNavController(view)
//                        .navigate(R.id.fragment_item_detail, arguments);
                        .navigate(R.id.action_FirstFragment_to_orders_nav_graph);
            }
        };

        mainViewModel.getOrderCounter().observe(getViewLifecycleOwner(), compositeDashBoardCounter -> {
            if (compositeDashBoardCounter!=null){
                binding.ordersNewNumber.setText(String.valueOf(compositeDashBoardCounter.getCount()));
                binding.ordersTotalNumber.setText(String.valueOf(compositeDashBoardCounter.getTotalCount()));
            }
        });
        binding.ordersText.setOnClickListener(ordersListener);
        binding.ordersImage.setOnClickListener(ordersListener);
        binding.ordersDesc.setOnClickListener(ordersListener);


        View.OnClickListener reservationsLister = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), BookingActivity.class);
//                getActivity().startActivity(intent);

                Navigation.findNavController(view)
//                        .navigate(R.id.fragment_item_detail, arguments);
                        .navigate(R.id.action_FirstFragment_to_booking_nav_graph);
            }
        };
        mainViewModel.getBookingCounter().observe(getViewLifecycleOwner(), compositeDashBoardCounter -> {
            if (compositeDashBoardCounter!=null){
                binding.reservationsNewNumber.setText(String.valueOf(compositeDashBoardCounter.getCount()));
                binding.reservationsTotalNumber.setText(String.valueOf(compositeDashBoardCounter.getTotalCount()));
            }
        });
        binding.reservationsText.setOnClickListener(reservationsLister);
        binding.reservationsImage.setOnClickListener(reservationsLister);
        binding.reservationsDesc.setOnClickListener(reservationsLister);

        View.OnClickListener tablesLister = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view)
                        .navigate(R.id.action_FirstFragment_to_tables_nav_graph);
            }
        };

        binding.tablesLayout.setOnClickListener(tablesLister);

        View.OnClickListener customersLister = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), BookingActivity.class);
//                getActivity().startActivity(intent);

                Navigation.findNavController(view)
//                        .navigate(R.id.fragment_item_detail, arguments);
                        .navigate(R.id.action_FirstFragment_to_customers_nav_graph);
            }
        };
        mainViewModel.getClientCounter().observe(getViewLifecycleOwner(), compositeDashBoardCounter -> {
            if (compositeDashBoardCounter!=null){
                binding.customersNewNumber.setText(String.valueOf(compositeDashBoardCounter.getCount()));
                binding.customersTotalNumber.setText(String.valueOf(compositeDashBoardCounter.getTotalCount()));
            }
        });
        binding.customersText.setOnClickListener(customersLister);
        binding.customersImage.setOnClickListener(customersLister);
        binding.customersDesc.setOnClickListener(customersLister);

        View.OnClickListener settingLister = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view)
                        .navigate(R.id.action_FirstFragment_to_setting_nav_graph);
            }
        };


        binding.settingsImage.setOnClickListener(settingLister);
        binding.settingsText.setOnClickListener(settingLister);
        binding.settingsText.setOnClickListener(settingLister);

        View.OnClickListener couponsListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view)
                        .navigate(R.id.action_FirstFragment_to_coupons_nav_graph);
            }
        };
        mainViewModel.getCouponCounter().observe(getViewLifecycleOwner(), compositeDashBoardCounter -> {
            if (compositeDashBoardCounter!=null){
                binding.couponsNewNumber.setText(String.valueOf(compositeDashBoardCounter.getCount()));
                binding.couponsTotalNumber.setText(String.valueOf(compositeDashBoardCounter.getTotalCount()));
            }
        });
        binding.coupons.setOnClickListener(couponsListener);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mainViewModel.reset();
        binding = null;
    }

}