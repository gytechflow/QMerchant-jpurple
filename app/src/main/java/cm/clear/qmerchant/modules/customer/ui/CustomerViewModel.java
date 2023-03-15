package cm.clear.qmerchant.modules.customer.ui;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;


import cm.clear.qmerchant.interfaces.ResponseListener;
import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.models.Thirdparty;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class CustomerViewModel extends ViewModel implements ResponseListener {
    private MutableLiveData<List<Thirdparty>> liveThirdParties = new MutableLiveData<>();
    private List<Thirdparty> old_LiveThirdParties = new ArrayList<>();
    MutableLiveData<Integer> progressBarVisibility = new MutableLiveData<>(View.INVISIBLE);


    @NonNull
    public LiveData<List<Thirdparty>> getCustomers(){
        if (liveThirdParties.getValue()==null){
            requestCustomers();
        }
        return liveThirdParties;
    }

    private void requestCustomers() {
        progressBarVisibility.setValue(View.VISIBLE);
        String filter = /*CommonFilters.fromToday(ClientCounter.DATE_ATTRIBUTE)*/"";
        Call<List<Thirdparty>> call = ApiUtil.getCustomersService().getCustomers("t.rowid", "AsC", "100", filter);
        QCallback<List<Thirdparty>> qCallback  = new QCallback<List<Thirdparty>>() {
            @Override
            public void responseSuccessful(Call<List<Thirdparty>> call, Response<List<Thirdparty>> response) {
                assert response.body() != null;
                List<Thirdparty> list = filter(response.body());
                if (hasChanged(list)){
                    progressBarVisibility.setValue(View.INVISIBLE);
                    reload(list);
                }
            }

            @Override
            public void requestFailure(Call<List<Thirdparty>> call, Throwable t) {
                progressBarVisibility.setValue(View.INVISIBLE);
            }

            @Override
            public void responseUnsuccessful(Call<List<Thirdparty>> call, Response<List<Thirdparty>> response) {
                progressBarVisibility.setValue(View.INVISIBLE);
            }
        };
        CallAndCallback<List<Thirdparty>> main_call = new CallAndCallback<List<Thirdparty>>(call, qCallback);
        RequestManager.getInstance().clear(this);
        RequestManager.getInstance().addNewCall(this, main_call);
    }

    @NonNull
    protected List<Thirdparty> filter(@NonNull List<Thirdparty> list) {
        List<Thirdparty> thirdpartyList = new ArrayList<>();
        List<String> identifiers = new ArrayList<>();
        for (Thirdparty thirdparty : list) {
            if (!identifiers.contains(thirdparty.getEmail())){
//                Log.d("J-Purple", "filter() called with: list = [" + thirdparty.getEmail() + "]");
                thirdpartyList.add(thirdparty);
                identifiers.add(thirdparty.getEmail());
            }
        }
        return thirdpartyList;
    }

    protected void reload(@NonNull List<Thirdparty> response) {
        liveThirdParties.setValue(response);
        old_LiveThirdParties = new ArrayList<>(response);
    }

    protected boolean hasChanged(@NonNull List<Thirdparty> list) {
        if (old_LiveThirdParties.size()==0){
            Log.i("J-Purple", "hasChanged: old_LiveThirdParties.size()==0");
            return true;
        }
        if (old_LiveThirdParties.size()!= list.size()){
            Log.i("J-Purple", "hasChanged: old_LiveThirdParties.size()!= list.size()");
            return true;
        }
        for (int i = 0; i < list.size(); i++) {
            boolean sameRef = old_LiveThirdParties.get(i).getId().equals(list.get(i).getId());
            boolean notSameModificationDate = !old_LiveThirdParties.get(i).getDateModification().equals(list.get(i).getDateModification()) && sameRef;
            if (!sameRef || notSameModificationDate){
                Log.e("J-Purple", "hasChanged: comparing ["+old_LiveThirdParties.get(i).getRef()+"]= ["+old_LiveThirdParties.get(i).getDateModification()+"] with ["+list.get(i).getRef()+"]= ["+list.get(i).getDateModification()+"]" );
                return true;
            }
        }
        return false;
    }

    @NonNull
    public LiveData<Integer> getProgressBarVisibility() {
        return progressBarVisibility;
    }

    @Override
    public void reloadRequest() {

    }
}
