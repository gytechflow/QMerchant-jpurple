package cm.clear.qmerchant.modules.customer.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import cm.clear.qmerchant.R;
import cm.clear.qmerchant.common.interfaces.NavigationListener;
import cm.clear.qmerchant.dashboard.MainViewModel;
import cm.clear.qmerchant.dashboard.fabAction;
import cm.clear.qmerchant.databinding.CustomersFragmentBinding;

public class Customers extends Fragment implements NavigationListener {

    private CustomersFragmentBinding binding;
    private MainViewModel mainViewModel;
    private CustomerViewModel customersViewModel;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customersViewModel = new ViewModelProvider(requireActivity()).get(CustomerViewModel.class);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CustomersFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.customersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), (String) v.getTag(), Toast.LENGTH_SHORT).show();
            }
        });

        mainViewModel.setFabAction(new fabAction() {
            @Override
            public int getNavigation() {
                return R.id.show_create_booking_fragment;
            }
        });
        mainViewModel.setFabVisible(false);


        customersViewModel.getProgressBarVisibility().observe(getViewLifecycleOwner(), visibility ->{
            binding.progressBar.setVisibility(visibility);
        });
        customersViewModel.getCustomers().observe(getViewLifecycleOwner(), list -> {
            if (list!=null){
                /*Log.d("J-Purple", "onViewCreated() called with: view = [" + view + "], savedInstanceState = [" + savedInstanceState + "]");
                if (binding.getRoot().getContext().getResources().getBoolean(R.bool.isTablet)){
                    binding.customersList.setLayoutManager(new GridLayoutManager(getContext(),2,LinearLayoutManager.VERTICAL,false));
                } else {
                    binding.customersList.setLayoutManager(new LinearLayoutManager(getContext()));
                }*/
                binding.customersList.setAdapter(new CustomerAdapter(list));
            }
        });

    }


    @Override
    public void onNavigate(String key, Bundle data, int res) {

    }
}
