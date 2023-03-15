package cm.clear.qmerchant.dashboard.custom.interfaces;

import cm.clear.qmerchant.interfaces.ResponseListener;

/**
 * created on: 17/01/22
 * by : J-Purple
 * custom interface for the dashboard view to generify getting the numbers we need
 */
public interface ISimpleDashboardCounter extends ResponseListener {
    /**
     * used to get a count, intended for the number of new events
     * @return : the number of new events for a category
     */
    int getCount();
}
