package cm.clear.qmerchant.modules.tables.ui.period_grid;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.databinding.TimeGridItemBinding;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.modules.tables.ui.TablesViewModel;

public class PeriodGridAdapter extends RecyclerView.Adapter<PeriodGridAdapter.ViewHolder> {
    private static final String TAG = PeriodGridAdapter.class.getName();
    Float prev = (float) 0L;
    public static final int TIME_INTERVAL = 2;
    private static final int MID_TIME = 15;

    private int periodMode;
    private GridPeriod gridPeriod;
    private List<Booking> periods;
    float item_height = 0f;
    float period;

    public PeriodGridAdapter(int periodMode, float view_height) {
        this.periodMode = periodMode;
        this.gridPeriod = new GridPeriod(getPeriods(), TIME_INTERVAL , getMidTime(), view_height);
    }

    public List<Booking> getPeriods() {
        if( periodMode == TablesViewModel.PERIOD_TIME_WHOLE_DAY ){
            periods = new ArrayList<Booking>(){{
                add(getBookingForTime(8));
                add(getBookingForTime(10));
                add(getBookingForTime(12));
                add(getBookingForTime(14));
                add(getBookingForTime(16));
                add(getBookingForTime(18));
                add(getBookingForTime(20));
                add(getBookingForTime(22));
            }};
        } else if (periodMode == TablesViewModel.PERIOD_TIME_MORNING_TIME) {
            periods = new ArrayList<Booking>(){{
                add(getBookingForTime(8));
                add(getBookingForTime(10));
                add(getBookingForTime(12));
                add(getBookingForTime(14));
                /*add(getBookingForTime(16));*/
            }};
        } else if (periodMode == TablesViewModel.PERIOD_TIME_EVENING_TIME) {
            periods = new ArrayList<Booking>(){{
                add(getBookingForTime(16));
                add(getBookingForTime(18));
                add(getBookingForTime(20));
                add(getBookingForTime(22));
            }};
        }

        Collections.sort(periods, new Comparator<Booking>() {
            @Override
            public int compare(Booking item1, Booking item2) {
                return Long.compare(item1.getDatep(), item2.getDatep());
            }
        });
        return periods;
    }

    @NonNull
    public GridPeriod getGridPeriodObject(){
        return gridPeriod;
    }

    private int getMidTime() {
        return MID_TIME;
    }

    public void setPeriods(@NonNull List<Booking> periods) {
        this.periods = periods;
    }

    @NonNull
    @Override
    public PeriodGridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TimeGridItemBinding binding = TimeGridItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new PeriodGridAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PeriodGridAdapter.ViewHolder holder, int position) {
        holder.bind(getPeriods().get(position));
    }

    @Override
    public int getItemCount() {
        return getPeriods().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TimeGridItemBinding binding;
        public ViewHolder(@NonNull TimeGridItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(@NonNull Booking booking){
            binding.setTime(TmsConverter.getTime(booking.getDatep(), TmsConverter.FROM_SQL_JAVA));
            float view_height = itemView.getContext().getResources().getDimension(R.dimen.single_table_size);
            float item_height = getHeight(booking);
            float margin_top = getMarginTop(booking, view_height, item_height);
            binding.setMarginTop(margin_top);
            binding.setHeight(item_height);
        }
    }

    @NonNull
    protected Booking getBookingForTime(int hours){
        return getBookingForTime(hours, 0);
    }

    @NonNull
    protected Booking getBookingForTime(int hours,int minutes){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        Booking booking = new Booking();
        booking.setDatep(TmsConverter.getFromJavaToSql(calendar.getTimeInMillis()));
        calendar.set(Calendar.HOUR_OF_DAY, hours+2);
        booking.setDatep2(TmsConverter.getFromJavaToSql(calendar.getTimeInMillis()));
        return booking;
    }

    public float getItemHeight(float view_height) {
        if (this.item_height==0f)
            this.item_height = view_height/getItemCount();
        return this.item_height;
    }
    public float getMarginTop(@NonNull Booking booking, float view_height, float item_height) {
        float startTimePercentage = getPercentageFromTime(booking.getDatep() * TmsConverter.FROM_SQL_JAVA);
        float startMargin = view_height * startTimePercentage;
        float margin = startMargin - prev /*- (prev==0f?0f:6.82f)*/;
        prev = prev + margin + item_height;

        return margin;
    }

    public float getPercentageFromTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int startPeriod = getStartMinutes();
        int endPeriod = getEndMinutes();

        float totalMinutes = hour * 60 + minute;
        /*if (totalMinutes <= startPeriod) {
            return 0f;
        }*/
        float res = (totalMinutes - startPeriod) / (getPeriod() * 60);
        return res;
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

        /*if (eventMinutes <= 0) {
            return 0f;
        } else if (eventMinutes >= totalMinutes) {
            return 1f;
        } else {
            return eventMinutes / totalMinutes;
        }*/
        return eventMinutes / totalMinutes;
    }

    protected float getHeight(@NonNull Booking booking){
        Calendar start = Calendar.getInstance();
        start.setTimeInMillis(( booking.getDatep())*TmsConverter.FROM_SQL_JAVA);
        Calendar end = Calendar.getInstance();
        end.setTimeInMillis(( booking.getDatep2())*TmsConverter.FROM_SQL_JAVA);
        long diffInMillies = Math.abs(end.getTime().getTime() - start.getTime().getTime());
        long minutes = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return  (minutes/60f) * getGridPeriod();
    }

    private float getGridPeriod(){
        return gridPeriod.getHourSize();
    }

    private float getPeriod(){
        return gridPeriod.getPeriod();
    }

    private int getStartMinutes() {
        return gridPeriod.getStartMinutes();
    }

    private int getEndMinutes() {
        return gridPeriod.getEndForPercentage();
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
}
