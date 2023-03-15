package cm.clear.qmerchant.common;

import androidx.annotation.NonNull;

import java.util.List;

public interface IsPagedList<T> {
    default boolean isNewPage(){
        return false;
    }

    @NonNull
    List<T> getList();
}
