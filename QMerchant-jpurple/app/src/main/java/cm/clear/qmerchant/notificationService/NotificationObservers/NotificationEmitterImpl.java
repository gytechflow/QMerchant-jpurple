package cm.clear.qmerchant.notificationService.NotificationObservers;

import androidx.annotation.Nullable;

import cm.clear.qmerchant.Schedulers.PollObserver;

public abstract class NotificationEmitterImpl implements PollObserver, NotificationEmitter {
    private NotificationObserver notificationObserver;
    private boolean active = true;

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public NotificationEmitterImpl(@Nullable NotificationObserver notificationObserver) {
        this.notificationObserver = notificationObserver;
    }

    @Nullable
    public NotificationObserver getNotificationObserver() {
        return notificationObserver;
    }

    public void setNotificationObserver(@Nullable NotificationObserver notificationObserver) {
        this.notificationObserver = notificationObserver;
    }
}
