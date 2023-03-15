package cm.clear.qmerchant.Schedulers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface PollingScheduler {
    @NonNull
    String getId();

    boolean isStarted();

    void startPolling();

    int addPollObserver(@Nullable PollObserver pollObserver);

    int removePollObserver(@Nullable PollObserver pollObserver);

    void notifyPollObservers();
}
