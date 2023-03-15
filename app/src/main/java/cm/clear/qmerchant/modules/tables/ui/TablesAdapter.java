package cm.clear.qmerchant.modules.tables.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.common.interfaces.NavigationListener;
import cm.clear.qmerchant.common.listviewmanagement.IAdapterItemChangeListener;
import cm.clear.qmerchant.common.listviewmanagement.ListChangeObserver;
import cm.clear.qmerchant.databinding.TableItemBinding;
import cm.clear.qmerchant.databinding.TimeIntervalSelectionDialogBinding;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.models.booking.comparator.StartDateComparator;
import cm.clear.qmerchant.models.qtable.NameComparator;
import cm.clear.qmerchant.models.qtable.QTable;
import cm.clear.qmerchant.modules.tables.InstantOccupation;
import cm.clear.qmerchant.modules.tables.data.DayPeriodListener;
import cm.clear.qmerchant.modules.tables.data.TableItemUpdater;
import cm.clear.qmerchant.modules.tables.data.TableState;
import cm.clear.qmerchant.modules.tables.ui.period_grid.GridPeriod;
import cm.clear.qmerchant.modules.tables.ui.period_grid.PeriodGridAdapter;
import cm.clear.qmerchant.modules.tables.ui.table_events.TableEventsAdapter;

public class TablesAdapter extends RecyclerView.Adapter<TablesAdapter.ViewHolder> implements IAdapterItemChangeListener, DayPeriodListener {
    protected final TableItemUpdater tableItemUpdater;
    private final List<QTable> tables;
    private final NavigationListener navigationListener;
    private Comparator<Booking> comparator = new StartDateComparator();
    private int period = TablesViewModel.PERIOD_TIME_WHOLE_DAY;

    protected TablesAdapter(int period, @NonNull List<QTable> tables, @NonNull NavigationListener navigationListener, long timestamp) {
        this.period = period;
        this.navigationListener = navigationListener;
        Collections.sort(tables, new NameComparator());
        tableItemUpdater = new TableItemUpdater(tables, this);
        tableItemUpdater.changeDate(timestamp);
        this.tables = tables;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TableItemBinding binding = TableItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QTable table = tables.get(position);
        TableState tableState = (TableState) tableItemUpdater.getObjectState(table, position);
        holder.binding.setTable(table);
        holder.binding.setState(tableState);

        float view_height = holder.itemView.getContext().getResources().getDimension(R.dimen.single_table_size);
        PeriodGridAdapter periodGridAdapter = new PeriodGridAdapter(period, view_height);
        holder.binding.setPeriodAdapter(periodGridAdapter);
        //holder.tableRef.setText(table.getRef());
        //holder.bookingCount.setText(String.valueOf(tableState.getAssignedEvents().size()));
//        Log.d("J-Purple", "onBindViewHolder: "+tableState.getAssignedEvents().size());

        //holder.recyclerview.setAdapter(new TableEventsAdapter(tableState.getAssignedEvents()));
        if (!tableState.getAssignedEvents().isEmpty()) {
            Collections.sort(tableState.getAssignedEvents(), comparator);
        }

        GridPeriod gridPeriod = periodGridAdapter.getGridPeriodObject();
        ListChangeObserver listChangeObserver = new ListChangeObserver() {
            @Override
            public void onListItemChange() {
                tableItemUpdater.loadExtras(table);
            }

            @Override
            public void onListBottomReached() {

            }
        };
        holder.recyclerview.setAdapter(new TableEventsAdapter(tableState.getAssignedEvents(), period, gridPeriod, listChangeObserver));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //viewModel.setEvents(tableState.getAssignedEvents());
                Bundle bundle = new Bundle();
                bundle.putInt("table_id", table.getId());
                bundle.putString("table_name", table.getRef());
                navigationListener.onNavigate(view, bundle, R.id.action_show_extended_view);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Calendar default_tms = Calendar.getInstance();
                Calendar start_tms = Calendar.getInstance();
                Calendar end_tms = Calendar.getInstance();
                TimeIntervalSelectionDialogBinding dialogBinding = TimeIntervalSelectionDialogBinding.inflate(LayoutInflater.from(view.getContext()));
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(dialogBinding.getRoot());
                AlertDialog dialog = builder.create();


                dialogBinding.startTimeButton.setText(TmsConverter.getTime(start_tms.getTime().getTime(), TmsConverter.NO_CONVERSION));
                dialogBinding.endTimeButton.setText(TmsConverter.getTimePlus(end_tms.getTime().getTime(), TmsConverter.NO_CONVERSION, 1));
                dialogBinding.endTimeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        final Dialog dialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Calendar tempCalendar = new GregorianCalendar();
                                end_tms.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                end_tms.set(Calendar.MINUTE, minute);
                                tempCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                tempCalendar.set(Calendar.MINUTE, minute);
//                        Timestamp tms = new Timestamp(tempCalendar.getTime().getTime());
                                long timestamp = tempCalendar.getTime().getTime();
//                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
//                        rootView.selectStartDateBtnHead.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//                        binding.timeButton.setText(formatter.format(tms));
                                dialogBinding.endTimeButton.setText(TmsConverter.getTime(timestamp, TmsConverter.NO_CONVERSION));
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                        dialog.show();
                    }
                });

                dialogBinding.startTimeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        final Dialog dialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Calendar tempCalendar = new GregorianCalendar();
                                start_tms.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                start_tms.set(Calendar.MINUTE, minute);
                                tempCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                tempCalendar.set(Calendar.MINUTE, minute);
