package cm.clear.qmerchant.common.interfaces;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

public interface NavigationListener {
    void onNavigate(String key, Bundle data, int res);
    default void onNavigate(@NonNull View view, @NonNull Bundle data, int action){
    };
}
