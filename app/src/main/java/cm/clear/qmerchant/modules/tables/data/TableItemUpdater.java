package cm.clear.qmerchant.modules.tables.data;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cm.clear.qmerchant.common.core.CommonFilters;
import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.common.listviewmanagement.IAdapterItemChangeListener;
import cm.clear.qmerchant.common.listviewmanagement.ItemStateUpdater;
import cm.clear.qmerchant.dashboard.custom.concreteobjects.BookingCounter;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.models.qtable.QTable;
import cm.clear.qmerchant.modules.tables.data.comparator.NumberOfEventsComparator;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class TableItemUpdater extends ItemStateUpdater {
    private String sqlFilter;
    private long date_tms = 0L;

    public TableItemUpdater(@NonNull List objects, @NonNull IAdapterItemChangeListener itemChangeListener) {
        super(objects, itemChangeListener);
    }

    @Override
    public void addToMap(@NonNull Object o) {
        getStateHolderMap().put(o, new TableStateHolder(new TableState()));
    }

    @Override
    public void loadExtras(@NonNull Object o) {
        getBookings(o);
    }

    private void getBookings(Object o) {
        //Log.d("J-Purple", "getBookings() called with: filter = [" + getFilter() + "]");
        QTable table = (QTable) o;
        Call<List<Booking>> loadBookings = ApiUtil.getBookingService().getEventsForResource(table.getId(), getFilter());
        QCallback<List<Booking>> onBookingsLoaded = new QCallback<List<Booking>>() {
            @Override
            public void responseSuccessful(Call<List<Booking>> call, Response<List<Booking>> response) {
                TableStateHolder holder = (TableStateHolder) getStateHolderMap().get(o);
                TableState tableState = new TableState((TableState) holder.getState());
                tableState.setAssignedEvents(response.body());
                if (getStateHolderMap().get(o).isValueUpdated(tableState))
                    update(holder.getPosition());
            }

            @Override
            public void requestFailure(Call<List<Booking>> call, Throwable t) {
                TableStateHolder holder = (TableStateHolder) getStateHolderMap().get(o);
                TableState tableState = (TableState) holder.getState();
                tableState.setAssignedEvents(new ArrayList<>());
                if (getStateHolderMap().get(o).isValueUpdated(tableState))
                    update(holder.getPosition());
            }

            @Override
            public void responseUnsuccessful(Call<List<Booking>> call, Response<List<Booking>> response) {
                TableStateHolder holder = (TableStateHolder) getStateHolderMap().get(o);
                TableState tableState = (TableState) holder.getState();
                tableState.setAssignedEvents(new ArrayList<>());
                if (getStateHolderMap().get(o).isValueUpdated(tableState))
                    update(holder.getPosition());
            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(loadBookings, onBookingsLoaded));
    }

    @Override
    public int getNewItemPosition(int position) {
        List<TableState> tableStates = new ArrayList<>();
        List<TableState> statesCopy = new ArrayList<>();
        for (Object o : getStateHolderMap().keySet()) {
            tableStates.add((TableState) getStateHolderMap().get(o).getState());
            statesCopy.add((TableState) getStateHolderMap().get(o).getState());
        }
        Collections.sort(tableStates, new NumberOfEventsComparator());
        return tableStates.indexOf(statesCopy.get(position));
    }

    public void changeDate(long new_date) {
        date_tms = new_date;
        reloadRequest();
    }

    @NonNull
    private String getDateFilter() {
        if (date_tms == 0L)
            return CommonFilters.todayOnly(BookingCounter.DATE_ATTRIBUTE);
        String date = TmsConverter.getDateForQuery(date_tms, TmsConverter.NO_CONVERSION);
        String tomorrow = TmsConverter.getDatePlusForQuery(date_tms, TmsConverter.NO_CONVERSION, 1);
        return "t.datep between DATE '" + date + "' and DATE '" + tomorrow + "'";
    }

    private String getFilter() {
        return getDateFilter() + " And t.percent= " + Booking.PERCENT_50;
    }
}
