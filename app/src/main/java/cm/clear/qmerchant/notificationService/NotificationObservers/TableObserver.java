package cm.clear.qmerchant.notificationService.NotificationObservers;

import androidx.annotation.Nullable;

import cm.clear.qmerchant.notificationService.ServiceGlobals;

public class TableObserver extends NotificationEmitterImpl {

    public TableObserver(@Nullable NotificationObserver notificationObserver) {
        super(notificationObserver);
    }

    @Override
    public void onDataChange() {
        if (getNotificationObserver() != null) {
            getNotificationObserver().createNotification(getIconResource(), getMessageResource());
        }
    }

    @Override
    public int getId() {
        return ServiceGlobals.ORDER_SERVICE;
    }

    @Override
    public int getMessageResource() {
        return 0;
    }

    @Override
    public int getIconResource() {
        return 0;
    }

    @Nullable
    @Override
    public Class<?> getDestinationClass() {
        return null;
    }
}
