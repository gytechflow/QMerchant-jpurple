package cm.clear.qmerchant.modules.tables.ui;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import cm.clear.qmerchant.BR;
import cm.clear.qmerchant.common.interfaces.FilterChangeObserver;
import cm.clear.qmerchant.common.interfaces.NavigationListener;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.models.qtable.QTable;
import cm.clear.qmerchant.modules.tables.data.DayPeriodListener;
import cm.clear.qmerchant.modules.tables.data.TablesRequestProvider;

public class TablesViewModel extends BaseObservable implements FilterChangeObserver, DayPeriodListener {
    private List<QTable> tables = new ArrayList<>();
    private TablesRequestProvider requestProvider;
    private TablesAdapter adapter;
    private  NavigationListener navigationListener;
    private  Runnable onListLoaded;
    private  long timestamp;
    private  int period = PERIOD_TIME_WHOLE_DAY;
    private  boolean isMorning = true;
    private  boolean isEvening = true;


    public static final int PERIOD_TIME_WHOLE_DAY = 0;
    public static final int PERIOD_TIME_MORNING_TIME = 1;
    public static final int PERIOD_TIME_EVENING_TIME = 2;

    public TablesViewModel(@NonNull NavigationListener navigationListener, @NonNull Runnable onListLoaded) {
        this.onListLoaded = onListLoaded;
        this.navigationListener = navigationListener;
        this.requestProvider = new TablesRequestProvider(this);
        requestProvider.getTables();
    }

    @NonNull
    public RecyclerView.Adapter<TablesAdapter.ViewHolder> getAdapter() {
        return adapter;
    }

    public void onTablesLoaded(@NonNull List<QTable> qTables) {
        processResults(qTables);
    }

    private void processResults(List<QTable> qTables) {
        tables = new ArrayList<>(qTables);
        reloadAdapter();
    }

    private void reloadAdapter() {
        adapter = new TablesAdapter(period, tables, navigationListener, timestamp);
        onListLoaded.run();
    }

    public void gettingTablesFailed() {

    }

    public void gettingTablesUnsuccessful() {

    }

    @Override
    public void onFilterChange(@NonNull String value) {
        requestProvider.setDate(value);
    }

    @Override
    public void onClearCalls() {

    }

    @Override
    public void refresh() {

    }

    public void reset() {
        adapter = new TablesAdapter(period, tables, navigationListener, timestamp);
        requestProvider.getTables();
    }

    public void setDate(long l) {
        this.timestamp = l;
        adapter.setDate(timestamp);
        requestProvider.setDate("" + timestamp);
    }

    @Override
    public void onDatePeriodChanged(int period) {
        this.period = period;
    }

    public void sortEvents(@NonNull Comparator<Booking> bookingComparator) {
        adapter.sortEvents(bookingComparator);
    }

    public void onMorningClicked(){
        isMorning = !isMorning || !isEvening;
        notifyPropertyChanged(BR.morning);
        changePeriod();
    }

    public void onEveningClicked(){
        isEvening = !isEvening || !isMorning;
        notifyPropertyChanged(BR.evening);
        changePeriod();
    }

    @Bindable
    public boolean isMorning() {
        return isMorning;
    }

    @Bindable
    public boolean isEvening() {
        return isEvening;
    }

    //@BindingAdapter("jp:checked")
    public static void checked(@NonNull MaterialButton button, boolean checked){
        button.setChecked(checked);
    }

    private void changePeriod(){
        if (isMorning)
            period = PERIOD_TIME_MORNING_TIME;
        if (isEvening)
            period = PERIOD_TIME_EVENING_TIME;
        if (isEvening == isMorning)
            period = PERIOD_TIME_WHOLE_DAY;
        reloadAdapter();
    }

    private void showUnoccupied(){

    }
}
