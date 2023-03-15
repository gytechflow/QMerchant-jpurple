package cm.clear.qmerchant.modules.tables.ui.period_grid;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.List;

import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.models.booking.Booking;

public class GridPeriod {
    private final List<Booking> periods;
    private final int interval;
    private final int mid_time;
    private final float view_height;

    public GridPeriod(@NonNull List<Booking> periods, int interval, int mid_time, float view_height) {
        this.periods = periods;
        this.interval = interval;
        this.mid_time = mid_time;
        this.view_height = view_height;
    }

    public int getMid_time() {
        return mid_time;
    }

    public int getInterval() {
        return interval;
    }

    public float getHourSize() {
        return view_height / (float) (periods.size() * interval);
    }

    ;

    public float getPeriod() {

        return ((periods.size() * interval));
    }

    ;

    public int getStartMinutes() {
        return getTimeInMinutesForBooking(periods.get(0));
    }

    public int getEndMinutesForPercentage() {
        int second_to_last_position = periods.size() - 1;
        return getTimeInMinutesForBooking(periods.get(second_to_last_position));
    }

    public int getEndForPercentage() {
        int second_to_last_position = periods.size() - 1;
        return getTimeInMinutesForBooking(periods.get(second_to_last_position));
    }

    private int getTimeInMinutesForBooking(@NonNull Booking booking) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((booking.getDatep() * TmsConverter.FROM_SQL_JAVA));
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return hour * 60 + minute;
    }

    public int getStartHour() {
        return getHourForBooking(periods.get(0));
    }

    public int getStartMinute() {
        return getMinutesForBooking(periods.get(0));
    }

    public int getEndHour() {
        int last_position = periods.size() - 1;
        return getHourForBooking(periods.get(last_position));
    }

    public int getEndMinute() {
        int last_position = periods.size() - 1;
        return getHourForBooking(periods.get(last_position));
    }

    private int getMinutesForBooking(@NonNull Booking booking) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((booking.getDatep() * TmsConverter.FROM_SQL_JAVA));
        return calendar.get(Calendar.MINUTE);
    }

    private int getHourForBooking(@NonNull Booking booking) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((booking.getDatep() * TmsConverter.FROM_SQL_JAVA));
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
}
