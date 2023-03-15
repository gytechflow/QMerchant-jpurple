package cm.clear.qmerchant.modules.orders.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import cm.clear.qmerchant.Schedulers.PollObserver;
import cm.clear.qmerchant.common.interfaces.NavigationListener;
import cm.clear.qmerchant.dashboard.MainViewModel;
import cm.clear.qmerchant.databinding.OrdersFragmentBinding;
import cm.clear.qmerchant.modules.orders.data.OrdersMessenger;
import cm.clear.qmerchant.notificationService.NotificationService;
import cm.clear.qmerchant.notificationService.ServiceGlobals;


public class OrdersFragment extends Fragment implements NavigationListener {
    private static final String TAG = OrdersFragment.class.getName();
    private OrdersViewModel ordersViewModel;
    private MainViewModel mainViewModel;
    @Nullable
    protected NotificationService.NotificationBinder binder;
    @NonNull
    protected PollObserver pollObserver = new PollObserver() {
        @Override
        public void onDataChange() {
            ordersViewModel.newOrderMade();
        }
    };
    private final ServiceConnection orderConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = (NotificationService.NotificationBinder) iBinder;
            binder.subscribe(ServiceGlobals.ORDER_SERVICE, pollObserver);
//            for (ToggleObject toggleObject : toggleObjects) {
//                binder.subscribe(ServiceGlobals.BOOKING_SERVICE, toggleObject);
//            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bindToNotificationService();
        }
    };


    protected void bindToNotificationService() {
        // Bind to LocalService
        Intent intent = new Intent(requireContext(), NotificationService.class);
        requireContext().bindService(intent, orderConnection, Context.BIND_AUTO_CREATE);
    }

    private OrdersFragmentBinding binding;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("JPurple-LifeCycle", TAG+"::onCreateView() called ");
        binding = OrdersFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("JPurple-LifeCycle", TAG+"::onCreate() called ");
        OrdersMessenger.getSharedInstance().setNavigationListener(this);
        ordersViewModel = new OrdersViewModel(requireContext());
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("JPurple-LifeCycle", TAG+"::onViewCreated() called ");
        binding.setViewModel(ordersViewModel);
    }

    @Override
    public void onResume() {
        Log.d("JPurple-LifeCycle", TAG+"::onResume() called ");
        bindToNotificationService();
        ordersViewModel.onResume();

        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("JPurple-LifeCycle", TAG+"::onPause() called ");
        OrdersMessenger.getSharedInstance().clearCalls();
        try {
            if (binder != null) {
                binder.unSubscribe(ServiceGlobals.ORDER_SERVICE,pollObserver);
            }
            requireContext().unbindService(orderConnection);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("JPurple-LifeCycle", TAG+"::onDestroyView() called ");
        binding = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("JPurple-LifeCycle", TAG+"::onStop() called ");
        ordersViewModel.onStop();

        try {
            if (binder != null) {
                binder.unSubscribe(ServiceGlobals.ORDER_SERVICE,pollObserver);
            }
            requireContext().unbindService(orderConnection);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        //ordersViewModel.reset();
    }

    @Override
    public void onDestroy() {
        ordersViewModel.onDestroy();
        Log.d("JPurple-LifeCycle", TAG+"::onDestroy() called ");
        //ordersViewModel.reset();
        super.onDestroy();
    }

    @Override
    public void onNavigate(String key, Bundle data, int res) {

        Navigation.findNavController(binding.getRoot())
//                        .navigate(R.id.fragment_item_detail, arguments);
                .navigate(res);
    }


}