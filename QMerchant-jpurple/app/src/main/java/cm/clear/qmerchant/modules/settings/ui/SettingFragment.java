package cm.clear.qmerchant.modules.settings.ui;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.dashboard.MainViewModel;
import cm.clear.qmerchant.database.QDatabase;
import cm.clear.qmerchant.database.entities.ServerInfo;
import cm.clear.qmerchant.databinding.ApiLoginDialogBinding;
import cm.clear.qmerchant.databinding.FragmentSettingBinding;
import cm.clear.qmerchant.manager.RequestManager;


public class SettingFragment extends Fragment {

        private FragmentSettingBinding binding;
        private MainViewModel mainViewModel;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View.OnClickListener notificationLister = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view)
                        .navigate(R.id.action_settingFragment_to_notificationsFragment3);
            }
        };
        binding.notificationLayout.setOnClickListener(notificationLister);

        View.OnClickListener userListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view)
                        .navigate(R.id.action_settingFragment_to_userfragment);
            }

        };
        binding.userLayout.setOnClickListener(userListener);

        View.OnClickListener moduleListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view)
                        .navigate(R.id.action_settingFragment_to_moduleFragment);
            }
        };
        binding.ModuleLayout.setOnClickListener(moduleListener);

        View.OnClickListener apiLoginListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiLoginDialogBinding dialogBinding = ApiLoginDialogBinding.inflate(getLayoutInflater());
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(dialogBinding.getRoot());
                AlertDialog dialog = builder.create();
                ServerInfo serverInfo = QDatabase.getInstance().ServerInfoDao().getServerInfo();
                if (serverInfo!=null){
                    dialogBinding.loginDialogUrl.setText(serverInfo.getServerUrl());
                    dialogBinding.loginDialogKey.setText(serverInfo.getApiKey());
                }
                View.OnClickListener cancelListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                };
                View.OnClickListener okListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = String.valueOf(dialogBinding.loginDialogUrl.getText());
                        if (TextUtils.isEmpty(url)){
                            dialogBinding.loginDialogUrl.setError("");
                            return;
                        }
                        String api_key = String.valueOf(dialogBinding.loginDialogKey.getText());
                        if (TextUtils.isEmpty(api_key)){
                            dialogBinding.loginDialogKey.setError("");
                            return;
                        }
                        ServerInfo serverInfo  = new ServerInfo(url, api_key);
                        serverInfo.setId(0);
                        try {
                            Log.e("J-Purple", "SettingFragment::onInsert: "+QDatabase.getInstance().ServerInfoDao().insert(serverInfo) );
                        } catch (SQLiteConstraintException e) {
                            //Log.d("HOSIRUS", "syncProfessions : " + Calendar.getInstance().getTime().toString() + " *** Duplicate ID for : "+ locationCategory.getLabel());
                            Log.e("J-Purple", "SettingFragment::onUpdate: "+QDatabase.getInstance().ServerInfoDao().update(serverInfo) );
                        }
                        RequestManager.getInstance().reset();
                        dialog.cancel();
                    }
                };
                dialogBinding.warningDialogCancelBt.setOnClickListener(cancelListener);
                dialogBinding.warningDialogOkBt.setOnClickListener(okListener);
                dialog.show();
            }
        };
        binding.apiLoginLayout.setOnClickListener(apiLoginListener);


    }
}