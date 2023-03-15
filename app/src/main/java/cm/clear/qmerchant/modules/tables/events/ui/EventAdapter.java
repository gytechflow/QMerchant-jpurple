package cm.clear.qmerchant.modules.tables.events.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.common.interfaces.QListChangeManager;
import cm.clear.qmerchant.common.interfaces.QNavigationManager;
import cm.clear.qmerchant.common.listprovider.IFilterable;
import cm.clear.qmerchant.common.listviewmanagement.IAdapterItemChangeListener;
import cm.clear.qmerchant.databinding.ReducedBookingItemBinding;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.models.booking.BookingActions;
import cm.clear.qmerchant.models.booking.BookingActionsObserver;
import cm.clear.qmerchant.models.booking.bookingStates.BookingState;
import cm.clear.qmerchant.modules.bookings.data.BookingItemUpdater;
import cm.clear.qmerchant.modules.bookings.data.BookingStateSafe;
import cm.clear.qmerchant.modules.tables.events.data.EventMessenger;

public class EventAdapter extends ListAdapter<Booking, EventAdapter.ViewHolder> implements IFilterable, IAdapterItemChangeListener {
    private BookingItemUpdater bookingItemUpdater;
    private final BookingActionsObserver actionsObserver;

    protected EventAdapter(@NonNull AsyncDifferConfig<Booking> config, @NonNull BookingActionsObserver actionsObserver) {
        super(config);
        this.actionsObserver = actionsObserver;
    }

    public EventAdapter(@NonNull DiffUtil.ItemCallback<Booking> diffCallback, @NonNull BookingActionsObserver actionsObserver) {
        super(diffCallback);
        bookingItemUpdater = new BookingItemUpdater(this);
        this.actionsObserver = actionsObserver;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReducedBookingItemBinding binding = ReducedBookingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
        Booking current_event = getItem(position);
        bookingItemUpdater.register(current_event);
        BookingState state = bookingItemUpdater.getObjectState(current_event, position);
        BindingObject bindingObject = new BindingObject(current_event, state, actionsObserver);
        holder.binding.setViewManager(bindingObject);
    }

