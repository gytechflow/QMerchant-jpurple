package cm.clear.qmerchant.notificationService;

import androidx.annotation.Nullable;

public interface Notifies {
    void sendNotification(@Nullable String notificationIconUrl, @Nullable String notificationMessage);
    void sendNotification(int notificationIconResource, @Nullable String notificationMessage);
}
