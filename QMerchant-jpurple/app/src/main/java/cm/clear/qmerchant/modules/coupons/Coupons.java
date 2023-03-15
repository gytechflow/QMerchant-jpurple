package cm.clear.qmerchant.modules.coupons;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.Schedulers.PollObserver;
import cm.clear.qmerchant.common.interfaces.FilterChangeObserver;
import cm.clear.qmerchant.common.interfaces.NavigationListener;
import cm.clear.qmerchant.common.interfaces.QFilterManager;
import cm.clear.qmerchant.common.toggleOptions.CouponToggleObject;
import cm.clear.qmerchant.common.toggleOptions.ScheduleManager;
import cm.clear.qmerchant.common.toggleOptions.ToggleAdapter;
import cm.clear.qmerchant.common.toggleOptions.ToggleObject;
import cm.clear.qmerchant.common.toggleOptions.ToggleScheduleManager;
import cm.clear.qmerchant.dashboard.MainViewModel;
import cm.clear.qmerchant.databinding.CouponsFragmentBinding;
import cm.clear.qmerchant.modules.coupons.adapter.CouponsAdapter;
import cm.clear.qmerchant.notificationService.NotificationService;
import cm.clear.qmerchant.notificationService.ServiceGlobals;

public class Coupons extends Fragment implements NavigationListener {
    private static final String TAG = "Coupons";
    private CouponsFragmentBinding binding;
    private ClickHandler headerClickHandler;
    CouponsViewModel viewModel;
    private MainViewModel mainViewModel;
    protected NotificationService.NotificationBinder binder;
    @NonNull
    protected PollObserver pollObserver = new PollObserver() {
        @Override
        public void onDataChange() {
            //viewModel.newBookingMade();
        }
    };
    private final ServiceConnection bookingConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = (NotificationService.NotificationBinder) iBinder;
            binder.subscribe(ServiceGlobals.COUPON_SERVICE, pollObserver);
//            for (ToggleObject toggleObject : toggleObjects) {
//                binder.subscribe(ServiceGlobals.BOOKING_SERVICE, toggleObject);
//            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //bindToNotificationService();
        }
    };

    @NonNull
    protected final ScheduleManager scheduleManager = new ToggleScheduleManager("Coupons", 5000);

    protected final List<ToggleObject> toggleObjects = new ArrayList<ToggleObject>(){{
        //add(new BookingToggleObject(R.string.all_text, "-1", scheduleManager));
        ToggleObject toggleObject = new CouponToggleObject(R.string.un_used_coupons, "0", scheduleManager);
        toggleObject.setClicked(true);
        add(toggleObject);
        add(new CouponToggleObject(R.string.used_coupons, "1", scheduleManager));
        add(new CouponToggleObject(R.string.canceled, "2", scheduleManager));
    }};


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]");
        super.onCreate(savedInstanceState);
        viewModel = new CouponsViewModel();
        headerClickHandler = new ClickHandler(viewModel.getDataSource());
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        binding = CouponsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated() called with: view = [" + view + "], savedInstanceState = [" + savedInstanceState + "]");
        super.onViewCreated(view, savedInstanceState);
        binding.setClickHandler(headerClickHandler);
        binding.header.setClickHandler(new ToggleAdapter(toggleObjects));
        mainViewModel.setFabAction(() -> R.id.action_show_create_coupon_fragment);
        mainViewModel.setFabVisible(true);
        viewModel.getCoupons().observe(getViewLifecycleOwner(), couponIsPagedList -> {
            if (couponIsPagedList!=null){
                if (couponIsPagedList.isNewPage()){
                    CouponsAdapter couponsAdapter = (CouponsAdapter) binding.couponsList.getAdapter();
                    if (couponsAdapter != null) {
                        couponsAdapter.add(couponIsPagedList.getList());
                    }
                } else {
                    binding.swiperefresh.setRefreshing(false);
                    binding.couponsList.setAdapter(new CouponsAdapter(couponIsPagedList.getList(), this));
                }
            }
        });

        binding.swiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        viewModel.reloadCoupons();
                    }
                }
        );

        binding.couponsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == Objects.requireNonNull(recyclerView.getAdapter()).getItemCount() - 1) {
                    //bottom of list!
                    viewModel.listBottomReached();
                }
            }
        });

        viewModel.isProgressBarVisible().observe(getViewLifecycleOwner(), visibility -> {
            binding.progressBar.setVisibility(visibility);
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.coupons_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        //bindToNotificationService();
        QFilterManager filterManager = new QFilterManager() {
            @Nullable
            @Override
            public FilterChangeObserver getFilterChangeObserver() {
                return viewModel.getDataSource();
            }
        };
        for (ToggleObject toggleObject : toggleObjects) {
            toggleObject.setFilterManager(filterManager);
            toggleObject.start();
        }
        scheduleManager.start();
        super.onResume();
    }

    @Override
    public void onPause() {
        //BookingMessenger.getSharedInstance().clearCalls();
        for (ToggleObject toggleObject : toggleObjects) {
            //toggleObject.setFilterManager(BookingMessenger.getSharedInstance());
            toggleObject.stop();
        }
        scheduleManager.stop();
        try {
            if (binder != null) {
                binder.unSubscribe(ServiceGlobals.BOOKING_SERVICE,pollObserver);
            }
            requireContext().unbindService(bookingConnection);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop() called");
        viewModel.reset();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        viewModel.reset();
        Log.d(TAG, "onDestroy() called");
        super.onDestroy();
    }

    @Override
    public void onNavigate(String key, Bundle data, int res) {
        Navigation.findNavController(binding.getRoot())
                        .navigate(res, data);
    }

    public void reload() {
        viewModel.reloadCoupons();
    }
}
