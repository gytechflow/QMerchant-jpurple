package cm.clear.qmerchant.common;

import androidx.annotation.NonNull;

public interface CustomCallback {
    void positive(@NonNull String message);
    void negative(@NonNull String message);
}
