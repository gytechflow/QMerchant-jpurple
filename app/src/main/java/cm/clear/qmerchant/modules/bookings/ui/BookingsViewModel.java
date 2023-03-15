package cm.clear.qmerchant.modules.bookings.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cm.clear.qmerchant.common.core.CommonFilters;
import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.common.listviewmanagement.ListChangeObserver;
import cm.clear.qmerchant.common.pagedrequests.Paginates;
import cm.clear.qmerchant.common.pagedrequests.PaginationViewModel;
import cm.clear.qmerchant.dashboard.custom.concreteobjects.BookingCounter;
import cm.clear.qmerchant.interfaces.ResponseListener;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.modules.bookings.data.BookingMessenger;
import cm.clear.qmerchant.modules.bookings.data.interfaces.BookingsChangeObserver;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class BookingsViewModel extends PaginationViewModel implements BookingsChangeObserver, ResponseListener, ListChangeObserver {

    private static final String TAG = BookingsViewModel.class.getName();

    private String requestFilter = "";
    private final boolean REFRESHED = true;
    private Long tms = 0L;
    private List<Booking> bookingsList = new ArrayList<>();
    MutableLiveData<Integer> progressBarVisibility = new MutableLiveData<>(View.INVISIBLE);
    private CallAndCallback<List<Booking>> main_call;
    private MutableLiveData<Map<Boolean, List<Booking>>> sortedBookingList = new MutableLiveData<>();
    private MutableLiveData<String> counterString = new MutableLiveData<>();

    private String selectedPercentage = Booking.DEFAULT_PERCENTAGE;
    private String selectedDate = "";

    public BookingsViewModel(@NonNull Context context) {
        super(context);
        BookingMessenger.getSharedInstance().setAdapterObserver(this);
    }

    @NonNull
    public LiveData<Map<Boolean, List<Booking>>> getBookingsList() {
        updateBookingList(getSqlFilter(selectedPercentage), REFRESHED);
        return sortedBookingList;
    }

    /**
     *
     * @param filters
     * @return
     */
    @NonNull
    public LiveData<Map<Boolean, List<Booking>>> getBookingsList(String[] filters) {

        selectedDate = filters[0];
        selectedPercentage = filters[1];

        updateBookingList(selectedDate, getSqlFilter(selectedPercentage), REFRESHED);
        return sortedBookingList;
    }

    /**
     *
     * @param dateFilter
     * @param stateFilter
     * @param refreshed
     */
    private void updateBookingList(String dateFilter, String stateFilter, boolean refreshed) {
        onStart();

        String sqlFilters = CommonFilters.allForToday(BookingCounter.DATE_ATTRIBUTE, dateFilter) + " AND " + stateFilter;
        setRequestFilter(sqlFilters);

        Log.d("HOSIRUS-LOG", this.getClass().getName()+"-->updateBookingList-->Filters : " + sqlFilters);

        Call<List<Booking>> call = ApiUtil.getBookingService().getBookings("t.id", "DESC", "5", "" + getPage(), "" + sqlFilters);
        QCallback<List<Booking>> onBookingsRequested = new QCallback<List<Booking>>() {
            @SuppressLint("SyntheticAccessor")
            @Override
            public void responseSuccessful(Call<List<Booking>> call, Response<List<Booking>> response) {
//
                Log.e("HOSIRUS", this.getClass().getName()+"-->updateBookingList-->responseSuccessful: " + response.body().size());
                boolean isLatestCall = main_call.getCall().request().toString().equalsIgnoreCase(call.request().toString());
                if (isLatestCall) {
                    assert response.body() != null;
                    Log.e("FAKE-JPurple", this.getClass().getName()+"-->responseSuccessful2: " + response.body().size());
                    onSuccess(response.body(), refreshed);
                }
            }
            @Override
            public void requestFailure(Call<List<Booking>> call, Throwable t) {
                onFailed();
            }

            @SuppressLint("SyntheticAccessor")
            @Override
            public void responseUnsuccessful(Call<List<Booking>> call, Response<List<Booking>> response) {
                onNotFound();
            }
        };
        main_call = new CallAndCallback<List<Booking>>(call, onBookingsRequested);
        RequestManager.getInstance().clear(this);

        RequestManager.getInstance().addTempCall(main_call);
    }

    /**
     *  UNUSED.
     *
     * @param sqlFilter
     * @param refreshed
     */
    @Deprecated
    private void updateBookingList(String sqlFilter, boolean refreshed) {
        onStart();
        sqlFilter = CommonFilters.fromToday(BookingCounter.DATE_ATTRIBUTE) + " AND " + sqlFilter;
        setRequestFilter(sqlFilter);

//        Call<List<Booking>> call = ApiUtil.getBookingService().getBookings("t.id", "DESC", "100", sqlFilter);
        Call<List<Booking>> call = ApiUtil.getBookingService().getBookings("t.id", "DESC", "5", "" + getPage(), "" + sqlFilter);
        QCallback<List<Booking>> onBookingsRequested = new QCallback<List<Booking>>() {
            @SuppressLint("SyntheticAccessor")
            @Override
            public void responseSuccessful(Call<List<Booking>> call, Response<List<Booking>> response) {
//                bookingsList.setValue(response.body());
                Log.e("HOSIRUS", this.getClass().getName()+"-->updateBookingList-->responseSuccessful: " + response.body().size());
                boolean isLatestCall = main_call.getCall().request().toString().equalsIgnoreCase(call.request().toString());
                if (isLatestCall) {
                    assert response.body() != null;
                    /*if (current_page > DEFAULT_PAGE) {
                        updateBookingList(response.body());
                    } else {
                        if (hasChanged(response.body()) || refreshed) {
                            reload(response.body(), false);
                        }
                    }*/
                    Log.e("J-Purple", "responseSuccessful2: " + response.body().size());
                    onSuccess(response.body(), refreshed);
                }
            }

            @Override
            public void requestFailure(Call<List<Booking>> call, Throwable t) {
                onFailed();
            }

            @SuppressLint("SyntheticAccessor")
            @Override
            public void responseUnsuccessful(Call<List<Booking>> call, Response<List<Booking>> response) {
                onNotFound();
                /*if (response.raw().toString().contains("Not Found")) {
                    if (current_page != DEFAULT_PAGE) {
                        updateBookingList(new ArrayList<>());
                    } else {
                        reload(new ArrayList<>(), false);
                    }
                }*/
            }
        };
        main_call = new CallAndCallback<List<Booking>>(call, onBookingsRequested);
        RequestManager.getInstance().clear(this);
        /*if (getPage()==Paginates.DEFAULT_PAGE) {
            int i = 0;
            //            RequestManager.getInstance().addTimerCall(this, main_call);
        }
        else*/
        RequestManager.getInstance().addTempCall(main_call);
    }

    @Override
    public void getNextListElements() {
        Log.d(TAG, "getNextListElements() called");
        setPage(getPage() + 1);
        updateBookingList(selectedDate, getSqlFilter(selectedPercentage), !REFRESHED);
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBarVisibility.setValue(View.VISIBLE);
    }

    @Override
    public void onEnd() {
        super.onEnd();
        progressBarVisibility.setValue(View.INVISIBLE);
    }

    /**
     * verifies if any element in the list is changed so the view is not updated uselessly
     *
     * @param list
     * @return true: if all the elements in the list are unchanged
     * false: if an element of the list has changed
     */
    private boolean hasChanged(List<Booking> list) {
        if (bookingsList.size() == 0)
            return true;
        if (bookingsList.size() != list.size())
            return true;
        for (int i = 0; i < bookingsList.size(); i++) {
            boolean sameRef = bookingsList.get(i).getRef().equals(list.get(i).getRef());
            boolean notSameModificationDate = !bookingsList.get(i).getDatem().equals(list.get(i).getDatem()) && sameRef;
            if (!sameRef || notSameModificationDate) {
                Log.e("J-Purple", "hasChanged: comparing [" + bookingsList.get(i).getRef() + "]= [" + bookingsList.get(i).getDatem() + "] with [" + list.get(i).getRef() + "]= [" + list.get(i).getDatem() + "]");
                return true;
            }
        }
        return false;
    }


    @Override
    public void onCounterChange(String counterDisplay) {
        counterString.setValue(counterDisplay);
    }

    @Override
    public void onListItemChange() {
        updateBookingList(selectedDate, getSqlFilter(selectedPercentage), REFRESHED);
    }

    @Override
    public void onListBottomReached() {
        if (!isGettingElements()) {
            getNextListElements();
        }
    }

    @Override
    public void onFilterChange(String value) {

        selectedPercentage = value;

        setPage(Paginates.DEFAULT_PAGE);
        Map<Boolean, List<Booking>> empty = new HashMap<>();
        empty.put(false, new ArrayList<>());
        sortedBookingList.setValue(empty);
        bookingsList = new ArrayList<>();

        updateBookingList(selectedDate, getSqlFilter(selectedPercentage), REFRESHED);
    }

    @Override
    public void onDateChange(Long tms) {
        this.tms = tms;
        // Execute request with selectedDate and selectedPercentage.
        this.selectedDate = TmsConverter.getDateForQuery(tms, TmsConverter.FROM_SQL_JAVA);
        //String filters [] = { selectedDate, selectedPercentage };

        setPage(Paginates.DEFAULT_PAGE);
        Map<Boolean, List<Booking>> empty = new HashMap<>();
        empty.put(false, new ArrayList<>());
        sortedBookingList.setValue(empty);
        bookingsList = new ArrayList<>();

        // Reload everything with the new date.
        updateBookingList(selectedDate, getSqlFilter(selectedPercentage), REFRESHED);
        //loadList(bookingsList, false);

    }

    @Override
    public void onClearCalls() {
        RequestManager.getInstance().remove(this);
    }

    @Override
    public void refresh() {
        setPage(Paginates.DEFAULT_PAGE);
        updateBookingList(selectedDate, getSqlFilter(selectedPercentage), REFRESHED);
    }

    private String getSqlFilter(String percentage) {
        if (percentage.equalsIgnoreCase(Booking.ALL_BOOKINGS)){
            return ApiUtil.ALWAYS_TRUE_FILTER;
        }
        return "t.percent=" + percentage;
    }

    private String getRequestFilter() {
        return requestFilter;
    }

    private void setRequestFilter(String filter) {
        requestFilter = filter;
    }

    @Override
    public void updateList(@NonNull List returnedList) {
        loadList(returnedList, Paginates.IS_UPDATE);
    }

    @Override
    public void reloadList(@NonNull List returnedList, boolean refreshed) {
        if (refreshed) {
            loadList(returnedList, !Paginates.IS_UPDATE);
        }
    }

    /**
     * reload adapter, filtering on the current values of {@link #tms}
     */
    private void loadList(List<Booking> returnedBookingList, boolean isListUpdate) {
        bookingsList = new ArrayList<>(returnedBookingList);
        List<Booking> tempList = new ArrayList<>();
        for (Booking booking : bookingsList) {
//                boolean percentageCompare = booking.getPercentage().equalsIgnoreCase(selectedPercentage);
//                boolean percentageIgnore = selectedPercentage.equalsIgnoreCase("-1");
            boolean dateCompare = TmsConverter.getDate(booking.getDatep(), TmsConverter.FROM_SQL_JAVA).equalsIgnoreCase(TmsConverter.getDate(tms, TmsConverter.FROM_SQL_JAVA));
            boolean dateIgnore = tms == 0;
            if (dateIgnore || dateCompare)
                tempList.add(booking);
        }
        Map<Boolean, List<Booking>> listAndType = new HashMap<>();
        listAndType.put(isListUpdate, new ArrayList<>(tempList));
        sortedBookingList.setValue(listAndType);
//        sortedBookingList.setValue(new ArrayList<>(tempList));
        updateCounter();
    }

    /**
     * update occupation counter
     */
    private void updateCounter() {
        int sum1 = 0;
        int sum2 = 0;
//        for (Booking booking : Objects.requireNonNull(sortedBookingList.getValue())) {
//            sum1 += booking.get
//        }
    }

    public void reset() {

        Log.d("J-Purple", "reset() called");

        this.selectedPercentage = Booking.DEFAULT_PERCENTAGE;
        setPage(Paginates.DEFAULT_PAGE);
        Map<Boolean, List<Booking>> listAndType = new HashMap<>();
        listAndType.put(!Paginates.IS_UPDATE, new ArrayList<>());
        sortedBookingList.setValue(listAndType);
//        sortedBookingList.setValue(new ArrayList<>());
        bookingsList = new ArrayList<>();
    }

    @NonNull
    public LiveData<Integer> getProgressBarVisibility() {
        return progressBarVisibility;
    }

    @Override
    public void reloadRequest() {

    }

    public void newBookingMade() {
        Toast.makeText(context, "newBookingMade", Toast.LENGTH_SHORT).show();
    }

    public String getSelectedPercentage() {
        return selectedPercentage;
    }
    public void setSelectedPercentage(String selectedPercentage) {
        this.selectedPercentage = selectedPercentage;
    }

    public String getSelectedDate() {
        return selectedDate;
    }
    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }
}
