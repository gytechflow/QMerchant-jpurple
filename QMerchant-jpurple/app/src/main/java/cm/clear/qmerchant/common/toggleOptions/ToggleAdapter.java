package cm.clear.qmerchant.common.toggleOptions;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cm.clear.qmerchant.databinding.ToggleOptionsItemBinding;

public class ToggleAdapter extends RecyclerView.Adapter<ToggleAdapter.ViewHolder> {
    private final List<ToggleObject> toggleObjects;
    private final ToggleUIManager toggleUIManager;

    public ToggleAdapter(@NonNull List<ToggleObject> toggleObjects) {
        this.toggleObjects = toggleObjects;
        toggleUIManager = new ToggleUIManager();
    }

    @NonNull
    @Override
    public ToggleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ToggleOptionsItemBinding binding = ToggleOptionsItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ToggleAdapter.ViewHolder holder, int position) {
        ToggleObject toggleObject = toggleObjects.get(position);
        toggleObject.setItemBinding(holder.binding);
        toggleUIManager.addOption(toggleObject);
    }

    @Override
    public int getItemCount() {
        return toggleObjects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ToggleOptionsItemBinding binding;
        public ViewHolder(@NonNull ToggleOptionsItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
