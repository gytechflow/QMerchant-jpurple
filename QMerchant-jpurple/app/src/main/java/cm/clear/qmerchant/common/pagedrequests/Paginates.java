package cm.clear.qmerchant.common.pagedrequests;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

public interface Paginates {
    int DEFAULT_PAGE = 0;
    boolean IS_UPDATE = true;

    int getPage();

    void setPage(int page);

    default void onStart() {
        Log.d("J-Purple", "onStart() called");
        setGettingElements(true);
    }

    default void onSuccess(@NonNull List<?> returnedList, boolean refreshed) {
        Log.d("J-Purple", "onSuccess() called with: returnedList = [" + returnedList + "], refreshed = [" + refreshed + "]");
        if (getPage() > DEFAULT_PAGE) {
            updateList(returnedList);
        } else {
            reloadList(returnedList, refreshed);
        }
//        setPage(getPage()+1);
        onEnd();
    }

    default void onNotFound() {
        Log.d("J-Purple", "onNotFound() called");
        setPage(getPage()-1);
//        Toast.makeText(getContext(), getContext().getString(R.string.no_new_item_found), Toast.LENGTH_SHORT).show();
        onEnd();
    }


    default void onFailed() {
        setPage(getPage()-1);
        Log.d("J-Purple", "onFailed() called");
//        Toast.makeText(getContext(), getContext().getString(R.string.server_error), Toast.LENGTH_SHORT).show();
        onEnd();
    }

    default void onEnd() {
        Log.d("J-Purple", "onEnd() called");
        setGettingElements(false);
    }

    void updateList(@NonNull List<?> returnedList);

    void reloadList(@NonNull List<?> returnedList, boolean refreshed);

    void getNextListElements();

    boolean isGettingElements();

    void setGettingElements(boolean gettingElements);
}
