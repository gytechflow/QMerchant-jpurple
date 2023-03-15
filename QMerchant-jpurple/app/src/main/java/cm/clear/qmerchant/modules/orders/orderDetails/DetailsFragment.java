package cm.clear.qmerchant.modules.orders.orderDetails;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import cm.clear.qmerchant.common.core.TextHelper;
import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.common.interfaces.NavigationListener;
import cm.clear.qmerchant.dashboard.MainViewModel;
import cm.clear.qmerchant.databinding.FragmentOrderDetailsBinding;
import cm.clear.qmerchant.models.order.Order;
import cm.clear.qmerchant.models.Thirdparty;
import cm.clear.qmerchant.models.order.OrdersActions;
import cm.clear.qmerchant.common.listviewmanagement.stateStorage.NotAKeyHolderException;
import cm.clear.qmerchant.common.listviewmanagement.stateStorage.StateKeyHolder;
import cm.clear.qmerchant.modules.orders.data.OrderStateSafe;

public class DetailsFragment extends Fragment implements NavigationListener, StateKeyHolder {

    private FragmentOrderDetailsBinding binding;
    private MainViewModel mainViewModel;
    private OrderDetailsViewModel viewModel;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        OrdersMessenger.getINSTANCE().setNavigationListener(this);
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OrderStateSafe.getInstance().register(this);
        Order order = DetailsFragmentArgs.fromBundle(getArguments()).getOrder();
        viewModel = new OrderDetailsViewModel(order);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        try {
            OrderStateSafe.getInstance().getStateObject(this).observe(getViewLifecycleOwner(), object -> {
                Log.e("J-Purple", "onViewCreated::getStateObject: " );
                Order order = (Order) object;
                binding.textRef.setText(order.getRef());
                binding.textName.setText(order.getName());
                Double totalTtc = Double.parseDouble(order.getTotalTtc());
                binding.textLocation.setText(order.getNotePublic());
                binding.textDevice.setText(order.getNotePrivate());
                binding.textAmount.setText(TextHelper.priceWithDecimal(totalTtc) + " " + order.getMulticurrencyCode());
                binding.textProgress.setText(order.getStatus());
                binding.textDate.setText(TmsConverter.getDate(Long.parseLong(order.getDateCreation()), TmsConverter.FROM_SQL_JAVA));
                binding.textTime.setText(TmsConverter.getTime(Long.parseLong(order.getDateCreation()), TmsConverter.FROM_SQL_JAVA));
                binding.textProgress.setText(order.getStatus());
                binding.textQuantity.setText(order.getLines().size() > 1 ? (order.getLines().size() + " products.") : (order.getLines().size() + " product."));

                try {
                    OrderStateSafe.getInstance().getState(this).observe(getViewLifecycleOwner(), orderState -> {
                        Log.e("J-Purple", "onViewCreated::getState: " );
                        Thirdparty thirdparty = orderState.getClient();
                        if (thirdparty != null) {
                            binding.textName.setText(thirdparty.getName());
                            binding.textEmail.setText(thirdparty.getEmail());
                            binding.textPhone.setText(thirdparty.getPhone());
                        }
                        binding.textConfirmResource.setText(orderState.getConfirmText(requireContext()));
                        binding.textAssignResource.setText(orderState.getAssignText(requireContext()));
                        binding.textRemoveResource.setText(orderState.getCancelText(requireContext()));
                        binding.layoutRefBackground.setBackgroundTintList(orderState.getRefColor(requireContext()));
                        binding.layoutProgressContainer.setBackgroundTintList(orderState.getRefColor(requireContext()));

                        binding.layoutConfirmBooking.setVisibility(orderState.getConfirmButtonVisibility());
                        binding.layoutRemoveResource.setVisibility(orderState.getCancelButtonVisibility());
                        binding.layoutAssignResource.setVisibility(orderState.getAssignButtonVisibility());
                        binding.layoutConfirmBooking.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                OrdersActions.getINSTANCE().confirmOrder(order.getId()).observe(getViewLifecycleOwner(), resourceResultState -> {
                                    if (resourceResultState.isSuccessful()) {
    //                        listener.reloadList();
//                                        OrdersMessenger.getINSTANCE().listItemChanged();
                                        OrderStateSafe.getInstance().reloadStateObject();
                                    }
                                });
                            }
                        });
                        binding.layoutRemoveResource.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                OrdersActions.getINSTANCE().cancelOrder(order.getId()).observe(getViewLifecycleOwner(), resourceResultState -> {
                                    if (resourceResultState != null) {
                                        if (resourceResultState.isSuccessful()) ;
                                        OrderStateSafe.getInstance().reloadStateObject();
//                                        OrdersMessenger.getINSTANCE().listItemChanged();
                                    }
                                });
                            }
                        });


                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
                        binding.recyclerView.setAdapter(new ProductsAdapter(order.getLines()));
                    });
                } catch (NotAKeyHolderException e) {
                    e.printStackTrace();
                }
            });
        } catch (NotAKeyHolderException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onNavigate(@NonNull String key, @NonNull Bundle data, int res) {

    }

    private void loadClientInfo(Thirdparty thirdparty) {
        if (binding != null) {
            binding.textName.setText(thirdparty.getName());
            binding.textEmail.setText(thirdparty.getEmail());
            binding.textPhone.setText(thirdparty.getPhone());
        }
    }
}