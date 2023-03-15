package cm.clear.qmerchant.common.interfaces;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface QNavigationManager {
    default void navigate(@NonNull String key, @Nullable Bundle data, int res) {
        if (getNavigationListener()==null)
            throw new NullPointerException("NavigationListener is null");
        getNavigationListener().onNavigate(key, data, res);
    }

    default void navigate(@NonNull View view, @NonNull Bundle data, int action) {
        if (getNavigationListener()==null)
            throw new NullPointerException("NavigationListener is null");
        getNavigationListener().onNavigate(view, data, action);
    }

    @Nullable
    NavigationListener getNavigationListener();
}
