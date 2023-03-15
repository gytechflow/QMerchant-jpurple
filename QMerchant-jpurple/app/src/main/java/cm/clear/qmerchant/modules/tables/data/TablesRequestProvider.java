package cm.clear.qmerchant.modules.tables.data;


import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import cm.clear.qmerchant.manager.CallAndCallback;
import cm.clear.qmerchant.manager.RequestManager;
import cm.clear.qmerchant.models.qtable.QTable;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import cm.clear.qmerchant.modules.tables.ui.TablesViewModel;
import retrofit2.Call;
import retrofit2.Response;

public class TablesRequestProvider {
    private TablesViewModel viewModel;
    private String TAG = TablesRequestProvider.class.getName();

    public TablesRequestProvider(@NonNull TablesViewModel tablesViewModel) {
        viewModel = tablesViewModel;
    }

    public void getTables() {
        Call<List<QTable>> loadTables = ApiUtil.getBookingService().getTables();
        QCallback<List<QTable>> onTablesLoaded = new QCallback<List<QTable>>() {
            @Override
            public void responseSuccessful(Call<List<QTable>> call, Response<List<QTable>> response) {
                viewModel.onTablesLoaded(response.body());
            }

            @Override
            public void requestFailure(Call<List<QTable>> call, Throwable t) {
                Log.e("JPurple", TAG+"::requestFailure() called with:  reason = [" + t.getLocalizedMessage() + "]");
                viewModel.gettingTablesFailed();
            }

            @Override
            public void responseUnsuccessful(Call<List<QTable>> call, Response<List<QTable>> response) {
                viewModel.gettingTablesUnsuccessful();
                try {
                    Log.e("JPurple", TAG+"::responseUnsuccessful() called with:  reason = [" + response.errorBody().string() + "]");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        RequestManager.getInstance().addTempCall(new CallAndCallback(loadTables, onTablesLoaded));
    }

    public void setDate(@NonNull String date) {
        rewriteFilter(date);
    }

    private void rewriteFilter(String date) {

    }
}
