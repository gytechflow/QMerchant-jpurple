package cm.clear.qmerchant.notificationService.NotificationObservers;

import androidx.annotation.Nullable;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.notificationService.ServiceGlobals;

public class BookingObserver extends NotificationEmitterImpl {

    private static final String TAG = BookingObserver.class.getName();

    public BookingObserver(@Nullable NotificationObserver notificationObserver) {
        super(notificationObserver);
    }

    @Override
    public void onDataChange() {
//        Log.d("JPurple", TAG+ "::onDataChange() called");
        if (getNotificationObserver() != null) {
//            Log.d("JPurple", TAG+ "::getNotificationObserver() != null");
//            Log.d("JPurple", TAG+ "::isActive() == "+isActive());
            if (isActive()){

                getNotificationObserver().createNotification(getIconResource(), getMessageResource());
            }
        }
    }

    @Override
    public int getId() {
        return ServiceGlobals.BOOKING_SERVICE;
    }

    @Override
    public int getMessageResource() {
        return R.string.notify_new_booking;
    }

    @Override
    public int getIconResource() {
        return R.drawable.ic_baseline_booking_notification_icon;
    }

    @Nullable
    @Override
    public Class<?> getDestinationClass() {
        return null;
    }
}
