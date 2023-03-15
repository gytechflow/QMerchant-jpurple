package cm.clear.qmerchant.modules.orders.orderDetails;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cm.clear.qmerchant.databinding.ItemProductListContentBinding;
import cm.clear.qmerchant.models.Line;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder>{

    private List<Line> list;

    public ProductsAdapter(List<Line> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductListContentBinding binding =
                ItemProductListContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder holder, int position) {
        Line line = list.get(position);
        holder.textDesc.setText(line.getProductLabel());
        holder.textUP.setText(line.getPrice());
        holder.textQty.setText(line.getQty());
        holder.textTotal.setText(line.getTotalHt());
        holder.productDescription.setText(line.getRefExt());
        if (TextUtils.isEmpty(line.getRefExt()))
            holder.productDescriptionContainer.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textDesc;
        final TextView textUP;
        final TextView textQty;
        final TextView textTotal;
        final TextView productDescription;
        final LinearLayout productDescriptionContainer;

        public ViewHolder(@NonNull ItemProductListContentBinding binding) {
            super(binding.getRoot());
            textDesc = binding.textDesc;
            textUP = binding.textUP;
            textQty = binding.textQty;
            textTotal = binding.textTotal;
            productDescription = binding.productDescription;
            productDescriptionContainer = binding.productDescriptionContainer;

        }
    }
}
