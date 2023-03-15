package cm.clear.qmerchant.modules.bookings.addResource.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.modules.bookings.addResource.data.ResourceListener;
import cm.clear.qmerchant.models.booking.BookingActions;
import cm.clear.qmerchant.modules.bookings.data.BookingStateSafe;
import cm.clear.qmerchant.models.booking.bookingStates.BookingState;
import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.common.listviewmanagement.stateStorage.NotAKeyHolderException;
import cm.clear.qmerchant.common.listviewmanagement.stateStorage.StateKeyHolder;
import cm.clear.qmerchant.dashboard.MainViewModel;
import cm.clear.qmerchant.databinding.FragmentSelectTablesBinding;
import cm.clear.qmerchant.interfaces.ResponseListener;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.models.qtable.QTable;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class AddResourceFragment extends Fragment implements ResponseListener, ResourceListener, StateKeyHolder {

    private FragmentSelectTablesBinding binding;
    private Call<List<QTable>> call_getTables;
    private CallAndCallback<Object> callAndCallback;
    private List<String> callList = new ArrayList<>();
    private List<QTable> selectedTables = new ArrayList<>();
    private List<QTable> filteredTables = new ArrayList<>();
    private List<QTable> tables = new ArrayList<>();
    private BookingState state;
    private int requiredSum = 0;
    private MainViewModel mainViewModel;
    private Booking selectedBooking;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSelectTablesBinding.inflate(inflater, container, false);
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
                call_getTables = ApiUtil.getBookingService().getFreeTables(Integer.parseInt(selectedBooking.getId()));
                QCallback<List<QTable>> qCallback = new QCallback<List<QTable>>() {

                    @Override
                    public void responseSuccessful(Call<List<QTable>> call, Response<List<QTable>> response) {
//                        Log.d("J-Purple", "responseSuccessful() called with: call = [" + call + "], response = [" + response + "]");
                        tables = response.body();
                        loadAdapter();
                    }

                    @Override
                    public void requestFailure(Call<List<QTable>> call, Throwable t) {

                    }

                    @Override
                    public void responseUnsuccessful(Call<List<QTable>> call, Response<List<QTable>> response) {

                    }

                };
                callAndCallback = new CallAndCallback(call_getTables, qCallback);
                RequestManager.getInstance().addNewCall(this, callAndCallback);
                requiredSum = Integer.parseInt(selectedBooking.getNbplace());
                String start_date = TmsConverter.getFullDate(selectedBooking.getDatep(), TmsConverter.FROM_SQL_JAVA);
                binding.dateAndTime.setText(start_date);
            });
        } catch (NotAKeyHolderException e) {
            e.printStackTrace();
        }


        try {
            BookingStateSafe.getInstance().getState(this).observe(getViewLifecycleOwner(), object -> {
                BookingState percentState = (BookingState) object;
                binding.sumOnTotal.setText(percentState.getOccupationTally());
                if (percentState.haveTablesLoaded() || !percentState.hasTables()) {
                    state = percentState;
                    loadAdapter();
//                    Log.d("J-Purple", "onViewCreated() called with: view = [" + view + "], savedInstanceState = [" + savedInstanceState + "]");
                }
            });
        } catch (NotAKeyHolderException e) {
            e.printStackTrace();
        }


        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });

        binding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.listview != null) {
                    if (binding.listview.getAdapter() != null) {
                        ((ResourceAdapter) binding.listview.getAdapter()).clearSelection();
                    }
                }
            }
        });

        binding.assignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingActions.removeAllResources(selectedBooking.getId()).observe(getViewLifecycleOwner(), resourceResultState -> {
                    if (resourceResultState.isSuccessful()) {
                        Log.d("J-Purple", "onClick() called with: v = [ success one ] list size = [" + selectedTables.size() + "]");
                        if (selectedTables.size()==0)
                            Navigation.findNavController(binding.getRoot()).popBackStack();
                        for (QTable selectedTable : selectedTables) {
                             String bookingId = selectedBooking.getId();
                            String tableId = selectedTable.getId().toString();

                            BookingActions.addResource(bookingId, tableId).observe(getViewLifecycleOwner(), resourceResultState1 -> {
                                if (resourceResultState1 != null) {
                                    if (resourceResultState1.isSuccessful()) {
                                        Log.d("J-Purple", "onClick() called with: v = [ success two ]");
                                        requestSuccessful(resourceResultState1.getResourceId());
                                    } else {
                                        requestUnsuccessful(resourceResultState1.getResourceId());
                                    }
                                }
                            });
                            callList.add(String.valueOf(selectedTable.getId()));
                        }
                    }
                });
            }
        });
    }

    private void loadAdapter() {

        if (binding != null && state != null && !tables.isEmpty()) {
            binding.listview.setLayoutManager(new LinearLayoutManager(getContext()));
            if (state.getTotalCapacity() == 0) {
                binding.listview.setAdapter(new ResourceAdapter(AddResourceFragment.this, tables, requiredSum));
            } else {
                List<QTable> qTables = new ArrayList<>();
                qTables.addAll(state.getAssignedTables());
                tables.removeAll(state.getAssignedTables());
                qTables.addAll(tables);
                for (QTable table : qTables) {
                    Log.e("J-Purple", "checkLimit:two added ref = [" + table.getRef() + "] id = [" + table.getId() + "]");
                }
                binding.listview.setAdapter(new ResourceAdapter(AddResourceFragment.this, qTables, requiredSum, state.getAssignedTables()));
            }
        }
    }

    private void requestUnsuccessful(String resourceId) {
    }

    private void requestSuccessful(String resourceId) {
        callList.remove(resourceId);

        if (callList.isEmpty()) {
            BookingStateSafe.getInstance().reloadStateObject();
            Toast.makeText(getContext(), "Resource Added!", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(binding.getRoot()).popBackStack();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void updateCounter(@NonNull List<QTable> selectedTables, int localSum) {
        this.selectedTables = selectedTables;
        this.filteredTables = selectedTables;
        binding.sumOnTotal.setText(localSum + "/" + selectedBooking.getNbplace());
    }

    @Override
    public void reloadRequest() {

    }
}
