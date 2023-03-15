package cm.clear.qmerchant.common.toggleOptions;

import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.R;

public class ToggleUIManager {
    private static final String TAG = ToggleUIManager.class.getName();
    private List<ToggleObject> toggleOptions = new ArrayList<>();

    public ToggleUIManager() {
        //initializeClickListeners();
    }

    public void addOption(@NonNull ToggleObject toggleOption) {
        if (!toggleOptions.contains(toggleOption)){
            toggleOptions.add(toggleOption);
            if (toggleOption.isClicked())
                toggleOption.getItemBinding().tableContainer.setBackgroundResource(R.drawable.toggle_options_selected);
            toggleOption.getItemBinding().mainContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleOption.onClick(view);
                    toggleOption.setClicked(true);
                    toggleOption.getItemBinding().tableContainer.setBackgroundResource(R.drawable.toggle_options_selected);
                    deactivateAllOthers(toggleOption);
                }
            });
        }
    }

    protected void deactivateAllOthers(@NonNull ToggleObject toggleOption) {
        for (ToggleObject option : toggleOptions) {
            if (option != toggleOption){
                option.setClicked(false);
                option.getItemBinding().tableContainer.setBackgroundResource(R.drawable.toggle_options_background);
            }
        }
    }

}
