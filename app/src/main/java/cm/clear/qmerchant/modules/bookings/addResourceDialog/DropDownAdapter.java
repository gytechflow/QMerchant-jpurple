package cm.clear.qmerchant.modules.bookings.addResourceDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.models.DropDownItem;

public class DropDownAdapter extends ArrayAdapter<DropDownItem> {
    public DropDownAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public DropDownAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public DropDownAdapter(@NonNull Context context, int resource, @NonNull DropDownItem[] objects) {
        super(context, resource, objects);
    }

    public DropDownAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull DropDownItem[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public DropDownAdapter(@NonNull Context context, int resource, @NonNull List<DropDownItem> objects) {
        super(context, resource, objects);
    }

    public DropDownAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<DropDownItem> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(getItem(position).getId());
    }

    static class ViewHolder {
        public TextView desc;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        DropDownAdapter.ViewHolder viewHolder;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.dropdown_spinner_item, parent, false);
            viewHolder = new DropDownAdapter.ViewHolder();

            viewHolder.desc = (TextView) rowView.findViewById(R.id.text);
            rowView.setTag(R.id.MEMBER_VIEWHOLDER, viewHolder);

        } else {
            viewHolder = (DropDownAdapter.ViewHolder) rowView.getTag(R.id.MEMBER_VIEWHOLDER);
        }

        viewHolder.desc.setText(getItem(position).getDesc());
        return rowView;
    }

}
