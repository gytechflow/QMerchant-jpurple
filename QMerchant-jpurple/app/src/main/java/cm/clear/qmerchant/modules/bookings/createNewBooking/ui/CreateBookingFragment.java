package cm.clear.qmerchant.modules.bookings.createNewBooking.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.common.listviewmanagement.stateStorage.NotAKeyHolderException;
import cm.clear.qmerchant.common.listviewmanagement.stateStorage.StateKeyHolder;
import cm.clear.qmerchant.dashboard.MainViewModel;
import cm.clear.qmerchant.databinding.FragmentCreateBookingBinding;
import cm.clear.qmerchant.models.QBookingType;
import cm.clear.qmerchant.models.Thirdparty;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.modules.bookings.data.BookingStateSafe;

public class CreateBookingFragment extends Fragment implements StateKeyHolder {

    private BookingFormViewModel formViewModel;
    private MainViewModel mainViewModel;
    private FragmentCreateBookingBinding binding;
    private Calendar start_tms = Calendar.getInstance();
    private Calendar end_tms = Calendar.getInstance();
    private QBookingType selectedBookingType;
    private boolean isUpdate;
    private String booking_id;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        formValidator = new BookingFormValidator();
        formViewModel = new BookingFormViewModel((LifecycleOwner) requireContext());
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        //mainViewModel.registerModel(formViewModel);
        mainViewModel.setFabVisible(false);
        BookingStateSafe.getInstance().register(this);
        isUpdate = CreateBookingFragmentArgs.fromBundle(getArguments()).getIsUpdate();
        booking_id = CreateBookingFragmentArgs.fromBundle(getArguments()).getBookingId();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentCreateBookingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.numberOfPlaces.setMaxValue(12);
        binding.numberOfPlaces.setMinValue(0);

        TextWatcher customerIdWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                formViewModel.getSuggestionById(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                formViewModel.setCustomerId(editable.toString());
            }
        };

        TextWatcher namesWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(getTag(), "J-Purple::onTextChanged() called with: charSequence = [" + charSequence + "], i = [" + i + "], i1 = [" + i1 + "], i2 = [" + i2 + "]");
                formViewModel.getSuggestionByNames(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(getTag(), "J-Purple::afterTextChanged() called with: editable = [" + editable + "]");
                formViewModel.setNames(editable.toString());
            }
        };


        TextWatcher phoneWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                formViewModel.getSuggestionByPhone(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                formViewModel.setPhone(editable.toString());
            }
        };

        TextWatcher emailWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                formViewModel.getSuggestionByEmail(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                formViewModel.setEmail(editable.toString());
            }
        };
        binding.editCustomerId.addTextChangedListener(customerIdWatcher);

//        AutoCompleteTextView editNamesTextView = (AutoCompleteTextView) binding.editNames;
        binding.editNames.addTextChangedListener(namesWatcher);

