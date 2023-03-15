package cm.clear.qmerchant.modules.tables.events.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.common.interfaces.NavigationListener;
import cm.clear.qmerchant.common.listprovider.ListObject;
import cm.clear.qmerchant.common.listprovider.ListObserver;
import cm.clear.qmerchant.common.listviewmanagement.ListChangeObserver;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.models.booking.BookingActionsObserver;
import cm.clear.qmerchant.modules.tables.events.data.EventBaseObservable;
import cm.clear.qmerchant.modules.tables.events.data.EventDiffUtilCallback;
import cm.clear.qmerchant.modules.tables.events.data.EventListProvider;
import cm.clear.qmerchant.modules.tables.events.data.EventMessenger;
import cm.clear.qmerchant.remote.ApiUtil;

public class EventsViewModel extends ViewModel {
    protected static final String TAG = EventsViewModel.class.getName();
    @NonNull
    public EventBaseObservable baseObservable = new EventBaseObservable();
    protected final EventAdapter adapter;
    private final EventListProvider listProvider;
    private String percentage = Booking.ALL_BOOKINGS;
    private static List<Booking> events = new ArrayList<>();
    private MutableLiveData<List<Booking>> sortedEvents = new MutableLiveData<>();
    private BookingActionsObserver actionsObserver;
    private ListChangeObserver listChangeObserver = new ListChangeObserver() {
        @Override
        public void onListItemChange() {
            EventsViewModel.this.onListItemChange();
        }

        @Override
        public void onListBottomReached() {
            EventsViewModel.this.onListBottomReached();
        }
    };

    private NavigationListener navigationListener = new NavigationListener() {
        @Override
        public void onNavigate(String key, Bundle data, int res) {
            Log.e("JPurple-Expected", TAG+"Unsupported::onNavigate() called with: key = [" + key + "], data = [" + data + "], res = [" + res + "]");
        }

        @Override
        public void onNavigate(@NonNull View view, @NonNull Bundle data, int action) {
            Navigation.findNavController(view)
                        .navigate(action, data);
        }
    };


    public EventsViewModel(@NonNull Context context, int table_id){
        EventMessenger.getInstance().setNavigationListener(navigationListener);
        EventMessenger.getInstance().setListChangeObserver(listChangeObserver);
        actionsObserver = new BookingActionsObserver() {
            @Override
            public void onAction(boolean isActionStart) {
                if (isActionStart)
                    baseObservable.setProgressBarVisibility(View.VISIBLE);
                else baseObservable.setProgressBarVisibility(View.INVISIBLE);
            }
        };
        adapter = new EventAdapter(new EventDiffUtilCallback(), actionsObserver);
        listProvider = new EventListProvider(context, table_id, ApiUtil.getBookingService(), getListObserver());
    }

    public void load(){
        listProvider.reload();
        requestStarted();
    }

    public void refresh() {
        load();
    }

    @NonNull
    public RecyclerView.Adapter<EventAdapter.ViewHolder> getAdapter(){
        return adapter;
    }

    @NonNull
    public RecyclerView.OnScrollListener getScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                assert layoutManager != null;
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!listProvider.isLoading() && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= EventListProvider.PAGE_SIZE) {
                    // End of the list has been reached, request more items
                    onListBottomReached();
                }
            }
        };
    }

    //@Override
    public void onListItemChange() {
        listProvider.reload();
        requestStarted();
    }

    //@Override
    public void onListBottomReached() {
        listProvider.listBottomReached();
        requestStarted();
    }

    private ListObserver getListObserver(){
        return new ListObserver() {
            @Override
            public void onListUpdate(@NonNull List<ListObject> items) {
                List<Booking> oldEvents = new ArrayList<>(adapter.getCurrentList());
                List<Booking> events = new ArrayList<>(toEventType(items));
                oldEvents.addAll(events);
                adapter.submitList(oldEvents);
                requestEnded();
            }

            @Override
            public void onReloadList(@NonNull List<ListObject> items) {
                List<Booking> unfilteredOrders = toEventType(items);
                adapter.submitList(unfilteredOrders);
                requestEnded();
            }
        };
    }

    protected void requestStarted() {
        baseObservable.setProgressBarVisibility(View.VISIBLE);
        baseObservable.setLoading(true);
    }

    protected void requestEnded() {
        baseObservable.setProgressBarVisibility(View.GONE);
        baseObservable.setLoading(false);
    }

    @NonNull
    public MutableLiveData<List<Booking>> getEvents() {
        return sortedEvents;
    }

    @NonNull
    public static List<Booking> toEventType(@NonNull List<ListObject> items){
        List<Booking> eventsTemp = new ArrayList<>();
        for (ListObject item : items) {
            eventsTemp.add((Booking)item);
            Log.d("JPurple-NewArch", TAG+"::toEventType() called "+((Booking)item).getStatus());
        }

        return eventsTemp;
    }
}
