package cm.clear.qmerchant.modules.tables.data.comparator;

import androidx.annotation.NonNull;

import java.util.Comparator;

import cm.clear.qmerchant.modules.tables.data.TableState;

public class NumberOfEventsComparator implements Comparator<TableState> {
    @Override
    public int compare(@NonNull TableState tableState, @NonNull TableState t1) {
        return Integer.compare(tableState.getAssignedEvents().size(),t1.getAssignedEvents().size());
    }
}
