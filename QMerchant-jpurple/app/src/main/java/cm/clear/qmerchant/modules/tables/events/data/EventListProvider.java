package cm.clear.qmerchant.modules.tables.events.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.common.core.CommonFilters;
import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.common.listprovider.IFilterable;
import cm.clear.qmerchant.common.listprovider.ListObject;
import cm.clear.qmerchant.common.listprovider.ListObserver;
import cm.clear.qmerchant.common.listprovider.ListRepository;
import cm.clear.qmerchant.dashboard.custom.concreteobjects.BookingCounter;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.modules.orders.ui.OrdersViewModel;
import cm.clear.qmerchant.remote.BookingsRequestInterface;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class EventListProvider implements ListRepository, IFilterable {
    protected static final String TAG = EventListProvider.class.getName();
    protected final Context context;
    protected final ListObserver listObserver;
    private Boolean loading;
    protected int page = 0;
    private String order = "DESC";
    private String filter = "";
    private String order_type = OrdersViewModel.DEFAULT_VALUE;
    private Long order_date = 0L;
    public static final int PAGE_SIZE = 5;
    private final BookingsRequestInterface requestInterface;
    protected int call_id;
    protected int table_id;
    private Call<List<Booking>> callForEvents;

    public EventListProvider(@NonNull Context context, int table_id, @NonNull BookingsRequestInterface requestInterface, @NonNull ListObserver listObserver) {
        this.table_id = table_id;
        this.context = context;
        this.listObserver = listObserver;
        this.requestInterface = requestInterface;
        loadItems();
    }

    public int getTable_id() {
        return table_id;
    }

    private void loadItems() {
        if (callForEvents !=null)
            callForEvents.cancel();
        loading = true;
        filter = getFilter();
        callForEvents = requestInterface.getEventsForResource(getTable_id(), getFilter());
        QCallback<List<Booking>> onEventsLoaded = new QCallback<List<Booking>>() {
            @Override
            public void responseSuccessful(Call<List<Booking>> call, Response<List<Booking>> response) {
                List<ListObject> events = new ArrayList<>(response.body());
                loading = false;
                if (page<1)
                    listObserver.onReloadList(events);
                else listObserver.onListUpdate(events);

                if (events.isEmpty())
                    page--;
            }

            @Override
            public void requestFailure(Call<List<Booking>> call, Throwable t) {
                Log.e("JPurple-Expected", TAG+"::requestFailure: error getting events for tables, might be network" );
                listObserver.onListUpdate(new ArrayList<>());
                t.printStackTrace();
            }

            @Override
            public void responseUnsuccessful(Call<List<Booking>> call, Response<List<Booking>> response) {
                Log.e("JPurple-Expected", TAG+"::requestFailure: possible reason = ["+response.errorBody()+"]" );
                listObserver.onListUpdate(new ArrayList<>());
            }
        };
        callForEvents.enqueue(onEventsLoaded);
    }

    private void resetCallId() {
        long time = System.currentTimeMillis();
        call_id = (int) (time & 0xfffffff);
    }

    private String getFilter() {
        return getDateFilter() + " AND " + getTypeFilter();
    }

    private String getTypeFilter() {
        return "t.percent= " + Booking.PERCENT_50;
    }

    @NonNull
    private String getDateFilter() {
        if (order_date == 0L)
            return CommonFilters.todayOnly(BookingCounter.DATE_ATTRIBUTE);
        String date = TmsConverter.getDateForQuery(order_date, TmsConverter.NO_CONVERSION);
        String tomorrow = TmsConverter.getDatePlusForQuery(order_date, TmsConverter.NO_CONVERSION, 1);
        return "t.datep between DATE '" + date + "' and DATE '" + tomorrow + "'";
    }

    @Override
    public void listBottomReached() {
        page++;
        loadItems();
    }

    @Override
    public void reload() {
        page = 0;
        loadItems();
    }

    @Override
    public boolean isLoading() {
        return loading;
    }

    @Override
    public void onTypeChanged(@NonNull String type) {
        this.order_type = type;
        reload();
    }

    @Override
    public void onDateChanged(@NonNull Long date_tms) {
        this.order_date = date_tms;
        reload();
    }
}
