package cm.clear.qmerchant.common.listviewmanagement.stateStorage;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.common.actionablebuttons.ClientAddon;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.models.Thirdparty;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public abstract class StateSafe {
    protected MutableLiveData<Object> selectedObject = new MutableLiveData<>();
    protected MutableLiveData<ClientAddon> state = new MutableLiveData<>();
    protected List<StateKeyHolder> keyHolders = new ArrayList<>();


    public void register(@NonNull StateKeyHolder holder){
        if (!keyHolders.contains(holder))
            keyHolders.add(holder);
    }

    @NonNull
    public LiveData<Object> getStateObject(@NonNull StateKeyHolder holder) throws NotAKeyHolderException {
        if (!keyHolders.contains(holder))
            throw new NotAKeyHolderException();
        return selectedObject;
    }

    public void setStateObject(@NonNull Object selectedObject) {
        this.selectedObject.setValue(selectedObject);
    }

    @NonNull
    public LiveData<ClientAddon> getState(@NonNull StateKeyHolder holder) throws NotAKeyHolderException{
        if (!keyHolders.contains(holder))
            throw new NotAKeyHolderException();

        checkAndReload();
//        if (state.getValue().getClient() == null){
//            getClient(selectedOrder);
//        }
        return state;
    }

    protected abstract void checkAndReload();

    public void setState(@NonNull ClientAddon state) {
        this.state.setValue(state);
    }

    protected void getClient(String clientId){
        MutableLiveData<Thirdparty> liveData = new MutableLiveData<>();
        Call<Thirdparty> call = ApiUtil.getCustomersService().getCustomerById(clientId);
        QCallback<Thirdparty> qCallback = new QCallback<Thirdparty>() {

            @Override
            public void responseSuccessful(Call<Thirdparty> call, Response<Thirdparty> response) {
                assert response.body() != null;
                state.getValue().setClient(response.body());
                state.setValue(state.getValue());

            }
            @Override
            public void requestFailure(Call<Thirdparty> call, Throwable t) {

            }
            @Override
            public void responseUnsuccessful(Call<Thirdparty> call, Response<Thirdparty> response) {

            }
        };
        RequestManager.getInstance().addTempCall(new CallAndCallback(call, qCallback));
    }

    public void reloadStateObject(){
        reloadObject();
        reloadExtras();
    }

    protected abstract void reloadExtras();

    protected abstract void reloadObject();
}

