package cm.clear.qmerchant.dashboard;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cm.clear.qmerchant.dashboard.custom.abstractclasses.CompositeDashBoardCounter;
import cm.clear.qmerchant.dashboard.custom.concreteobjects.BookingCounter;
import cm.clear.qmerchant.dashboard.custom.concreteobjects.ClientCounter;
import cm.clear.qmerchant.dashboard.custom.concreteobjects.CouponCounter;
import cm.clear.qmerchant.dashboard.custom.concreteobjects.OrderCounter;
import cm.clear.qmerchant.dashboard.custom.interfaces.CompositeCountListener;
import cm.clear.qmerchant.dashboard.custom.interfaces.ISimpleDashboardCounter;
import cm.clear.qmerchant.dashboard.custom.interfaces.ITotalCounterAddon;
import cm.clear.qmerchant.interfaces.ResponseListener;

public class MainViewModel extends ViewModel implements CompositeCountListener {

    private MutableLiveData<fabAction> fabAction  = new MutableLiveData<>(new fabAction() {
        @Override
        public int getNavigation() {
            return 0;
        }
    });

    private MutableLiveData<Boolean> fabVisible = new MutableLiveData<>(false);
    private CompositeDashBoardCounter  orderAddon;
    private CompositeDashBoardCounter  bookingAddon;
    private CompositeDashBoardCounter  clientAddon;
    private CompositeDashBoardCounter  couponAddon;
    private final Map<ResponseListener, MutableLiveData<CompositeDashBoardCounter>> addonMap = new HashMap<>();
    private final Map<ResponseListener, MutableLiveData<ISimpleDashboardCounter>> simpleMap = new HashMap<>();


    @NonNull
    public LiveData<CompositeDashBoardCounter> getOrderCounter() {
        if (orderAddon == null){
            orderAddon = new OrderCounter(this);
            addonMap.put(orderAddon, new MutableLiveData<>(orderAddon));
        }
        return Objects.requireNonNull(addonMap.get(orderAddon));
    }

    @NonNull
    public LiveData<CompositeDashBoardCounter> getClientCounter(){
        if (clientAddon == null){
            clientAddon = new ClientCounter(this);
            addonMap.put(clientAddon, new MutableLiveData<>(clientAddon));
        }
        return Objects.requireNonNull(addonMap.get(clientAddon));
    }

    @NonNull
    public LiveData<CompositeDashBoardCounter> getBookingCounter(){
        if (bookingAddon == null){
            bookingAddon = new BookingCounter(this);
            addonMap.put(bookingAddon, new MutableLiveData<>(bookingAddon));
        }
        return Objects.requireNonNull(addonMap.get(bookingAddon));
    }

    @NonNull
    public LiveData<CompositeDashBoardCounter> getCouponCounter(){
        if (couponAddon == null){
            couponAddon = new CouponCounter(this);
            addonMap.put(couponAddon, new MutableLiveData<>(couponAddon));
        }
        return Objects.requireNonNull(addonMap.get(couponAddon));
    }

    @NonNull
    public MutableLiveData<cm.clear.qmerchant.dashboard.fabAction> getFabAction() {
        return fabAction;
    }

    public void setFabAction(@NonNull cm.clear.qmerchant.dashboard.fabAction fabAction) {
        this.fabAction.setValue(fabAction);
    }

    @NonNull
    public MutableLiveData<Boolean> getFabVisible() {
        return fabVisible;
    }

    public void setFabVisible(boolean fabVisible) {
        this.fabVisible.setValue(fabVisible);
    }

    @Override
    public void onCompositeCountChange(@NonNull ITotalCounterAddon totalCounterAddon) {
//        Log.d("J-Purple", "onCompositeCountChange() called with: totalCounterAddon = [" + totalCounterAddon + "]");
        if (addonMap.containsKey(totalCounterAddon))
            Objects.requireNonNull(addonMap.get(totalCounterAddon)).setValue(Objects.requireNonNull(addonMap.get(totalCounterAddon)).getValue());
    }

    @Override
    public void onSimpleCountChange(@NonNull ISimpleDashboardCounter simpleDashboardCounter) {
        if (simpleMap.containsKey(simpleDashboardCounter))
            Objects.requireNonNull(simpleMap.get(simpleDashboardCounter)).setValue(simpleDashboardCounter);

        if (addonMap.containsKey(simpleDashboardCounter)){
            Objects.requireNonNull(addonMap.get(simpleDashboardCounter)).setValue(Objects.requireNonNull(addonMap.get(simpleDashboardCounter)).getValue());
        }
    }

    public void reset() {
//        addonMap.clear();
//        simpleMap.clear();
//        clientAddon =null;
//        bookingAddon =null;
//        orderAddon =null;
    }

    /**
     *
     *
    private List<ViewModel> viewModelList;
    public void registerModel(ViewModel viewModel) {
        if(viewModelList==null){
            viewModelList = new ArrayList<>();
        }
        if(!viewModelList.contains(viewModel)){
            viewModelList.add(viewModel);
        }
    }
    public void notifyOnBookingAdded() {
        for(ViewModel vm : viewModelList){
            //vm.notify();
        }
    }
    */
}
