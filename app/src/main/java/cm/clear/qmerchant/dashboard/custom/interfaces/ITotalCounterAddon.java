package cm.clear.qmerchant.dashboard.custom.interfaces;

import cm.clear.qmerchant.interfaces.ResponseListener;

/**
 * created on: 17/01/22
 * by : J-Purple
 *
 * a possible addition to the {@link ISimpleDashboardCounter} that implements an additional method
 */
public interface ITotalCounterAddon{
    /**
     * used to get the total number of events for a category
     * @return : the total number of events
     */
    int getTotalCount();


}
