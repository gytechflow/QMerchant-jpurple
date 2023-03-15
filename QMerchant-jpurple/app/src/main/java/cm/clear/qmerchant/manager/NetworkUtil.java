package cm.clear.qmerchant.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import java.io.IOException;

import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.ResponseErrorHandler;
import retrofit2.Call;

public class NetworkUtil {

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isWebReachable(Context context){
        if (isNetworkAvailable(context)){
            Call<Object> ping = ApiUtil.getPingService().ping();
            try {
                ResponseErrorHandler<Object> handler = new ResponseErrorHandler<Object>(ping, (LifecycleOwner) context);
                if (handler.isSuccessful()){
//                    Log.i("J-purple", "NetworkUtil::isWebReachable: "+ handler.getResponse().body().toString());
                    return true;
                } else return false;
            } catch (IOException e) {
                Log.e("J-Purple", NetworkUtil.class.getName()+"::isWebReachable: "+e.getMessage() );;
                return false;
            }
        } else
            return false;
    }
}
