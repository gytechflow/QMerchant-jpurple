package cm.clear.qmerchant.common.listprovider;

import androidx.annotation.NonNull;

/**
 * Interface for classes that listen filter changes
 */
public interface IFilterable {
    /**
     * method called when there is date change
     * @param date, new value of date as string
     */
    default void onDateChanged(@NonNull String date){};

    /**
     * method called when there is date change
     * @param date_tms, new value of date as long timestamp
     */
    default void onDateChanged(@NonNull Long date_tms){};

    /**
     * method called when there is a type changed, currently used
     * to filter lists whose items have different states or types
     * @param type, the new state or type
     */
    default void onTypeChanged(@NonNull String type){};
}
