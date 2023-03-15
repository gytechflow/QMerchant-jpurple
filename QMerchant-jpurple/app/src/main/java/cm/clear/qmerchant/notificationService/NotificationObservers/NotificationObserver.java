package cm.clear.qmerchant.notificationService.NotificationObservers;

import androidx.annotation.Nullable;

public interface NotificationObserver {
    void createNotification(@Nullable String notificationIconUrl, @Nullable String notificationMessage);
    void createNotification(int notificationIconResource, @Nullable String notificationMessage);

    void createNotification(int notificationIconResource,  int notificationMessageResource);
}