//        AutoCompleteTextView editPhoneTextView = (AutoCompleteTextView) binding.editPhone;
        binding.editPhone.addTextChangedListener(phoneWatcher);


        binding.editEmail.addTextChangedListener(emailWatcher);
        binding.dateButton.setText(TmsConverter.getDate(start_tms.getTime().getTime(), TmsConverter.NO_CONVERSION));
        binding.startTimeButton.setText(TmsConverter.getTime(start_tms.getTime().getTime(), TmsConverter.NO_CONVERSION));
        binding.endTimeButton.setText(TmsConverter.getTimePlus(end_tms.getTime().getTime(), TmsConverter.NO_CONVERSION,1));


        if(isUpdate){
            try {
                BookingStateSafe.getInstance().getState(this).observe(getViewLifecycleOwner(), clientAddon -> {
                    Thirdparty thirdparty = clientAddon.getClient();
                    if (thirdparty != null) {

                    formViewModel.setEditing(true);
                        binding.editCustomerId.setEnabled(false);
                        binding.editCustomerId.removeTextChangedListener(customerIdWatcher);
                        binding.editNames.setEnabled(false);
                        binding.editNames.removeTextChangedListener(namesWatcher);
                        binding.editPhone.setEnabled(false);
                        binding.editPhone.removeTextChangedListener(phoneWatcher);
                        binding.editEmail.setEnabled(false);
                        binding.editEmail.removeTextChangedListener(emailWatcher);
                        binding.createButton.setText(getString(R.string.update));

                        binding.editCustomerId.setText(TextUtils.isEmpty(thirdparty.getId()) ? "" : thirdparty.getId());
                        binding.editNames.setText(TextUtils.isEmpty(thirdparty.getName()) ? "" : thirdparty.getName());
                        binding.editPhone.setText(TextUtils.isEmpty(thirdparty.getPhone()) ? "" : thirdparty.getPhone());
                        binding.editEmail.setText(TextUtils.isEmpty(thirdparty.getEmail()) ? "" : thirdparty.getEmail());


                    }
                });
            } catch (NotAKeyHolderException e) {
                e.printStackTrace();
            }

            try {
                BookingStateSafe.getInstance().getStateObject(this).observe(getViewLifecycleOwner(), o -> {
                    Booking booking = (Booking) o;

                    long startTms = booking.getDatep();
                    start_tms.setTime(new Date(startTms*TmsConverter.FROM_SQL_JAVA));
                    long endTms = booking.getDatep2();
                    end_tms.setTime(new Date(endTms*TmsConverter.FROM_SQL_JAVA));
                    binding.endTimeLayout.setVisibility(View.VISIBLE);
                    binding.dateButton.setText(TmsConverter.getDate(start_tms.getTime().getTime(), TmsConverter.NO_CONVERSION));
                    binding.startTimeButton.setText(TmsConverter.getTime(start_tms.getTime().getTime(), TmsConverter.NO_CONVERSION));
                    binding.endTimeButton.setText(TmsConverter.getTime(end_tms.getTime().getTime(), TmsConverter.NO_CONVERSION));
                    binding.numberOfPlaces.setValue(Integer.parseInt(booking.getNbplace()));
                    binding.dropdown.setText(booking.getType());
                    binding.textDescription.setText(TextUtils.isEmpty(booking.getNote_private()) ? "" : booking.getNote_private());
                });
            } catch (NotAKeyHolderException e) {
                e.printStackTrace();
            }
        }

        formViewModel.getClientInfo().observe(getViewLifecycleOwner(), thirdparty -> {
            if (thirdparty != null) {
                if (!formViewModel.isFilterInput()) {
                    binding.editCustomerId.setText(TextUtils.isEmpty(thirdparty.getId()) ? "" : thirdparty.getId());
                    binding.editNames.setText(TextUtils.isEmpty(thirdparty.getName()) ? "" : thirdparty.getName());
                    binding.editPhone.setText(TextUtils.isEmpty(thirdparty.getPhone()) ? "" : thirdparty.getPhone());
                    binding.editEmail.setText(TextUtils.isEmpty(thirdparty.getEmail()) ? "" : thirdparty.getEmail());
                }
            }
        });


        //AutoCompleteTextView completeTextView = binding.simpleSearchView.findViewById(R.id.search_src_text);

        binding.editEmail.setThreshold(0);
        formViewModel.getSuggestions().observe(getViewLifecycleOwner(), qBookingTypes -> {
            if (qBookingTypes != null) {
                binding.editEmail.setAdapter(new ThirdpartySuggestionAdapter<>(requireContext(), R.layout.basic_dropdown_spinner_item, qBookingTypes));
                binding.editEmail.showDropDown();
                if (qBookingTypes.isEmpty())
                    binding.editEmail.dismissDropDown();
                else binding.editEmail.showDropDown();
            }
        });

        binding.editEmail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                formViewModel.setClientInfo((Thirdparty) binding.editEmail.getAdapter().getItem(i));
                binding.editEmail.dismissDropDown();
            }
        });

        binding.dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
                final Dialog dialog = new DatePickerDialog(requireContext(), android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar tempCalendar;
                        tempCalendar = new GregorianCalendar(year, month, dayOfMonth);
                        tempCalendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
                        start_tms.set(Calendar.DAY_OF_MONTH, tempCalendar.get(Calendar.DAY_OF_MONTH));
                        start_tms.set(Calendar.MONTH, tempCalendar.get(Calendar.MONTH));
                        start_tms.set(Calendar.YEAR, tempCalendar.get(Calendar.YEAR));

                        Timestamp tms = new Timestamp(tempCalendar.getTime().getTime());
                        long timestamp = tempCalendar.getTime().getTime();
//                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                        rootView.selectStartDateBtnHead.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//                        binding.dateButton.setText(formatter.format(tms));
                        binding.dateButton.setText(TmsConverter.getDate(timestamp, TmsConverter.NO_CONVERSION));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.show();
            }
        });

        binding.startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
                final Dialog dialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar tempCalendar = new GregorianCalendar();
                        tempCalendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
                        start_tms.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        start_tms.set(Calendar.MINUTE, minute);
                        tempCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        tempCalendar.set(Calendar.MINUTE, minute);
