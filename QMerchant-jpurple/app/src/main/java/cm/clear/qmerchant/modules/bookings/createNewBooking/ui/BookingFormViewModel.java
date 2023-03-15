package cm.clear.qmerchant.modules.bookings.createNewBooking.ui;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.models.QBookingType;
import cm.clear.qmerchant.models.Thirdparty;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.models.booking.BookingActions;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class BookingFormViewModel extends ViewModel {
    private MutableLiveData<Integer> createResult = new MutableLiveData<>();
    private MutableLiveData<List<QBookingType>> bookingTypes = new MutableLiveData<>();
    private MutableLiveData<List<Thirdparty>> suggestions = new MutableLiveData<>();
    private MutableLiveData<Thirdparty> selectedClient = new MutableLiveData<>();
    private boolean filterInput = false;
    private String latestcall;
    private Thirdparty thirdPartyForCreation;
    private boolean editing = false;
    private final LifecycleOwner lifecycleOwner;

    public BookingFormViewModel(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }


//    public LiveData<Boolean> isFormValid(){
//        return formValidator.getValidity();
//    }

    public LiveData<List<Thirdparty>> getSuggestions() {
        return suggestions;
    }

    public LiveData<Integer> getCreateResult() {
        return createResult;
    }

    private void updateSuggestion(String sqlfilter) {
        QCallback<List<Thirdparty>> callback = new QCallback<List<Thirdparty>>() {
            @Override
            public void responseSuccessful(Call<List<Thirdparty>> call, Response<List<Thirdparty>> response) {
//                Log.e("J-purple", "responseSuccessful: "this );
                if (this.getIdentifier().equals(latestcall))
                    suggestions.setValue(response.body());
            }

            @Override
            public void requestFailure(Call<List<Thirdparty>> call, Throwable t) {
                suggestions.setValue(new ArrayList<>());
            }

            @Override
            public void responseUnsuccessful(Call<List<Thirdparty>> call, Response<List<Thirdparty>> response) {
                suggestions.setValue(new ArrayList<>());
            }
        };
        latestcall = callback.getIdentifier();
        ApiUtil.getCustomersService().getCustomers("t.rowid", "AsC", "5", sqlfilter).enqueue(callback);
    }

    public MutableLiveData<List<QBookingType>> getBookingTypes() {
        if (bookingTypes.getValue() == null) {
            ApiUtil.getBookingService().getBookingTypes().enqueue(new QCallback<List<QBookingType>>() {
                @Override
                public void responseSuccessful(Call<List<QBookingType>> call, Response<List<QBookingType>> response) {
                    bookingTypes.setValue(response.body());
                }

                @Override
                public void requestFailure(Call<List<QBookingType>> call, Throwable t) {

                }

                @Override
                public void responseUnsuccessful(Call<List<QBookingType>> call, Response<List<QBookingType>> response) {

                }
            });
        }
        return bookingTypes;
    }

    public void createOrUpdateBooking(Booking booking) {
        if (isEditing())
            updateBooking(booking);
        else createBooking(booking);
    }

    private void createBooking(Booking booking){
        BookingActions.createBooking(booking).observe(lifecycleOwner, resourceResult -> {
            if (resourceResult!=null){
                if (resourceResult.isSuccessful())
                    createResult.setValue(Integer.valueOf(resourceResult.getResultMessage()));
                else createResult.setValue(-1);
            }
        });
    }

    private void updateBooking(Booking booking){
        //Log.e("J-Purple", "updateBooking() called with: booking = [" + booking + "]");
        BookingActions.updateBooking(booking).observe(lifecycleOwner, resourceResult -> {
            if (resourceResult!=null){
                if (resourceResult.isSuccessful())
                    createResult.setValue(1);
                else createResult.setValue(-1);
            }
        });
    }


    public void setCustomerId(String customerId) {
        getNonNUllThirdPartyForCreation().setId(customerId);
    }

    public void setEmail(String email) {
        getNonNUllThirdPartyForCreation().setEmail(email);
    }

    public void setNames(String names) {
        getNonNUllThirdPartyForCreation().setName(names);
    }

    public void setPhone(String phone) {
        getNonNUllThirdPartyForCreation().setPhone(phone);
    }

    public void setClientInfo(Thirdparty item) {
        thirdPartyForCreation = item;
        selectedClient.setValue(item);
    }

    public void getSuggestionById(String id) {
        if (!TextUtils.isEmpty(id))
            updateSuggestion("t.rowid like '" + id + "%'");
        else clearSuggestions();
    }

    public void getSuggestionByNames(String names) {
        if (!TextUtils.isEmpty(names))
            updateSuggestion("t.nom like '%" + names + "%'");
        else clearSuggestions();
    }

    public void getSuggestionByPhone(String phone) {
        if (!TextUtils.isEmpty(phone))
            updateSuggestion("t.phone like '" + phone + "%'");
        else clearSuggestions();
    }

    public void getSuggestionByEmail(String email) {
        if (!TextUtils.isEmpty(email))
            updateSuggestion("t.email like '" + email + "%'");
        else clearSuggestions();
    }

    private void clearSuggestions() {
        latestcall = "0";
    }

    public void setFilterInput(boolean filterInput) {
        this.filterInput = filterInput;
    }

    @NonNull
    public LiveData<Thirdparty> getClientInfo() {
        return selectedClient;
    }

    @NonNull
    private Thirdparty getNonNUllThirdPartyForCreation(){
        if (thirdPartyForCreation==null)
            thirdPartyForCreation = new Thirdparty();
        return thirdPartyForCreation;
    }
    @Nullable
    public Thirdparty getThirdPartyForCreation(){
        return thirdPartyForCreation;
    }

    public boolean isFilterInput() {
        return filterInput;
    }

    public void reset() {
        createResult = new MutableLiveData<>();
        selectedClient.setValue(null);
        suggestions = new MutableLiveData<>();
        thirdPartyForCreation = null;
        setEditing(false);
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

}
