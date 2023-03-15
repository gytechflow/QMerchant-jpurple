package cm.clear.qmerchant.modules.tables.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.common.interfaces.NavigationListener;
import cm.clear.qmerchant.dashboard.MainViewModel;
import cm.clear.qmerchant.databinding.TablesListFragmentBinding;

public class TablesFragment extends Fragment implements NavigationListener {
    @Nullable
    protected TablesListFragmentBinding binding;
    @Nullable
    protected TablesViewModel viewModel;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new TablesViewModel(this, new Runnable() {
            @Override
            public void run() {
                initRecyclerView();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TablesListFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerview.setAdapter(viewModel.getAdapter());
        binding.setViewManager(viewModel);

        String today = TmsConverter.todayDateOnly();
        binding.textSelectDate.setText(today);
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
                        viewModel.setDate(timeStamp);
                    }
                }, today.getYear(), today.getMonthValue() - 1, today.getDayOfMonth());
                dialog.show();
            }
        });
    }

    protected void initRecyclerView(){
        if (binding!=null)
            binding.recyclerview.setAdapter(viewModel.getAdapter());
    }

    @Override
    public void onNavigate(@Nullable String key, @Nullable Bundle data, int res) {
        Navigation.findNavController(binding.getRoot())
//                        .navigate(R.id.fragment_item_detail, arguments);
                .navigate(res);
    }

    @Override
    public void onNavigate(@NonNull View view, @NonNull Bundle data, int action) {
        Navigation.findNavController(view)
                        .navigate(action, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.reset();
    }
}
