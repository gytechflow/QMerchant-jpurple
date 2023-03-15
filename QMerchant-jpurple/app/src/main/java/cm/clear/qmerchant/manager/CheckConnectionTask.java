package cm.clear.qmerchant.manager;

import android.content.Context;

import java.util.TimerTask;

public class CheckConnectionTask extends TimerTask {
    private Context context;

    public CheckConnectionTask(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        NetworkStateHandler.getInstance().changeNetworkStatus(NetworkUtil.isWebReachable(context));
    }
}
