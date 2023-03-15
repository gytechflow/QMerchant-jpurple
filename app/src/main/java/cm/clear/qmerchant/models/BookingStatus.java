package cm.clear.qmerchant.models;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.interfaces.QDropDownItem;

public class BookingStatus implements QDropDownItem {
    private int id;
    private String label;

    public BookingStatus(int id, String label) {
        this.id = id;
        this.label = label;
    }

    @NonNull
    public String getId() {
        return String.valueOf(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
