package cm.clear.qmerchant.common.core;

import androidx.annotation.NonNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class TmsConverter {
    public static final Long FROM_SQL_JAVA = 1000L;
    public static final Long FROM_JAVA_SQL = 1L/1000L;
    public static final Long NO_CONVERSION = 1L;

    private static Calendar today(){
        Calendar calendar = Calendar.getInstance();
//        calendar = removeTime(calendar);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    @NonNull
    public static Long getFromJavaToSql(@NonNull Long tms){
        return tms / FROM_SQL_JAVA;
    }

    @NonNull
    public static Long getFromSqlToJava(@NonNull Long tms){
        return tms * FROM_SQL_JAVA;
    }

    @NonNull
    public static String todayDateOnly(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Calendar calendar = today();
        return formatter.format(calendar.getTime());
    }

    @NonNull
    public static String todayDateOnlyWithFormat(SimpleDateFormat formatter){
        Calendar calendar = today();
        return formatter.format(calendar.getTime());
    }


    public static long todayDateOnlyTms(){
        Calendar calendar = today();
        return calendar.getTime().getTime();
    }

    @NonNull
    public static String todayFull(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }


    public static long todayFullTms(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime().getTime();
    }

    public static long todayPlusFullTms(int extra){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+extra);
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        return calendar.getTime().getTime();
    }

    @NonNull
    private static String getDate(long timeStamp, long tms_conversion_multiplier,String pattern,int field, int extra){
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(timeStamp * tms_conversion_multiplier);
        calendar.set(field, calendar.get(field)+extra);
        Timestamp sqlTimestamp = new Timestamp(calendar.getTime().getTime());
        SimpleDateFormat formatter = new SimpleDateFormat(""+pattern, Locale.getDefault());
        formatter.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        return formatter.format(sqlTimestamp);
    }

    @NonNull
    public static String getDate(long tms, long tms_conversion_multiplier) {
//        Timestamp tms1 = new Timestamp(tms * 1000L);
//            Timestamp tms2 = new Timestamp(end*1000L);
//            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
//        return formatter.format(tms1);
        return getDate(0+tms, 0+tms_conversion_multiplier, "dd-MM-yyyy",Calendar.DAY_OF_MONTH,0);
    }

    @NonNull
    public static String getDateForQuery(long tms, long tms_conversion_multiplier) {
//        Timestamp tms1 = new Timestamp(tms * 1000L);
//            Timestamp tms2 = new Timestamp(end*1000L);
//            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
//        return formatter.format(tms1);
        return getDate(0+tms, 0+tms_conversion_multiplier, "yyyy-MM-dd",Calendar.DAY_OF_MONTH,0);
    }

    /*public static long getSqlDate(@NonNull String date){
        return 0L;
    }*/

    @NonNull
    public static String getTime(long tms, long tms_conversion_multiplier) {
//        Timestamp tms1 = new Timestamp(tms * 1000L);
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
//        return formatter.format(tms1);
        return getDate(0+tms, 0+tms_conversion_multiplier, "HH:mm",Calendar.HOUR_OF_DAY,0);
    }

    @NonNull
    public static String getTimePlus(long tms, long tms_conversion_multiplier, int extra) {
//        Timestamp tms1 = new Timestamp(tms * 1000L);
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
//        return formatter.format(tms1);
        return getDate(0+tms, 0+tms_conversion_multiplier, "HH:mm",Calendar.HOUR_OF_DAY,extra);
    }

    @NonNull
    public static String getFullDate(long tms, long tms_conversion_multiplier) {
        Timestamp tms1 = new Timestamp(tms * 1000L);
//            Timestamp tms2 = new Timestamp(end*1000L);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        return getDate(0+tms, 0+tms_conversion_multiplier, "dd-MM-yyyy HH:mm:ss",Calendar.DAY_OF_MONTH,0);
    }

    public static String getDatePlus(long tms, long tms_conversion_multiplier,int extra) {
        return getDate(0+tms, 0+tms_conversion_multiplier, "dd-MM-yyyy",Calendar.DAY_OF_MONTH, extra);
    }

    public static String getDatePlusForQuery(long tms, long tms_conversion_multiplier,int extra) {
        return getDate(0+tms, 0+tms_conversion_multiplier, "yyyy-MM-dd",Calendar.DAY_OF_MONTH, extra);
    }
}
