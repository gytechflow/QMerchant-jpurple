package cm.clear.qmerchant.modules.tables.data;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.common.listviewmanagement.IItemState;
import cm.clear.qmerchant.models.booking.Booking;

public class TableState implements IItemState {
    private List<Booking> assignedEvents = new ArrayList<>();

    public TableState(@NonNull TableState tableState) {
        this.assignedEvents  = new ArrayList<>(tableState.getAssignedEvents());
    }

    public TableState() {
    }

    @NonNull
    public List<Booking> getAssignedEvents() {
        return assignedEvents;
    }

    public void setAssignedEvents(@NonNull List<Booking> assignedEvents) {
        this.assignedEvents = assignedEvents;
    }
}
