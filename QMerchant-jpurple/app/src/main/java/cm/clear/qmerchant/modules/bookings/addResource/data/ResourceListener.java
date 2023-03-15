package cm.clear.qmerchant.modules.bookings.addResource.data;

import java.util.List;

import cm.clear.qmerchant.models.qtable.QTable;

public interface ResourceListener {
    void updateCounter(List<QTable> selectedTables, int localSum);
}
