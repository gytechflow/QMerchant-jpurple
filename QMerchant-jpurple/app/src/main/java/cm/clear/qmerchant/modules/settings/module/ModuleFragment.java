package cm.clear.qmerchant.modules.settings.module;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.databinding.FragmentModuleBinding;
import cm.clear.qmerchant.interfaces.ResponseListener;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;


public class ModuleFragment extends Fragment{
    private FragmentModuleBinding binding;
    private String StatusOrder;
    private String StatusBooking;
    private String StatusMedia;
    private static String accept_order = "QLLA_ACCEPT_ORDERS";
    private static String accept_booking = "QLLA_ACCEPT_BOOKINGS";
    private static String accept_media = "QLLA_ACCEPT_MEDIAS";





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getOrderAvailable();
        getBookingAvailable();
        getMediaAvailable();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentModuleBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switchElement();
    }

    public void switchElement(){
        binding.switchOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.switchOrder.isChecked()) {
                    StatusOrder = "0";
                    setStatus(accept_order, StatusOrder);
                    Log.d("TAG", "onClick: " + StatusOrder);

                } else {
                    StatusOrder = "1";
                    setStatus(accept_order, StatusOrder);
                    Log.d("TAG", "onClick: " + StatusOrder);

                }
            }
        });

        binding.switchBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.switchBooking.isChecked()) {
                    StatusBooking = "0";
                    setStatus(accept_booking, StatusBooking);
                    Log.d("TAG", "onClick: " + StatusBooking);

                } else {
                    StatusBooking = "1";
                    setStatus(accept_booking, StatusBooking);
                    Log.d("TAG", "onClick: " + StatusBooking);

                }
            }
        });
        binding.switchMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.switchMedia.isChecked()) {
                    StatusMedia = "0";
                    setStatus(accept_media, StatusMedia);
                    Log.d("TAG", "onClick: " + StatusMedia);

                } else {
                    StatusMedia = "1";
                    setStatus(accept_media, StatusMedia);
                    Log.d("TAG", "onClick: " + StatusMedia);

                }
            }
        });
    }
    public void getOrderAvailable() {
        Call<String> stringCall = ApiUtil.getSetupService().getStatus(accept_order);
        QCallback<String> qCallback = new QCallback<String>() {
            @Override
            public void responseSuccessful(Call<String> call, Response<String> response) {
                StatusOrder = response.body();
                Log.d("TAG", "Status is: " + StatusOrder);
                if (StatusOrder.equalsIgnoreCase("1")) {
                    Log.e("KJ7", "responseSuccessful: " + response.body());
                    binding.switchOrder.setChecked(true);

                } else {
                    binding.switchOrder.setChecked(false);


                }
                binding.switchOrder.setVisibility(View.VISIBLE);
            }

            @Override
            public void requestFailure(Call<String> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<String> call, Response<String> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));

    }
    public void getBookingAvailable(){
        Call<String> stringCall = ApiUtil.getSetupService().getStatus(accept_booking);
        QCallback<String> qCallback = new QCallback<String>() {
            @Override
            public void responseSuccessful(Call<String> call, Response<String> response) {
                StatusBooking = response.body();
                Log.d("TAG", "Status is: " + StatusBooking);
                if (StatusBooking.equalsIgnoreCase("1")) {
                    Log.e("KJ7", "responseSuccessful: " + response.body());
                    binding.switchBooking.setChecked(true);

                } else {
                    binding.switchBooking.setChecked(false);


                }
                binding.switchBooking.setVisibility(View.VISIBLE);
            }

            @Override
            public void requestFailure(Call<String> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<String> call, Response<String> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));

    }
    public void getMediaAvailable(){
        Call<String> stringCall = ApiUtil.getSetupService().getStatus(accept_media);
        QCallback<String> qCallback = new QCallback<String>() {
            @Override
            public void responseSuccessful(Call<String> call, Response<String> response) {
                StatusMedia = response.body();
                Log.d("TAG", "Status is: " + StatusMedia);
                if (StatusMedia.equalsIgnoreCase("1")) {
                    Log.e("KJ7", "responseSuccessful: " + response.body());
                    binding.switchMedia.setChecked(true);

                } else {
                    binding.switchMedia.setChecked(false);


                }
                binding.switchMedia.setVisibility(View.VISIBLE);
            }

            @Override
            public void requestFailure(Call<String> call, Throwable t) {

            }

            @Override
            public void responseUnsuccessful(Call<String> call, Response<String> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));
    }
    public void setStatus(String constsname, String constvalue) {
        Call<Void> stringCall = ApiUtil.getSetupService().postStatus(constsname, constvalue);
        QCallback<Void> qCallback = new QCallback<Void>() {
            @Override
            public void responseSuccessful(Call<Void> call, Response<Void> response) {
                Log.d("KJ7", "responseSuccessful : ");
                Toast toast = Toast.makeText(getContext(), "Update Successfull", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 20, 30);
                toast.show();
            }

            @Override
            public void requestFailure(Call<Void> call, Throwable t) {
                Toast toast = Toast.makeText(getContext(), "Update Failled", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 20, 30);
                toast.show();

            }

            @Override
            public void responseUnsuccessful(Call<Void> call, Response<Void> response) {
                Toast toast = Toast.makeText(getContext(), "Update Failled", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 20, 30);
                toast.show();
            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(stringCall, qCallback));


    }


}