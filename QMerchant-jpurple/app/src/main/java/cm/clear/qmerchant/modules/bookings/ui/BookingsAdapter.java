package cm.clear.qmerchant.modules.bookings.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cm.clear.qmerchant.R;
import cm.clear.qmerchant.modules.bookings.data.BookingItemUpdater;
import cm.clear.qmerchant.modules.bookings.data.BookingStateSafe;
import cm.clear.qmerchant.modules.bookings.data.BookingMessenger;
import cm.clear.qmerchant.models.booking.bookingStates.BookingState;
import cm.clear.qmerchant.common.core.TmsConverter;
import cm.clear.qmerchant.common.interfaces.AdapterManager;
import cm.clear.qmerchant.models.booking.BookingActions;
import cm.clear.qmerchant.common.listviewmanagement.IAdapterItemChangeListener;
import cm.clear.qmerchant.databinding.EditThirdpartyDialogBinding;
import cm.clear.qmerchant.databinding.ItemBookingListBinding;
import cm.clear.qmerchant.models.booking.Booking;
import cm.clear.qmerchant.models.Thirdparty;
import cm.clear.qmerchant.remote.ApiUtil;
import cm.clear.qmerchant.requestsHandler.QCallback;
import retrofit2.Call;
import retrofit2.Response;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolder> implements IAdapterItemChangeListener {
    private List<Booking> sortedBookingList;
    private BookingItemUpdater bookingItemUpdater;
    private AdapterManager messageMan;
    private long tms = 0;
    private String percentage = "0";

    public BookingsAdapter(List<Booking> bookingList) {
        this.sortedBookingList = bookingList;
        messageMan = BookingMessenger.getSharedInstance();
        this.bookingItemUpdater = new BookingItemUpdater(sortedBookingList, this);
    }

    @NonNull
    @Override
    public BookingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookingListBinding binding =
                ItemBookingListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        if (binding.getRoot().getContext().getResources().getBoolean(R.bool.isTablet)) {
            //binding.getRoot().getLayoutParams().height = parent.getHeight() / 6;
        }
//        binding.getRoot().getLayoutParams().height = parent.getHeight() / 6;
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingsAdapter.ViewHolder holder, int position) {

        Booking selectedBooking = sortedBookingList.get(position);
        BookingState bookingState = bookingItemUpdater.getObjectState(selectedBooking, position);
        if (bookingState.getClient() != null) {
            holder.textName.setText(bookingState.getClient().getName());

            holder.customerId.setText(bookingState.getClient().getId());
            holder.customerPhone.setText(bookingState.getClient().getPhone());
            holder.bookingTables.setText(bookingState.getTablesListString());

        } else {
            holder.textName.setText(sortedBookingList.get(position).getNames());
        }
        holder.textRef.setText(sortedBookingList.get(position).getRef());
        holder.textDate.setText(TmsConverter.getDate(sortedBookingList.get(position).getDatep(), TmsConverter.FROM_SQL_JAVA));
        holder.textStartTime.setText(TmsConverter.getTime(sortedBookingList.get(position).getDatep(), TmsConverter.FROM_SQL_JAVA));
        holder.textEndTime.setText(TmsConverter.getTime(sortedBookingList.get(position).getDatep2(), TmsConverter.FROM_SQL_JAVA));

        holder.layoutRefContainerBackground.setBackgroundTintList(bookingState.getRefColor(holder.itemView.getContext()));

        holder.textTables.setText(bookingState.getOccupationTally());
        holder.bookingTables.setText(bookingState.getTablesListString());

        holder.textConfirmBooking.setText(bookingState.getConfirmText(holder.itemView.getContext()));
        holder.textAssignResource.setText(bookingState.getAssignText(holder.itemView.getContext()));
        holder.textRemoveResource.setText(bookingState.getCancelText(holder.itemView.getContext()));

        View.OnClickListener editBtnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditThirdpartyDialogBinding dialogBinding = EditThirdpartyDialogBinding.inflate(LayoutInflater.from(holder.itemView.getContext()));
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setView(dialogBinding.getRoot());
                AlertDialog dialog = builder.create();

                dialogBinding.name.setText(bookingState.getClient().getName());
                dialogBinding.phone.setText(bookingState.getClient().getPhone());
                dialogBinding.email.setText(bookingState.getClient().getEmail());

                View.OnClickListener cancelBtnListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                };

                View.OnClickListener updateBtnListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (bookingState.getClient() != null) {
                            Thirdparty thirdparty = bookingState.getClient();
                            thirdparty.setName(String.valueOf(dialogBinding.name.getText()));
                            thirdparty.setPhone(String.valueOf(dialogBinding.phone.getText()));
                            thirdparty.setEmail(String.valueOf(dialogBinding.email.getText()));
                            dialogBinding.progressBar.setVisibility(View.VISIBLE);
                            ApiUtil.getCustomersService().updateCustomer(thirdparty.getId(), thirdparty).enqueue(new QCallback<Thirdparty>() {
                                @Override
                                public void responseSuccessful(Call<Thirdparty> call, Response<Thirdparty> response) {
                                    bookingItemUpdater.register(selectedBooking);
                                    notifyItemChanged(holder.getAbsoluteAdapterPosition());
                                    dialogBinding.progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(holder.itemView.getContext(), holder.itemView.getContext().getString(R.string.updateSucessfull), Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                }

                                @Override
                                public void requestFailure(Call<Thirdparty> call, Throwable t) {
                                    dialogBinding.progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(holder.itemView.getContext(), holder.itemView.getContext().getString(R.string.updateUnSucessfull), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void responseUnsuccessful(Call<Thirdparty> call, Response<Thirdparty> response) {
                                    dialogBinding.progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(holder.itemView.getContext(), holder.itemView.getContext().getString(R.string.updateUnSucessfull), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                };


                dialogBinding.warningDialogCancelBt.setOnClickListener(cancelBtnListener);
                dialogBinding.warningDialogOkBt.setOnClickListener(updateBtnListener);
                dialog.show();
            }
        };

        holder.editLayout.setOnClickListener(editBtnClickListener);

        holder.layoutAssignResourceContainerBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedBooking.getPercentage().equalsIgnoreCase(Booking.PERCENT_100)) {
                    Bundle bookingDetails = new Bundle();
                    BookingStateSafe.getInstance().setStateObject(selectedBooking);
                    BookingStateSafe.getInstance().setState(bookingState);
                    bookingDetails.putSerializable("booking", selectedBooking);
                    bookingDetails.putSerializable("bookingDetails", bookingState);
                    messageMan.navigate(BookingsFragment.BOOKING_AND_STATE_KEY, bookingDetails, R.id.action_show_resources_fragment);
                }
            }
        });
        holder.layoutRemoveResourceContainerBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Booking booking = sortedBookingList.get(holder.getBindingAdapterPosition());
                BookingActions.endEvent(booking.getId()).observe((LifecycleOwner) v.getContext(), resourceResultState -> {
                    if (resourceResultState != null) {
                        if (resourceResultState.isSuccessful()) {
                            sortedBookingList.remove(selectedBooking);
                            notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                            messageMan.listItemChanged();
                        }
                    }
                });
            }
        });
        holder.layoutConfirmBookingContainerBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingState.getConfirmResult("").observe((LifecycleOwner) holder.itemView.getContext(), resourceResult -> {
                    if (resourceResult.isSuccessful()) {
                        BookingActions.confirmBooking(sortedBookingList.get(holder.getBindingAdapterPosition()).getId()).observe((LifecycleOwner) holder.itemView.getContext(), resourceResultState -> {
                            if (resourceResultState.isSuccessful()) {
                                sortedBookingList.remove(selectedBooking);
                                notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                                messageMan.listItemChanged();
                            }
                        });
                    } else {
                        Bundle bookingDetails = new Bundle();
                        BookingStateSafe.getInstance().setStateObject(selectedBooking);
                        BookingStateSafe.getInstance().setState(bookingState);
                        bookingDetails.putSerializable("booking", selectedBooking);
                        bookingDetails.putSerializable("bookingDetails", bookingState);
                        messageMan.navigate(BookingsFragment.BOOKING_AND_STATE_KEY, bookingDetails, R.id.action_show_booking_details_fragment);
                    }
                });
            }
        });
        holder.layoutAssignResourceContainerBackground.setBackgroundTintList(bookingState.getAssignButtonColor(holder.itemView.getContext()));
        holder.layoutRemoveResourceContainerBackground.setBackgroundTintList(bookingState.getCancelButtonColor(holder.itemView.getContext()));
        holder.layoutConfirmBookingContainerBackground.setBackgroundTintList(bookingState.getConfirmButtonColor(holder.itemView.getContext()));
        //holder.layoutTablesForeground.setBackgroundResource(bookingState.getTablesColor());
        holder.layoutAssignResourceContainerBackground.setVisibility(bookingState.getAssignButtonVisibility());
        holder.layoutRemoveResourceContainerBackground.setVisibility(bookingState.getCancelButtonVisibility());
        holder.layoutConfirmBookingContainerBackground.setVisibility(bookingState.getConfirmButtonVisibility());

        holder.layoutRefContainerBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bookingDetails = new Bundle();
                BookingStateSafe.getInstance().setStateObject(selectedBooking);
                BookingStateSafe.getInstance().setState(bookingState);
                bookingDetails.putSerializable("booking", selectedBooking);
                bookingDetails.putSerializable("bookingDetails", bookingState);
                messageMan.navigate(BookingsFragment.BOOKING_AND_STATE_KEY, bookingDetails, R.id.action_show_booking_details_fragment);

            }
        });

    }


    @Override
    public int getItemCount() {
        return sortedBookingList.size();
    }

    @Override
    public void onItemChanged(int position) {
        notifyItemChanged(position);
    }

    public void addAll(List<Booking> bookings) {
        for (Booking booking : bookings) {
            bookingItemUpdater.register(booking);
        }
        sortedBookingList.addAll(bookings);
        notifyItemRangeInserted(sortedBookingList.indexOf(bookings.get(0)), bookings.size());
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textRef;
        final TextView textName;
        final TextView textDate;
        final TextView textStartTime;
        final TextView textEndTime;
        final TextView textTables;
        final TextView textAssignResource;
        final TextView textConfirmBooking;
        final TextView textRemoveResource;

        final TextView bookingTables;
        final TextView customerId;
        final TextView customerPhone;

        final LinearLayout layoutRefContainerBackground;
        //final LinearLayout layoutTablesForeground;
        final LinearLayout layoutAssignResourceContainerBackground;
        final LinearLayout layoutRemoveResourceContainerBackground;
        final LinearLayout layoutConfirmBookingContainerBackground;
        final LinearLayout editLayout;

        public ViewHolder(@NonNull ItemBookingListBinding binding) {
            super(binding.getRoot());
            textRef = binding.textRef;
            textName = binding.textName;
            textDate = binding.textDate;
            textStartTime = binding.textStartTime;
            textEndTime = binding.textEndTime;
            textTables = binding.textTables;
            textAssignResource = binding.textAssignResource;
            textConfirmBooking = binding.textConfirmBooking;
            textRemoveResource = binding.textRemoveResource;
            layoutRefContainerBackground = binding.layoutRefContainerBackground;
            //layoutTablesForeground = binding.layoutTablesForeground;
            layoutAssignResourceContainerBackground = binding.layoutAssignResourceContainerBackground;
            layoutRemoveResourceContainerBackground = binding.layoutRemoveResourceContainerBackground;
            layoutConfirmBookingContainerBackground = binding.layoutConfirmBookingContainerBackground;
            editLayout = binding.editLayout;

            customerId = binding.customerId;
            customerPhone = binding.customerPhone;
            bookingTables = binding.bookingTables;

        }
    }

}
