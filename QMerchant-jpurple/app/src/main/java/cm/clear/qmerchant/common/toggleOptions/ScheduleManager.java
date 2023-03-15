package cm.clear.qmerchant.common.toggleOptions;

import androidx.annotation.NonNull;

public interface ScheduleManager extends Stoppable{
    boolean scheduleMe(@NonNull ScheduledRequests scheduledRequests);
}
