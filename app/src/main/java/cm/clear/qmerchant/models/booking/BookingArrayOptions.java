package cm.clear.qmerchant.models.booking;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingArrayOptions {
    @SerializedName("options_supp_status")
    @Expose
    private String supp_status = "1";

    @NonNull
    public String getSupp_status() {
        if (TextUtils.isEmpty(supp_status))
            supp_status = "1";
        return supp_status;
    }

    public void setSupp_status(@Nullable String supp_status) {
        this.supp_status = supp_status;
    }
}
