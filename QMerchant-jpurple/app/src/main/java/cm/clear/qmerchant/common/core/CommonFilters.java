package cm.clear.qmerchant.common.core;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CommonFilters {

    @NonNull
    public static String fromTodayXX(@NonNull String dateAttribute){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return dateAttribute+" >= DATE '" + formatter.format(calendar.getTime())+"'";
    }

    @NonNull
    public static String fromToday(@NonNull String dateAttribute){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return dateAttribute+" between DATE '" + formatter.format(calendar.getTime())+"' and DATE '"+TmsConverter.getDateForQuery(TmsConverter.todayPlusFullTms(1),TmsConverter.NO_CONVERSION)+"'";
    }

    @NonNull
    public static String todayOnly(@NonNull String dateAttribute){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return dateAttribute+" between DATE '" + formatter.format(calendar.getTime())+"' and DATE '"+TmsConverter.getDateForQuery(TmsConverter.todayPlusFullTms(1),TmsConverter.NO_CONVERSION)+"'";
    }


    @NonNull
    public static String allForToday(@NonNull String dateAttribute, String selectedFormattedDate){
        String startDate = selectedFormattedDate+" 00:00";
        String endDate = selectedFormattedDate+" 23:59";

        //return dateAttribute+" between DATE '" +startDate+"' and DATE '"+endDate+"'";
        return dateAttribute+" between '" +startDate+"' and '"+endDate+"'";
    }
}
