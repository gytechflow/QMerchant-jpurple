package cm.clear.qmerchant.modules.bookings.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.Schedulers.PollObserver;
import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.common.interfaces.NavigationListener;
import cm.clear.qmerchant.common.toggleOptions.BookingToggleObject;
import cm.clear.qmerchant.common.toggleOptions.ScheduleManager;
import cm.clear.qmerchant.common.toggleOptions.ToggleAdapter;
import cm.clear.qmerchant.common.toggleOptions.ToggleObject;
import cm.clear.qmerchant.common.toggleOptions.ToggleScheduleManager;
import cm.clear.qmerchant.dashboard.MainViewModel;
import cm.clear.qmerchant.databinding.BookingsFragmentBinding;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.modules.bookings.data.BookingMessenger;
import cm.clear.qmerchant.notificationService.NotificationService;
import cm.clear.qmerchant.notificationService.ServiceGlobals;

public class BookingsFragment extends Fragment implements NavigationListener {

    public static final String BOOKING_AND_STATE_KEY = "selectedBooking";
    protected static final String TAG = BookingsFragment.class.getName();

    private BookingsFragmentBinding binding;
    @Nullable
    protected BookingsViewModel bookingsViewModel;
    private MainViewModel mainViewModel;
    @Nullable
    protected NotificationService.NotificationBinder binder;
    @NonNull
    protected PollObserver pollObserver = new PollObserver() {
        @Override
        public void onDataChange() {
            bookingsViewModel.newBookingMade();
        }
    };

    private final ServiceConnection bookingConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = (NotificationService.NotificationBinder) iBinder;
            binder.subscribe(ServiceGlobals.BOOKING_SERVICE, pollObserver);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bindToNotificationService();
        }
    };

    @NonNull
    protected final ScheduleManager scheduleManager = new ToggleScheduleManager("Bookings", 500, 10);

    private Boolean [] selections = { true, false, false, false};
    private List<ToggleObject>  toggleObjects;
    /*
    protected List<ToggleObject>  toggleObjects = new ArrayList<ToggleObject>(){
        {
        add(new BookingToggleObject(bookingsViewModel, R.string.new_text, Booking.OPTIONS_VALUES[0], scheduleManager, selections[0]));
        add(new BookingToggleObject(bookingsViewModel, R.string.confirmed_text, Booking.OPTIONS_VALUES[1], scheduleManager, selections[1]));
        add(new BookingToggleObject(bookingsViewModel, R.string.assigned, Booking.OPTIONS_VALUES[2], scheduleManager, selections[2]));
        add(new BookingToggleObject(bookingsViewModel, R.string.completed, Booking.OPTIONS_VALUES[3], scheduleManager, selections[3]));
        }
    };
    */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        BookingMessenger.getSharedInstance().setNavigationListener(this);
        bookingsViewModel = new ViewModelProvider(requireActivity(), new BookingsViewModelFactory(requireContext())).get(BookingsViewModel.class);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        toggleObjects = new ArrayList<ToggleObject>(){
            {
                add(new BookingToggleObject(bookingsViewModel, R.string.new_text, Booking.OPTIONS_VALUES[0], scheduleManager, selections[0]));
                add(new BookingToggleObject(bookingsViewModel, R.string.confirmed_text, Booking.OPTIONS_VALUES[1], scheduleManager, selections[1]));
                add(new BookingToggleObject(bookingsViewModel, R.string.assigned, Booking.OPTIONS_VALUES[2], scheduleManager, selections[2]));
                add(new BookingToggleObject(bookingsViewModel, R.string.completed, Booking.OPTIONS_VALUES[3], scheduleManager, selections[3]));
            }
        };

    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BookingsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    protected void bindToNotificationService() {
        // Bind to LocalService
        Intent intent = new Intent(requireContext(), NotificationService.class);
        requireContext().bindService(intent, bookingConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //binding.bookingList.setOnClickListener(v -> Toast.makeText(getContext(), (String) v.getTag(), Toast.LENGTH_SHORT).show());

        mainViewModel.setFabAction(() -> R.id.action_show_create_booking_fragment);
        mainViewModel.setFabVisible(true);

        // on list bottom reached
        binding.bookingList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == Objects.requireNonNull(recyclerView.getAdapter()).getItemCount() - 1) {
                    //bottom of list!
                    BookingMessenger.getSharedInstance().listBottomReached();
                }
            }
        });

        if (bookingsViewModel != null) {
            bookingsViewModel.getProgressBarVisibility().observe(getViewLifecycleOwner(), visibility -> {
                binding.progressBar.setVisibility(visibility);
            });
        }

        binding.scrollviewButtonGroup.setAdapter(new ToggleAdapter(toggleObjects));

        binding.swiperefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        BookingMessenger.getSharedInstance().refreshFilter();
                    }
                }
        );

        //TODO replace with getCurrentDay from external class
