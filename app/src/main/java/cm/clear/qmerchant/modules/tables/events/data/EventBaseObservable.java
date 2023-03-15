package cm.clear.qmerchant.modules.tables.events.data;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import cm.clear.qmerchant.BR;

public class EventBaseObservable extends BaseObservable {
    @NonNull
    private Integer progressBarVisibility = View.VISIBLE;
    private boolean loading = false;

    @Bindable
    @NonNull
    public Integer getProgressBarVisibility() {
        return progressBarVisibility;
    }

    public void setProgressBarVisibility(@NonNull Integer progressBarVisibility) {
        this.progressBarVisibility = progressBarVisibility;
        notifyPropertyChanged(BR.progressBarVisibility);
    }

    @Bindable
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean isLoading) {
        this.loading = isLoading;
        if (!loading)
            notifyPropertyChanged(BR.loading);
    }
}
