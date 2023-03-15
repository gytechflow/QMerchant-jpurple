package cm.clear.qmerchant.notificationService.NotificationObservers;

import androidx.annotation.Nullable;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.notificationService.ServiceGlobals;

public class OrderObserver extends NotificationEmitterImpl {

    public OrderObserver(@Nullable NotificationObserver notificationObserver) {
        super(notificationObserver);
    }

    @Override
    public void onDataChange() {
        if (getNotificationObserver() != null) {
            if (isActive())
                getNotificationObserver().createNotification(getIconResource(), getMessageResource());
        }
    }

    @Override
    public int getId() {
        return ServiceGlobals.ORDER_SERVICE;
    }

    @Override
    public int getMessageResource() {
        return R.string.notify_new_order;
    }

    @Override
    public int getIconResource() {
        return R.drawable.ic_baseline_order_notification_icon;
    }

    @Nullable
    @Override
    public Class<?> getDestinationClass() {
        return null;
    }
}
