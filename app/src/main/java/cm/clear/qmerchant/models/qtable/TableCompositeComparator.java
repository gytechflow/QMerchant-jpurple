package cm.clear.qmerchant.models.qtable;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TableCompositeComparator implements Comparator<QTable> {
    private List<Comparator<QTable>> comparators;

    @SafeVarargs
    public TableCompositeComparator(@NonNull Comparator<QTable>... comparators) {
        this.comparators = Arrays.asList(comparators);
    }

    @Override
    public int compare(@NonNull QTable qTable, @NonNull QTable t1) {
        for (Comparator<QTable> comparator : comparators) {
            int result = comparator.compare(qTable, t1);
            if (result!=0)
                return result;
        }
        return 0;
    }
}
