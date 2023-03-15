package cm.clear.qmerchant.modules.tables.ui.table_events;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import cm.clear.qmerchant.BR;
import cm.clear.qmerchant.R;
import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.common.listviewmanagement.ListChangeObserver;
import cm.clear.qmerchant.databinding.EventActionsDialogBinding;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.models.booking.bookingStates.BookingState;
import cm.clear.qmerchant.models.booking.bookingStates.SuppStatusHelper;
import cm.clear.qmerchant.modules.tables.data.OverlappingTimeEvent;
import cm.clear.qmerchant.modules.tables.ui.eventactions.EventAction;
import cm.clear.qmerchant.modules.tables.ui.eventactions.EventActionsVM;
import cm.clear.qmerchant.modules.tables.ui.period_grid.GridCalculator;

public class EventViewManager extends BaseObservable {
    private final Booking event;
    private final BookingState state;
    private final GridCalculator gridCalculator;
    private EventActionsVM eventActionsVM;
    protected final ListChangeObserver listChangeObserver;
    private boolean loading = true;
    private int state_color = R.color.black_transparent;
    private Runnable onCategoryLoaded = new Runnable() {
        @Override
        public void run() {
            setColor();
            setLoading(false);
        }
    };
    private static final String TAG = EventViewManager.class.getName();

    public EventViewManager(@NonNull Booking event, @NonNull GridCalculator gridCalculator,@NonNull BookingState state, @NonNull ListChangeObserver listChangeObserver) {
        this.event = event;
        this.state = state;
        this.gridCalculator = gridCalculator;
        this.listChangeObserver = listChangeObserver;
        this.state.addCategoryObserver(onCategoryLoaded);
    }

    @NonNull
    public Booking getEvent() {
        return event;
    }

    @Bindable
    public boolean isLoading() {
        loading = !state.isCategoriesLoaded();
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
    }

    public float getMarginTop(@NonNull Context context){
        float view_height = context.getResources().getDimension(R.dimen.single_table_size);
        float item_height = getHeight();
        return gridCalculator.getMarginTop((OverlappingTimeEvent) event, view_height, item_height);
    }

    public float getHeight() {
        return gridCalculator.getHeight((OverlappingTimeEvent) event);
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
    public String getPlaces(){
        return event.getNbplace();
    }


    @Bindable
    @NonNull
    public int getState_color(){
        state_color = SuppStatusHelper.getColor(SuppStatusHelper.getMainCategory(state.getCategories()).getColor());
        return state_color;
    }

    public void setColor(){
        state_color = SuppStatusHelper.getColor(SuppStatusHelper.getMainCategory(state.getCategories()).getColor());
        notifyPropertyChanged(BR.state_color);
    }

    @NonNull
    public int getBackground(){
        if (event.getNames().equalsIgnoreCase("Instant Occupation"))
            return R.drawable.instant_notification_bg;
        else
            return R.drawable.white_secnd_color_margin_bottom;
    }

    public boolean onLongClick(@NonNull View view){
            EventActionsDialogBinding dialogBinding = EventActionsDialogBinding.inflate(LayoutInflater.from(view.getContext()));
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setView(dialogBinding.getRoot());
            AlertDialog dialog = builder.create();
            EventAction.ActionCompleteCallBack completeCallBack = () -> {
                Log.d("Jpurple",TAG+ "completeCallBack() called with: dialog != null = [" + (dialog != null) + "]");
                if (dialog != null) {
                    listChangeObserver.onListItemChange();
                    dialog.dismiss();
                }
            };
            dialogBinding.setViewManager(new EventActionsVM(view, event, state, completeCallBack));
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
        return false;
    }
}
