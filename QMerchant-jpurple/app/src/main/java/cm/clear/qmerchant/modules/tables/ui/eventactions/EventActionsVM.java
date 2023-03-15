package cm.clear.qmerchant.modules.tables.ui.eventactions;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.BR;
import cm.clear.qmerchant.R;
import cm.clear.qmerchant.common.CustomCallback;
import cm.clear.qmerchant.common.actionablebuttons.ResourceResult;
import cm.clear.qmerchant.common.interfaces.QNavigationManager;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.models.booking.BookingActions;
import cm.clear.qmerchant.models.booking.bookingStates.BookingState;
import cm.clear.qmerchant.models.booking.bookingStates.SuppStatusHelper;
import cm.clear.qmerchant.models.category.Category;
import cm.clear.qmerchant.models.category.StaticCategoryRepo;
import cm.clear.qmerchant.modules.bookings.data.BookingStateSafe;
import cm.clear.qmerchant.modules.tables.events.data.EventMessenger;

public class EventActionsVM extends BaseObservable {
    private static final String TAG = EventActionsVM.class.getName();
    protected final Booking event;
    protected final BookingState state;
    protected  boolean loaderVisible = true;
    protected  boolean errorVisible = false;
    protected final EventAction.ActionCompleteCallBack completeCallBack;
    protected final View view;
    protected final EventActionsAdapter adapter;
    private final CustomCallback onCategoriesLoaded = new CustomCallback() {
        @Override
        public void positive(@NonNull String message) {
            if (StaticCategoryRepo.getCategories() != null) {
                List<Category> categoryList = SuppStatusHelper.getCategoriesToDisplay(StaticCategoryRepo.getCategories(), state.getCategories());
                updateAdapter(getEventActions(categoryList));
            }
            setLoaderVisible(false);
        }

        @Override
        public void negative(@NonNull String message) {
            setLoaderVisible(false);
            setErrorVisible(true);
        }
    };


    public EventActionsVM(@NonNull View view, @NonNull Booking event, @NonNull BookingState state, @Nullable EventAction.ActionCompleteCallBack completeCallBack) {
        this.event = event;
        this.state = state;
        this.completeCallBack = completeCallBack;
        this.view = view;
        //this.eventActions = getBasicEventActions();
        adapter = new EventActionsAdapter();
        adapter.submitList(new ArrayList<>(getBasicEventActions()));
        if (state.isCategoriesLoaded())
            StaticCategoryRepo.loadCategories(onCategoriesLoaded);
        else state.addCategoryObserver(new Runnable() {
            @Override
            public void run() {
                StaticCategoryRepo.loadCategories(onCategoriesLoaded);
            }
        });
    }

    private List<EventAction> getBasicEventActions() {
        List<EventAction> tempList = new ArrayList<EventAction>() {{
            EventAction base = new EventAction("option",
                    () -> null,
                    (LifecycleOwner) view.getContext(),
                    completeCallBack
            );
            try {
                add(base.clone()
                        .setName(view.getContext().getString(R.string.close_text))
                        .setResultLiveData(() -> BookingActions.endEvent(event.getId())));
                add(base.clone()
                        .setName(view.getContext().getString(R.string.modify))
                        .setResultLiveData(() -> {
                            if (view.getContext() != null) {
                                QNavigationManager navigationManager = EventMessenger.getInstance().getNavigationManager();
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("is_update", true);
                                bundle.putString("booking_id", event.getId());
                                BookingStateSafe.getInstance().setStateObject(event);
                                BookingState bookingState = BookingState.getState(event.getPercentage(), Integer.parseInt(event.getNbplace()));
                                BookingStateSafe.getInstance().setState(bookingState);
                                if (navigationManager != null)
                                    Navigation.findNavController(view)
                                            .navigate(R.id.action_show_create_booking_fragment, bundle);
                            }
                            return new MutableLiveData<>(new ResourceResult("", "yup", true));
                        }));
            } catch (CloneNotSupportedException e) {
                Log.e("JPurple-Expected", TAG + "instance initializer: Seems Clone not supported for EventAction", e);
            }
        }};
        return new ArrayList<>(tempList);
    }

    @NonNull
    protected List<EventAction> getEventActions(@NonNull List<Category> loadedCategories) {
        List<EventAction> tempList = new ArrayList<EventAction>() {{
            EventAction base = new EventAction("Option",
                    () -> null,
                    (LifecycleOwner) view.getContext(),
                    completeCallBack
            );
            try {
                for (Category loadedCategory : loadedCategories) {
                    add(base.clone()
                            .setName(loadedCategory.getLabel())
                            .setResultLiveData(() -> BookingActions.setSuppStatus(event.getId(), loadedCategory)));
                }
            } catch (CloneNotSupportedException e) {
                Log.e("JPurple-Expected", TAG + "instance initializer: Seems Clone not supported for EventAction", e);
            }
        }};
        return new ArrayList<>(tempList);
    }

    protected void updateAdapter(@NonNull List<EventAction> new_categories){
        List<EventActionsItemVM> old_categories = adapter.getCurrentList();
        List<EventActionsItemVM> all_categories = new ArrayList<>(new_categories);
        all_categories.addAll(old_categories);
        adapter.submitList(all_categories);
    }

    @Bindable
    public boolean isLoaderVisible() {
        return loaderVisible;
    }

    public void setLoaderVisible(boolean value) {
        loaderVisible = value;
        notifyPropertyChanged(BR.loaderVisible);
    }

    @Bindable
    public boolean isErrorVisible() {
        return errorVisible;
    }

    public void setErrorVisible(boolean value) {
        errorVisible = value;
        notifyPropertyChanged(BR.errorVisible);
    }

    @NonNull
    public String getEventName() {
        return event.getNames();
    }

    @NonNull
    public EventActionsAdapter getAdapter() {
        return adapter;
    }

    public void onRetryClick() {
        StaticCategoryRepo.loadCategories(onCategoriesLoaded);
        setLoaderVisible(true);
        setErrorVisible(false);
    }
}
