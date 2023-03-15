package cm.clear.qmerchant.modules.bookings.createNewBooking.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.databinding.ThirdpartySuggestionItemBinding;

public class ThirdpartySuggestionAdapter<Thirdparty> extends ArrayAdapter<cm.clear.qmerchant.models.Thirdparty> {

    public ThirdpartySuggestionAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public ThirdpartySuggestionAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public ThirdpartySuggestionAdapter(@NonNull Context context, int resource, @NonNull cm.clear.qmerchant.models.Thirdparty[] objects) {
        super(context, resource, objects);
    }

    public ThirdpartySuggestionAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull cm.clear.qmerchant.models.Thirdparty[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public ThirdpartySuggestionAdapter(@NonNull Context context, int resource, @NonNull List<cm.clear.qmerchant.models.Thirdparty> objects) {
        super(context, resource, objects);
    }

    public ThirdpartySuggestionAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<cm.clear.qmerchant.models.Thirdparty> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(getItem(position).getId());
    }

    static class ViewHolder {

        private TextView customerId;
        private TextView name;
        private TextView email;
        private TextView phone;

        private ImageView profile;
        private TextView reservationsCount;
        private TextView ordersCount;


        public ViewHolder(View binding) {

            this.customerId = binding.findViewById(R.id.customer_id);
            this.name = binding.findViewById(R.id.name);
            this.email = binding.findViewById(R.id.email);
            this.phone = binding.findViewById(R.id.phone);

            this.profile = binding.findViewById(R.id.customer_profile);
            this.reservationsCount = binding.findViewById(R.id.reservations);
            this.ordersCount = binding.findViewById(R.id.orders);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        ThirdpartySuggestionAdapter.ViewHolder viewHolder;
        if (rowView == null) {
            rowView = ThirdpartySuggestionItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot();
            viewHolder = new ThirdpartySuggestionAdapter.ViewHolder(rowView);

            rowView.setTag(R.id.MEMBER_VIEWHOLDER, viewHolder);

        } else {
            viewHolder = (ThirdpartySuggestionAdapter.ViewHolder) rowView.getTag(R.id.MEMBER_VIEWHOLDER);
        }

        viewHolder.customerId.setText(getItem(position).getId());
        viewHolder.name.setText(getItem(position).getName());
        viewHolder.phone.setText(getItem(position).getPhone());
        viewHolder.email.setText(getItem(position).getEmail());

        //viewHolder.profile.setImageDrawable(getItem(position).getPhone());
        viewHolder.reservationsCount.setText(getItem(position).getId());
        viewHolder.ordersCount.setText(getItem(position).getId());

        return rowView;
    }

    /*@NonNull
    @Override
    public Filter getFilter() {
        return super.getFilter();
        new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                if (!TextUtils.isEmpty(charSequence.toString())){

                }
                return null;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            }
        };
    }*/
}
