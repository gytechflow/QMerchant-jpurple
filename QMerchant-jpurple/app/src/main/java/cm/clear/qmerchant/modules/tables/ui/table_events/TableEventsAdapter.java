package cm.clear.qmerchant.modules.tables.ui.table_events;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.common.listviewmanagement.ListChangeObserver;
import cm.clear.qmerchant.databinding.TableSubItemBinding;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.models.booking.bookingStates.BookingState;
import cm.clear.qmerchant.modules.bookings.data.BookingItemUpdater;
import cm.clear.qmerchant.modules.tables.data.OverlappingTimeEvent;
import cm.clear.qmerchant.modules.tables.ui.TablesViewModel;
import cm.clear.qmerchant.modules.tables.ui.period_grid.GridCalculator;
import cm.clear.qmerchant.modules.tables.ui.period_grid.GridPeriod;

public class TableEventsAdapter extends RecyclerView.Adapter<TableEventsAdapter.ViewHolder> {
    private static final String TAG = TableEventsAdapter.class.getName();
    private List<OverlappingTimeEvent> bookings;
    Float prev = (float) 0;
    private final GridPeriod gridPeriod;
    protected final ListChangeObserver listChangeObserver;
    protected final GridCalculator gridCalculator;
    private final BookingItemUpdater bookingItemUpdater;


    public TableEventsAdapter(@NonNull List<Booking> bookings, int mode, @NonNull GridPeriod gridPeriod, @NonNull ListChangeObserver listChangeObserver) {
        //bookingItemUpdater = new BookingItemUpdater(this::notifyItemChanged);
        bookingItemUpdater = new BookingItemUpdater(position -> {});
        this.gridPeriod = gridPeriod;
        this.gridCalculator = new GridCalculator(gridPeriod);
        this.listChangeObserver = listChangeObserver;
        this.bookings = new ArrayList<>();
        for (Booking booking : bookings) {
            this.bookings.add(new OverlappingTimeEvent(mode, booking, gridPeriod));
        }
        this.bookings = new ArrayList<OverlappingTimeEvent>(filterBookingsByPeriod(this.bookings, mode));
        Collections.sort(this.bookings, (Comparator<Booking>) (item1, item2) -> Long.compare(item1.getDatep(), item2.getDatep()));

    }

    private List<OverlappingTimeEvent> filterBookingsByPeriod(List<OverlappingTimeEvent> bookings, int mode) {
        List<OverlappingTimeEvent> filtered_list = new ArrayList<>();
        for (OverlappingTimeEvent booking : bookings) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(TmsConverter.FROM_SQL_JAVA * booking.getDatep());
            int start_time = calendar.get(Calendar.HOUR_OF_DAY);
            calendar.setTimeInMillis(TmsConverter.FROM_SQL_JAVA * booking.getDatep2());
            int end_time = calendar.get(Calendar.HOUR_OF_DAY);
            switch (mode){
                case TablesViewModel.PERIOD_TIME_WHOLE_DAY:{
                    filtered_list.add(booking);
                }break;
                case TablesViewModel.PERIOD_TIME_MORNING_TIME:{
                    if (start_time<=getMidTime())
                        filtered_list.add(booking);
                }break;
                case TablesViewModel.PERIOD_TIME_EVENING_TIME:{
                    if (end_time>getMidTime()){
                        filtered_list.add(booking);
                    }
                }break;
            }
        }

        return filtered_list;
    }

    private int getMidTime() {
        return gridPeriod.getMid_time();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TableSubItemBinding binding = TableSubItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OverlappingTimeEvent booking = bookings.get(position);
        bookingItemUpdater.register(booking);
        BookingState state = bookingItemUpdater.getObjectState(booking, position);
        holder.bind(booking, state);
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    private float getGridPeriod(){
        return gridPeriod.getHourSize();
    }

    private float getPeriod(){
        return gridPeriod.getPeriod();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView startTime;
        TextView endTime;
        TextView name;
        TableSubItemBinding binding;

        public ViewHolder(@NonNull TableSubItemBinding binding) {
            super(binding.getRoot());
            startTime = binding.startTime;
            endTime = binding.endTime;
            name = binding.name;
            this.binding = binding;
        }

        void bind(@NonNull OverlappingTimeEvent booking, @NonNull BookingState state){
            EventViewManager eventViewManager = new EventViewManager(booking, gridCalculator, state, listChangeObserver);
            binding.setViewManager(eventViewManager);
            /*startTime.setText(TmsConverter.getTime(booking.getDatep(), TmsConverter.FROM_SQL_JAVA));
            endTime.setText(TmsConverter.getTime(booking.getDatep2(), TmsConverter.FROM_SQL_JAVA));
            name.setText(booking.getNames());
            binding.capacity.setText(booking.getNbplace());
            float view_height = itemView.getContext().getResources().getDimension(R.dimen.single_table_size);
            float item_height = getHeight(booking);

            binding.setHeight(item_height);
            binding.setMarginTop((float) getMarginTop(booking, view_height, item_height));*/
            /*if (booking.getNames().equalsIgnoreCase("Instant Occupation"))
                binding.mainBg.setBackgroundResource(R.drawable.instant_notification_bg);
            else
                binding.mainBg.setBackgroundResource(R.drawable.white_secnd_color_margin_bottom);*/


            /*binding.setDialog(view -> {
                EventActionsDialogBinding dialogBinding = EventActionsDialogBinding.inflate(LayoutInflater.from(view.getContext()));
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(dialogBinding.getRoot());
                AlertDialog dialog = builder.create();
                EventAction.ActionCompleteCallBack completeCallBack = () -> {
                    if (dialog != null) {
                        listChangeObserver.onListItemChange();
                        dialog.dismiss();
                    }
                };
                dialogBinding.setViewManager(new EventActionsVM(booking, completeCallBack, view));
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                return false;
            });*/
        }
    }


    private int getStartMinutes() {
        return gridPeriod.getStartMinutes();
    }

    private int getEndMinutes() {
        return gridPeriod.getEndForPercentage();
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

    public float getMarginTop(@NonNull OverlappingTimeEvent booking, float view_height, float item_height) {
        float startTimePercentage = getPercentageFromTime(booking.getStartTimeForHeight() * TmsConverter.FROM_SQL_JAVA);
        float startMargin = view_height * startTimePercentage;
        float margin = startMargin - prev;
        prev = prev + margin + item_height;

        return margin;
    }

    protected float getHeight(@NonNull OverlappingTimeEvent booking){
        Calendar start = Calendar.getInstance();
        start.setTimeInMillis(( booking.getStartTimeForHeight())*TmsConverter.FROM_SQL_JAVA);
        Calendar end = Calendar.getInstance();
        end.setTimeInMillis(( booking.getEndTimeForHeight())*TmsConverter.FROM_SQL_JAVA);
        long diffInMillies = Math.abs(end.getTime().getTime() - start.getTime().getTime());
        long minutes = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return  (minutes/60f) * getGridPeriod();
    }



}
