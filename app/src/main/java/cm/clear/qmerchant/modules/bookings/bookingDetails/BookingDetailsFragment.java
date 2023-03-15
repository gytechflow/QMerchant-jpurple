package cm.clear.qmerchant.modules.bookings.bookingDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.models.booking.BookingActions;
import cm.clear.qmerchant.modules.bookings.data.BookingStateSafe;
import cm.clear.qmerchant.models.booking.bookingStates.BookingState;
import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.common.listviewmanagement.stateStorage.NotAKeyHolderException;
import cm.clear.qmerchant.common.listviewmanagement.stateStorage.StateKeyHolder;
import cm.clear.qmerchant.dashboard.MainViewModel;
import cm.clear.qmerchant.databinding.FragmentBookingDetailsBinding;
import cm.clear.qmerchant.models.booking.Booking;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingDetailsFragment extends Fragment implements StateKeyHolder {

    private FragmentBookingDetailsBinding binding;
    private MainViewModel mainViewModel;
    private Booking selectedBooking;


    public BookingDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        selectedBooking = arViewModel.getSelectedBooking();
        binding = FragmentBookingDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        mainViewModel.setFabVisible(false);
        BookingStateSafe.getInstance().register(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            BookingStateSafe.getInstance().getStateObject(this).observe(getViewLifecycleOwner(), object -> {
                selectedBooking = (Booking) object;
                binding.textRef.setText(selectedBooking.getRef());
                binding.textName.setText(selectedBooking.getNames());
                binding.textEmail.setText(selectedBooking.getEmail());
                binding.textCode.setText(selectedBooking.getCode());
                binding.textPhone.setText(selectedBooking.getPhone());
                binding.textProgress.setText(selectedBooking.getPercentage());
                binding.textNote.setText(selectedBooking.getLabel());
                binding.textDescription.setText(selectedBooking.getNote_private());
                binding.textDate.setText(TmsConverter.getDate(selectedBooking.getDatep(), TmsConverter.FROM_SQL_JAVA));
                binding.textStartTime.setText(TmsConverter.getTime(selectedBooking.getDatep(), TmsConverter.FROM_SQL_JAVA));
                binding.textEndTime.setText(TmsConverter.getTime(selectedBooking.getDatep2(), TmsConverter.FROM_SQL_JAVA));
            });
        } catch (NotAKeyHolderException e) {
            e.printStackTrace();
        }

        try {
            BookingStateSafe.getInstance().getState(this).observe(getViewLifecycleOwner(), object -> {
                BookingState percentState  = (BookingState) object;
                if (percentState != null) {

                    if (percentState.getClient()!=null){
                        binding.textName.setText(percentState.getClient().getName());
                        binding.textEmail.setText(percentState.getClient().getEmail());
                        binding.textPhone.setText(percentState.getClient().getPhone());
                    }

                    binding.textConfirmResource.setText(percentState.getConfirmText(getContext()));
                    binding.textAssignResource.setText(percentState.getAssignText(getContext()));
                    binding.textRemoveResource.setText(percentState.getCancelText(getContext()));

                    binding.layoutAssignResource.setBackgroundTintList(percentState.getAssignButtonColor(getContext()));
                    binding.layoutRemoveResource.setBackgroundTintList(percentState.getCancelButtonColor(getContext()));
                    binding.layoutConfirmBooking.setBackgroundTintList(percentState.getConfirmButtonColor(getContext()));
                    binding.layoutRefBackground.setBackgroundTintList(percentState.getRefColor(getContext()));
                    binding.layoutAssignResource.setVisibility(percentState.getAssignButtonVisibility());
                    binding.layoutRemoveResource.setVisibility(percentState.getCancelButtonVisibility());
                    binding.layoutConfirmBooking.setVisibility(percentState.getConfirmButtonVisibility());
                    binding.layoutProgressContainer.setBackgroundTintList(percentState.getRefColor(binding.getRoot().getContext()));
                    binding.textTableCounter.setText(percentState.getOccupationTally());

                    if (percentState.haveTablesLoaded()){
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.recyclerView.setAdapter(new TablesListAdapter(percentState.getAssignedTables()));
                    }

                    binding.layoutEditResource.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Booking booking = selectedBooking;
                            BookingStateSafe.getInstance().setStateObject(booking);
                            BookingStateSafe.getInstance().setState(percentState);
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("is_update", true);
                            Navigation.findNavController(view)
                                    //                        .navigate(R.id.fragment_item_detail, arguments);
                                    .navigate(R.id.action_show_create_booking_fragment, bundle);
                        }
                    });

                    binding.layoutAssignResource.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Booking booking = selectedBooking;
                            BookingStateSafe.getInstance().setStateObject(booking);
                            BookingStateSafe.getInstance().setState(percentState);

                            Navigation.findNavController(v)
    //                        .navigate(R.id.fragment_item_detail, arguments);
                                    .navigate(R.id.action_show_resources_fragment);
                        }
                    });

                    binding.layoutConfirmBooking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            BookingActions.confirmBooking(selectedBooking.getId()).observe(getViewLifecycleOwner(), resourceResultState -> {
//                                BookingStateSafe.getInstance().reloadStateObject();
//                            });
                            percentState.getConfirmResult("").observe(getViewLifecycleOwner(), resourceResult -> {
                                if (resourceResult.isSuccessful()){
                                    BookingActions.confirmBooking(selectedBooking.getId()).observe(getViewLifecycleOwner(), resourceResultState -> {
                                        if (resourceResultState.isSuccessful()){
                                            BookingStateSafe.getInstance().reloadStateObject();
                                        }
                                    });
                                }
                            });
                        }
                    });
                }

                binding.layoutRemoveResource.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BookingActions.endEvent(selectedBooking.getId()).observe(getViewLifecycleOwner(), resourceResultState -> {
                            BookingStateSafe.getInstance().reloadStateObject();
                        });
                    }
                });
            });
        } catch (NotAKeyHolderException e) {
            e.printStackTrace();
        }
    }
}