//                        Timestamp tms = new Timestamp(tempCalendar.getTime().getTime());
                                long timestamp = tempCalendar.getTime().getTime();
//                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
//                        rootView.selectStartDateBtnHead.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//                        binding.timeButton.setText(formatter.format(tms));
                                dialogBinding.startTimeButton.setText(TmsConverter.getTime(timestamp, TmsConverter.NO_CONVERSION));
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                        dialog.show();
                    }
                });

                dialogBinding.warningDialogCancelBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialogBinding.warningDialogOkBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (end_tms.getTime().getTime() == default_tms.getTime().getTime()) {
                            end_tms.set(Calendar.HOUR_OF_DAY, start_tms.get(Calendar.HOUR_OF_DAY) + 1);
                        }
                        dialogBinding.progressBar.setVisibility(View.VISIBLE);
                        InstantOccupation.createInstantOccupation(view.getContext(), table.getId(), start_tms.getTimeInMillis(), end_tms.getTimeInMillis()).observe((LifecycleOwner) view.getContext(), integer -> {
                            if (integer != null) {
                                if (integer > 0) {
                                    dialog.cancel();
                                    tableItemUpdater.loadExtras(table);
                                } else {
                                    Toast.makeText(view.getContext(), "Failure!", Toast.LENGTH_SHORT).show();
                                    //binding.progressBarCreateMember.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    }
                });
                dialog.show();
                return false;
            }
        });

    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        /*for (Booking assignedEvent : tableState.getAssignedEvents()) {
            //assignToView(holder.bookingsLayout, TableSubItemBinding.inflate(LayoutInflater.from(holder.itemView.getContext())), assignedEvent, position);
        }*/
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

    @Override
    public void onItemChanged(int position) {
//        Log.d("J-Purple", "onItemChanged() called with: position = [" + position + "]");
        notifyItemChanged(position);
//        notifyItemMoved(position, tableItemUpdater.getNewItemPosition(position));
    }

    public void setDate(long l) {
        tableItemUpdater.changeDate(l);
    }

    public void sortEvents(@NonNull Comparator comparator) {
        this.comparator = comparator;
        for (int i = 0; i < tables.size(); i++) {
            notifyItemChanged(i);
        }
    }

    @Override
    public void onDatePeriodChanged(int period) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tableRef;
        TextView bookingCount;
        RecyclerView recyclerview;
        TableItemBinding binding;


        public ViewHolder(@NonNull TableItemBinding binding) {
            super(binding.getRoot());
            tableRef = binding.tableRef;
            bookingCount = binding.bookingCount;
            recyclerview = binding.recyclerview;
            this.binding = binding;
        }
    }

}
