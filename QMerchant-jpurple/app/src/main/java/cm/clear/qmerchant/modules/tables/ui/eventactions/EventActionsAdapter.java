package cm.clear.qmerchant.modules.tables.ui.eventactions;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import cm.clear.qmerchant.databinding.EventActionsDialogActionItemBinding;

public class EventActionsAdapter extends ListAdapter<EventActionsItemVM, EventActionsAdapter.ViewHolder> {
    protected EventActionsAdapter(/*@NonNull DiffUtil.ItemCallback<EventActionsItemVM> diffCallback*/) {
        super(new DiffUtil.ItemCallback<EventActionsItemVM>() {
            @Override
            public boolean areItemsTheSame(@NonNull EventActionsItemVM oldItem, @NonNull EventActionsItemVM newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull EventActionsItemVM oldItem, @NonNull EventActionsItemVM newItem) {
                return false;
            }
        });
    }

    @NonNull
    @Override
    public EventActionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EventActionsDialogActionItemBinding binding = EventActionsDialogActionItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventActionsAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private EventActionsDialogActionItemBinding binding;
        public ViewHolder(@NonNull EventActionsDialogActionItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(@NonNull EventActionsItemVM item){
            binding.setViewManager(item);
        }
    }
}