    @Override
    public void onItemChanged(int position) {
        notifyItemChanged(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ReducedBookingItemBinding binding;
        public ViewHolder(@NonNull ReducedBookingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public void submitList(@Nullable List<Booking> list) {
        super.submitList(list);
    }

    @Override
    public void submitList(@Nullable List<Booking> list, @Nullable Runnable commitCallback) {
        super.submitList(list, commitCallback);
    }

    @NonNull
    @Override
    protected Booking getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @NonNull
    @Override
    public List<Booking> getCurrentList() {
        return super.getCurrentList();
    }

    @Override
    public void onCurrentListChanged(@NonNull List<Booking> previousList, @NonNull List<Booking> currentList) {
        super.onCurrentListChanged(previousList, currentList);
    }



    public static class BindingObject extends BaseObservable {
        private final Booking event;
        private final BookingState state;
        private final BookingActionsObserver actionsObserver;
        @NonNull
        protected QNavigationManager navigationManager;
        @NonNull
        protected QListChangeManager listChangeManager;
        private String order_ref;
        private String order_type;
        private String order_name;
        private String order_phone;
        private String order_date;
        private String order_start_time;
        private String order_end_time;
        private String order_assign_btn_tint;
        private Integer order_confirm_btn_tint;
        private Integer order_remove_btn_tint;


        public BindingObject(@NonNull Booking event, @NonNull BookingState state, @NonNull BookingActionsObserver actionsObserver) {
            this.event = event;
            this.state = state;
            this.actionsObserver = actionsObserver;
            navigationManager = EventMessenger.getInstance().getNavigationManager();
            listChangeManager = EventMessenger.getInstance().getListChangeManager();
        }

        @NonNull
        public Booking getEvent() {
            return event;
        }

        @NonNull
        public BookingState getState() {
            return state;
        }

        @Nullable
        public String getRef(){
            return getEvent().getRef();
        }

        @Nullable
        public String getPlaces(){
            return state.getOccupationTally();
        }

        @NonNull
        public String getDate(){
            return TmsConverter.getDate(event.getDatep(), TmsConverter.FROM_SQL_JAVA);
        }

        @NonNull
        public String getStartTime(){
            return TmsConverter.getTime(event.getDatep(), TmsConverter.FROM_SQL_JAVA);
        }

        @NonNull
        public String getEndTime(){
            return TmsConverter.getTime(event.getDatep2(), TmsConverter.FROM_SQL_JAVA);
        }

        @NonNull
        public String getName(){
            return (state.getClient() != null ? state.getClient().getName() : " ** Not yet");
        }

        @NonNull
        public String getPhone(){
            return (state.getClient() != null ? state.getClient().getPhone() : " ** Not yet");
        }

        @NonNull
        public String getId(){
            return (state.getClient() != null ? state.getClient().getId() : " ** Not yet");
        }

        public int getAssignButtonVisibility() {
            return state.getAssignButtonVisibility();
        }

        public int getCancelButtonVisibility() {
            return state.getCancelButtonVisibility();
        }

        public int getConfirmButtonVisibility() {
            return state.getConfirmButtonVisibility();
        }

        @NonNull
        public ColorStateList getConfirmButtonColor(@NonNull Context context) {
            return state.getConfirmButtonColor(context);
        }

        @NonNull
        public ColorStateList getAssignButtonColor(@NonNull Context context) {
            return state.getAssignButtonColor(context);
        }

        @NonNull
        public ColorStateList getCancelButtonColor(@NonNull Context context) {
            return state.getCancelButtonColor(context);
        }

        @NonNull
        public String getConfirmButtonText(@NonNull Context context) {
            return state.getConfirmText(context);
        }

        @NonNull
        public String getAssignButtonText(@NonNull Context context) {
            return state.getAssignText(context);
        }

        @NonNull
        public String getCancelButtonText(@NonNull Context context) {
            return state.getCancelText(context);
        }

        public void onAssignedClicked(@NonNull View view){
            if (!event.getPercentage().equalsIgnoreCase(Booking.PERCENT_100)) {
                Bundle bookingDetails = new Bundle();
                BookingStateSafe.getInstance().setStateObject(event);
                BookingStateSafe.getInstance().setState(state);
                bookingDetails.putSerializable("booking", event);
                bookingDetails.putSerializable("bookingDetails", state);
                navigationManager.navigate(view, bookingDetails, R.id.action_show_resources_fragment);
            }
        }

        public void onConfirmedClicked(@NonNull View v){
            actionsObserver.onConfirm(true);
            state.getConfirmResult("").observe((LifecycleOwner) v.getContext(), resourceResult -> {
                actionsObserver.onConfirm(false);
                if (resourceResult.isSuccessful()) {
                    BookingActions.confirmBooking(event.getId()).observe((LifecycleOwner) v.getContext(), resourceResultState -> {
                        if (resourceResultState.isSuccessful()) {
                            listChangeManager.listItemChanged();
                        }
                    });
                } else {
                    Bundle bookingDetails = new Bundle();
                    BookingStateSafe.getInstance().setStateObject(event);
                    BookingStateSafe.getInstance().setState(state);
                    bookingDetails.putSerializable("booking", event);
                    bookingDetails.putSerializable("bookingDetails", state);
                    navigationManager.navigate(v, bookingDetails, R.id.action_show_booking_details_fragment);
                }
            });
        }

        public void onRemoveClicked(@NonNull View view){
            actionsObserver.onCancel(true);
            BookingActions.endEvent(event.getId()).observe((LifecycleOwner) view.getContext(), resourceResultState -> {
                actionsObserver.onCancel(false);
                if (resourceResultState != null) {
                    if (resourceResultState.isSuccessful()) {
                        listChangeManager.listItemChanged();
                    }
                }
            });
        }

        public void onRefClicked(@NonNull View view){
            Bundle bookingDetails = new Bundle();
            BookingStateSafe.getInstance().setStateObject(event);
            BookingStateSafe.getInstance().setState(state);
            bookingDetails.putSerializable("booking", event);
            bookingDetails.putSerializable("bookingDetails", state);
            navigationManager.navigate(view, bookingDetails, R.id.action_show_booking_details_fragment);
        }

        public float getMarginTop(@NonNull Context context){
            return context.getResources().getBoolean(R.bool.isTablet)?0:5;
        }
    }


}
