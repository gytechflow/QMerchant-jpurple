package cm.clear.qmerchant.modules.bookings.addResourceDialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.databinding.FragmentBookingDetailBinding;
import cm.clear.qmerchant.models.DropDownItem;
import cm.clear.qmerchant.models.qtable.QTable;
import cm.clear.qmerchant.remote.ApiUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddResourceDialogFragment extends DialogFragment {
    private FragmentBookingDetailBinding binding;
    private Call<List<QTable>> call;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookingDetailBinding.inflate(inflater, container, false);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.fragment_dialog_background);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        call = ApiUtil.getBookingService().getTables();
        call.enqueue(new Callback<List<QTable>>() {
            @Override
            public void onResponse(Call<List<QTable>> call, Response<List<QTable>> response) {
                if (response.isSuccessful()){
//                    binding.progressBar.setVisibility(View.INVISIBLE);
//                    binding.menu.setEnabled(true);
                    List<DropDownItem> dropDownItems = new ArrayList<>();
                    for (QTable qTable : response.body()) {
                        DropDownItem dropDownItem = new DropDownItem();
                        dropDownItem.setId(qTable.getId());
                        dropDownItem.setDesc(qTable.getRef());
                        dropDownItems.add(dropDownItem);
                    }
//                    binding.dropdownMenu.setAdapter(new DropDownAdapter(getContext(), 0,dropDownItems));
                } else {
                    try {
                        Log.e("J-purple", "AddResourceDialogFragment::onResponse: "+response.errorBody().string() );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<QTable>> call, Throwable t) {
                binding.progressBar.setVisibility(View.INVISIBLE);
                t.printStackTrace();
            }
        });

        binding.itemDetailContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });

        binding.mainContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }



}
