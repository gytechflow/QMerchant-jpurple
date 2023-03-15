package cm.clear.qmerchant.common.toggleOptions;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.Schedulers.PollObserver;
import cm.clear.qmerchant.common.interfaces.QFilterManager;
import cm.clear.qmerchant.databinding.ToggleOptionsItemBinding;

public abstract class ToggleObject implements View.OnClickListener, PollObserver, ScheduledRequests{

    private static final String ZERO_COUNT = "0";
    private static final String TAG = ToggleObject.class.getName();
    private static final int WAITING_TIME = 2000;

    private ToggleOptionsItemBinding itemBinding;
    protected final int optionText;
    protected final String optionValue;
    private String optionCounter = ZERO_COUNT;
    private QFilterManager qFilterManager;
    private boolean clicked;
    private boolean isStopped;
    protected final ScheduleManager scheduleManager;

    public ToggleObject(@NonNull int optionText, @NonNull String optionValue, @NonNull ScheduleManager scheduleManager) {
        this.optionText = optionText;
        this.optionValue = optionValue;
        this.scheduleManager = scheduleManager;
    }


    @Override
    public void onClick(View view) {
        qFilterManager.changeFilter(optionValue);
    }

    void notifyManager(){
        if (!isStopped){
            Log.d("JPurple",TAG+ "::notifyManager() called by"+optionValue);
            if (!scheduleManager.scheduleMe(this)){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notifyManager();
                    }
                }, WAITING_TIME);
            }
        }
    }


    public void setFilterManager(@NonNull QFilterManager qFilterManager) {
        this.qFilterManager = qFilterManager;
    }

    public void setItemBinding(@NonNull ToggleOptionsItemBinding itemBinding) {
        this.itemBinding = itemBinding;
        this.itemBinding.textRef.setText(itemBinding.getRoot().getContext().getString(optionText));
        this.itemBinding.textCapacity.setText(optionCounter);
    }

    @NonNull
    public ToggleOptionsItemBinding getItemBinding() {
        return itemBinding;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    protected void updateCapacity(@NonNull String recentValue){
        optionCounter = recentValue;
        if (itemBinding!=null)
            this.itemBinding.textCapacity.setText(optionCounter);
    }

    @Override
    public void start() {
        isStopped = false;
        notifyManager();
    }

    @Override
    public void stop() {
        isStopped = true;
    }
}
