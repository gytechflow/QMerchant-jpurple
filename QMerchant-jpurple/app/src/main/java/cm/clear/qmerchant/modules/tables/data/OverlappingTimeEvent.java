package cm.clear.qmerchant.modules.tables.data;

import androidx.annotation.NonNull;

import java.util.Calendar;

import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.modules.tables.ui.period_grid.GridPeriod;
import cm.clear.qmerchant.modules.tables.ui.TablesViewModel;

public class OverlappingTimeEvent extends Booking {
    private final GridPeriod gridPeriod;
    private int mode ;

    public OverlappingTimeEvent(int mode, @NonNull Booking booking, @NonNull GridPeriod gridPeriod) {
        super(booking);
        this.mode = mode;
        this.gridPeriod = gridPeriod;
        //initDuration();
    }

    private void initDuration() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(TmsConverter.FROM_SQL_JAVA * this.getDatep());
        int start_time = calendar.get(Calendar.HOUR_OF_DAY);
        calendar.setTimeInMillis(TmsConverter.FROM_SQL_JAVA * this.getDatep2());
        int end_time = calendar.get(Calendar.HOUR_OF_DAY);
        int mid_time = gridPeriod.getMid_time();
        switch (mode){
            case TablesViewModel.PERIOD_TIME_WHOLE_DAY:{
            }break;
            case TablesViewModel.PERIOD_TIME_MORNING_TIME:{
                if (start_time<=mid_time){
                    int hour = gridPeriod.getEndHour()+gridPeriod.getInterval();
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, 0);
                    this.setDatep2(TmsConverter.getFromJavaToSql(calendar.getTimeInMillis()));
                }
            }break;
            case TablesViewModel.PERIOD_TIME_EVENING_TIME:{
                if (end_time>mid_time){
                    int hour = gridPeriod.getStartHour();
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    this.setDatep(TmsConverter.getFromJavaToSql(calendar.getTimeInMillis()));
                }
            }break;
        }
    }

    @NonNull
    public Long getStartTimeForHeight(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(TmsConverter.FROM_SQL_JAVA * this.getDatep());
        int start_time = calendar.get(Calendar.HOUR_OF_DAY);
        calendar.setTimeInMillis(TmsConverter.FROM_SQL_JAVA * this.getDatep2());
        int end_time = calendar.get(Calendar.HOUR_OF_DAY);
        int mid_time = gridPeriod.getMid_time();
        if (mode==TablesViewModel.PERIOD_TIME_EVENING_TIME && start_time<=mid_time){
            int hour = gridPeriod.getStartHour();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, 0);
            return TmsConverter.getFromJavaToSql(calendar.getTimeInMillis());
        }
        return getDatep();
    }

    @NonNull
    public Long getEndTimeForHeight(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(TmsConverter.FROM_SQL_JAVA * this.getDatep());
        int start_time = calendar.get(Calendar.HOUR_OF_DAY);
        calendar.setTimeInMillis(TmsConverter.FROM_SQL_JAVA * this.getDatep2());
        int end_time = calendar.get(Calendar.HOUR_OF_DAY);
        int mid_time = gridPeriod.getMid_time();
        if (mode==TablesViewModel.PERIOD_TIME_MORNING_TIME && end_time>mid_time){
            int hour = gridPeriod.getEndHour()+gridPeriod.getInterval();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, 0);
            return TmsConverter.getFromJavaToSql(calendar.getTimeInMillis());
        }
        return getDatep2();
    }
}