//        String today = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        String today = TmsConverter.todayDateOnly();
        binding.textSelectDate.setText(today);
        long timestamp = 0L;

        binding.switchDateSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.switchDateSort.isChecked()) {
                    long tms;
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    try {

                        tms = Objects.requireNonNull(formatter.parse(binding.textSelectDate.getText().toString())).getTime() / 1000L;
                        BookingMessenger.getSharedInstance().changeDate(tms);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    BookingMessenger.getSharedInstance().changeDate(0L);
                }
            }
        });

        binding.layoutSelectDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                LocalDate today = LocalDate.now();
                final Dialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar tempCalendar;
                        tempCalendar = new GregorianCalendar(year, month, dayOfMonth);
                        long timeStamp = tempCalendar.getTime().getTime();
//                        Timestamp tms = new Timestamp(tempCalendar.getTime().getTime());
//                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//                        rootView.selectStartDateBtnHead.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                        binding.textSelectDate.setText(TmsConverter.getDate(timeStamp, TmsConverter.NO_CONVERSION));
                        binding.switchDateSort.setChecked(true);
//                        setDate(tms.getTime()/1000L);

                        bookingsViewModel.onDateChange(TmsConverter.getFromJavaToSql(timeStamp));
                        BookingMessenger.getSharedInstance().changeDate(TmsConverter.getFromJavaToSql(timeStamp));

                    }
                }, today.getYear(), today.getMonthValue() - 1, today.getDayOfMonth());
                dialog.show();
            }
        });

        // Initial request with today and NEW.
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String filters [] = { TmsConverter.todayDateOnlyWithFormat(formatter), Booking.OPTIONS_VALUES[0] };
        bookingsViewModel.getBookingsList(filters).observe(getViewLifecycleOwner(), bookings -> {
            if (bookings != null) {
                binding.swiperefresh.setRefreshing(false);
                //Log.d("HOSIRUS", "onViewCreated() called with: view = [" + view + "], savedInstanceState = [" + savedInstanceState + "]");

                boolean isUpdate = (boolean) bookings.keySet().toArray()[0];
                if (isUpdate) {
                    BookingsAdapter bookingsAdapter = (BookingsAdapter) binding.bookingList.getAdapter();
//                    assert bookingsAdapter != null;
                    if (bookingsAdapter != null) {
                        List<Booking> bookingList = bookings.get(true);
                        assert bookingList != null;
                        if (!bookingList.isEmpty()) {
                            bookingsAdapter.addAll(bookingList);
                        }
                    }
                } else {
                    binding.bookingList.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.bookingList.setAdapter(new BookingsAdapter(bookings.get(false)));
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        bindToNotificationService();
        for (ToggleObject toggleObject : toggleObjects) {
            toggleObject.setFilterManager(BookingMessenger.getSharedInstance());
            toggleObject.start();
        }
        scheduleManager.start();
        super.onResume();

        Log.e("PROOF-JPurple", BookingsFragment.class.getName()+"-->onResume: ");
    }

    @Override
    public void onPause() {
        BookingMessenger.getSharedInstance().clearCalls();
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

        Log.e("PROOF-JPurple", BookingsFragment.class.getName()+"-->onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (bookingsViewModel != null) {
            bookingsViewModel.reset();
        }
//        binding.toggleButton.check(binding.button0.getId());
        Log.e("PROOF-JPurple", BookingsFragment.class.getName()+"-->onStop: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (binder != null) {
                binder.unSubscribe(ServiceGlobals.BOOKING_SERVICE,pollObserver);
            }

            requireContext().unbindService(bookingConnection);

        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        if (bookingsViewModel != null) {
            bookingsViewModel.reset();
        }

        scheduleManager.stop();
        Log.e("PROOF-JPurple", BookingsFragment.class.getName()+"-->onDestroy: ");
    }

    @Override
    public void onNavigate(String key, Bundle data, int res) {
        Navigation.findNavController(binding.getRoot()).navigate(res);
    }

    public Boolean[] getSelections() {
        return selections;
    }
    public void setSelections(Boolean[] selections) {
        if(this.selections.length == selections.length){
            this.selections = selections;
        }
    }
}
