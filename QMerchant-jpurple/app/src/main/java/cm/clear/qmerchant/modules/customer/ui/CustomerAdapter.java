package cm.clear.qmerchant.modules.customer.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cm.clear.qmerchant.common.listviewmanagement.IAdapterItemChangeListener;
import cm.clear.qmerchant.modules.customer.data.CustomerItemUpdater;
import cm.clear.qmerchant.modules.customer.data.CustomerState;
import cm.clear.qmerchant.databinding.ItemCustomerListBinding;
import cm.clear.qmerchant.models.Thirdparty;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> implements IAdapterItemChangeListener {
    private List<Thirdparty> thirdpartyList;
    private final CustomerItemUpdater itemUpdater;

    public CustomerAdapter(List<Thirdparty> thirdpartyList) {
        this.thirdpartyList = thirdpartyList;
        itemUpdater = new CustomerItemUpdater(thirdpartyList,this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCustomerListBinding binding =
                ItemCustomerListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

//        binding.getRoot().getLayoutParams().height = parent.getHeight() / 6;
        return new CustomerAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerAdapter.ViewHolder holder, int position) {
        holder.textName.setText(thirdpartyList.get(position).getName());
        holder.textEmail.setText(thirdpartyList.get(position).getEmail());
        holder.textPhone.setText(thirdpartyList.get(position).getPhone());
        CustomerState customerState= itemUpdater.getObjectState(thirdpartyList.get(position), position);

        holder.textBookings.setText(String.valueOf(customerState.getBookingCount()));
        holder.textOrders.setText(String.valueOf(customerState.getOrderCount()));
    }

    @Override
    public int getItemCount() {
        return thirdpartyList.size();
    }

    @Override
    public void onItemChanged(int position) {
        notifyItemChanged(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textName;
        private final TextView textEmail;
        private final TextView textPhone;
        private final TextView textBookings;
        private final TextView textOrders;

        public ViewHolder(@NonNull ItemCustomerListBinding binding) {
            super(binding.getRoot());
            this.textName = binding.textName;
            this.textEmail = binding.textEmail;
            this.textPhone = binding.textPhone;
            this.textBookings = binding.textBookings;
            this.textOrders = binding.textOrders;
        }
    }
}
