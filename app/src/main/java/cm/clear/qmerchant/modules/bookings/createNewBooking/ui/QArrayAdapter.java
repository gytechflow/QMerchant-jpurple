package cm.clear.qmerchant.modules.bookings.createNewBooking.ui;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

import cm.clear.qmerchant.interfaces.QDropDownItem;

public class QArrayAdapter<T> extends ArrayAdapter<T> {
    public QArrayAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public QArrayAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public QArrayAdapter(@NonNull Context context, int resource, @NonNull T[] objects) {
        super(context, resource, objects);
    }

    public QArrayAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull T[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public QArrayAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
        super(context, resource, objects);
    }

    public QArrayAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<T> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(((QDropDownItem)getItem(position)).getId());
    }
}
