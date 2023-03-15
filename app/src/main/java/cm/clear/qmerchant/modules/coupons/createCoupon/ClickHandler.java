package cm.clear.qmerchant.modules.coupons.createCoupon;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ClickHandler {
    private static final String TAG = "create/ClickHandler";
    private final CreateViewModel owner;

    ClickHandler(CreateViewModel owner) {
        this.owner = owner;
    }

    public void createCoupon(){
        owner.checkCouponInfo();
    }

    public void setExpiryDate(@NonNull View view){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        final Dialog dialog = new DatePickerDialog(view.getContext(), android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar tempCalendar;
                tempCalendar = new GregorianCalendar(year, month, dayOfMonth);
                tempCalendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
                Timestamp tms = new Timestamp(tempCalendar.getTime().getTime());
                long timestamp = tempCalendar.getTime().getTime();
//                view.setText(TmsConverter.getDate(timestamp, TmsConverter.NO_CONVERSION));
                owner.setCouponExpiryTms(timestamp);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }
}
