package cm.clear.qmerchant.remote;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import cm.clear.qmerchant.database.QDatabase;
import cm.clear.qmerchant.database.entities.ServerInfo;

public class ApiUtil {

    public static final String DUMMY_URL = "https://nolink.com/";

    private ApiUtil() { }

    public static final String DEFAULT_API_URL = "https://quesadilla-saarlouis.merchant-scalable.com/api/index.php/";
    private static final String API_URL = "https://quesadilla-saarlouis.merchant-scalable.com/api/index.php/";
    private static final String API_KEY = "12825d52ce2a321c7a64ff8bde75ba135af2a26a";
    private static final String GOOGLE_URL = "https://google.com";
    public static final String DEFAULT_SORT_FIELD = "t.rowid";
    public static final String DEFAULT_SORT_FIELD_ALT = "t.id";
    public static final String DEFAULT_SORT_ORDER = "ASC";
    public static final String DEFAULT_LIMIT = "100";
    public static final String ALWAYS_TRUE_FILTER = "1=1";

    @NonNull
    public static BookingsRequestInterface getBookingService() {
        return cm.clear.qmerchant.remote.RetrofitClient.getClient(getApiUrl()).create(BookingsRequestInterface.class);
    }

    @NonNull
    public static BookingsRequestInterface getPingService() {
        return cm.clear.qmerchant.remote.RetrofitClient.getPingClient().create(BookingsRequestInterface.class);
    }

    @NonNull
    public static OrdersRequestInterface getOrdersService() {
        return cm.clear.qmerchant.remote.RetrofitClient.getClient(getApiUrl()).create(cm.clear.qmerchant.remote.OrdersRequestInterface.class);
    }

    @NonNull
    public static CustomersRequestInterface getCustomersService() {
        return cm.clear.qmerchant.remote.RetrofitClient.getClient(getApiUrl()).create(cm.clear.qmerchant.remote.CustomersRequestInterface.class);
    }

    @NonNull
    public static SetupRequestInterface getSetupService() {
        return cm.clear.qmerchant.remote.RetrofitClient.getClient(getApiUrl()).create(cm.clear.qmerchant.remote.SetupRequestInterface.class);
    }

    @NonNull
    public static CouponRequestInterface getCouponService() {
        return cm.clear.qmerchant.remote.RetrofitClient.getClient(getApiUrl()).create(cm.clear.qmerchant.remote.CouponRequestInterface.class);
    }

    @NonNull
    public static CategoryRequestInterface getCategoryService() {
        return cm.clear.qmerchant.remote.RetrofitClient.getClient(getApiUrl()).create(cm.clear.qmerchant.remote.CategoryRequestInterface.class);
    }

    @NonNull
    public static String getApiUrl() {
//        Log.d("J-Purple", "getApiUrl() called");
        ServerInfo serverInfo;
        try {
            serverInfo = QDatabase.getInstance().ServerInfoDao().getServerInfo();
        } catch (IllegalArgumentException e) {
            Log.e("FAKE-JPurple", ApiUtil.class.getName()+"->getApiUrl: "+e.getMessage() );
            e.printStackTrace();
            return API_URL;
        }
        if (serverInfo == null) {
            //Log.d("FAKE-JPurple", ApiUtil.class.getName()+"->getApiUrl: 1" );
            return API_URL;
        }
        if (TextUtils.isEmpty(serverInfo.getServerUrl())) {
            Log.d("FAKE-JPurple", ApiUtil.class.getName()+"->getApiUrl: 2" );
            return API_URL;
        }

        Log.d("FAKE-JPurple", ApiUtil.class.getName()+"->getApiUrl: 3=="+serverInfo.getServerUrl() );
        return serverInfo.getServerUrl();
    }

    @NonNull
    public static String getApiKey() {
//        Log.d("J-Purple", "getApiKey() called");
        ServerInfo serverInfo;
        try {
            serverInfo = QDatabase.getInstance().ServerInfoDao().getServerInfo();
        } catch (IllegalArgumentException e) {
            Log.e("FAKE-JPurple", ApiUtil.class.getName()+"->getApiKey: "+e.getMessage() );
            //e.printStackTrace();
            return API_KEY;
        }
        if (serverInfo == null) {
            return API_KEY;
        }
        if (TextUtils.isEmpty(serverInfo.getServerUrl())) {
            return API_KEY;
        }
        return serverInfo.getApiKey();
    }
}
