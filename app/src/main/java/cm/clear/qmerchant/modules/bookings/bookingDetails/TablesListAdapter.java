package cm.clear.qmerchant.modules.bookings.bookingDetails;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cm.clear.qmerchant.databinding.ItemTableListContentBinding;
import cm.clear.qmerchant.models.qtable.QTable;

public class TablesListAdapter extends RecyclerView.Adapter<TablesListAdapter.ViewHolder>{

    private List<QTable> list;

    public TablesListAdapter(List<QTable> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TablesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTableListContentBinding binding =
                ItemTableListContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TablesListAdapter.ViewHolder holder, int position) {
        holder.textTable.setText(list.get(position).getRef());
        holder.textCapacity.setText(String.valueOf(list.get(position).getCapacity()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textTable;
        final TextView textCapacity;

        public ViewHolder(@NonNull ItemTableListContentBinding binding) {
            super(binding.getRoot());
            textTable = binding.textTable;
            textCapacity = binding.textCapacity;
        }
    }
}