//                        Timestamp tms = new Timestamp(tempCalendar.getTime().getTime());
                        long timestamp = tempCalendar.getTime().getTime();
//                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
//                        rootView.selectStartDateBtnHead.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//                        binding.timeButton.setText(formatter.format(tms));
                        binding.startTimeButton.setText(TmsConverter.getTime(timestamp, TmsConverter.NO_CONVERSION));
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                dialog.show();
            }
        });

        binding.endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                final Dialog dialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar tempCalendar = new GregorianCalendar();
                        tempCalendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
                        end_tms.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        end_tms.set(Calendar.MINUTE, minute);
                        tempCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        tempCalendar.set(Calendar.MINUTE, minute);
//                        Timestamp tms = new Timestamp(tempCalendar.getTime().getTime());
                        long timestamp = tempCalendar.getTime().getTime();
//                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
//                        rootView.selectStartDateBtnHead.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//                        binding.timeButton.setText(formatter.format(tms));
                        binding.endTimeButton.setText(TmsConverter.getTime(timestamp, TmsConverter.NO_CONVERSION));
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                dialog.show();
            }
        });

        binding.dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBookingType = (QBookingType) binding.dropdown.getAdapter().getItem(i);
            }
        });

        formViewModel.getBookingTypes().observe(getViewLifecycleOwner(), qBookingTypes -> {
            if (qBookingTypes != null) {
                binding.dropdown.setAdapter(new QArrayAdapter<>(requireContext(), R.layout.basic_dropdown_spinner_item, qBookingTypes));
            }
        });

        formViewModel.getCreateResult().observe(getViewLifecycleOwner(), integer -> {
            if (integer != null) {
                if (integer > 0) {

                    //binding.progressBarCreateMember.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(binding.getRoot()).popBackStack();

                    // Data update event has to be emitted from here
                    //mainViewModel.notifyOnBookingAdded();

                } else {
                    Toast.makeText(getContext(), "Failure!", Toast.LENGTH_SHORT).show();
                    //binding.progressBarCreateMember.setVisibility(View.INVISIBLE);
                }
            }
        });


        binding.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //binding.progressBarCreateMember.setVisibility(View.VISIBLE);
                Booking booking = new Booking();

                if (formViewModel.isEditing()) {
                    try {
                        booking = (Booking) BookingStateSafe.getInstance().getStateObject(CreateBookingFragment.this).getValue();
                    } catch (NotAKeyHolderException e) {
                        e.printStackTrace();
                    }
                    if (end_tms!=null){
                        booking.setDatep2(end_tms.getTimeInMillis() / 1000L);
                    }
                } else booking = makeBooking();

                booking.setNbplace("" + binding.numberOfPlaces.getValue());
                booking.setDatep(start_tms.getTimeInMillis() / 1000L);
                booking.setNote_private(binding.textDescription.getText().toString());;
                Log.d("J-Purple", "onClick() called with: datep = [" + TmsConverter.getFullDate(booking.getDatep(), TmsConverter.FROM_SQL_JAVA) + "]");
                Log.d("J-Purple", "onClick() called with: datep2 = [" + TmsConverter.getFullDate(booking.getDatep2()==null?0L:booking.getDatep2(), TmsConverter.FROM_SQL_JAVA) + "]");
                formViewModel.createOrUpdateBooking(booking);
            }
        });


    }

    private Booking makeBooking() {
        Booking booking = new Booking();
        Thirdparty thirdparty = formViewModel.getThirdPartyForCreation();
        if (thirdparty == null) {
            thirdparty = new Thirdparty();
//            thirdparty.populate();
        }
        if (TextUtils.isEmpty(thirdparty.getEmail())){
            thirdparty.setEmail(thirdparty.getDefaultEmail());
        }
        booking.setNames(thirdparty.getName());
        booking.setPhone(thirdparty.getPhone());
        booking.setEmail(thirdparty.getEmail());
        Log.d(getTag(), "makeBooking() called"+thirdparty.getFullDetails());
        selectedBookingType = selectedBookingType == null ? new QBookingType(0, "", "") : selectedBookingType;
        booking.setType_id(selectedBookingType.getId());
        booking.setUserownerid("1");

        return booking;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        formViewModel.reset();
    }
}