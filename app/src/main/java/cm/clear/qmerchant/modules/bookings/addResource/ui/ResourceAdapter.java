package cm.clear.qmerchant.modules.bookings.addResource.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.modules.bookings.addResource.data.ResourceListener;
import cm.clear.qmerchant.databinding.TablesListItemBinding;
import cm.clear.qmerchant.models.qtable.QTable;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.ViewHolder> {

    private List<QTable> tables;
    private List<QTable> selectedTables = new ArrayList<>();
    private int sumTotal;
    private final int requiredSum;
    private ResourceListener listener;
    private List<MutableLiveData<Boolean>> states = new ArrayList<>();

    public ResourceAdapter(ResourceListener listener,  List<QTable> tables, int requiredSum) {
        this.listener = listener;
        this.tables = tables;
        this.requiredSum = requiredSum;
        this.sumTotal = 0;
    }

    public ResourceAdapter(ResourceListener listener,  List<QTable> tables, int requiredSum, List<QTable> selectedTables) {
        this.listener = listener;
        this.tables = tables;
        this.requiredSum = requiredSum;
        countTables(selectedTables);
    }

    private void countTables(List<QTable> selectedTables) {
        if (selectedTables!=null){
            for (QTable table : selectedTables) {
                sumTotal += table.getCapacity();
                table.setState(new MutableLiveData<>(true));
            }
            this.selectedTables = selectedTables;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TablesListItemBinding binding =
                TablesListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
//            binding.getRoot().getLayoutParams().height = parent.getHeight()/6;
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        tables.get(position).getState().observe((LifecycleOwner) holder.itemView.getContext(), aBoolean -> {
            if (aBoolean) {
                holder.tableContainer.setBackgroundResource(R.drawable.booking_selected_item_table_background);
//                    holder.tableCountLayout.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.booking_table_selected));
            } else {
                holder.tableContainer.setBackgroundResource(R.drawable.booking_item_normal_text_background);
//                    holder.tableCountLayout.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.transparent));
            }
        });

        holder.itemView.setTag(R.id.STATE, tables.get(position).getState());

        holder.textCapacity.setText(String.valueOf(tables.get(position).getCapacity()));
        holder.textRef.setText(tables.get(position).getRef());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean currentState = ((MutableLiveData<Boolean>) v.getTag(R.id.STATE)).getValue().booleanValue();
                ((MutableLiveData<Boolean>) v.getTag(R.id.STATE)).setValue(checkLimit(currentState, tables.get(holder.getBindingAdapterPosition())));
                notifyDataSetChanged();
            }
        });
        states.add(tables.get(position).getState());
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

    public void clearSelection() {
        this.selectedTables = new ArrayList<>();
        updateTotal(0);
        for (MutableLiveData<Boolean> state : states) {
            state.setValue(false);
        }
    }


    private boolean checkLimit(boolean b, QTable table) {
        if (b) {
            if (selectedTables.contains(table)) {
                selectedTables.remove(table);
                int sum = sumTotal;
                sum -= table.getCapacity();
//                sumTotal = sum;
                updateTotal(sum);
            }
            return false;
        } else {
            int sum = sumTotal;
//                if (sum == requiredSum)
//                    return false;
            int localSum = table.getCapacity() + sum;
            // no verif addition

            // no verif addition
            selectedTables.add(table);
            updateTotal(localSum);
            // no verif addition
            return true;
                /*if (localSum <= requiredSum) {
//                    sumTotal=localSum;
                    sumTotal.postValue(localSum);
                    selectedTables.add(table);
                    return true;
                } else {
                    return false;
                }*/
        }
    }

    private void updateTotal(int localSum) {
        sumTotal = localSum;
//        Log.d("J-Purple", "onClick() called with: v = [ success one ] list id = ["+selectedTables.get(0).getId()+"]");
        listener.updateCounter(selectedTables, localSum);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textRef;
        private TextView textCapacity;
        private ConstraintLayout tableContainer;
        private LinearLayout tableCountLayout;
        private ConstraintLayout mainContainer;

        public ViewHolder(@NonNull TablesListItemBinding binding) {
            super(binding.getRoot());

            this.textRef = binding.textRef;
            this.textCapacity = binding.textCapacity;
            this.tableContainer = binding.tableContainer;
            this.tableCountLayout = binding.tableCountLayout;
            this.mainContainer = binding.mainContainer;
        }
    }
}
