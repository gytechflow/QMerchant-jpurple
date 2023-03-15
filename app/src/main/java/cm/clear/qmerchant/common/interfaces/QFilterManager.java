package cm.clear.qmerchant.common.interfaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface QFilterManager{
    @Nullable
    FilterChangeObserver getFilterChangeObserver();

    default void changeFilter(@NonNull String value) {
        if (getFilterChangeObserver()==null)
            throw new NullPointerException("FilterChangeObserver is null");
        getFilterChangeObserver().onFilterChange(value);
    }

    default void clearCalls() {
        if (getFilterChangeObserver()==null)
            throw new NullPointerException("FilterChangeObserver is null");
        getFilterChangeObserver().onClearCalls();
    }

    default void refreshFilter() {
        if (getFilterChangeObserver()==null)
            throw new NullPointerException("FilterChangeObserver is null");
        getFilterChangeObserver().refresh();
    }
}
