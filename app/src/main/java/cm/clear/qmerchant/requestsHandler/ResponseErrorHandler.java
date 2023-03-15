package cm.clear.qmerchant.requestsHandler;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import java.io.IOException;

import cm.clear.qmerchant.manager.CheckConnectionTask;
import cm.clear.qmerchant.manager.NetworkStateHandler;
import retrofit2.Call;
import retrofit2.Response;

public class ResponseErrorHandler<T> {
    private Response<T> response;
    private Call<T> call;
    private boolean successful;
    private int call_counter = 0;

    private LifecycleOwner owner;

    public ResponseErrorHandler(Call<T> call, LifecycleOwner owner) throws IOException {
        this.call = call;
        this.owner = owner;
        handleResponse(call);
    }

    private Response<T> handleResponse(Call<T> call) throws IOException {
        this.response = call.isExecuted()?call.clone().execute():call.execute();
        if (response.isSuccessful()){
            successful = true;
            return response;
        } else {
            if (!NetworkStateHandler.getInstance().isConnected()){
                successful = false;
                return response;
            }
            try {
                Log.e("J-Purple", "ResponseErrorHandler:"+call_counter+":handleResponse: "+ response.errorBody().string());
            } catch (IOException e) {
                Log.e("J-purple", "ResponseErrorHandler:"+call_counter+":handleResponse: IOException" );
                e.printStackTrace();
            }
            return repeatExecute(call);
        }
    }

    public Response<T> getResponse() {
        return response;
    }

    public boolean isSuccessful() {
        return successful;
    }

    private Response<T> repeatExecute(Call<T> call) throws IOException {
        call_counter++;
        if (call_counter<=QCallback.REPEAT_COUNT_MAX)
            return handleResponse(this.call);
        else {
            call_counter = QCallback.REPEAT_COUNT_ZERO;
            successful = false;
            return response;
        }
    }
}
