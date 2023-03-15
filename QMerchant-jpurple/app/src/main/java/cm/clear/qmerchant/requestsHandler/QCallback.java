package cm.clear.qmerchant.requestsHandler;

import java.util.Calendar;

import cm.clear.qmerchant.manager.NetworkStateHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class QCallback<T> implements Callback<T> {
    private static final String TAG = "QCallback<T>";
    private int call_count = 0;
    public static final int REPEAT_COUNT_MAX = 0;
    public static final int REPEAT_COUNT_ZERO = 0;
    private String identifier;

    public QCallback() {
        identifier = Calendar.getInstance().getTime().getTime()+""+this.hashCode();
//        this.owner = owner;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (!response.isSuccessful()){
            if (response.errorBody() != null) {
//                    Log.e("J-Purple", "QCallback:"+call_count+":onResponse: "+ response.errorBody().string());
//                    Log.e("J-Purple", "QCallback:"+call_count+":onResponse: code "+ response.code());
//                    Log.e("J-Purple", "QCallback:"+call_count+":onResponse: body "+ response.body().toString());
//                    Log.e("J-Purple", "QCallback:"+call_count+":onResponse: errorBody "+ response.errorBody().toString());
            }
            repeatCall(call, response);
        } else {
            responseSuccessful(call, response);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

        t.printStackTrace();
        repeatCall(call, t);
    }

    private void repeatCall(Call<T> call, Throwable t){
//        Log.i("J-Purple", "QCallback::onResponse: "+call.isCanceled());

        if (NetworkStateHandler.getInstance().isConnected() && !call.isCanceled()){
                call_count++;
                if (call_count<=1){
                    call.clone().enqueue(this);
                } else {
                    call_count = REPEAT_COUNT_MAX;
//                    Log.e("J-Purple", "QCallback:"+call_count+":onFailure: \n" );
                    //Log.e(TAG, "repeatCall: ", t);
                    requestFailure(call, t);
                }
            }
    }

    private void repeatCall(Call<T> call, Response<T> response){
//        Log.i("J-Purple", "QCallback::onResponse: "+call.isCanceled());

        if (NetworkStateHandler.getInstance().isConnected() && !call.isCanceled()){
                call_count++;
                if (call_count<=REPEAT_COUNT_MAX){
                    call.clone().enqueue(this);
                } else {
                    call_count = REPEAT_COUNT_ZERO;
//                    Log.e("J-Purple", "QCallback:"+call_count+":onResponse: raw "+ response.raw().toString());
                    //Log.e(TAG, "repeatCall: "+ response.raw().toString());
                    responseUnsuccessful(call, response);
                }
            }
    }

    public abstract void responseSuccessful(Call<T> call, Response<T> response);

    public abstract void requestFailure(Call<T> call, Throwable t);

    public abstract void responseUnsuccessful(Call<T> call, Response<T> response);

    public String getIdentifier(){
        return identifier;
    }
}
