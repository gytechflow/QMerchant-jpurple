package cm.clear.qmerchant.models.coupon;

import cm.clear.qmerchant.R;

public class CouponState {
    static public final int ALL = -1;
    static public final int USED = 1;
    static public final int UNUSED = 0;
    static public final int CANCELED = 2;
    public static int getStateColor(int state_code){
        switch(state_code){
            case USED: return R.color.red_transparent;
            case UNUSED: return R.color.blue_transparent;
            case CANCELED: return R.color.black_transparent;
            default: return R.color.booking_table_normal;
        }
    }

    public static int getUseStateColor(int state_code) {
        switch(state_code){
            case USED:
            case CANCELED:
                return R.color.assign_table0;
            default: return R.color.booking_table_selected;
        }
    }

    public static int getCancelStateColor(int state_code) {
        switch(state_code){
            case USED:
            case CANCELED:
                return R.color.assign_table0;
            default: return R.color.negative_red;
        }
    }
}
