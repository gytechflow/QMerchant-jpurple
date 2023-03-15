package cm.clear.qmerchant.modules.tables.ui.eventactions;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import cm.clear.qmerchant.BR;
import cm.clear.qmerchant.R;

public abstract class EventActionsItemVM extends BaseObservable {

    @NonNull
    private Integer actionLoading = View.INVISIBLE;

    public EventActionsItemVM() {

    }

    public int getActionTint() {
        return R.color.blue_transparent;
    }

    public int getActionTextColor() {
        return R.color.white;
    }

    public void actionClicked(@NonNull View view) {
        startAction();
    }


    public String actionName() {
        return "";
    }

    @Bindable
    public int getActionLoading() {
        return actionLoading;
    }

    private void setActionLoading(int actionLoading) {
        this.actionLoading = actionLoading;
        notifyPropertyChanged(BR.actionLoading);
    }

    protected void startAction(){
        setActionLoading(View.VISIBLE);
    }

    protected void endAction(){
        setActionLoading(View.INVISIBLE);
    }
}
