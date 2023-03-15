package cm.clear.qmerchant.models.qtable;

import androidx.annotation.NonNull;

import java.util.Comparator;

public class IdComparator implements Comparator<QTable> {
    @Override
    public int compare(@NonNull QTable qTable, @NonNull QTable t1) {
        return qTable.getId().compareTo(t1.getId());
    }
}
