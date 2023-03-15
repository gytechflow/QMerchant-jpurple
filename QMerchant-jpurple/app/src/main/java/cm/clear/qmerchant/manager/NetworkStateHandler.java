package cm.clear.qmerchant.manager;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cm.clear.qmerchant.requestsHandler.NetworkStateObserver;

public class NetworkStateHandler {
    private boolean connected = true;
    private List<NetworkStateObserver> networkStateObservers = new ArrayList<>();
    private static NetworkStateHandler INSTANCE;

    public static NetworkStateHandler getInstance(){
        if (INSTANCE==null)
            INSTANCE = new NetworkStateHandler();

        return INSTANCE;
    }

    protected void changeNetworkStatus(boolean connectionStatus){
//        Log.e("J-Purple", "changeNetworkStatus: previous =["+this.connected+"] next = ["+connectionStatus+"]" );
        if (this.connected!=connectionStatus){
            this.connected = connectionStatus;
            notifyNetworkStateChanged();
        }
        this.connected = connectionStatus;
    }

    private void notifyNetworkStateChanged(){
        for (NetworkStateObserver stateObserver : networkStateObservers) {
            stateObserver.onStatusChanged(connected);
        }
    }

    protected int removeObserver(NetworkStateObserver observer){
        if (networkStateObservers.contains(observer)){
            if (networkStateObservers.remove(observer))
                return 0;
            else return 1;
        } else {
            return -1;
        }
    }

    protected void addObserver(NetworkStateObserver observer){
        networkStateObservers.add(observer);
    }

    public boolean isConnected() {
        return connected;
    }
}
