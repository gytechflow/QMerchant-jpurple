package cm.clear.qmerchant.modules.tables.events.ui;

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

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.common.interfaces.NavigationListener;
import cm.clear.qmerchant.dashboard.MainViewModel;
import cm.clear.qmerchant.databinding.EventsFragmentBinding;

public class Events extends Fragment implements NavigationListener {
    private static final String TAG = Events.class.getName();
    EventsFragmentBinding binding;
    private EventsViewModel viewModel;
    private MainViewModel mainViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        int table_id = EventsArgs.fromBundle(getArguments()).getTableId();
        if (table_id==-1){
            Log.e("JPurple-Expected", TAG+"::onCreate: called with invalid argument table_id = ["+table_id+"]");
            Toast.makeText(requireContext(), "Error Getting Selected Table", Toast.LENGTH_SHORT).show();
        }
        viewModel = new EventsViewModel(requireContext(), table_id);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = EventsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel.setFabAction(() -> R.id.action_show_create_booking_fragment);
        //mainViewModel.setFabVisible(true);
        viewModel.load();
        binding.setViewManager(viewModel);


    }

    @Override
    public void onNavigate(String key, Bundle data, int res) {
        Navigation.findNavController(binding.getRoot())
//                        .navigate(R.id.fragment_item_detail, arguments);
                .navigate(res);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.load();
    }
}
