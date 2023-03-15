package cm.clear.qmerchant.modules.tables.ui.eventactions;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import cm.clear.qmerchant.common.actionablebuttons.ResourceResult;

public class EventAction extends EventActionsItemVM{
    private String name;
    private IEventAction resultLiveData;
    private final LifecycleOwner lifecycleOwner;
    private final ActionCompleteCallBack completeCallBack;

    public EventAction(String name, @NonNull IEventAction resultLiveData,
                       @NonNull LifecycleOwner lifecycleOwner, @NonNull ActionCompleteCallBack completeCallBack) {
        this.name = name;
        this.resultLiveData = resultLiveData;
        this.lifecycleOwner = lifecycleOwner;
        this.completeCallBack = completeCallBack;
    }

    private EventAction(@NonNull EventAction other) {
        this.name = other.name;
        this.resultLiveData = other.resultLiveData;
        this.lifecycleOwner = other.lifecycleOwner;
        this.completeCallBack = other.completeCallBack;
    }

    @NonNull
    @Override
    protected EventAction clone() throws CloneNotSupportedException {
        return new EventAction(this);
    }

    @Override
    public void actionClicked(@NonNull View view) {
        super.actionClicked(view);
        getResultLiveData().getAction().observe(getLifecycleOwner(), resourceResult -> {
            if (resourceResult!=null){
                if (resourceResult.isSuccessful()){
                    endAction();
                    getCompleteCallBack().onActionSuccess();
                }
                else{
                    endAction();
                    getCompleteCallBack().onActionComplete();
                }
            }
        });
    }


    @Override
    @Nullable
    public String actionName() {
        return getName();
    }

    @Nullable
    public String getName() {
        return name.toUpperCase();
    }

    @NonNull
    public EventAction setName(String name) {
        this.name = name;
        return this;
    }

    @NonNull
    public EventAction setResultLiveData(@NonNull IEventAction resultLiveData) {
        this.resultLiveData = resultLiveData;
        return this;
    }

    @NonNull
    public IEventAction getResultLiveData() {
        return resultLiveData;
    }

    @NonNull
    public LifecycleOwner getLifecycleOwner() {
        return lifecycleOwner;
    }

    @NonNull
    public ActionCompleteCallBack getCompleteCallBack() {
        return completeCallBack;
    }

    public interface IEventAction{
        @NonNull
        LiveData<ResourceResult> getAction();
    }

    public interface ActionCompleteCallBack{
        void onActionComplete();
        default void onActionSuccess(){
            onActionComplete();
        };

        default void onActionFailure(){
            onActionComplete();
        };
    }
}
