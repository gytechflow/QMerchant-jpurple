package cm.clear.qmerchant.notificationService.NotificationObservers;

import androidx.annotation.NonNull;

public interface NotificationEmitter {

    int getId();
    int getMessageResource();

    int getIconResource();

    @NonNull
    Class<?> getDestinationClass();

    boolean isActive();

    void setActive(boolean newState);
}
