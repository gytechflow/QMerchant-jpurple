package cm.clear.qmerchant.modules.tables.ui.period_grid;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.modules.tables.data.OverlappingTimeEvent;

public class GridCalculator {
    private static final String TAG = GridCalculator.class.getName();
    private float prev = 0f;
    private final GridPeriod gridPeriod;
    @NonNull
    Map<OverlappingTimeEvent, Float> eventList = new HashMap<>();

    public GridCalculator(@NonNull GridPeriod gridPeriod) {
        this.gridPeriod = gridPeriod;
    }


    public float getPercentageFromTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int startPeriod = getStartMinutes();
        int endPeriod = getEndMinutes();

        float totalMinutes = hour * 60 + minute;
        float res = (totalMinutes - startPeriod) / (getPeriod() * 60);
        return res;
    }

    public float getHeight(@NonNull OverlappingTimeEvent booking){
        Calendar start = Calendar.getInstance();
        start.setTimeInMillis(( booking.getStartTimeForHeight())* TmsConverter.FROM_SQL_JAVA);
        Calendar end = Calendar.getInstance();
        end.setTimeInMillis(( booking.getEndTimeForHeight())*TmsConverter.FROM_SQL_JAVA);
        long diffInMillies = Math.abs(end.getTime().getTime() - start.getTime().getTime());
        long minutes = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return  (minutes/60f) * getGridPeriod();
    }

    public float getMarginTop(@NonNull OverlappingTimeEvent booking, float view_height, float item_height) {
        if (eventList.containsKey(booking))
            return eventList.get(booking);
        float startTimePercentage = getPercentageFromTime(booking.getStartTimeForHeight() * TmsConverter.FROM_SQL_JAVA);
        float startMargin = view_height * startTimePercentage;
        float margin = startMargin - prev;
        prev = prev + margin + item_height;
        eventList.put(booking, margin);
        return margin;
    }

    public float getPercentageFromTime2(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int startHour = getStartHour();
        int startMinute = getStartMinute();
        int endHour = getEndHour();
        int endMinute = getEndMinute();

        float totalMinutes = (endHour - startHour) * 60 + (endMinute - startMinute);
        float eventMinutes = (hour - startHour) * 60 + (minute - startMinute);

        if (eventMinutes <= 0) {
            return 0f;
        } else if (eventMinutes >= totalMinutes) {
            return 1f;
        } else {
            return eventMinutes / totalMinutes;
        }
    }

    private int getStartHour() {
        return gridPeriod.getStartHour();
    }

    private int getStartMinute() {
        return gridPeriod.getStartMinute();
    }

    private int getEndHour() {
        return gridPeriod.getEndHour();
    }

    private int getEndMinute() {
        return gridPeriod.getEndMinute();
    }


    private int getStartMinutes() {
        return gridPeriod.getStartMinutes();
    }

    private int getEndMinutes() {
        return gridPeriod.getEndForPercentage();
    }



    private float getGridPeriod(){
        return gridPeriod.getHourSize();
    }

    private float getPeriod(){
        return gridPeriod.getPeriod();
    }
}
